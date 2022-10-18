package com.lagou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.entity.*;
import com.lagou.mapper.*;
import com.lagou.remote.OrderRemoteService;
import com.lagou.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/15 15:39
 * @Description
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private LessonMapper lessonMapper;

    @Autowired
    private SectionMapper sectionMapper;

    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    @Autowired
    private OrderRemoteService orderRemoteService;

    /*
    @Override
    public List<Course> getAllCourse() {

        //将redis内存中的序列化的集合名称用String重新命名（增加可读性）
        RedisSerializer rs = new StringRedisSerializer();
        redisTemplate.setKeySerializer(rs);

        // 1.先去redis中查询
        List<Course> list = (List<Course>) redisTemplate.opsForValue().get("allCourses");
        System.out.println("***查询redis***");

        // 双层检测锁，加两层判断
        // 2.redis中没有，才会去MySQL查询
        if (list == null) {
            // 先排队
            synchronized (this) {
                // 再去redis中拿
                list = (List<Course>) redisTemplate.opsForValue().get("allCourses");
                if (list == null) {
                    //去数据库
                    System.out.println("===查询mysql===");
                    list = courseMapper.getAllCourse();
                    // 把从数据库查询的集合放在redis内存中(key,value,过期时间,分钟为单位)
                    redisTemplate.opsForValue().set("allCourses", list, 10, TimeUnit.MINUTES);
                }
            }
        }

        return list;
    }

     */


    @Override
    public List<CourseDTO> getAllCourse() {

        //将redis内存中的序列化的集合名称用String重新命名（增加可读性）
        RedisSerializer rs = new StringRedisSerializer();
        redisTemplate.setKeySerializer(rs);

        // 1.先去redis中查询
        // 课程的DTO集合
        List<CourseDTO> courseDTOS = (List<CourseDTO>) redisTemplate.opsForValue().get("allCourses");
        System.out.println("***查询redis***");

        // 双层检测锁，加两层判断
        // 2.redis中没有，才会去MySQL查询
        if (courseDTOS == null) {
            // 先排队
            synchronized (this) {
                // 再去redis中拿
                courseDTOS = (List<CourseDTO>) redisTemplate.opsForValue().get("allCourses");
                if (courseDTOS == null) {
                    //去数据库
                    System.out.println("===查询mysql===");
                    // 查询全部课程
                    List<Course> courses = getInitCourse(null);
                    courseDTOS = new ArrayList<>();
                    for (Course course : courses) {

                        CourseDTO dto = new CourseDTO();
                        // course将属性全部赋值给CourseDTO对象
                        BeanUtils.copyProperties(course,dto);
                        courseDTOS.add(dto);
                        // 设置老师
                        setTeacher(dto);
                        // 设置前两节课
                        setTop2Lesson(dto);

                    }

                    // 把从数据库查询的集合放在redis内存中(key,value,过期时间,分钟为单位)
                    redisTemplate.opsForValue().set("allCourses", courseDTOS, 10, TimeUnit.MINUTES);
                }
            }
        }



        return courseDTOS;
    }


    @Override
    public List<CourseDTO> getOrdersByUserId(Integer userId) {
        // 根据用户id获取已经购买的课程id集合
        List<Object> ids = orderRemoteService.getOrdersByUserId(userId);
        if (!ids.isEmpty()) {
            // 根据课程id集合，查询相应的课程信息集合返回
            return getMyCourses(ids);
        }
        return new ArrayList<>();

    }


    // 查询已购买的课程
    private List<CourseDTO> getMyCourses(List<Object> ids) {

        //将redis内存中的序列化的集合名称用String重新命名（增加可读性）
        RedisSerializer rs = new StringRedisSerializer();
        redisTemplate.setKeySerializer(rs);

        // 1.先去redis中查询
        // 课程的DTO集合
        List<CourseDTO> courseDTOS = (List<CourseDTO>) redisTemplate.opsForValue().get("myCourses");
        System.out.println("***查询redis***");

        // 双层检测锁，加两层判断
        // 2.redis中没有，才会去MySQL查询
        if (courseDTOS == null) {
            // 先排队
            synchronized (this) {
                // 再去redis中拿
                courseDTOS = (List<CourseDTO>) redisTemplate.opsForValue().get("myCourses");
                if (courseDTOS == null) {
                    //去数据库
                    System.out.println("===查询mysql===");
                    // 查询全部课程
                    List<Course> courses = getInitCourse(ids);
                    courseDTOS = new ArrayList<>();
                    for (Course course : courses) {

                        CourseDTO dto = new CourseDTO();
                        // course将属性全部赋值给CourseDTO对象
                        BeanUtils.copyProperties(course,dto);
                        courseDTOS.add(dto);
                        // 设置老师
                        setTeacher(dto);
                        // 设置前两节课
                        setTop2Lesson(dto);

                    }

                    // 把从数据库查询的集合放在redis内存中(key,value,过期时间,分钟为单位)
                    redisTemplate.opsForValue().set("myCourses", courseDTOS, 10, TimeUnit.MINUTES);
                }
            }
        }



        return courseDTOS;
    }


    // 初始化基本的全部课程
    private List<Course> getInitCourse(List<Object> ids){

        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();
        queryWrapper.eq("status",1);    // 已上架
        queryWrapper.eq("is_del",Boolean.FALSE);    // 未删除
        if (ids != null) {
            queryWrapper.in("id",ids);  // where id in(1,2,3)   根据课程id查询匹配的课程
        }
            queryWrapper.orderByDesc("sort_num");   // 排序
            return courseMapper.selectList(queryWrapper);

    }



    // 基本的老师查询
    private void setTeacher(CourseDTO dto){

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<Teacher>();
        queryWrapper.eq("course_id",dto.getId());    // 一个课程，一个老师
        queryWrapper.eq("is_del",Boolean.FALSE);    // 未删除
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        dto.setTeacher(teacher);
    }


    // 前两节课
    private void setTop2Lesson(CourseDTO dto){

        QueryWrapper<CourseLesson> queryWrapper = new QueryWrapper<CourseLesson>();
        queryWrapper.eq("course_id",dto.getId());    // 一个课程，一个老师
        queryWrapper.eq("is_del",Boolean.FALSE);    // 未删除
        queryWrapper.orderByAsc("section_id","order_num");  // 排序
        queryWrapper.last("limit 0," + 2);  // 只要前两条数据
        List<CourseLesson> lessons = lessonMapper.selectList(queryWrapper);
        dto.setLessonDTOS2(lessons);
    }




    @Override
    public CourseDTO getCourseById(Integer courseId) {
        // 根据课程id获取课程的基本信息
        Course course = courseMapper.selectById(courseId);
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course,courseDTO);
        // 关联老师
        setTeacher(courseDTO);
        // 关联章节
        List<SectionDTO> sectionDTOS = getCourseSection(courseDTO);
        courseDTO.setCourseSections(sectionDTOS);
        return courseDTO;
    }


    // 关联章节
    private List<SectionDTO> getCourseSection(CourseDTO courseDTO){

        QueryWrapper q = new QueryWrapper<>();
        q.eq("course_id", courseDTO.getId());// 一个课程，N章
        q.eq("is_del", Boolean.FALSE);// 未删除
        q.eq("status", 2);// 已发布
        q.orderByAsc("order_num"); //排序

        // 基本的章节集合
        List<CourseSection> list = sectionMapper.selectList(q);

        // 关联的章节集合
        List<SectionDTO> sectionDTOS = new ArrayList<>();
        for (CourseSection courseSection : list) {
            SectionDTO sectionDTO = new SectionDTO();
            BeanUtils.copyProperties(courseSection,sectionDTO);

            q.clear(); // 清除条件
            q.eq("section_id", sectionDTO.getId());// 已发布
            q.eq("is_del", Boolean.FALSE);// 未删除
            q.orderByDesc("order_num"); //排序
            // 某章节的全部小节（基本信息）
            List<CourseLesson> lessons = this.lessonMapper.selectList(q);
            // 某章节的全部小节（关联信息）
            List<CourseLessonDTO> lessonDTOS = new ArrayList<>();
            for (CourseLesson lesson : lessons) {
                CourseLessonDTO lessonDTO = new CourseLessonDTO();
                BeanUtils.copyProperties(lesson,lessonDTO);
                // 设置每节课对应的视频
                setMedia(lessonDTO);
                lessonDTOS.add(lessonDTO);
            }
            // 将章节关联所有小节
            sectionDTO.setCourseLessons(lessonDTOS);
            // 某个章节放入到章节集合
            sectionDTOS.add(sectionDTO);
        }

        return sectionDTOS;
    }


    // 设置每节课的视频
    private void setMedia(CourseLessonDTO dto){

        QueryWrapper<CourseMedia> queryWrapper = new QueryWrapper();
        queryWrapper.eq("lesson_id",dto.getId());    // 一节课，一个视频
        queryWrapper.eq("is_del",Boolean.FALSE);    // 未删除
        CourseMedia courseMedia = mediaMapper.selectOne(queryWrapper);

        dto.setCourseMedia(courseMedia);
    }

}

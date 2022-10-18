package com.lagou.controller;

import com.lagou.entity.CourseDTO;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/15 15:31
 * @Description
 */
@RestController
@RequestMapping("/course")
@CrossOrigin //跨域
public class CourseController {

    @Autowired
    private CourseService courseService;




    /**
     * 查询全部课程信息
     *
     * @return
     */
    @GetMapping("/getAllCourse")
    public List<CourseDTO> getAllCourse() {

        // 模拟多线程：创建一个容量20个的线程池
        /*ExecutorService es = Executors.newFixedThreadPool(20);
        // 模拟20个线程同时查询
        for (int i = 0; i < 20; i++) {
            es.submit(new Runnable() {
                @Override
                public void run() {
                    courseService.getAllCourse();
                }
            });
        }*/


        List<CourseDTO> list = courseService.getAllCourse();
        return list;
    }


    /**
     * 根据用户id查询已购买的全部课程
     * @param userId
     * @return
     */
    @GetMapping("/getCoursesByUserId/{userId}")
    public List<CourseDTO> getCourseByUserId(@PathVariable("userId") Integer userId ) {
        return courseService.getOrdersByUserId(userId);
    }


//    /**
//     * 根据用户id查询已购买的全部课程
//     * @param userId
//     * @return
//     */
//    @GetMapping("getCourseByUserId/{userId}")
//    public List<Course> getCourseByUserId(@PathVariable("userId") String userId ) {
//        List<Course> list = courseService.getCourseByUserId(userId);
//        return list;
//    }



    /**
     * 根据课程id查询该课程的详情
     * @param courseId
     * @return
     */
    @GetMapping("/getCourseById/{courseId}")
    public CourseDTO getCourseById(@PathVariable("courseId")Integer courseId) {
        return courseService.getCourseById(courseId);
    }
}
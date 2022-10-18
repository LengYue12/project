package com.lagou.course.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lagou.course.service.CourseService;
import com.lagou.entity.Course;
import mapper.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @BelongsProject lagou-edu
 * @Author lengy
 * @CreateTime 2022/8/6 16:25
 * @Description
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;
    @Override
    public List<Course> getAllCourse() {
        return courseDao.getAllCourse();
    }

    @Override
    public List<Course> getCourseByUserId(String userId) {
        return courseDao.getCourseByUserId(userId);
    }

    @Override
    public Course getCourseById(Integer courseId) {
        return courseDao.getCourseById(courseId);
    }
}

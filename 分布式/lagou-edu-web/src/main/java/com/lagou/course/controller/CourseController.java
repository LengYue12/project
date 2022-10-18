package com.lagou.course.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lagou.course.service.CourseService;
import com.lagou.entity.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @BelongsProject lagou-edu-web
 * @Author lengy
 * @CreateTime 2022/8/6 16:30
 * @Description
 */
@RestController
@RequestMapping("course")
public class CourseController {

    @Reference
    private CourseService courseService;

    @GetMapping("getAllCourse")
    public List<Course> getAllCourse(){
        return courseService.getAllCourse();
    }


    @GetMapping("getCourseByUserId/{userId}")
    public List<Course> getCourseByUserId(@PathVariable String userId){
        return courseService.getCourseByUserId(userId);
    }


    @GetMapping("getCourseById/{courseId}")
    public Course getCourseById(@PathVariable Integer courseId){
        return courseService.getCourseById(courseId);
    }
}

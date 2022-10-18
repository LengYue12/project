package com.lagou.course.service;

import com.lagou.entity.Course;

import java.util.List;

/**
 * @BelongsProject lagou-edu
 * @Author lengy
 * @CreateTime 2022/8/6 16:25
 * @Description
 */
public interface CourseService {

    /**
     * 查询全部课程信息
     * @return
     */
    List<Course> getAllCourse();

    /**
     * 查询某个用户已购买的全部课程
     * @param userId    用户编号
     * @return
     */
    List<Course> getCourseByUserId(String userId);

    /**
     * 查询某门课程的详细信息
     * @param courseId 课程编号
     * @return
     */
    Course getCourseById(Integer courseId);
}

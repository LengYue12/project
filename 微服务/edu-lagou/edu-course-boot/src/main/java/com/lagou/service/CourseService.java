package com.lagou.service;

import com.lagou.entity.CourseDTO;

import java.util.List;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/15 15:36
 * @Description
 */
public interface CourseService {
    /**
     * 查询全部课程信息
     * @return
     */
    List<CourseDTO> getAllCourse();

    /**
     * 查询已登录用户购买的全部课程信息
     * @return
     */
    List<CourseDTO> getOrdersByUserId(Integer userId);


    /**
     * 查询某门课程的详细信息
     * @param courseId 课程编号
     * @return
     */
    CourseDTO getCourseById(Integer courseId);

}
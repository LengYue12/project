package com.lagou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 课程章节表(CourseSection)实体类
 *
 * @author LengYue
 * @since 2022-08-06 13:24:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseSection implements Serializable {
    private static final long serialVersionUID = -49123006939597076L;

    private List<CourseLesson> courseLessons;   // 一章对应多个小节
    /**
     * id
     */
    private Object id;
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 章节名
     */
    private String sectionName;
    /**
     * 章节描述
     */
    private String description;
    /**
     * 记录创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Integer isDel;
    /**
     * 排序字段
     */
    private Integer orderNum;
    /**
     * 状态，0:隐藏；1：待更新；2：已发布
     */
    private Integer status;
}


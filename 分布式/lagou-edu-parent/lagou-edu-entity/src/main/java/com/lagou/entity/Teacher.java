package com.lagou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 讲师表(Teacher)实体类
 *
 * @author LengYue
 * @since 2022-08-06 13:24:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher implements Serializable {
    private static final long serialVersionUID = -20409154348084565L;
    /**
     * id
     */
    private Object id;
    /**
     * 课程ID
     */
    private Integer courseId;
    /**
     * 讲师姓名
     */
    private String teacherName;
    /**
     * 职务
     */
    private String position;
    /**
     * 讲师介绍
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
}


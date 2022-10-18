package com.lagou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 课节视频表(CourseMedia)实体类
 *
 * @author LengYue
 * @since 2022-08-06 13:50:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseMedia implements Serializable {
    private static final long serialVersionUID = 430447460621295305L;
    /**
     * 课程媒体主键ID
     */
    private Integer id;
    /**
     * 课程Id
     */
    private Integer courseId;
    /**
     * 章ID
     */
    private Integer sectionId;
    /**
     * 课时ID
     */
    private Integer lessonId;
    /**
     * 封面图URL
     */
    private String coverImageUrl;
    /**
     * 时长（06:02）
     */
    private String duration;
    /**
     * 媒体资源文件对应的EDK
     */
    private String fileEdk;
    /**
     * 文件大小MB
     */
    private String fileSize;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 媒体资源文件对应的DK
     */
    private String fileDk;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除，0未删除，1删除
     */
    private Integer isDel;
    /**
     * 时长，秒数（主要用于音频在H5控件中使用）
     */
    private Integer durationNum;
    /**
     * 媒体资源文件ID
     */
    private String fileId;
}


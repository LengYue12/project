package com.lagou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程留言点赞表(CourseCommentFavoriteRecord)实体类
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("course_comment_favorite_record")
public class CourseCommentFavoriteRecord implements Serializable {
    private static final long serialVersionUID = -16175132905618315L;

    /**
     * 用户评论点赞j记录ID
     */
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户评论ID
     */
    private Integer commentId;
    /**
     * 是否删除，0：未删除（已赞），1：已删除（取消赞状态）
     */
    private Integer isDel;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}


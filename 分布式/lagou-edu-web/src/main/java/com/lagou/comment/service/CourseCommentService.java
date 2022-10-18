package com.lagou.comment.service;

import com.lagou.entity.CourseComment;

import java.util.List;

/**
 * @BelongsProject lagou-edu-web
 * @Author lengy
 * @CreateTime 2022/8/6 22:48
 * @Description
 */
public interface CourseCommentService {

    /**
     * 保存留言
     * @param courseComment 留言内容对象
     * @return  受影响的行数
     */
    Integer saveComment(CourseComment courseComment);


    /**
     * 某个课程的全部留言（分页）
     * @param courseId  课程编号
     * @param offset    数据偏移
     * @param pageSize  每页条目数
     * @return          返回留言集合
     */
    List<CourseComment> getCommentsByCourseId(Integer courseId, Integer offset, Integer pageSize);


    /**
     * 点赞
     * @param commentId 评论编号
     * @param userId    用户编号
     * @return  0:保存失败，1:保存成功
     */
    Integer saveFavorite(Integer commentId,Integer userId);


    /**
     * 取消赞
     * @param commentId 评论编号
     * @param userId    用户编号
     * @return  0:保存失败，1:保存成功
     */
    Integer cancelFavorite(Integer commentId,Integer userId);
}

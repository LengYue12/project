package com.lagou.comment.service;

import com.lagou.entity.CourseComment;

import java.util.List;


/**
 * 课程留言表(CourseComment)表服务接口
 *
 * @author LengYue
 * @since 2022-08-06 22:14:19
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
    List<CourseComment> getCommentsByCourseId(Integer courseId,Integer offset,Integer pageSize);


    /**
     * 没有点过赞，保存点赞信息
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

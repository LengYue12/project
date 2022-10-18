package com.lagou.comment.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.lagou.comment.service.CourseCommentService;
import com.lagou.entity.CourseComment;
import mapper.CourseCommentDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 课程留言表(CourseComment)表服务实现类
 *
 * @author LengYue
 * @since 2022-08-06 22:14:20
 */
@Service    //  暴露服务
public class CourseCommentServiceImpl implements CourseCommentService {


    @Autowired
    private CourseCommentDao courseCommentDao;

    @Override
    public Integer saveComment(CourseComment courseComment) {
        return courseCommentDao.saveComment(courseComment);
    }

    @Override
    public List<CourseComment> getCommentsByCourseId(Integer courseId, Integer offset, Integer pageSize) {
        return courseCommentDao.getCommentsByCourseId(courseId,offset,pageSize);
    }



    /**
     * 点赞，先查看当前用户对这条留言是否点过赞，
     * 如果点过：修改is_del的状态，即可，取消赞
     * 如果没点，保存一条点赞的信息
     *
     *
     * 最终更新赞的数量
     * @param commentId 评论编号
     * @param userId    用户编号
     * @return
     */
    @Override
    public Integer saveFavorite(Integer commentId, Integer userId) {
        Integer i = courseCommentDao.existsFavorite(commentId, userId);
        int i1 = 0;
        int i2 = 0;
        if (i == 0) { // 没点过赞
            // 保存
            i1 = courseCommentDao.saveCommentFavorite(commentId,userId);
        } else {    // 点过赞
            i1 = courseCommentDao.updateCommentFavorite(0,commentId,userId);
        }
        i2 = courseCommentDao.updateLikeCount(1,commentId);

        if(i1 ==0 || i2 == 0){
            throw new RuntimeException("点赞失败！");
        }
        return commentId;
    }


    /**
     * 删除点赞的信息（is_del = 1）
     * 更新留言赞的数量 -1
     * @param commentId 评论编号
     * @param userId    用户编号
     * @return  0:失败，1：成功
     */
    @Override
    public Integer cancelFavorite(Integer commentId, Integer userId) {

        // 删除赞
        Integer i1 = courseCommentDao.updateCommentFavorite(1, commentId, userId);
        // 减赞的数量
        Integer i2 = courseCommentDao.updateLikeCount(-1, commentId);

        if(i1 ==0 || i2 == 0){
            throw new RuntimeException("取消赞失败！");
        }
        return i2;
    }
}

package com.lagou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.entity.CourseComment;
import com.lagou.entity.CourseCommentFavoriteRecord;
import com.lagou.mapper.CourseCommentDao;
import com.lagou.mapper.CourseCommentFavoriteRecordDao;
import com.lagou.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/17 14:41
 * @Description
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CourseCommentDao courseCommentDao;

    @Autowired
    private CourseCommentFavoriteRecordDao courseCommentFavoriteRecordDao;

    //保存留言
    public Integer saveComment(CourseComment comment) {
        return courseCommentDao.insert(comment);
    }

    // 查询留言
    public List<CourseComment> getCommentsByCourseId(Integer courseId, Integer offset, Integer pageSize) {
        return courseCommentDao.getCommentsByCourseId(courseId, offset, pageSize);
    }


    /**
     *点赞：
     * 先查看当前用户对这条留言是否点过赞，
     * 如果点过：修改is_del状态即可，取消赞
     * 如果没点过：保存一条点赞的信息
     *
     * 最终，更新赞的数量
     */
    public Integer saveFavorite(Integer commentId, Integer userId) {
        QueryWrapper<CourseCommentFavoriteRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id",commentId);
        queryWrapper.eq("user_id",userId);
        Integer i = courseCommentFavoriteRecordDao.selectCount(queryWrapper);


        int i1 = 0;
        int i2 = 0;
        if(i == 0){ //没点过赞
            // 保存赞信息
            CourseCommentFavoriteRecord favoriteRecord = new CourseCommentFavoriteRecord();
            favoriteRecord.setCommentId(commentId);
            favoriteRecord.setUserId(userId);
            favoriteRecord.setIsDel(0);
            favoriteRecord.setCreateTime(new Date());
            favoriteRecord.setUpdateTime(new Date());
            i1 = courseCommentFavoriteRecordDao.insert(favoriteRecord);
        }else{
            // 更新赞的状态
            CourseCommentFavoriteRecord favoriteRecord = new CourseCommentFavoriteRecord();
            favoriteRecord.setIsDel(0);

            QueryWrapper<CourseCommentFavoriteRecord> qw2 = new QueryWrapper<>();
            qw2.eq("comment_id", commentId); // where comment_id = #{cid}
            qw2.eq("user_id", userId); // and user_id = #{uid}
            i1 = courseCommentFavoriteRecordDao.update(favoriteRecord,qw2);
        }
        // 更新本条留言赞的数量
        i2 = courseCommentDao.updateLikeCount(1,commentId);

        if(i1 == 0 || i2 == 0){
            // 在项目中，使用抛异常的方式来回滚事务！
            throw  new RuntimeException("点赞失败！");
        }
        return commentId;
    }

    /** 取消赞
     * 删除点赞的信息（is_del = 1）
     * 更新留言赞的数量-1
     * @param commentId 留言编号
     * @param userId 用户编号
     * @return 0:失败，1：成功
     */
    public Integer cancelFavorite(Integer commentId, Integer userId) {
        //Integer i1 = courseCommentDao.updateFavoriteStatus(1, commentId, userId);

        // 赞实体
        CourseCommentFavoriteRecord favoriteRecord = new CourseCommentFavoriteRecord();
        favoriteRecord.setIsDel(1); // 表示该赞被取消

        // 条件
        QueryWrapper<CourseCommentFavoriteRecord> qw = new QueryWrapper<>();
        qw.eq("comment_id", commentId); // where comment_id = #{cid}
        qw.eq("user_id", userId); // and user_id = #{uid}

        // 删除点赞信息
        Integer i1 = courseCommentFavoriteRecordDao.update(favoriteRecord,qw);
        // 更新留言赞的数量
        Integer i2 = courseCommentDao.updateLikeCount(-1,commentId);

        if(i1 == 0 || i2 == 0){
            throw  new RuntimeException("取消赞失败！");
        }
        return i2;
    }
}

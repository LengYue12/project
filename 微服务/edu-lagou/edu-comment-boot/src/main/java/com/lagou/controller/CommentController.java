package com.lagou.controller;

import com.lagou.entity.CourseComment;
import com.lagou.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/17 14:36
 * @Description
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 保存留言
    @GetMapping("/saveCourseComment")
    public Object saveCourseComment(Integer courseId,Integer userId,String userName,String comment) throws UnsupportedEncodingException {
        System.out.println("昵称：" + userName);
        System.out.println("内容：" + comment);
        CourseComment courseComment = new CourseComment();
        courseComment.setCourseId(courseId); // 课程编号
        courseComment.setSectionId(0); // 章节编号 （预留字段，为项目的2.0版本保留）
        courseComment.setLessonId(0);// 小节编号（预留字段，为项目的2.0版本保留）
        courseComment.setUserId(userId); // 用户编号
        courseComment.setUserName(userName); // 用户昵称
        courseComment.setParentId(0); //没有父id（预留字段，为项目的2.0版本保留）
        courseComment.setComment(comment);// 留言内容
        courseComment.setType(0); // 0用户留言（预留字段，为项目的2.0版本保留）
        courseComment.setLastOperator(userId); //最后操作的用户编号
        Integer i = commentService.saveComment(courseComment);
        return i;
    }


    // 查询全部留言
    @GetMapping("/getCourseCommentList/{courseId}/{pageIndex}/{pageSize}")
    public List<CourseComment> getCommentsByCourseId(@PathVariable("courseId") Integer courseId, @PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize){
        int offset = (pageIndex-1)*pageSize;
        List<CourseComment> list = commentService.getCommentsByCourseId(courseId, offset, pageSize);
        System.out.println("获取第"+courseId+"门课程的留言：共计"+list.size()+"条");
        return list;
    }


    // 保存赞
    @GetMapping("/saveFavorite/{commentId}/{userId}")
    public Integer saveFavorite(@PathVariable("commentId") Integer commentId,@PathVariable("userId") Integer userId){
        return commentService.saveFavorite(commentId, userId);
    }

    // 取消赞
    @GetMapping("/cancelFavorite/{commentId}/{userId}")
    public Integer cancelFavorite(@PathVariable("commentId") Integer commentId,@PathVariable("userId") Integer userId){
        return commentService.cancelFavorite(commentId, userId);
    }
}
package com.lagou.comment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lagou.comment.service.CourseCommentService;
import com.lagou.entity.CourseComment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @BelongsProject lagou-edu-web
 * @Author lengy
 * @CreateTime 2022/8/6 22:47
 * @Description
 */
@RestController
@RequestMapping("course")
public class CourseCommentController {

    @Reference // 远程消费
    private CourseCommentService courseCommentService;


    @GetMapping("comment/saveCourseComment")
    public Object saveComment(Integer courseId,Integer userId,String userName,String comment) throws Exception{

        userName = new String(userName.getBytes("ISO-8859-1"),"UTF-8");
        comment = new String(comment.getBytes("ISO-8859-1"),"UTF-8");
        System.out.println("昵称：" + userName);

        CourseComment courseComment = new CourseComment();
        courseComment.setCourseId(courseId);   // 课程编号
        courseComment.setSectionId(0);  // 章节编号(预留字段)
        courseComment.setLessonId(0);   // 小节编号
        courseComment.setUserId(userId); // 用户编号
        courseComment.setUserName(userName); // 用户昵称
        courseComment.setParentId(0);   // 没有父id
        courseComment.setComment(comment);    // 留言内容
        courseComment.setType(0);   // 用户留言
        courseComment.setLastOperator(userId);   // 最后操作的用户编号
        Integer i = courseCommentService.saveComment(courseComment);
        System.out.println("i = " + i);
        return null;
    }



    @GetMapping("comment/getCourseCommentList/{courseId}/{pageIndex}/{pageSize}")
    public List<CourseComment> getCommentsByCourseId(@PathVariable("courseId") Integer courseId,@PathVariable("pageIndex") Integer pageIndex,@PathVariable("pageSize") Integer pageSize){
        int offset = (pageIndex-1)*pageSize;
        List<CourseComment> list = courseCommentService.getCommentsByCourseId(courseId, offset, pageSize);
        System.out.println("获取第"+courseId+"门课程的留言：共计"+list.size()+"条");
        return list;
    }


    @GetMapping("comment/saveFavorite/{commentId}/{userId}")
    public Integer saveFavorite(@PathVariable("commentId") Integer commentId,@PathVariable("userId") Integer userId){

       return courseCommentService.saveFavorite(commentId,userId);
    }


    @GetMapping("comment/cancelFavorite/{commentId}/{userId}")
    public Integer cancelFavorite(@PathVariable("commentId") Integer commentId,@PathVariable("userId") Integer userId){

        return courseCommentService.cancelFavorite(commentId,userId);
    }
}

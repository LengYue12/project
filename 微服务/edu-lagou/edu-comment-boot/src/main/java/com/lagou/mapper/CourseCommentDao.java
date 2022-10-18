package com.lagou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lagou.entity.CourseComment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程留言表(CourseComment)表数据库访问层
 *
 * @author LengYue
 * @since 2022-08-06 22:14:17
 */
@Repository
public interface CourseCommentDao extends BaseMapper<CourseComment> {


    /**
     * 某个课程的全部留言（分页）
     * @param courseId  课程编号
     * @param offset    数据偏移
     * @param pageSize  每页条目数
     * @return          返回留言集合
     */
    @Select({"SELECT * FROM course_comment \n" +
        "        WHERE is_del = 0\n" +
        "        AND course_id = #{courseId}\n" +
        "        ORDER BY is_top DESC , like_count DESC , create_time DESC\n" +
        "        LIMIT #{offset}, #{pageSize}"})
    @Results({
        @Result(column = "id",property = "id"),
        @Result(column = "id", property = "favoriteRecords", many = @Many(select = "com.lagou.mapper.CourseCommentFavoriteRecordDao.getFavorites"))
    })
    List<CourseComment> getCommentsByCourseId(@Param("courseId") Integer courseId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);


    /**
     * 更新点赞的数量
     * @param x +1的话，赞的数量增加，-1的话，赞的数量减少
     * @param commentId 某条留言的编号
     * @return 0:保存失败，1:保存成功
     */
    @Update({"update course_comment set like_count = like_count + #{x} where id = #{commentId}"})
    Integer updateLikeCount(@Param("x") Integer x, @Param("commentId") Integer commentId);
}

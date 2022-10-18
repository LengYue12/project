package com.lagou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lagou.entity.CourseCommentFavoriteRecord;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/17 15:03
 * @Description
 */
@Repository
public interface CourseCommentFavoriteRecordDao extends BaseMapper<CourseCommentFavoriteRecord> {

    // 查询当前留言的点赞情况  查询已赞的
    @Select({"select * from course_comment_favorite_record where comment_id = #{comment_id} and is_del=0"})
    List<CourseCommentFavoriteRecord> getFavorites(Integer comment_id);
}

package mapper;

import com.lagou.entity.CourseComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程留言表(CourseComment)表数据库访问层
 *
 * @author LengYue
 * @since 2022-08-06 22:14:17
 */
@Repository
public interface CourseCommentDao {


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
    List<CourseComment> getCommentsByCourseId(@Param("courseId")Integer courseId,@Param("offset")Integer offset,@Param("pageSize")Integer pageSize);


    /**
     * 查看某个用户的某条留言是否点过赞
     * @param commentId 评论编号
     * @param userId    用户编号
     * @return  0:没点过赞，1:点过赞
     */
    Integer existsFavorite(@Param("cId")Integer commentId,@Param("uId")Integer userId);



    /**
     * 没有点过赞，保存点赞信息
     * @param commentId 评论编号
     * @param userId    用户编号
     * @return  0:保存失败，1:保存成功
     */
    Integer saveCommentFavorite(@Param("cId")Integer commentId,@Param("uId")Integer userId);


    /**
     * 更新点赞信息的状态(将is_del=0,表示已赞)
     * @param status 状态，0：表示已赞，1：表示取消赞
     * @param commentId 评论编号
     * @param userId 用户编号
     * @return 0:保存失败，1:保存成功
     */
    Integer updateCommentFavorite(@Param("status")Integer status,@Param("cId")Integer commentId,@Param("uId")Integer userId);


    /**
     * 更新点赞的数量
     * @param x +1的话，赞的数量增加，-1的话，赞的数量减少
     * @param commentId 某条留言的编号
     * @return 0:保存失败，1:保存成功
     */
    Integer updateLikeCount(@Param("x")Integer x,@Param("commentId")Integer commentId);
}

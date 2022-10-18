package comment;

import com.lagou.comment.service.CourseCommentService;
import com.lagou.entity.CourseComment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @BelongsProject lagou-edu
 * @Author lengy
 * @CreateTime 2022/8/6 22:34
 * @Description
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-*.xml"})
public class TestComment {


    @Autowired
    private CourseCommentService courseCommentService;


    @Test
    public void save(){
        CourseComment courseComment = new CourseComment();
        courseComment.setCourseId(7);   // 课程编号
        courseComment.setSectionId(8);  // 章节编号
        courseComment.setLessonId(10);   // 小节编号
        courseComment.setUserId(100030011); // 用户编号
        courseComment.setUserName("往事如烟"); // 用户昵称
        courseComment.setParentId(0);   // 没有父id
        courseComment.setComment("精辟！");    // 留言内容
        courseComment.setType(0);   // 用户留言
        courseComment.setLastOperator(100030011);   // 最后操作的用户编号
        Integer i = courseCommentService.saveComment(courseComment);
        System.out.println("i = " + i);
    }


    @Test
    public void getCommentsByCourseId(){
        int pageIndex = 3;  // 页码（第几页）
        int pageSize = 20;
        List<CourseComment> list = courseCommentService.getCommentsByCourseId(1, (pageIndex-1)*pageSize, pageSize);
        for (int i = 0; i < list.size(); i++) {
            CourseComment courseComment = list.get(i);
            System.out.println((i+1) + "、楼【" + courseComment.getUserName() + " 】说：" + courseComment.getComment());
        }
    }



    @Test
    public void saveFavorite(){
        Integer i = courseCommentService.saveFavorite(1, 110);
        System.out.println("i = " + i);
    }


    @Test
    public void cancelFavorite(){
        Integer i = courseCommentService.cancelFavorite(1, 110);
        System.out.println("i = " + i);
    }
}

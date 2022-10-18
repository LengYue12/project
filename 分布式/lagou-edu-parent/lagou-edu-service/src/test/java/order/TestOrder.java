package order;

import com.lagou.order.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @BelongsProject lagou-edu
 * @Author lengy
 * @CreateTime 2022/8/6 19:04
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-*.xml"})
public class TestOrder {

    @Autowired
    private OrderService orderService;


    @Test
    public void saveOrder(){
//        String orderNo = UUID.randomUUID().toString();
//        String user_id = "100030011";
//        String course_id = "7";
//        String activity_course_id = "0";    // 0表示，本课程没活动
//        String source_type = "1";
//        orderService.saveOrder(orderNo,user_id,course_id,activity_course_id,source_type);
    }
}

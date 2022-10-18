package com.lagou.controller;


import com.lagou.entity.PayOrder;
import com.lagou.entity.PayOrderRecord;
import com.lagou.entity.SmsVo;
import com.lagou.entity.UserCourseOrder;
import com.lagou.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/saveOrder")
    public String saveOrder(String orderNo,String user_id,String course_id,String activity_course_id,String source_type,Double price) {
        // 保存支付订单
        orderService.saveOrder(orderNo,user_id,course_id,activity_course_id,source_type);
        // 保存订单的记录日志
        // 创建订单记录
        PayOrderRecord por = new PayOrderRecord();
        por.setOrder_no(orderNo);// 订单号
        por.setType("CREATE"); //操作类型：CREATE\|PAY\|REFUND...
        por.setPaid_amount(price);
        por.setFrom_status("0");
        por.setTo_status("1");
        por.setCreated_by("auto");
        por.setCreated_at(new Date());
        System.out.println("创建订单记录 = " + orderNo);
        orderService.saveOrderRecord(por);

        return orderNo;
    }


    @GetMapping("/updateOrder")
    public Integer updateOrder(String orderNo , Integer status, String user_id , String course_id, String course_name, Double price, HttpServletRequest request,String phone) {
        System.out.println("订单编号 = " + orderNo);
        System.out.println("状态编码 = " + status);
        Integer integer = orderService.updateOrder(orderNo, status);
        System.out.println("订单更新 = " + integer);

        if(integer == 1){
            // 创建支付订单信息
            PayOrder po = new PayOrder();
            po.setOrder_no(orderNo);// 订单号
            po.setUser_id(user_id);// 购买者
            po.setProduct_id(course_id); //课程（产品）编号
            po.setProduct_name(course_name); //课程（产品）名称
            po.setAmount(price);// 金额
            po.setCount(1); // 购买数量
            po.setCurrency("cny"); //货币类型：人民币
            po.setChannel("weChat");  //支付渠道：weChat-微信支付
            po.setStatus(2); // 支付成功
            po.setOrder_type(1);//类型 1-购买课程
            po.setSource(3); // 支付来源 1-app 2-h5 3-pc
            String ip = "";
            if (request != null) {
                ip = request.getHeader("X-FORWARDED-FOR");
                if (ip == null || "".equals(ip)) {
                    ip = request.getRemoteAddr();
                }
            }
            po.setClient_ip(ip); // 客户端ip
            po.setCreated_time(new Date());
            po.setUpdated_time(new Date());
            //支付订单信息
            orderService.saveOrderInfo(po);

            // 创建支付订单记录
            PayOrderRecord por = new PayOrderRecord();
            por.setOrder_no(orderNo);// 订单号
            por.setType("PAY"); //操作类型：CREATE\|PAY\|REFUND...
            por.setPaid_amount(price);
            por.setFrom_status("1");
            por.setTo_status("2");
            por.setCreated_by("auto");
            por.setCreated_at(new Date());

            //支付订单状态日志
            orderService.saveOrderRecord(por);


            // 发送短信成功的通知(消息的生产者)
            SmsVo smsVo = new SmsVo();
            smsVo.setPhone(phone);// 手机号码
            smsVo.setCourseName(course_name);
            rabbitTemplate.convertAndSend(queue,smsVo);
        }
        return integer;
    }


    @GetMapping("/deleteOrder")
    public Integer deleteOrder(String orderNo) {
        return orderService.deleteOrder(orderNo);
    }


    @GetMapping("/getOrdersByUserId/{userId}")
    public List<Object> getOrdersByUserId(@PathVariable("userId")Integer userId) {
        List<UserCourseOrder> okOrderCourseIds = orderService.getOrdersByUserId(userId);
        List<Object> ids = new ArrayList<>();
        for (UserCourseOrder okOrderCourseId : okOrderCourseIds) {
            ids.add(okOrderCourseId.getCourseId());
        }
        return ids;
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    // MQ的生产端
    @GetMapping("/sendMQ")
    public void sendMQ(){
        //String msg = "你好啊，MQ!";
        //rabbitTemplate.convertAndSend(queue,msg);
        SmsVo sv = new SmsVo();
        sv.setPhone("110");
        sv.setCourseName("java高薪课程");
        rabbitTemplate.convertAndSend(queue,sv);
        System.out.println("购买成功的消息已发送到RabbitMQ！");

    }



}

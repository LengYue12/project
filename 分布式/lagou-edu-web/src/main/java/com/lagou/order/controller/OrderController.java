package com.lagou.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lagou.entity.UserCourseOrder;
import com.lagou.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @BelongsProject lagou-edu-web
 * @Author lengy
 * @CreateTime 2022/8/6 19:47
 * @Description
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @GetMapping("saveOrder")
    public String saveOrder(String orderNo,String user_id,String course_id,String activity_course_id,String source_type) {
        //String orderNo = UUID.randomUUID().toString();
        orderService.saveOrder(orderNo,user_id,course_id,activity_course_id,source_type);
        return orderNo;
    }


    @GetMapping("updateOrder")
    public Integer updateOrder(String orderNo,Integer status) {
        System.out.println("订单编号；" + orderNo);
        System.out.println("状态编码；" + status);
        Integer integer = orderService.updateOrder(orderNo, status);
        System.out.println("订单更新 = " + integer);
        return integer;
    }


    @GetMapping("deleteOrder/{orderNo}")
    public Integer deleteOrder(@PathVariable("orderNo")String orderNo) {
        return orderService.deleteOrder(orderNo);
    }


    @GetMapping("getOrdersByUserId/{userId}")
    public List<UserCourseOrder> getOrdersByUserId(@PathVariable("userId")String userId) {
        return orderService.getOrdersByUserId(userId);
    }
}

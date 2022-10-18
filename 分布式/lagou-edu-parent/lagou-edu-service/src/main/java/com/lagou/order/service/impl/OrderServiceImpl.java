package com.lagou.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lagou.entity.UserCourseOrder;
import com.lagou.order.service.OrderService;
import mapper.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @BelongsProject lagou-edu
 * @Author lengy
 * @CreateTime 2022/8/6 19:01
 * @Description
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public void saveOrder(String orderNo, String user_id, String course_id, String activity_course_id, String source_type) {
        orderDao.saveOrder(orderNo,user_id,course_id,activity_course_id,source_type);
    }

    @Override
    public Integer updateOrder(String orderNo, int status) {
        return orderDao.updateOrder(orderNo,status);
    }

    @Override
    public Integer deleteOrder(String orderNo) {
        return orderDao.deleteOrder(orderNo);
    }

    @Override
    public List<UserCourseOrder> getOrdersByUserId(String userId) {
        return orderDao.getOrdersByUserId(userId);
    }
}

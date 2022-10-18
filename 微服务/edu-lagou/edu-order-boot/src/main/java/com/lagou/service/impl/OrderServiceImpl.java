package com.lagou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.entity.PayOrder;
import com.lagou.entity.PayOrderRecord;
import com.lagou.entity.UserCourseOrder;
import com.lagou.mapper.OrderDao;
import com.lagou.mapper.PayOrderDao;
import com.lagou.mapper.PayOrderRecordDao;
import com.lagou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/18 13:50
 * @Description
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PayOrderDao payOrderDao;

    @Autowired
    private PayOrderRecordDao payOrderRecordDao;

    // 保存订单
    @Override
    public void saveOrder(String orderNo, String user_id, String course_id, String activity_course_id, String source_type) {
        UserCourseOrder order = new UserCourseOrder();
        order.setOrderNo(orderNo);
        order.setUserId(user_id);
        order.setCourseId(course_id);
        order.setActivityCourseId(Integer.parseInt(activity_course_id));
        order.setSourceType(source_type);
        order.setStatus(0);
        order.setIsDel(0);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        orderDao.insert(order);

    }

    // 更新订单
    @Override
    public Integer updateOrder(String orderNo, int status) {

        UserCourseOrder order = new UserCourseOrder();
        order.setOrderNo(orderNo);
        order.setStatus(status);
        order.setUpdateTime(new Date());


        return orderDao.updateById(order);
    }

    @Override
    public Integer deleteOrder(String orderNo) {

        return orderDao.delete(new QueryWrapper<UserCourseOrder>().eq("order_no",orderNo));
    }

    @Override
    public List<UserCourseOrder> getOrdersByUserId(Integer userId) {

        return orderDao.selectList(new QueryWrapper<UserCourseOrder>().eq("status",20) // 购买成功
            .eq("is_del",Boolean.FALSE) // 未删除
            .eq("user_id",userId)); // 该用户
    }


    @Override
    public void saveOrderInfo(PayOrder payOrder) {

        payOrderDao.insert(payOrder);
    }

    @Override
    public void saveOrderRecord(PayOrderRecord payOrderRecord) {

        payOrderRecordDao.insert(payOrderRecord);
    }
}

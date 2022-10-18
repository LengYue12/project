package com.lagou.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/18 22:26
 * @Description Feign接口，远程去调用订单微服务查询该用户购买的课程id集合
 */
@FeignClient(name = "edu-order-boot",path = "/order")   // 去Eureka注册中心获取订单微服务
public interface OrderRemoteService {

    // 远程调用订单微服务
    @GetMapping("/getOrdersByUserId/{userId}")
    List<Object> getOrdersByUserId(@PathVariable("userId") Integer userId);
}

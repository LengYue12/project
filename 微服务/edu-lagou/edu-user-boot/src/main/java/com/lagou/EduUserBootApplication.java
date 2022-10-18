package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/14 20:34
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient  // 注册到Eureka
@MapperScan("com.lagou.mapper")
public class EduUserBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduUserBootApplication.class,args);
    }
}

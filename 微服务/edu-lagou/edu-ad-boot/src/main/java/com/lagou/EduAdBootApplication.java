package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  // 注册到Eureka
@MapperScan("com.lagou.mapper")
public class EduAdBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduAdBootApplication.class, args);
    }

}

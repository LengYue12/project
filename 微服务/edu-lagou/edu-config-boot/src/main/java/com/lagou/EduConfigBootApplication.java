package com.lagou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer // 启动配置中心
public class EduConfigBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduConfigBootApplication.class, args);
    }

}

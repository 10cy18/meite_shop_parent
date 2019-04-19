package com.cy;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 微服务服务实现
 * @Author: cy
 * @Date: 2019/2/28 14:59
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2Doc
public class AppWeiXin {
    public static void main(String[] args) {
        SpringApplication.run(AppWeiXin.class, args);
    }
}

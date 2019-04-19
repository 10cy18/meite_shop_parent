package com.cy.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AppEuraka {
    public static void main(String[] args) {
        SpringApplication.run(AppEuraka.class,args);
    }
}

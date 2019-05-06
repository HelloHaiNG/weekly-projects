package com.ucarweekly.usercenterservice;

import com.ucar.api.WeeklyConst;
import com.ucarweekly.usercenterapi.WeeklyUserCenterConst;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.ucarweekly.usercenterservice", WeeklyConst.Weekly_CLIENT_HYSTRIX_SCAN_PATH,WeeklyUserCenterConst.WeeklyUserCenter_CLIENT_HYSTRIX_SCAN_PATH})
@EnableEurekaClient
@EnableFeignClients(basePackages = {WeeklyUserCenterConst.WeeklyUserCenter_CLIENT_SCAN_PATH,WeeklyConst.Weekly_CLIENT_SCAN_PATH})
@EnableTransactionManagement
public class UsercenterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsercenterServiceApplication.class, args);
    }
}

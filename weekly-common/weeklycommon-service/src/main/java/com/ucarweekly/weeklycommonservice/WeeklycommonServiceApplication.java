package com.ucarweekly.weeklycommonservice;

import com.ucar.api.WeeklyConst;
import com.ucarweekly.usercenterapi.WeeklyUserCenterConst;
import com.ucarweekly.weeklycommonapi.WeeklyCommonConst;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.ucarweekly.weeklycommonservice", WeeklyCommonConst.WeeklyCommon_CLIENT_HYSTRIX_SCAN_PATH, WeeklyUserCenterConst.WeeklyUserCenter_CLIENT_HYSTRIX_SCAN_PATH,
        WeeklyConst.Weekly_CLIENT_HYSTRIX_SCAN_PATH})
@EnableEurekaClient
@EnableFeignClients(basePackages = {WeeklyCommonConst.WeeklyCommon_CLIENT_SCAN_PATH, WeeklyUserCenterConst.WeeklyUserCenter_CLIENT_SCAN_PATH, WeeklyConst.Weekly_CLIENT_SCAN_PATH})
@EnableTransactionManagement
public class WeeklycommonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeeklycommonServiceApplication.class, args);
    }
}

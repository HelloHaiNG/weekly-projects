package com.ucar.weeklyadminwebservice;

import com.ucar.api.WeeklyConst;
import com.ucar.weeklyadminwebapi.WeeklyAdminWebConst;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.ucar.weeklyadminwebservice", WeeklyAdminWebConst.WeeklyAdminWeb_CLIENT_HYSTRIX_SCAN_PATH,WeeklyConst.Weekly_CLIENT_HYSTRIX_SCAN_PATH})
@EnableEurekaClient
@EnableFeignClients(basePackages = {WeeklyAdminWebConst.WeeklyAdminWeb_CLIENT_SCAN_PATH,WeeklyConst.Weekly_CLIENT_SCAN_PATH})
@EnableTransactionManagement
public class WeeklyadminwebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeeklyadminwebServiceApplication.class, args);
    }
}

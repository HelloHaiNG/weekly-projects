package com.weekly.eurekaregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaregisterApplication {

    public static void main(String[] args) { SpringApplication.run(EurekaregisterApplication.class, args);

    }
}

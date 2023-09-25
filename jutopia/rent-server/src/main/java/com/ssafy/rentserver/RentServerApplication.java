package com.ssafy.rentserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(com.ssafy.common.objectmapper.ObjectMapperConfig.class)
public class RentServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentServerApplication.class, args);
    }

}

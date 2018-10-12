package com.bonaparte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by yangmingquan on 2018/10/12.
 */
@EnableEurekaServer
@SpringBootApplication
public class Application {

    public static void main(String args[]){
        SpringApplication.run(Application.class, args);
    }
}

package com.jh.myservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class MyService {

    public boolean ping() {
        return true;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(MyService.class);
        app.run(args);
    }
}

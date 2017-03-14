package com.jh.myclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleClient {

    public boolean ping() {
        return true;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(SimpleClient.class);
        app.run(args);
    }
}

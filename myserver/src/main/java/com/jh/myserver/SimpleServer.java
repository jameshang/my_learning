package com.jh.myserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SimpleServer {

    public boolean ping() {
        return true;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(SimpleServer.class);
        app.run(args);
    }
}

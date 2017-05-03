package com.jh.eurekasrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableEurekaServer
@EnableHystrixDashboard
public class MyEurekaServer {

	public boolean ping() {
		return true;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MyEurekaServer.class);
		app.run(args);
	}
}

package com.jh.myservice;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class MyService {

	public boolean ping() {
		return true;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(MyService.class);
		app.run(args);
	}
}

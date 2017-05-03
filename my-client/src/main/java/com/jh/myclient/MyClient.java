package com.jh.myclient;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringCloudApplication
@EnableFeignClients
@EnableZuulProxy
public class MyClient {

	public boolean ping() {
		return true;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(MyClient.class);
		app.run(args);
	}
}

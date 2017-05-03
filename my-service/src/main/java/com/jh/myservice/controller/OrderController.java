package com.jh.myservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jh.myservice.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping("/ping")
	public String ping(@RequestParam("msg") String msg) {
		return String.valueOf(msg);
	}

	@RequestMapping("/list")
	@HystrixCommand(fallbackMethod = "defaultList")
	public List<Map<String, String>> list(@RequestParam("merchantId") String merchantId) {
		return orderService.list(merchantId);
	}

	private List<Map<String, String>> defaultList(String merchantId) {
		return new ArrayList<>();
	}

}

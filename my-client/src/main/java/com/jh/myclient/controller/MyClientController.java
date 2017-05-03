package com.jh.myclient.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jh.myclient.service.OrderService;

@RestController
@RequestMapping("/")
public class MyClientController {

	@Autowired
	private DiscoveryClient client;

	@Autowired
	private OrderService orderService;

	@RequestMapping("/foo")
	public List<Object> foo(@RequestParam("serviceId") String serviceId) {
		List<Object> list = new ArrayList<>();
		List<ServiceInstance> services = this.client.getInstances(serviceId);
		if (services.isEmpty()) {
			list.add("Service not found -- " + serviceId);
		} else {
			ServiceInstance service = services.get(0);
			list.add(service.getPort());
			list.add(service.getHost());
			list.add(service.getMetadata());
			list.add(service.getServiceId());
			list.add(service.getUri());
		}
		return list;
	}

	@GetMapping("/listOrder")
	public List<Map<String, String>> listOrder(@RequestParam("merchantId") String merchantId) {
		return this.orderService.list(merchantId);
	}

}

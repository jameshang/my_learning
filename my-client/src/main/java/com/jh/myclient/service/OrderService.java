package com.jh.myclient.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "my-service")
public interface OrderService {

	@GetMapping("/order/list")
	List<Map<String, String>> list(@RequestParam("merchantId") String merchantId);

}

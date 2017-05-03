package com.jh.myservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Value("${local.env.node}")
	private String node;

	public List<Map<String, String>> list(String merchantId) {
		List<Map<String, String>> list = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("id", String.valueOf(i));
			map.put("merchantId", merchantId);
			map.put("desc", "Order of merchant-" + merchantId);
			map.put("node", this.node);
			list.add(map);
		}
		return list;
	}

}

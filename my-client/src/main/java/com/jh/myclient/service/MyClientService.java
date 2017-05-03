package com.jh.myclient.service;

import org.springframework.stereotype.Service;

@Service
public class MyClientService {

	public String foo(String name) {
		return "Hello " + name + "!";
	}

}

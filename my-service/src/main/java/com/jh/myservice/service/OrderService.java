package com.jh.myservice.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderService {

    @RequestMapping("/ping")
    public String ping(@RequestParam("msg") String msg) {
        return String.valueOf(msg);
    }

}

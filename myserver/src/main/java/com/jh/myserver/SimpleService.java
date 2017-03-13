package com.jh.myserver;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleService {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ");

    @Value("${app.env}")
    private String env;

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/time")
    String time() {
        return sdf.format(new Date());
    }

    @RequestMapping("/app/env")
    String env() {
        return this.env;
    }

}

package com.jh.myclient;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyClientService {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ");

    @Autowired
    private Environment environment;

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

    @RequestMapping("/app/env/{key}")
    String env(@PathVariable("key") String key) {
        return this.environment.getProperty(key);
    }

}

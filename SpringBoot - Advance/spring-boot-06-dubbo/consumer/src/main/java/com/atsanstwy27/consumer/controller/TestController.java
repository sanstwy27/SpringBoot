package com.atkjs927.consumer.controller;

import com.atkjs927.api.TestInterface;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Reference(version = "1.0.0")
    TestInterface testService; // 這個接口是api裡面定義的接口

    @RequestMapping("/hello")
    public String testHello(String name) {
        return testService.testHello(name);
    }

}

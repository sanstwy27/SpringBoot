package com.atkjs927.provider.service;

import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import com.atkjs927.api.TestInterface;

@Service(version = "1.0.0")
public class TestServiceImpl implements TestInterface {

    @Override
    public String testHello(String name) {
        return "hello " + name;
    }
}

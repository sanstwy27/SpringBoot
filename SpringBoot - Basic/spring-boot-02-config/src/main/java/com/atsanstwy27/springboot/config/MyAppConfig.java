package com.atkjs927.springboot.config;

import com.atkjs927.springboot.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAppConfig {

    @Bean
    public HelloService helloService() {
        System.out.println("@Bean Ioc..");
        return new HelloService();
    }
}

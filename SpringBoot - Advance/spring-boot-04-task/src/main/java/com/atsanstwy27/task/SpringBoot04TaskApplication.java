package com.atkjs927.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class SpringBoot04TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot04TaskApplication.class, args);
    }

}

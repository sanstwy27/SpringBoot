package com.atkjs927.springboot.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class MyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized...webapp started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed...webapp stopped");
    }

}

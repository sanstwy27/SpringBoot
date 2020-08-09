package com.atkjs927.springboot;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBoot03LoggingApplicationTests {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    void contextLoads() {

        logger.trace("this trace log..");
        logger.debug("this debug log..");
        // Default [info] Level
        logger.info("this info log..");
        logger.warn("this warn log..");
        logger.error("this error log..");


    }

}

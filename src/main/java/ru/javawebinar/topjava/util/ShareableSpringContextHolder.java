package ru.javawebinar.topjava.util;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ShareableSpringContextHolder {

    private static ConfigurableApplicationContext CONTEXT;

    static {
        CONTEXT = new ClassPathXmlApplicationContext("classpath:spring/spring-app.xml");
    }

    public static ConfigurableApplicationContext getContext() {
        return CONTEXT;
    }

    private ShareableSpringContextHolder() {
    }
}

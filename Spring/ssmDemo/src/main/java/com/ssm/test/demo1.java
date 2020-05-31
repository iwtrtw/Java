package com.ssm.test;

import com.ssm.service.UserService;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class demo1 {
    @Test
    public void testSpring() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring.xml");
        System.out.println("Before");
        UserService userService = (UserService) appContext.getBean("userServieImpl");
        System.out.println("After");
        userService.findUserInfo();
    }
}

package com.ssm.controller;


import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/showInfo")
    public String showInfo() {
//        System.out.println("-------shoinfo");
        userService.findUserInfo();
        return "userInfo";

    }
}

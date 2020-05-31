package com.ssm.service.impl;

import com.ssm.pro.User;
import com.ssm.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServieImpl implements UserService {
    public UserServieImpl(){
        System.out.println("无参构造");
    }


    @Override
    public List<User> findUserInfo() {
        System.out.println("查询用户信息");
        return null;
    }
}

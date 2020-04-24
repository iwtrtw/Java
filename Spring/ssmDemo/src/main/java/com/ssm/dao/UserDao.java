package com.ssm.dao;

import com.ssm.pro.User;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserDao {

    public List<User> findUser();
}

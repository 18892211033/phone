package com.isoft.demo.service;


import com.isoft.demo.entity.User;

import java.util.Map;

public interface UserRegisterService {
    // 注册 ：账号、密码
    User register(String name , String pass , String email, String phone) ;

    // 激活处理
    boolean active(String activeCode , Integer id) ;

    // 登录
    Map<String , Object> login(String name , String pass) ;

    boolean getNameCount(String name);

     boolean getEmailCount(String email);

}

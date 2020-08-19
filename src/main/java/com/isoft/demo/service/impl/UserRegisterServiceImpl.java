package com.isoft.demo.service.impl;

import com.isoft.demo.consts.Global;
import com.isoft.demo.dao.UserRegisterDao;
import com.isoft.demo.entity.User;
import com.isoft.demo.service.UserRegisterService;
import com.isoft.demo.util.MD5Util;
import com.isoft.demo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    @Autowired
    UserRegisterDao userRegisterDao;

    @Override
    public User register(String name, String pass, String email, String phone) {
        if(StringUtil.isEmpty(name) || StringUtil.isEmpty(pass) || StringUtil.isEmpty(email)||StringUtil.isEmpty(phone)) {
            return null ;
        }
        if(userRegisterDao.nameCheck(name) > 0) {
            return null ;
        }
        if(userRegisterDao.emailCheck(email) > 0) {
            return null ;
        }
        User user = new User() ;
        user.setUserName(name);
        user.setUserPass(MD5Util.MD5(pass));
        user.setUserEmail(email);
        user.setUserPhone(phone);
        int r = userRegisterDao.add(user) ;
        if(r == 0) {
            return null ;
        }
        return userRegisterDao.selectByEmail(email);
    }

    @Override
    public boolean active(String activeCode, Integer id) {
        if(StringUtil.isEmpty(activeCode)) {
            return false ;
        }
        if(id == null || id < 1) {
            return false ;
        }
        return userRegisterDao.updateStatus(User.STATUS_ACTIVE , id, activeCode) > 0;
    }

    @Override
    public Map<String, Object> login(String name, String pass) {
        Map<String , Object> map = new HashMap<>() ;
        if(StringUtil.isEmpty(name) || StringUtil.isEmpty(pass)) {
            map.put("status" , Global.LOGIN_RESULT_FAIL) ;
            return map ;
        }
        if(userRegisterDao.nameCheck(name) != 1) {
            map.put("status" , Global.LOGIN_RESULT_NO_ACCOUNT) ;
            return map ;
        }
        if(userRegisterDao.getStatus(name) == User.STATUS_NONE_ACTIVE) {
            map.put("status" , Global.LOGIN_RESULT_NONE_ACTIVE) ;
            return map ;
        }
        User user = userRegisterDao.getUser(name , MD5Util.MD5(pass)) ;
        if(null == user) {
            map.put("status" , Global.LOGIN_RESULT_ERROR_PWD) ;
        } else {
            map.put("status" , Global.LOGIN_RESULT_OK) ;
            map.put("user" , user) ;
        }
        return map ;
    }
}

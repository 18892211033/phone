package com.isoft.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isoft.demo.entity.User;
import com.isoft.demo.mapper.UserRegisterMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public class UserRegisterDao {
    @Autowired
    UserRegisterMapper userRegisterMapper ;

    QueryWrapper<User> queryWrapper = new QueryWrapper<>() ;

    public int nameCheck(String name) {
        queryWrapper.clear();
        queryWrapper.eq("userName",name) ;
        User user = userRegisterMapper.selectOne(queryWrapper) ;
        return user == null ? 0 : 1 ;
    }

    public int emailCheck(String email) {
        queryWrapper.clear();
        queryWrapper.eq("userEmail" , email) ;
        User user = userRegisterMapper.selectOne(queryWrapper) ;
        return user == null ? 0 : 1 ;
    }

    public int add(User user) {
        user.setUserState(0);
        user.setActivecode(UUID.randomUUID().toString().replace("-" , ""));
        user.setUserCreateTime(new Date());
        return userRegisterMapper.insert(user) ;
    }

    public User selectByEmail(String email) {
        queryWrapper.clear();
        queryWrapper.eq("userEmail" , email) ;
        return userRegisterMapper.selectOne(queryWrapper) ;
    }

    public int updateStatus(@Param("status") Integer status ,@Param("id") Integer id ,@Param("activeCode") String activeCode) {
        queryWrapper.clear();
        queryWrapper.eq("userId" , id) ;
        queryWrapper.eq("activecode" , activeCode) ;
        User user = new User() ;
        user.setUserState(status);
        return userRegisterMapper.update(user , queryWrapper) ;
    }

    public int getStatus(String name) {
        queryWrapper.clear();
        queryWrapper.select("userState") ;
        queryWrapper.eq("userName", name) ;
        User u = userRegisterMapper.selectOne(queryWrapper) ;
        return u.getUserState() ;
    }

    public User getUser(@Param("name") String name ,@Param("pass") String pass) {
        queryWrapper.clear();
        queryWrapper.eq("userName" , name) ;
        queryWrapper.eq("userPass", pass) ;
        return userRegisterMapper.selectOne(queryWrapper) ;
    }

}

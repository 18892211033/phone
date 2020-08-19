package com.isoft.demo.service;

import com.isoft.demo.entity.User;

public interface UserService {

    boolean updatePhoto(User users);

    int updatePass(Integer id , String oldPass , String newPass);

}

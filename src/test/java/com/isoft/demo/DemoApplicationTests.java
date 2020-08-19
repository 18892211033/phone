package com.isoft.demo;

import com.isoft.demo.dao.UserRegisterDao;
import com.isoft.demo.service.UserRegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UserRegisterDao userRegisterDao;

        @Autowired
        UserRegisterService userRegisterService;
    @Test
    void testUserService() {
        System.out.println(userRegisterService.register("wst" , "123" , "361687699@qq.com","18892211033"));
//        System.out.println("-------");
//        System.out.println(userService.register("mp2" , "123" , "rounding@163.com"));

//        System.out.println(userRegisterService.login("mp2" , "123"));
//        System.out.println("---------");
//        System.out.println(userRegisterService.active("ffda1519d6a84709ae0765664699a548" , 9));
//        System.out.println("-----");
//        System.out.println(userRegisterService.login("mp2" , "123"));
    }
    @Test
    void contextLoads() {
//        User u = new User() ;
//        u.setUserName("mp");
//        u.setUserPass(MD5Util.MD5("123"));
//        u.setUserEmail("2764985356@163.com");
//        u.setUserPhone("15522290378");
//        System.out.println(userRegisterDao.add(u));
        System.out.println(userRegisterDao.nameCheck("mpp"));
//        System.out.println(userRegisterDao.nameCheck("mp2"));
//        System.out.println("---");
//        System.out.println(userRegisterDao.emailCheck("rrr@163.com"));
//        System.out.println(userRegisterDao.emailCheck("rrr2@163.com"));
//        System.out.println("---");
//        System.out.println(userRegisterDao.selectByEmail("rrr@163.com"));
//        System.out.println(userRegisterDao.selectByEmail("rrr2@163.com"));
//        System.out.println("---");
//        System.out.println(userRegisterDao.getStatus("mp"));
//        System.out.println("---");
//        System.out.println(userRegisterDao.getUser("mp" , MD5Util.MD5("123")));
//        System.out.println("--------");
//        System.out.println(userRegisterDao.updateStatus(0, 11, "a3cbf241d5654e1eb7634e23cf48ac04"));
    }
}

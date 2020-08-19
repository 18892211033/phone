package com.isoft.demo.controller;

import com.isoft.demo.bean.ResponseData;
import com.isoft.demo.consts.Global;
import com.isoft.demo.entity.User;
import com.isoft.demo.service.EmailService;
import com.isoft.demo.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/userRegister")
public class UserRegisterController {
    @Autowired
    UserRegisterService userRegisterService;
    @Autowired
    EmailService emailService ;
    @GetMapping("sss")
    public String hello(){
        return "Hello";
    }

    @PostMapping
    public ResponseData register(String name , String pass , String email, String phone) {
        User u = userRegisterService.register(name , pass , email,phone) ;
        boolean r = false ;
        if(null != u) {  // dao 层成功
            // 发送激活邮件
            r = emailService.sendActive("user/active" , u.getActivecode(), u.getUserId() , u.getUserEmail()) ;
        }
        return new ResponseData(r ? 0 : 1 , r ? "注册成功" : "注册失败" , r) ;
    }

    @GetMapping("active")
    public ModelAndView active(String activeCode , Integer id) {
        boolean r = userRegisterService.active(activeCode , id) ;
        // 如果激活失败 : 重新生成一个activeCode ==》根据id修改activeCode  == 》发送电子邮件
        ModelAndView mav = new ModelAndView() ;
        mav.addObject("result" , r ? "激活成功" : "激活失败！") ;
        mav.setViewName("ActiveResult");
        return mav ;
    }

    @GetMapping("{name}/{pass}")
    public ResponseData login(@PathVariable("name") String name , @PathVariable("pass") String pass) {
        Map<String , Object> map = userRegisterService.login(name , pass) ;
        String msg = "" ;
        switch ((Integer)map.get("status")) {
            case Global.LOGIN_RESULT_ERROR_PWD :
                msg = "密码错误!" ;
                break;
            case Global.LOGIN_RESULT_FAIL :
                msg = "登录失败!" ;
                break;
            case Global.LOGIN_RESULT_NO_ACCOUNT :
                msg = "账号不存在!" ;
                break;
            case Global.LOGIN_RESULT_NONE_ACTIVE :
                msg = "未激活，不允许登录!" ;
                break;
            case Global.LOGIN_RESULT_OK :
                msg = "登录成功!" ;
                break;
        }
        return new ResponseData(
                (Integer)map.get("status") ,
                msg ,
                map.get("user")
        );
    }



    @GetMapping("name/{name}")
    public ResponseData nameCheck(@PathVariable("name") String name) {
        return null ;
    }
    @GetMapping("email/{email}")
    public ResponseData emailCheck(@PathVariable("email") String email) {
        return null ;
    }


}

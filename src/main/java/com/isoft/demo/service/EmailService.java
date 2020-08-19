package com.isoft.demo.service;


import com.isoft.demo.bean.ActiveMessage;
import com.isoft.demo.bean.MailMessage;
import com.isoft.demo.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    MailUtil mailUtil ;

    @Autowired
    TemplateEngine templateEngine ;

    @Value("${com.email.ip}")
    String serverIP ;
    @Value(("${com.email.port}"))
    String serverPort ;

    public boolean sendActive(String activeUrl , String activeCode , Integer id , String email) {

        String title = "激活账号" ;
        try {
            ActiveMessage activeMessage = new ActiveMessage();
            activeMessage.setIp(serverIP);
            activeMessage.setPort(serverPort);
            activeMessage.setUrl(activeUrl);
            activeMessage.setActiveCode(activeCode);
            activeMessage.setId(String.valueOf(id));
            Map<String, Object> map = new HashMap<>();
            map.put("params", activeMessage);
            // 获取模板页内容，填充模板页中的参数
            org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
            context.setVariables(map);
            String text = templateEngine.process("ActiveTemplate.html", context);

            MailMessage mailMessage = new MailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(title);
            mailMessage.setText(text);
            mailUtil.sendMailHtml(mailMessage);
        }catch (Exception e) {
            e.printStackTrace();
            return false ;
        }
        return true ;
    }

}

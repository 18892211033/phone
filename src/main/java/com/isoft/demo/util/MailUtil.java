package com.isoft.demo.util;


import com.isoft.demo.bean.MailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
    @Autowired
    JavaMailSender javaMailSender ;

    @Value("${spring.mail.username}")
    String from ;

    /**
     * 发送简单邮件
     */
    public void sendSimpleMail(MailMessage message) {
        SimpleMailMessage msg = new SimpleMailMessage() ;
        msg.setFrom(from);
        msg.setTo(message.getTo());
        msg.setSubject(message.getSubject());
        msg.setText(message.getText());
        javaMailSender.send(msg);
    }
    /**
     * 发送 正文是HTML的邮件,不带有附件
     */
    public void sendMailHtml(MailMessage message) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage) ;
            helper.setFrom(from);
            helper.setTo(message.getTo());
            helper.setSubject(message.getSubject());
            helper.setText(message.getText() , true);   // 一定携带第二个参数 true
        } ;
        javaMailSender.send(mimeMessagePreparator);
    }
    /**
     * 发送 带有附件
     */
    public void sendMailAttachment(MailMessage message , boolean isHtml) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage , true) ;
            helper.setFrom(from);
            helper.setTo(message.getTo());
            helper.setSubject(message.getSubject());
            helper.setText(message.getText() , isHtml);
            helper.addAttachment(message.getAttachmentFileName() , message.getAttachmentFile());
        } ;
        javaMailSender.send(mimeMessagePreparator);
    }
}

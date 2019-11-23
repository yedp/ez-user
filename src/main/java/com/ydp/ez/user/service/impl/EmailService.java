package com.ydp.ez.user.service.impl;

import com.ydp.ez.user.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @Author: yedp
 * @Date: 2019/11/22 9:24
 * @Description：${description}
 */
@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sendUser;

    /**
     * 发送邮件
     *
     * @param receiveEmail
     * @param subject
     * @param content
     * @return
     */
    @Override
    public void sendEmail(String receiveEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendUser);
        message.setTo(receiveEmail);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }
}

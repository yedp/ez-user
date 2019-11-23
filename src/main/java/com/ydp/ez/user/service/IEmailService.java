package com.ydp.ez.user.service;

/**
 * @Author: yedp
 * @Date: 2019/11/22 9:11
 * @Description：${description}
 */
public interface IEmailService {
    /**
     * 发送邮件
     *
     * @param receiveEmail
     * @param subject
     * @param content
     * @return
     */
    void sendEmail(String receiveEmail, String subject, String content);
}

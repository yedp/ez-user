package com.ydp.ez.user;

import com.ydp.ez.user.service.IEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: yedp
 * @Date: 2019/11/22 9:32
 * @Description：${description}
 */
@SpringBootTest()
public class EmailServiceTest {
    @Autowired
    IEmailService emailService;

    @Test
    public void sendMail() {
        try {
            String receiver = "240073129@qq.com";
            emailService.sendEmail(receiver, "你好", "测试");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}

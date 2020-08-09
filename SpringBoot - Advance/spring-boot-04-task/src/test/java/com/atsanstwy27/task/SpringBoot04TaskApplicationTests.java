package com.atkjs927.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
class SpringBoot04TaskApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Hello~~");
        message.setText("World!");

        message.setTo("XXXXXXXX");
        message.setFrom("XXXXXXXX");

        mailSender.send(message);
    }

    @Test
    public void test02() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject("Hello~~");
        helper.setText("<b style='color:red'>World!</b>", true);

        helper.setTo("XXXXXXXX");
        helper.setFrom("XXXXXXXX");

        helper.addAttachment("1.jpg", new File("XXXXXXXX\\0320014.jpg"));

        mailSender.send(mimeMessage);
    }
}

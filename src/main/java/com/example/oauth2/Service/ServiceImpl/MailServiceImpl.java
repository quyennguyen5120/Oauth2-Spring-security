package com.example.oauth2.Service.ServiceImpl;

import com.example.oauth2.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String mail) {

    }
}

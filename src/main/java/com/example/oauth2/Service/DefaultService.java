package com.example.oauth2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultService{

    @Autowired
    MailService mailService;

    @Value("${mail.smtp.username}")
    String mailFrom;

    public void sendMail(String toAddress, String subject, String content){
        String url = " test sent mail";
        this.mailService.sendMail(
                mailFrom,
                toAddress,
                subject,
                null,
                null,
                content
        );
    }
    public void sendMailResetPassWord(String toAddress, String subject, String content, String token){
        String url = " test sent mail";
        this.mailService.resetPassWord(
                mailFrom,
                toAddress,
                subject,
                null,
                null,
                content,
                token
        );
    }

    public void spamMail(String toAddress, String subject, String filePath, String content){
        String url = " test sent mail";
        this.mailService.spamMail(
                mailFrom,
                toAddress,
                subject,
                null,
                filePath,
                content
        );
    }

}

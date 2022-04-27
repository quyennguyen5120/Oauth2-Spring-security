package com.example.oauth2.Service.ServiceImpl;

import com.example.oauth2.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void sendMail(String from, String toAddress, String subject, Object model, String filePath, String content) {
        try{
            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("content", content);
            final MimeMessage message = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, "UTF-8");
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            String templateHtml = templateEngine.process("templeteEmail", ctx);
            mimeMessageHelper.setText(templateHtml, true);

            this.javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void resetPassWord(String from, String toAddress, String subject, Object model, String filePath, String content, String tokken) {
        try{
            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("token_mail", tokken);
            ctx.setVariable("content", content);
            final MimeMessage message = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, "UTF-8");
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            String templateHtml = templateEngine.process("templeteResetEmail", ctx);
            mimeMessageHelper.setText(templateHtml, true);

            this.javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void spamMail(String from, String toAddress, String subject, Object model, String filePath, String content) {
        try{
            for(int i=0; i<10; i++){
                final Context ctx = new Context(LocaleContextHolder.getLocale());
                ctx.setVariable("content", content + i);
                final MimeMessage message = this.javaMailSender.createMimeMessage();
                final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, "UTF-8");
                mimeMessageHelper.setTo(toAddress);
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setSubject(subject + i);
                String templateHtml = templateEngine.process("templeteEmail", ctx);
                mimeMessageHelper.setText(templateHtml, true);

                this.javaMailSender.send(message);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}

package com.example.oauth2.Service;

public interface MailService {

    public void sendMail(String from, String toAddress, String subject, Object model, String filePath, String content);
    public void resetPassWord(String from, String toAddress, String subject, Object model, String filePath, String content,String tokken);
    public void spamMail(String from, String toAddress, String subject, Object model, String filePath, String content);
}

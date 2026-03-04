package com.banking.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailUtil {

    public static void sendEmail(String toEmail, String userName) throws Exception {

        final String fromEmail = "chandrannisalya@gmail.com";
        final String password = "*****";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(toEmail));
        message.setSubject("Welcome to Banking App");

        message.setText("Hello " + userName +
                ",\n\nYour registration is successful.\n\nRegards,\nBank Team");

        Transport.send(message);
    }
}
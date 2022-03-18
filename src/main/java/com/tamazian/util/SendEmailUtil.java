package com.tamazian.util;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Properties;

public class SendEmailUtil {

    private static final SendEmailUtil INSTANCE = new SendEmailUtil();
    private static final URL EMAIL_PROPERTIES = SendEmailUtil.class.getClassLoader().getResource("emailService.properties");
    private static final String USERNAME_KEY = "mail.smtp.username";
    private static final String PASSWORD_KEY = "mail.smtp.password";
    private static final String CONTENT_TYPE = "text/html";

    public void sendEmail(String emailTo, String messageContent) {

        Properties properties = null;
        if (EMAIL_PROPERTIES != null) {
            properties = PropertiesLoader.loadPropertiesData(EMAIL_PROPERTIES);
        }

        String username = properties != null ? properties.getProperty(USERNAME_KEY) : null;
        String password = properties != null ? properties.getProperty(PASSWORD_KEY) : null;


        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME_KEY));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Account activation");
            message.setContent(messageContent, CONTENT_TYPE);
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public static SendEmailUtil getInstance() {
        return INSTANCE;
    }

}

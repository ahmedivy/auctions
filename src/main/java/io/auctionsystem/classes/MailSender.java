package io.auctionsystem.classes;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class MailSender {
    String to, otp;
    String from = "ahmadabdullah4919@gmail.com";

    public MailSender(String to) {
        this.to = to;
        randomGenerator();
    }

    public void randomGenerator(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        otp = String.format("%06d", number);
    }

    public String sendOtp(){
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(from,"ewljewwnjnpoukfh");
            }
        });

        session.setDebug(true);


        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Email Authentication");
            message.setText("Your Verification Code is: "+otp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return otp;
    }

}
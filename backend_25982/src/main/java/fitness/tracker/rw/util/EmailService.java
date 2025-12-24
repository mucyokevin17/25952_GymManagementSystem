package fitness.tracker.rw.util;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Properties;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    private static final String senderEmail = "emile.tester10@gmail.com";
    private static final String appPassword = "kgznsqwksvyhnknt"; // App password
    
    @Autowired
    private JavaMailSender mailSender;

    public void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    private static Session getSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });
    }

    public static void sendEmail(String recipient, String subject, String body) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

   
    public static void sendPasswordResetEmail(String recipient, String resetToken) {
    String resetLink = "http://localhost:5173/reset-password?token=" + resetToken;
    String subject = "Reset Your Password";
    String body = "Click the link to reset your password:\n" + resetLink;
    sendEmail(recipient, subject, body);
    }

}

package com.example.service.history;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    private final JavaMailSender javaMailSender;
    private EmailHistoryService emailHistoryService;
    @Value("${spring.mail.username}")
    private String fromMailAddress;

    public MailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(String toAccount, String subject, String text)  {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromMailAddress);
            MimeMessageHelper helper = null;
            helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

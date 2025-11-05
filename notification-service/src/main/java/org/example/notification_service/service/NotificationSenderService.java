package org.example.notification_service.service;

import jakarta.validation.Valid;
import org.example.notification_service.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationSenderService {

    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public NotificationSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailNotification(@Valid Message message) {
        SimpleMailMessage notification = new SimpleMailMessage();
        notification.setFrom(fromEmail);
        notification.setTo(message.getUserEmail());
        switch (message.getStatus()) {
            case CREATED:
                notification.setSubject("Welcome to the club");
                notification.setText("Твой аккаунт успешно создан, добро пожаловать!!!");
                javaMailSender.send(notification);
                break;
            case DELETED:
                notification.setSubject("Good buy, pretty");
                notification.setText("Твой аккаунт успешно удален, возвращайтесь!!!");
                javaMailSender.send(notification);
                break;
            default:
                break;
        }
    }
}

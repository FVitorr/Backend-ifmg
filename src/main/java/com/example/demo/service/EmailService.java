package com.example.demo.service;

import com.example.demo.dtos.EmailDTO;
import com.example.demo.service.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendMail(EmailDTO dto) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(dto.getTo());
            message.setSubject(dto.getSubjet());
            message.setText(dto.getBody());

            mailSender.send(message);
        }catch (MailSendException e){
            throw new EmailException(e.getMessage());
        }


    }

}

package com.ncc.service.impl;

import com.ncc.dto.MailDTO;
import com.ncc.entity.Employee;
import com.ncc.service.IMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendWelcomeEmail(Employee employee) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(employee.getEmail());
            helper.setSubject("Welcome to our company!");

            Context context = new Context();
            context.setVariable("employee", employee);
            String emailContent = templateEngine.process("welcome-template", context);

            helper.setText(emailContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Xử lý lỗi khi gửi email
        }
    }

    @Async
    @Override
    public void sendCheckInOutReminderEmail(Employee employee) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(employee.getEmail());
            helper.setSubject("Reminder: Check-In/Check-Out");

            Context context = new Context();
            context.setVariable("employee", employee);
            String emailContent = templateEngine.process("checkinout-reminder-template", context);

            helper.setText(emailContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Xử lý lỗi khi gửi email
        }
    }

    @Async
    @Override
    public void sendEmail(String toAddress, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }
}







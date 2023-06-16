package com.ncc.service.impl;

import com.ncc.dto.MailDTO;
import com.ncc.entity.Employee;
import com.ncc.service.IMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
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
    private final JavaMailSenderImpl mailSender;
    private final MailDTO mailDTO;
    private final SpringTemplateEngine templateEngine;

    @Override
    public String sendSimpleMail(String code, String emailTo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("chien.nguyenvan@ncc.asia");
            message.setTo(emailTo);
            if (code.length() == 4) {
                message.setSubject("Welcome to the company");
                message.setText("Your code is: " + code);
            } else {
                message.setSubject("Notification");
                message.setText(code);
            }
            mailSender.send(message);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }
    }

    @Async
    @Override
    public String sendHtmlMessage(Employee employee, String password) throws MessagingException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Chien");
        map.put("subscriptionDate", new Date());
        map.put("username", "chien.nguyen");
        map.put("code", "2222");
        map.put("password", "123456");
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", user.getFirstName());
//        map.put("subscriptionDate", new Date());
//        map.put("username", user.getUsername());
//        map.put("code", user.getCode());
//        map.put("password", password);
//        map.put("company", messageSource.getMessage("company", null, lang));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(map);
        helper.setFrom("chien.nguyenvan@ncc.asia");
        helper.setTo("chiennguyen2198@gmail.com");
        helper.setSubject("Welcome to the company");
        String html = templateEngine.process("welcome.html", context);
        helper.setText(html, true);

        mailSender.send(message);
        return "Mail Sent Successfully...";
    }
}


package com.ncc.service.impl;

import com.ncc.constants.MessageConstant;
import com.ncc.dto.MailDTO;
import com.ncc.entity.Employee;
import com.ncc.service.IMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.Quota;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final Environment env;

    @Override
    public String sendmail(MailDTO mailDTO) throws MessagingException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(env.getProperty("spring.mail.username"));
            message.setTo(mailDTO.getSubject());
            message.setText(mailDTO.getContent());
            mailSender.send(message);
            return MessageConstant.SEND_MAIL_DONE;

        } catch (Exception e) {
            return MessageConstant.SEND_MAIL_FAIL;
        }
    }

    @Override
    public String sendMailWithHTML(MailDTO mailDTO, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        Context context = new Context();
        context.setVariables(mailDTO.getProps());
        String html = templateEngine.process(templateName, context);
        helper.setFrom(env.getProperty("spring.mail.username"));
        helper.setTo(mailDTO.getTo());
        helper.setSubject(mailDTO.getSubject());
        helper.setText(html, true);
        try {
            mailSender.send(message);
            return MessageConstant.SEND_MAIL_DONE;
        } catch (Exception e) {
            return MessageConstant.SEND_MAIL_FAIL;
        }
    }

    @Override
    public void sendEmployeeCreationEmail(Employee employee) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String templateContent = getTemplateContent("welcome.html");

            Context context = new Context();
            context.setVariable("employeeName", employee.getFirstName());
            context.setVariable("employeeCode", employee.getEmployeeCode());

            String processedTemplate = templateEngine.process(templateContent, context);
            helper.setText(processedTemplate, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTemplateContent(String templateName) {
        Resource resource = new ClassPathResource(templateName);
        try {
            return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

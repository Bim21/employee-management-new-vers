package com.ncc.service;

import com.ncc.dto.MailDTO;
import com.ncc.entity.Employee;

import javax.mail.MessagingException;

public interface IMailService {
    String sendmail(MailDTO mailDTO) throws MessagingException;

    String sendMailWithHTML(MailDTO mailDTO, String templateName) throws MessagingException;

    void sendEmployeeCreationEmail(Employee employee);
}

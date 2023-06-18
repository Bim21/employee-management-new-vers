package com.ncc.service;

import com.ncc.entity.Employee;

import javax.mail.MessagingException;
import java.util.Locale;

public interface IMailService {
//    String sendSimpleMail(String code, String emailTo);
//    String sendHtmlMessage(Employee employee, String password) throws MessagingException;

    void sendWelcomeEmail(Employee employee);
    void sendCheckInOutReminderEmail(Employee employee);
}

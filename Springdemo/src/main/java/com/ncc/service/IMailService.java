package com.ncc.service;

import com.ncc.entity.Employee;

import javax.mail.MessagingException;
import java.util.Locale;

public interface IMailService {
    void sendWelcomeEmail(Employee employee);
    void sendCheckInOutReminderEmail(Employee employee);
}

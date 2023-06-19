package com.ncc.event;

import com.ncc.entity.Employee;
import com.ncc.service.impl.MailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCreatedEventListener {
    private final MailService emailService;

    public EmployeeCreatedEventListener(MailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void handleEmployeeCreatedEvent(EmployeeCreatedEvent event) {

        Employee employee = event.getEmployee();
        System.out.println("có user đã được tạo");
        emailService.sendWelcomeEmail(employee);
        System.out.println("gửi email cho user");
    }
}

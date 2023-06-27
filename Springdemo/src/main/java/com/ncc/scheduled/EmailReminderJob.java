package com.ncc.scheduled;

import com.ncc.entity.CheckInOut;
import com.ncc.entity.Employee;
import com.ncc.repository.IEmployeeRepository;
import com.ncc.service.ICronjobService;
import com.ncc.service.IMailService;
import com.ncc.service.impl.EmployeeService;
import com.ncc.service.impl.MailService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class EmailReminderJob implements Job {
    private final IEmployeeRepository employeeRepository;
    private final IMailService emailService;

    private final ICronjobService cronjobService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Employee> employeesWithMissingCheckInOut = cronjobService.getEmployeesWithMissingCheckInOut();

        for (Employee employee : employeesWithMissingCheckInOut) {
            String emailContent = generateEmailContent(employee);
            emailService.sendEmail(employee.getEmail(), "Reminder: Daily Check-In/Check-Out", emailContent);
        }
    }

    private String generateEmailContent(Employee employee) {
        return "Dear " + employee.getFirstName() + ",\n\n"
                + "This is a reminder that you have forgotten to check in/check out today.\n"
                + "Please make sure to perform your daily check-in/check-out.\n\n"
                + "Best regards,\n"
                + "Employee Management System";
    }
}

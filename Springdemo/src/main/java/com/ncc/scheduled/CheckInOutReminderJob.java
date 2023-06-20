package com.ncc.scheduled;

import com.ncc.entity.Employee;
import com.ncc.service.impl.CheckInOutService;
import com.ncc.service.impl.EmployeeService;
import com.ncc.service.impl.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CheckInOutReminderJob {

    private final EmployeeService employeeService;
    private final MailService mailService;

    // TODO: dynamic Cron

    // Định nghĩa phương thức sẽ được gọi theo lịch trình cron
    @Scheduled(cron = "${cron.job.sendCheckInOutReminders}") // Chạy vào lúc 8:00 AM hàng ngày
    public void sendCheckInOutReminders() {
        // Lấy danh sách nhân viên chưa check-in/checkout
        List<Employee> employees = employeeService.getEmployeesWithoutError();

        // Gửi email thông báo cho từng nhân viên
        for (Employee employee : employees) {
            mailService.sendCheckInOutReminderEmail(employee);
        }
    }
}

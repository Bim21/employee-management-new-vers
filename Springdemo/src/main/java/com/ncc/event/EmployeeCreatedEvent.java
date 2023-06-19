package com.ncc.event;

import com.ncc.entity.Employee;
import org.springframework.context.ApplicationEvent;

public class EmployeeCreatedEvent extends ApplicationEvent {
    private final Employee employee;

    public EmployeeCreatedEvent(Employee employee) {
        super(employee);
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}


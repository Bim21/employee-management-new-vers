package com.ncc.scheduled;

import org.springframework.context.ApplicationEvent;

public class CheckInCheckOutEvent extends ApplicationEvent {
    private String employeeId;

    public CheckInCheckOutEvent(Object source, String employeeId) {
        super(source);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}

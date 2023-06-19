package com.ncc.testbeanscope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope("prototype")
public class PrototypeBean {

    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void displayMessage(){
        System.out.println("Message: " + message);
    }
//    private final LocalDateTime creationTime;
//
//    public PrototypeBean() {
//        this.creationTime = LocalDateTime.now();
//    }
//
//    public LocalDateTime getCreationTime() {
//        return creationTime;
//    }
}

package com.ncc.testbeanscope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope("prototype")
public class SingletonBean {

    private String message;

    public SingletonBean(){
        this.message = "Hello, i'm singleton bean!";
    }

    public String getMessage() {
        return message;
    }
//    private final LocalDateTime creationTime;
//
//    public SingletonBean() {
//        this.creationTime = LocalDateTime.now();
//    }
//
//    public LocalDateTime getCreationTime() {
//        return creationTime;
//    }
}


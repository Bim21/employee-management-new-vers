package com.ncc.exception;

public class CronJobException extends RuntimeException{
    public CronJobException(String message){
        super(message);
    }
}

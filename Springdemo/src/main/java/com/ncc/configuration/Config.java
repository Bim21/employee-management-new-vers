package com.ncc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:application-alpha.properties"),
        @PropertySource("classpath:application-dev.properties")
})
public class Config {
    @Value("${message}")
    private String message;

    public String Getdata() {
        return message;
    }
}

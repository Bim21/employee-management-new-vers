package com.ncc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:application-dev.properties"),
        @PropertySource("classpath:application-alpha.properties")
})
public class Config {
    @Value("${message}")
    private String message;

    public String Getdata() {
        return message;
    }
}

// TODO: Spring Property
// ConfigurationProperties
// dùng option của java để define file nào cần chạy
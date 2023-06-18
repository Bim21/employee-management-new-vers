package com.ncc;

import com.ncc.configuration.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringdemoApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SpringdemoApplication.class, args);
        Config config = applicationContext.getBean(Config.class);
        String message = config.Getdata();
        System.out.println(message);
    }
}


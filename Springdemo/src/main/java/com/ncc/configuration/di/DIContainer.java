package com.ncc.configuration.di;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DIContainer {
    @Bean
    public ModelMapper provideModelMapper(){
        return new ModelMapper();
    }
}

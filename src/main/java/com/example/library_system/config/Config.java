package com.example.library_system.config;


import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Bean
    ModelMapper construcModelMapper(){
        final ModelMapper modelMapper = new ModelMapper();
        final org.modelmapper.config.Configuration configuration = modelMapper.getConfiguration();
        configuration.setSkipNullEnabled(true);
        configuration.setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

}

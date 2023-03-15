package com.bodzilla.context;

import com.bodzilla.services.InvoiceService;
import com.bodzilla.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Spring needs a configuration class in order to construct an ApplicationContext.
// This is the configuration class.
// It does not need to implement a specific interface or extend a specific class.
// It also can have any name you want, though the convention is <app name>ApplicationConfiguration makes sense.
@Configuration
public class DemoApplicationConfiguration {

    // Your application needs a UserService.
    // So, in your configuration class, you create a method that returns a UserService, annotated with the @Bean annotation.
    // It tells Spring that, on ApplicationContext startup, it should create one instance of your UserService, by calling that very @Bean method.
    @Bean
    public UserService userService() {
        return new UserService();
    }

    // Since UserService is already defined as a bean, Spring will construct it and pass it into this method.
    @Bean
    public InvoiceService invoiceService(UserService userService) {
        return new InvoiceService(userService);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}


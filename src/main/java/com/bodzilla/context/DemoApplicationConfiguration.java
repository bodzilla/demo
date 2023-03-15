package com.bodzilla.context;

import com.bodzilla.ApplicationLauncher;
import com.bodzilla.services.InvoiceService;
import com.bodzilla.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

// Spring needs a configuration class in order to construct an ApplicationContext.
// This is the configuration class.
// It does not need to implement a specific interface or extend a specific class.
// It also can have any name you want, though the convention is <app name>ApplicationConfiguration makes sense.
@Configuration // This tells Spring that this is the configuration class which wires up all the IOC.

// This tells Spring to scan your classpath to find all the beans in the project, which it does not do by default so we do it in this class.
// We set the basePackageClasses to the ApplicationLauncher class since it lives in the root folder and Spring will know this is where to start scanning for all folders and sub-folders.
// In Sprint Boot projects, @SpringBootApplication is a meta annotation which includes the @ComponentScan annotation so when using that, we won't need ComponentScan.
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
public class DemoApplicationConfiguration {

    // Your application needs a UserService.
    // So, in your configuration class, you create a method that returns a UserService, annotated with the @Bean annotation.
    // It tells Spring that, on ApplicationContext startup, it should create one instance of your UserService, by calling that very @Bean method.

    // SCOPES:
    // Singleton: one instance of your object.
    // Prototype: multiple instances of your object (similar to "scoped" in .NET).
    // Request: an instance of your object per HTTP request. Only valid for web-aware ApplicationContexts.
    // Session: an instance of your object per http session. Only valid for web-aware ApplicationContexts (check difference between this and Request).
    // Application: an instance of your object per servlet context. Only valid for web-aware ApplicationContexts.
    // Websocket: an instance of your object per web socket. Only valid for web-aware ApplicationContexts.
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE) // Unless explicitly changed, Spring will always construct a singleton bean.
    public UserService userService() {
        return new UserService();
    }

    // Since UserService is already defined as a bean, Spring will construct it and pass it into this method.
    public InvoiceService invoiceService(UserService userService) {
        return new InvoiceService(userService);
    }

    // Since objectMapper is an external dependency, we still need to explicitly declare this as a @Bean.
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}


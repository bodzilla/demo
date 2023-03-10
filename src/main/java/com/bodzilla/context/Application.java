package com.bodzilla.context;

import com.bodzilla.services.InvoiceService;
import com.bodzilla.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application {

    public static final UserService userService = new UserService();
    public static final InvoiceService invoiceService = new InvoiceService(userService);
    public static final ObjectMapper objectMapper = new ObjectMapper();
}

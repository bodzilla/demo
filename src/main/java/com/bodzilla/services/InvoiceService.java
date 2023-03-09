package com.bodzilla.services;

import com.bodzilla.models.Invoice;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class InvoiceService {

    private final UserService userService;

    private final List<Invoice> invoices = new CopyOnWriteArrayList<>();

    public InvoiceService(UserService userService) {
        this.userService = userService;
    }

    public Invoice create(String userId, Integer amount) {
        if (Objects.isNull(userService.findById(userId))) {
            throw new IllegalStateException("User does not exist.");
        }

        Invoice invoice = new Invoice(userId, amount, "http://www.africau.edu/images/default/sample.pdf");
        invoices.add(invoice);
        return invoice;
    }

    public List<Invoice> getAll() {
        return invoices;
    }
}

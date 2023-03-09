package com.bodzilla.services;

import com.bodzilla.models.Invoice;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InvoiceService {

    private final List<Invoice> invoices;

    public InvoiceService() {
        invoices = new CopyOnWriteArrayList<>();
    }

    public Invoice create(String userId, Integer amount) {
        var invoice = new Invoice(userId, amount, "http://www.africau.edu/images/default/sample.pdf");
        invoices.add(invoice);
        return invoice;
    }

    public List<Invoice> getAll() {
        return invoices;
    }
}

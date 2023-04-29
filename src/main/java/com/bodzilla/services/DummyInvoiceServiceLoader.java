package com.bodzilla.services;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service

// The DummyInvoiceServiceLoader is a normal Spring bean, but it is annotated with the @Profile
// annotation, which means the bean will only exist whenever you start up Spring with the dev
// environment.
@Profile("dev")
public class DummyInvoiceServiceLoader {

  private final InvoiceService invoiceService;

  public DummyInvoiceServiceLoader(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @PostConstruct
  public void setup() {
    System.out.println("Creating dev invoices...");
    invoiceService.create("someUserId", 50);
    invoiceService.create("someOtherUserId", 100);
  }
}

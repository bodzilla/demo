package com.bodzilla.services;

import com.bodzilla.models.Invoice;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // This annotation tells Spring that it should turn your UserService and your
// InvoiceService into @Beans.
public class InvoiceService {
  private final UserService userService;
  private final List<Invoice> invoices = new CopyOnWriteArrayList<>();
  private final String cdnUrl;

  // the @Value annotation tells Spring to inject the value of the property into the field, defined
  // in the application.properties file.
  public InvoiceService(UserService userService, @Value("${cdn.url}") String cdnUrl) {
    this.userService = userService;
    this.cdnUrl = cdnUrl;
  }

  // This annotation tells to call this method after it constructs InvoiceService and all injected
  // dependencies.
  @PostConstruct
  public void init() {
    System.out.println("Fetching PDF Template from S3...");
  }

  // This annotation tells Spring to call this method before the application is
  // gracefully shut down.
  @PreDestroy
  public void shutdown() {
    System.out.println("Deleting downloaded templates...");
  }

  public Invoice create(String userId, Integer amount) {
    if (Objects.isNull(userService.findById(userId))) {
      throw new IllegalStateException("User does not exist.");
    }

    Invoice invoice = new Invoice(userId, amount, cdnUrl + "/images/default/sample.pdf");
    invoices.add(invoice);
    return invoice;
  }

  public List<Invoice> getAll() {
    return invoices;
  }
}

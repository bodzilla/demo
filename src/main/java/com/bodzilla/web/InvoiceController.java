package com.bodzilla.web;

import com.bodzilla.dto.InvoiceDto;
import com.bodzilla.models.Invoice;
import com.bodzilla.services.InvoiceService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class InvoiceController {

  private final InvoiceService invoiceService;

  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping("/invoices")
  public List<Invoice> invoices() {
    return invoiceService.findAll();
  }

  @PostMapping("/invoices")
  public Invoice createInvoice(@RequestBody @Valid InvoiceDto invoiceDto) {
    return invoiceService.create(invoiceDto.getUserId(), invoiceDto.getAmount());
  }
}

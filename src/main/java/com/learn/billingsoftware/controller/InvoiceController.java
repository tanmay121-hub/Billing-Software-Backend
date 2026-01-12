package com.learn.billingsoftware.controller;


import com.learn.billingsoftware.dto.InvoiceRequestDTO;
import com.learn.billingsoftware.entity.Invoice;
import com.learn.billingsoftware.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // Generate new invoice
    @PostMapping
    public Invoice generateInvoice(@RequestBody InvoiceRequestDTO request) {
        return invoiceService.generateInvoice(request);
    }

    // Get all invoices
    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    // Get invoice by id
    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    // Get invoices by customer
    @GetMapping("/customer/{customerId}")
    public List<Invoice> getInvoicesByCustomer(@PathVariable Long customerId) {
        return invoiceService.getInvoicesByCustomer(customerId);
    }
}

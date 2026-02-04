package com.learn.billingsoftware.controller;

import com.learn.billingsoftware.dto.InvoiceRequestDTO;
import com.learn.billingsoftware.entity.Invoice;
import com.learn.billingsoftware.service.InvoiceService;
import com.learn.billingsoftware.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.learn.billingsoftware.service.PdfService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final PdfService pdfService;

    @PostMapping
    public Invoice generateInvoice(@RequestBody InvoiceRequestDTO request) {
        return invoiceService.generateInvoice(request);
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    // Updated to use the new JPA method
    @GetMapping("/customer/{customerId}")
    public List<Invoice> getInvoicesByCustomer(@PathVariable Long customerId) {
        return invoiceService.getInvoicesByCustomer(customerId);
    }


    @GetMapping("/{id}/pdf")
    public ResponseEntity<InputStreamResource> downloadInvoice(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);

        ByteArrayInputStream pdf = pdfService.generateInvoicePdf(invoice);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=invoice_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
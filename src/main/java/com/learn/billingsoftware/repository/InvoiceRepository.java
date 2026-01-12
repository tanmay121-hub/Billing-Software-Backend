package com.learn.billingsoftware.repository;

import com.learn.billingsoftware.entity.Invoice;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class InvoiceRepository {
    private final List<Invoice> invoiceList = new ArrayList<>();
    private long nextId = 1;

    public Invoice save(Invoice invoice) {
        if (invoice.getInvoiceId() == null) {
            invoice.setInvoiceId(nextId++);
            invoiceList.add(invoice);
        } else {
            invoiceList.removeIf(inv -> inv.getInvoiceId().equals(invoice.getInvoiceId()));
            invoiceList.add(invoice);
        }
        return invoice;
    }

    public Optional<Invoice> findById(Long id) {
        return invoiceList.stream()
                .filter(inv -> inv.getInvoiceId().equals(id))
                .findFirst();
    }

    public List<Invoice> findAll() {
        return new ArrayList<>(invoiceList);
    }

    public List<Invoice> findByCustomerId(Long customerId) {
        return invoiceList.stream()
                .filter(inv -> inv.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
    }
}

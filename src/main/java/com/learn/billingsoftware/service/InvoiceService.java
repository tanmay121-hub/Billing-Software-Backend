package com.learn.billingsoftware.service;

import com.learn.billingsoftware.entity.Invoice;
import com.learn.billingsoftware.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    public Invoice save(Invoice invoice){
        return repository.save(invoice);
    }

    public Optional<Invoice> findById(Long id) {
        return repository.findById(id);
    }
    public List<Invoice> findAll() {
        return repository.findAll();
    }
    public List<Invoice> findByCustomerId(Long customerId){
        return repository.findByCustomerId(customerId);
    }
}

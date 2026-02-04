package com.learn.billingsoftware.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime invoiceDate;

    // Relationship: Many Invoices belong to One Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Double totalAmount;
    private Double totalTax;
    private Double discount;
    private Double finalAmount;

    // Relationship: One Invoice has Many Items
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> items;
}
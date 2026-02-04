package com.learn.billingsoftware.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Link back to the Invoice
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnore // Stop infinite recursion when printing JSON
    private Invoice invoice;

    private Integer quantity;
    private Double price;
    private Double taxAmount;
    private Double total;
}
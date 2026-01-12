package com.learn.billingsoftware.entity;

public class InvoiceItem {
    private Long id;
    private Product product;
    private Integer quantity;
    private Double price;
    private Double taxAmount;
    private Double total;

    public InvoiceItem() {}

    public InvoiceItem(Long id, Product product, Integer quantity, Double price, Double taxAmount, Double total) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.taxAmount = taxAmount;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}

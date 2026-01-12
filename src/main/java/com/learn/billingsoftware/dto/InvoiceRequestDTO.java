package com.learn.billingsoftware.dto;

import java.util.List;

public class InvoiceRequestDTO {
    private Long customerId;
    private List<InvoiceItemRequestDTO> items;
    private Double discount;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<InvoiceItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemRequestDTO> items) {
        this.items = items;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}

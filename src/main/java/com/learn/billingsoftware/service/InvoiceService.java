package com.learn.billingsoftware.service;

import com.learn.billingsoftware.dto.InvoiceItemRequestDTO;
import com.learn.billingsoftware.dto.InvoiceRequestDTO;
import com.learn.billingsoftware.entity.Customer;
import com.learn.billingsoftware.entity.Invoice;
import com.learn.billingsoftware.entity.InvoiceItem;
import com.learn.billingsoftware.entity.Product;
import com.learn.billingsoftware.repository.CustomerRepository;
import com.learn.billingsoftware.repository.InvoiceRepository;
import com.learn.billingsoftware.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository
    ) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Invoice generateInvoice(InvoiceRequestDTO request) {
        // Validate customer
        Customer customer = customerRepository.findById(request.getCustomerId());
        if (customer == null) {
            throw new RuntimeException("Customer not found with id: " + request.getCustomerId());
        }

        // Create invoice shell
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(LocalDateTime.now());

        double totalAmount = 0.0;
        double totalTax = 0.0;

        List<InvoiceItem> invoiceItems = new ArrayList<>();

        // Process each invoice item
        for (InvoiceItemRequestDTO itemDTO : request.getItems()) {

            Product product = productRepository.findById(itemDTO.getProductId());
            if (product == null) {
                throw new RuntimeException("Product not found with id: " + itemDTO.getProductId());
            }

            int requestedQty = itemDTO.getQuantity();

            // insufficient stock
            if (product.getStockQuantity() < requestedQty) {
                throw new RuntimeException(
                        "Insufficient stock for product: " + product.getName()
                );
            }

            // Calculations
            double itemPrice = product.getPrice() * requestedQty;
            double itemTax = itemPrice * (product.getGstPercentage() / 100);
            double itemTotal = itemPrice + itemTax;

            // Reduce stock
            product.setStockQuantity(product.getStockQuantity() - requestedQty);

            // Create invoice item
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setProduct(product);
            invoiceItem.setQuantity(requestedQty);
            invoiceItem.setPrice(itemPrice);
            invoiceItem.setTaxAmount(itemTax);
            invoiceItem.setTotal(itemTotal);

            invoiceItems.add(invoiceItem);

            totalAmount += itemPrice;
            totalTax += itemTax;
        }

        // Final invoice totals
        double discount = request.getDiscount() != null ? request.getDiscount() : 0.0;
        double finalAmount = totalAmount + totalTax - discount;

        invoice.setItems(invoiceItems);
        invoice.setTotalAmount(totalAmount);
        invoice.setTotalTax(totalTax);
        invoice.setDiscount(discount);
        invoice.setFinalAmount(finalAmount);

        // Save invoice
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Invoice not found with id: " + id)
                );
    }

    public List<Invoice> getInvoicesByCustomer(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }


}

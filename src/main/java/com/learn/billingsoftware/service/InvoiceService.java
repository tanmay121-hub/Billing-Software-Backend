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
import jakarta.transaction.Transactional; // Import this!
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public InvoiceService(InvoiceRepository invoiceRepo, CustomerRepository customerRepo, ProductRepository productRepo) {
        this.invoiceRepository = invoiceRepo;
        this.customerRepository = customerRepo;
        this.productRepository = productRepo;
    }

    @Transactional // <---  If anything fails, rollback everything
    public Invoice generateInvoice(InvoiceRequestDTO request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(LocalDateTime.now());

        double totalAmount = 0.0;
        double totalTax = 0.0;
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        for (InvoiceItemRequestDTO itemDTO : request.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }

            // Calculation Logic
            double itemPrice = product.getPrice() * itemDTO.getQuantity();
            double itemTax = itemPrice * (product.getGstPercentage() / 100);

            // Stock Reduction
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            productRepository.save(product);

            // Build Item
            InvoiceItem item = new InvoiceItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(itemPrice);
            item.setTaxAmount(itemTax);
            item.setTotal(itemPrice + itemTax);
            item.setInvoice(invoice);

            invoiceItems.add(item);

            totalAmount += itemPrice;
            totalTax += itemTax;
        }

        double discount = (request.getDiscount() != null) ? request.getDiscount() : 0.0;

        invoice.setItems(invoiceItems);
        invoice.setTotalAmount(totalAmount);
        invoice.setTotalTax(totalTax);
        invoice.setDiscount(discount);
        invoice.setFinalAmount((totalAmount + totalTax) - discount);

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }


    public List<Invoice> getInvoicesByCustomer(Long customerId){
        return invoiceRepository.findByCustomerId(customerId);
    }
}
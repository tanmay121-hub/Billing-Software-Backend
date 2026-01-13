package com.learn.billingsoftware.repository;

import com.learn.billingsoftware.entity.Customer;
import com.learn.billingsoftware.entity.Invoice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class InvoiceRepository {

    private final JdbcTemplate jdbcTemplate;

    public InvoiceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. SAVE
    public Invoice save(Invoice invoice) {
        if (invoice.getInvoiceId() == null) {
            return insert(invoice);
        } else {
            return update(invoice);
        }
    }

    private Invoice insert(Invoice invoice) {
        String sql = "INSERT INTO invoice (customer_id, total_amount, invoice_date) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                invoice.getCustomer().getId(),
                invoice.getTotalAmount(),
                invoice.getInvoiceDate()
        );
        return invoice;
    }

    private Invoice update(Invoice invoice) {
        String sql = "UPDATE invoice SET customer_id = ?, total_amount = ?, invoice_date = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                invoice.getCustomer().getId(),
                invoice.getTotalAmount(),
                invoice.getInvoiceDate(),
                invoice.getInvoiceId());
        return invoice;
    }

    // 2. FIND BY ID
    public Optional<Invoice> findById(Long id) {
        String sql = "SELECT * FROM invoice WHERE id = ?";
        List<Invoice> results = jdbcTemplate.query(sql, this::mapRowToInvoice, id);
        return results.stream().findFirst();
    }

    // 3. FIND ALL
    public List<Invoice> findAll() {
        String sql = "SELECT * FROM invoice";
        return jdbcTemplate.query(sql, this::mapRowToInvoice);
    }

    // 4. FIND BY CUSTOMER
    public List<Invoice> findByCustomerId(Long customerId) {
        String sql = "SELECT * FROM invoice WHERE customer_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToInvoice, customerId);
    }

    // --- Helper: RowMapper ---
    private Invoice mapRowToInvoice(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getLong("id"));
        invoice.setTotalAmount(rs.getDouble("total_amount"));

        Timestamp timestamp = rs.getTimestamp("invoice_date");
        if (timestamp != null) {
            invoice.setInvoiceDate(timestamp.toLocalDateTime());
        }

        // Reconstruct Customer Placeholder
        Customer customer = new Customer();
        customer.setId(rs.getLong("customer_id"));
        invoice.setCustomer(customer);

        return invoice;
    }
}
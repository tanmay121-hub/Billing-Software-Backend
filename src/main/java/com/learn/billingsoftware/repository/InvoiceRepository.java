package com.learn.billingsoftware.repository;

import com.learn.billingsoftware.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // Custom query method derived from name
    List<Invoice> findByCustomerId(Long customerId);
}
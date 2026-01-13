package com.learn.billingsoftware.repository;

import com.learn.billingsoftware.entity.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. ADD (Create)
    public Customer addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (name, address, email, phone) VALUES (?, ?, ?, ?)";

        // Execute update
        jdbcTemplate.update(sql,
                customer.getName(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPhone());


        return customer;
    }

    // 2. FIND BY ID (Read)
    public Customer findById(Long id) {
        String sql = "SELECT * FROM customer WHERE id = ?";


        List<Customer> result = jdbcTemplate.query(sql, this::mapRowToCustomer, id);

        return result.isEmpty() ? null : result.get(0);
    }

    // 3. GET ALL (Read)
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, this::mapRowToCustomer);
    }

    // 4. UPDATE
    public Customer updateCustomer(Long id, String address, String email, String phone) {
        String sql = "UPDATE customer SET address = ?, email = ?, phone = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, address, email, phone, id);

        if (rowsAffected > 0) {
            return findById(id); // Fetch the updated record
        }
        return null;
    }

    // 5. DELETE
    public void deleteById(Long id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }



    private Customer mapRowToCustomer(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong("id"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        return customer;
    }
}
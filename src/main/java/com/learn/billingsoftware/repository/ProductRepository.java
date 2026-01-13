package com.learn.billingsoftware.repository;

import com.learn.billingsoftware.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. ADD
    public Product addProduct(Product product) {

        String sql = "INSERT INTO product (name, price, stock_quantity) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                product.getName(),
                product.getPrice(),
                product.getStockQuantity());

        return product;
    }

    // 2. FIND BY ID
    public Product findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";

        List<Product> result = jdbcTemplate.query(sql, this::mapRowToProduct, id);

        return result.isEmpty() ? null : result.get(0);
    }

    // 3. FIND ALL
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, this::mapRowToProduct);
    }

    // 4. UPDATE
    // Updates only price and stock based on your previous logic
    public Product updateProduct(Long id, Double price, Integer stock) {
        String sql = "UPDATE product SET price = ?, stock_quantity = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, price, stock, id);

        if (rowsAffected > 0) {
            return findById(id); // Return the updated object from DB
        }
        return null;
    }

    // 5. DELETE
    public void deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // --- Helper: RowMapper ---
    // Maps SQL columns to the Java Product object
    private Product mapRowToProduct(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("price"));
        // Make sure the column name matches your DB (e.g., 'stock_quantity' or 'stock')
        product.setStockQuantity(rs.getInt("stock_quantity"));
        return product;
    }
}
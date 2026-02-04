package com.learn.billingsoftware.service;

import com.learn.billingsoftware.entity.Product;
import com.learn.billingsoftware.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product updateProduct(Long id, Double price, Integer stock) {
        Product existing = findById(id);

        if (price != null) existing.setPrice(price);
        if (stock != null) existing.setStockQuantity(stock);

        return repository.save(existing);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
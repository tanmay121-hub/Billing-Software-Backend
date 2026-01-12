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

    //add
    public Product addproduct(Product product){
        repository.addProduct(product);
        return product;
    }

    // find by id
    public Product findById(Long id){
        return repository.findById(id);

    }

    // get product list
    public List<Product> findAll(){
        return repository.findAll();
    }

    //update
    public Product updateProduct(Long id, Double price, Integer stock) {
        return repository.updateProduct(id, price, stock);
    }

    // delete
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}

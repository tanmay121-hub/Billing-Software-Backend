package com.learn.billingsoftware.repository;

import com.learn.billingsoftware.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private  final ArrayList<Product> list = new ArrayList<>();

    // add
    private Long idCounter = 1L;
    public Product addProduct(Product product){
        product.setId(idCounter++);
        list.add(product);
        return product;
    }

    // find by id
    public Product findById(Long id){
        return list.stream().filter(product1 -> product1.getId().equals(id) ).findFirst().orElse(null);
    }

    // get product list
    public List<Product> findAll(){
        return list;
    }
    // update
    public Product updateProduct(Long id, Double price, Integer stock){
        Product existingProduct = findById(id);
        if (existingProduct != null){
            existingProduct.setPrice(price);
            existingProduct.setStockQuantity(stock);
        }
        return existingProduct;
    }

    // delete
    public void deleteById(Long id) {
        list.removeIf(p -> p.getId().equals(id));
    }
}

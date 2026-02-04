package com.learn.billingsoftware.service;

import com.learn.billingsoftware.entity.Customer;
import com.learn.billingsoftware.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer addCustomer(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    public Customer updateCustomer(Long id, String address, String email, String phone) {
        Customer existing = findById(id);


        if(address != null) existing.setAddress(address);
        if(email != null) existing.setEmail(email);
        if(phone != null) existing.setPhone(phone);

        return repository.save(existing);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
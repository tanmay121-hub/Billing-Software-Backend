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
    // add
    public void addCustomer(Customer customer){
        repository.addCustomer(customer);
    }

    // find by id
    public Customer findById(Long id){
        return repository.findById(id);
    }

    // get customer list
    public List<Customer> findAll(){
        return repository.findAll();
    }

    // update
    public Customer updateCustomer(Long id,String address,String email, String phone ){
        return repository.updateCustomer(id, address, email, phone);
    }

    // delete
    public void deleteById(Long id){
        repository.deleteById(id);
    }

}
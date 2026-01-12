package com.learn.billingsoftware.controller;

import com.learn.billingsoftware.entity.Customer;
import com.learn.billingsoftware.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        service.addCustomer(customer);
        return customer;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @RequestParam String address,
            @RequestParam String email,
            @RequestParam String phone
    ) {
        return service.updateCustomer(id, address, email, phone);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteById(id);
    }
}
package com.learn.billingsoftware.repository;

import com.learn.billingsoftware.entity.Customer;
import com.learn.billingsoftware.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CustomerRepository {

    private final List<Customer> list = new ArrayList<>();

    private Long idCounter = 1L;
    // add
    public Customer addCustomer(Customer customer){
        customer.setId(idCounter++);
        list.add(customer);
        return customer;
    }

    // find by id
    public Customer findById(Long id){
        return list.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // get customer list
    public List<Customer> findAll(){
        return list;
    }
    // update
    public Customer updateCustomer(Long id,String address,String email, String phone ){
        Customer existingCustomer = findById(id);
        if (existingCustomer != null){
            existingCustomer.setAddress(address);
            existingCustomer.setEmail(email);
            existingCustomer.setPhone(phone);
        }
        return existingCustomer;
    }

    // delete
    public void deleteById(Long id) {
        list.removeIf(p -> p.getId().equals(id));
    }
}

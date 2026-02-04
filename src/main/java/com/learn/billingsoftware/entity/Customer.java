package com.learn.billingsoftware.entity;

import jakarta.persistence.*;
import lombok.Data; // Generates Getters/Setters automatically

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private String address;
}
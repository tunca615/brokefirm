package com.ing.brokefirm.service.impl;

import com.ing.brokefirm.mapper.CustomerMapper;
import com.ing.brokefirm.model.Customer;
import com.ing.brokefirm.repository.CustomerRepository;
import com.ing.brokefirm.repository.entity.CustomerEntity;
import com.ing.brokefirm.service.ICustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;

    @Transactional
    public Customer registerCustomer(Customer customer) {
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already exist");
        }

        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        CustomerEntity customerEntity = CustomerEntity.builder().username(customer.getUsername()).password(encodedPassword).role("ROLE_USER").build();
        return customerMapper.customerEntityToCustomer(customerRepository.save(customerEntity));
    }

    public Customer findByUsername(String username) {
        return customerMapper.customerEntityToCustomer(customerRepository.findByUsername(username).orElse(null));
    }

    public List<Customer> listAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::customerEntityToCustomer
        ).toList();
    }
}

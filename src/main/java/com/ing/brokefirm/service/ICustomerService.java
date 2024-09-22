package com.ing.brokefirm.service;

import com.ing.brokefirm.model.Customer;

import java.util.List;

public interface ICustomerService {
    Customer registerCustomer(Customer customer);
    Customer findByUsername(String username);
    List<Customer> listAllCustomers();
}

package com.ing.brokefirm.controller;

import com.ing.brokefirm.controller.resource.CustomerResource;
import com.ing.brokefirm.controller.command.CustomerCommand;
import com.ing.brokefirm.mapper.CustomerMapper;
import com.ing.brokefirm.model.Customer;
import com.ing.brokefirm.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping
    public CustomerResource registerCustomer(@RequestBody CustomerCommand customerCommand) {
        Customer customer = customerService.registerCustomer(customerMapper.customerCommandToCustomer(customerCommand));
        return customerMapper.customerToCustomerResource(customer);
    }

    @GetMapping("/me")
    public CustomerResource getCurrentCustomer(UsernamePasswordAuthenticationToken principal) {
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        return customerMapper.customerToCustomerResource(customer);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<CustomerResource> listAllCustomers() {
        List<Customer> customers = customerService.listAllCustomers();
        return customers.stream().map(customerMapper::customerToCustomerResource).toList();
    }
}

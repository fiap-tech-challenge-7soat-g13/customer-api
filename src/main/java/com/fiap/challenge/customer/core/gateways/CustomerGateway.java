package com.fiap.challenge.customer.core.gateways;

import com.fiap.challenge.customer.core.domain.Customer;

import java.util.List;

public interface CustomerGateway {

    Customer save(Customer customer);

    List<Customer> findAll();

    List<Customer> findByDocument(String document);

    List<Customer> findByEmail(String email);

}

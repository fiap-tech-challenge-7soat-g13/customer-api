package com.fiap.challenge.customer.core.gateways;

import com.fiap.challenge.customer.core.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerGateway {

    Customer save(Customer customer);

    Customer findById(UUID id);

    List<Customer> findAll();

    List<Customer> findByDocument(String document);

    List<Customer> findByEmail(String email);

    void removeById(UUID id);

}

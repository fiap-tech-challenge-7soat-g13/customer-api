package com.fiap.challenge.customer.app.adapter.output.persistence.gateway;

import com.fiap.challenge.customer.app.adapter.output.externalapis.CredentialsClient;
import com.fiap.challenge.customer.app.adapter.output.persistence.entity.CustomerEntity;
import com.fiap.challenge.customer.app.adapter.output.persistence.mapper.CustomerMapper;
import com.fiap.challenge.customer.app.adapter.output.persistence.repository.CustomerRepository;
import com.fiap.challenge.customer.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.core.gateways.CustomerGateway;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerGatewayImpl implements CustomerGateway {

    private final CustomerMapper mapper;
    private final CustomerRepository repository;
    private final CredentialsClient credentialsClient;

    @Transactional
    public Customer save(Customer customer) {
        credentialsClient.create(customer.getEmail(), customer.getPassword());
        CustomerEntity customerEntity = mapper.toCustomerEntity(customer);
        CustomerEntity customerSave = repository.save(customerEntity);
        return mapper.toCustomer(customerSave);
    }

    public Optional<Customer> findById(Long id) {
        Optional<CustomerEntity> customerEntity = repository.findById(id);
        return customerEntity.map(mapper::toCustomer);
    }

    public List<Customer> findAll() {
        List<CustomerEntity> customerList = repository.findAll();
        return mapper.toCustomer(customerList);
    }

    @Transactional
    public List<Customer> findByDocument(String document) {
        List<CustomerEntity> customerList = repository.findByDocument(document);
        return mapper.toCustomer(customerList);
    }

    public List<Customer> findByEmail(String email) {
        List<CustomerEntity> customerList = repository.findByEmail(email);
        return mapper.toCustomer(customerList);
    }

    public void removeById(Long id) {
        CustomerEntity customerEntity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        credentialsClient.delete(customerEntity.getEmail());
        repository.delete(customerEntity);
    }

}

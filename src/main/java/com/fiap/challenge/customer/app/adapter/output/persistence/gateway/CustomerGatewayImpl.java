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
import java.util.UUID;

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

    public Customer findById(UUID id) {
        return mapper.toCustomer(repository.findById(id));
    }

    public List<Customer> findAll() {
        return mapper.toCustomer(repository.findAll());
    }

    @Transactional
    public List<Customer> findByDocument(String document) {
        return mapper.toCustomer(repository.findByDocument(document));
    }

    public List<Customer> findByEmail(String email) {
        return mapper.toCustomer(repository.findByEmail(email));
    }

    public void removeById(UUID id) {
        CustomerEntity customerEntity = repository.findById(id);
        if (customerEntity == null)
            throw new EntityNotFoundException();

        credentialsClient.delete(customerEntity.getEmail());
        repository.delete(customerEntity);
    }

}

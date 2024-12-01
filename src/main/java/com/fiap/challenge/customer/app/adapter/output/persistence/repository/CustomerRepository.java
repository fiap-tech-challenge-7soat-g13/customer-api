package com.fiap.challenge.customer.app.adapter.output.persistence.repository;

import com.fiap.challenge.customer.app.adapter.output.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository {

    List<CustomerEntity> findByDocument(String document);

    List<CustomerEntity> findByEmail(String email);

    CustomerEntity save(CustomerEntity customerEntity);

    CustomerEntity findById(UUID id);

    List<CustomerEntity> findAll();

    void delete(CustomerEntity customerEntity);

}

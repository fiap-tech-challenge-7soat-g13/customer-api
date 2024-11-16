package com.fiap.challenge.customer.app.adapter.output.persistence.repository;

import com.fiap.challenge.customer.app.adapter.output.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    List<CustomerEntity> findByDocument(String document);

    List<CustomerEntity> findByEmail(String email);

}

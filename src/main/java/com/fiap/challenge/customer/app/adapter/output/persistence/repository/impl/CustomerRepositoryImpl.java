package com.fiap.challenge.customer.app.adapter.output.persistence.repository.impl;

import com.fiap.challenge.customer.app.adapter.output.persistence.entity.CustomerEntity;
import com.fiap.challenge.customer.app.adapter.output.persistence.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<CustomerEntity> findByDocument(String document) {
        Query query = new Query(Criteria.where("document").is(document));

        return mongoTemplate.find(
                query,
                CustomerEntity.class
        );
    }

    @Override
    public List<CustomerEntity> findByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));

        return mongoTemplate.find(
                query,
                CustomerEntity.class
        );
    }

    @Override
    public CustomerEntity save(CustomerEntity customerEntity) {
        if (Objects.isNull(customerEntity.getId()))
            customerEntity.setId(UUID.randomUUID());

        return mongoTemplate.save(customerEntity);
    }

    @Override
    public CustomerEntity findById(UUID id) {
        return mongoTemplate.findById(id, CustomerEntity.class);
    }

    @Override
    public List<CustomerEntity> findAll() {
        return mongoTemplate.findAll(CustomerEntity.class);
    }

    @Override
    public void delete(CustomerEntity customerEntity) {
        mongoTemplate.remove(customerEntity);
    }

}

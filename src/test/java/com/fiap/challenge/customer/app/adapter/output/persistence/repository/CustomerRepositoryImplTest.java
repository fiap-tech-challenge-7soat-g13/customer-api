package com.fiap.challenge.customer.app.adapter.output.persistence.repository;

import com.fiap.challenge.customer.app.adapter.output.persistence.entity.CustomerEntity;
import com.fiap.challenge.customer.app.adapter.output.persistence.repository.impl.CustomerRepositoryImpl;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerRepositoryImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    private CustomerRepositoryImpl customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerRepository = new CustomerRepositoryImpl(mongoTemplate);
    }

    @Test
    void shouldSave() {
        CustomerEntity customerEntity = getCustomerEntityMock(null);
        CustomerEntity customerEntityWithId = getCustomerEntityMock(null);

        when(mongoTemplate.save(any())).thenReturn(customerEntityWithId);

        CustomerEntity customerEntitySave = customerRepository.save(customerEntity);

        verify(mongoTemplate).save(any());

        assertNotNull(customerEntitySave);
        assertEquals(customerEntitySave, customerEntityWithId);
    }

    @Test
    void shouldSaveWithId() {
        CustomerEntity customerEntity = getCustomerEntityMock(null);
        CustomerEntity customerEntityWithId = getCustomerEntityMock(UUID.randomUUID());

        when(mongoTemplate.save(any())).thenReturn(customerEntityWithId);

        CustomerEntity customerEntitySave = customerRepository.save(customerEntity);

        verify(mongoTemplate).save(any());

        assertNotNull(customerEntitySave);
        assertEquals(customerEntitySave, customerEntityWithId);
    }

    @Test
    void shouldFindByDocument() {
        CustomerEntity customerEntityWithId = getCustomerEntityMock(UUID.randomUUID());
        List<CustomerEntity> customerEntities = List.of(customerEntityWithId);

        when(mongoTemplate.find(any(Query.class), eq(CustomerEntity.class))).thenReturn(customerEntities);

        List<CustomerEntity> customersEntity = customerRepository.findByDocument(customerEntityWithId.getDocument());

        verify(mongoTemplate).find(any(Query.class), eq(CustomerEntity.class));

        assertNotNull(customersEntity);
        assertTrue(!customersEntity.isEmpty());
    }

    @Test
    void shouldFindByEmail() {
        CustomerEntity customerEntityWithId = getCustomerEntityMock(UUID.randomUUID());
        List<CustomerEntity> customerEntities = List.of(customerEntityWithId);

        when(mongoTemplate.find(any(Query.class), eq(CustomerEntity.class))).thenReturn(customerEntities);

        List<CustomerEntity> customersEntity = customerRepository.findByEmail(customerEntityWithId.getEmail());

        verify(mongoTemplate).find(any(Query.class), eq(CustomerEntity.class));

        assertNotNull(customersEntity);
        assertTrue(!customersEntity.isEmpty());
    }

    @Test
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        CustomerEntity customerEntityWithId = getCustomerEntityMock(id);

        when(mongoTemplate.findById(any(UUID.class), eq(CustomerEntity.class))).thenReturn(customerEntityWithId);

        CustomerEntity customerEntity = customerRepository.findById(customerEntityWithId.getId());

        verify(mongoTemplate).findById(any(UUID.class), eq(CustomerEntity.class));

        assertNotNull(customerEntity);
        assertEquals(id, customerEntity.getId());
    }

    @Test
    void shouldFindAll() {
        CustomerEntity customerEntityWithId = getCustomerEntityMock(UUID.randomUUID());
        List<CustomerEntity> customerEntities = List.of(customerEntityWithId);

        when(mongoTemplate.findAll(eq(CustomerEntity.class))).thenReturn(customerEntities);

        List<CustomerEntity> customersEntity = customerRepository.findAll();

        verify(mongoTemplate).findAll(eq(CustomerEntity.class));

        assertNotNull(customersEntity);
        assertTrue(!customersEntity.isEmpty());
    }

    @Test
    void shouldDelete() {
        UUID id = UUID.randomUUID();
        CustomerEntity customerEntityWithId = getCustomerEntityMock(id);

        when(mongoTemplate.remove(any(CustomerEntity.class))).thenReturn(DeleteResult.acknowledged(1));

        customerRepository.delete(customerEntityWithId);

        verify(mongoTemplate).remove(any(CustomerEntity.class));
    }

    private CustomerEntity getCustomerEntityMock(UUID id) {
        CustomerEntity customerEntity = new CustomerEntity();

        if (id != null)
            customerEntity.setId(id);
        else
            customerEntity.setId(UUID.randomUUID());

        customerEntity.setDocument("01234567890");
        customerEntity.setName("Usuário Teste");
        customerEntity.setEmail("teste123@teste.com.br");

        return customerEntity;
    }

}

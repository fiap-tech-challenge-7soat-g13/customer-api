package com.fiap.challenge.customer.app.adapter.output.persistence.gateway;

import com.fiap.challenge.customer.app.adapter.output.externalapis.CredentialsClient;
import com.fiap.challenge.customer.app.adapter.output.persistence.entity.CustomerEntity;
import com.fiap.challenge.customer.app.adapter.output.persistence.mapper.CustomerMapper;
import com.fiap.challenge.customer.app.adapter.output.persistence.repository.CustomerRepository;
import com.fiap.challenge.customer.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.customer.core.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerGatewayImplTest {

    private final CustomerMapper customerMapper = mock(CustomerMapper.class);

    private final CustomerRepository customerRepository = mock(CustomerRepository.class);

    private final CredentialsClient credentialsClient = mock(CredentialsClient.class);

    private final CustomerGatewayImpl customerGateway = new CustomerGatewayImpl(customerMapper, customerRepository, credentialsClient);

    @Test
    void shouldSave() {

        Customer customer = new Customer();

        customer.setEmail("email@email.com");
        customer.setPassword("password");

        CustomerEntity entity = new CustomerEntity();
        CustomerEntity savedEntity = new CustomerEntity();
        Customer expected = new Customer();

        when(customerMapper.toCustomerEntity(customer)).thenReturn(entity);
        when(customerRepository.save(entity)).thenReturn(savedEntity);
        when(customerMapper.toCustomer(savedEntity)).thenReturn(expected);

        Customer actual = customerGateway.save(customer);

        verify(credentialsClient).create(customer.getEmail(), customer.getPassword());
        verify(customerMapper).toCustomerEntity(customer);
        verify(customerRepository).save(entity);
        verify(customerMapper).toCustomer(savedEntity);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {

        UUID id = UUID.randomUUID();
        CustomerEntity entity = new CustomerEntity();
        Customer expected = new Customer();

        when(customerRepository.findById(id)).thenReturn(entity);
        when(customerMapper.toCustomer(entity)).thenReturn(expected);

        Customer actual = customerGateway.findById(id);

        verify(customerRepository).findById(id);
        verify(customerMapper).toCustomer(entity);

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindById() {

        UUID id = UUID.randomUUID();

        when(customerRepository.findById(id)).thenReturn(null);

        Customer actual = customerGateway.findById(id);

        verify(customerRepository).findById(id);
        verify(customerMapper, never()).toCustomer(any(CustomerEntity.class));

        assertNull(actual);
    }

    @Test
    void shouldFindAll() {

        List<CustomerEntity> entities = List.of(new CustomerEntity(), new CustomerEntity(), new CustomerEntity());
        List<Customer> expected = List.of(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(entities);
        when(customerMapper.toCustomer(entities)).thenReturn(expected);

        List<Customer> actual = customerGateway.findAll();

        verify(customerRepository).findAll();
        verify(customerMapper).toCustomer(entities);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByDocument() {

        String document = "document";
        List<CustomerEntity> entities = List.of(new CustomerEntity());
        List<Customer> expected = List.of(new Customer());

        when(customerRepository.findByDocument(document)).thenReturn(entities);
        when(customerMapper.toCustomer(entities)).thenReturn(expected);

        List<Customer> actual = customerGateway.findByDocument(document);

        verify(customerRepository).findByDocument(document);
        verify(customerMapper).toCustomer(entities);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByEmail() {

        String email = "email@email.com";
        List<CustomerEntity> entities = List.of(new CustomerEntity());
        List<Customer> expected = List.of(new Customer());

        when(customerRepository.findByEmail(email)).thenReturn(entities);
        when(customerMapper.toCustomer(entities)).thenReturn(expected);

        List<Customer> actual = customerGateway.findByEmail(email);

        verify(customerRepository).findByEmail(email);
        verify(customerMapper).toCustomer(entities);

        assertEquals(expected, actual);
    }


    @Test
    void shouldRemoveById() {

        UUID id = UUID.randomUUID();
        CustomerEntity entity = new CustomerEntity();
        entity.setEmail("email@email.com");

        when(customerRepository.findById(id)).thenReturn(entity);

        customerGateway.removeById(id);

        verify(customerRepository).findById(id);
        verify(credentialsClient).delete(entity.getEmail());
        verify(customerRepository).delete(entity);
    }

    @Test
    void shouldNotRemoveById() {

        UUID id = UUID.randomUUID();

        when(customerRepository.findById(id)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> customerGateway.removeById(id));

        verify(customerRepository).findById(id);
        verify(credentialsClient, never()).delete(any());
        verify(customerRepository, never()).delete(any());
    }

}

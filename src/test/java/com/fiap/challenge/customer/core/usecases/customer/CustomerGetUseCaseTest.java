package com.fiap.challenge.customer.core.usecases.customer;

import com.fiap.challenge.customer.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.core.gateways.CustomerGateway;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerGetUseCaseTest {

    private final CustomerGateway customerGateway = mock(CustomerGateway.class);

    private final CustomerGetUseCase customerGetUseCase = new CustomerGetUseCase(customerGateway);

    @Test
    void shouldGet() {

        UUID id = UUID.randomUUID();

        Customer expected = new Customer();

        when(customerGateway.findById(id)).thenReturn(expected);

        Customer actual = customerGetUseCase.execute(id);

        verify(customerGateway).findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowEntityNotFoundException() {

        UUID id = UUID.randomUUID();

        when(customerGateway.findById(id)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> customerGetUseCase.execute(id));

        verify(customerGateway).findById(id);
    }

}
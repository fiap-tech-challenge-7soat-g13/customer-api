package com.fiap.challenge.customer.core.usecases.customer;

import com.fiap.challenge.customer.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.core.gateways.CustomerGateway;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerGetUseCaseTest {

    private final CustomerGateway customerGateway = mock(CustomerGateway.class);

    private final CustomerGetUseCase customerGetUseCase = new CustomerGetUseCase(customerGateway);

    @Test
    void shouldGet() {

        Long id = 1L;

        Customer expected = new Customer();

        when(customerGateway.findById(id)).thenReturn(Optional.of(expected));

        Customer actual = customerGetUseCase.execute(id);

        verify(customerGateway).findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowEntityNotFoundException() {

        Long id = 1L;

        when(customerGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerGetUseCase.execute(id));

        verify(customerGateway).findById(id);
    }

}
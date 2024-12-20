package com.fiap.challenge.customer.core.usecases.customer;

import com.fiap.challenge.customer.core.gateways.CustomerGateway;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CustomerRemoveUseCaseTest {

    private final CustomerGateway customerGateway = mock(CustomerGateway.class);

    private final CustomerRemoveUseCase customerRemoveUseCase = new CustomerRemoveUseCase(customerGateway);

    @Test
    void shouldRemove() {

        UUID id = UUID.randomUUID();

        customerRemoveUseCase.execute(id);

        verify(customerGateway).removeById(id);
    }

}
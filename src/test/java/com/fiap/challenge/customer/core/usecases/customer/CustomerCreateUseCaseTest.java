package com.fiap.challenge.customer.core.usecases.customer;

import com.fiap.challenge.customer.core.common.validator.CustomerCreateValidator;
import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.core.gateways.CustomerGateway;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CustomerCreateUseCaseTest {

    private final CustomerGateway customerGateway = mock(CustomerGateway.class);

    private final CustomerCreateValidator customerCreateValidator = mock(CustomerCreateValidator.class);

    private final CustomerCreateUseCase customerCreateUseCase = new CustomerCreateUseCase(customerGateway, customerCreateValidator);

    @Test
    void shouldSave() {

        Customer customer = new Customer();

        customerCreateUseCase.execute(customer);

        verify(customerCreateValidator).validate(customer);
        verify(customerGateway).save(customer);
    }

}
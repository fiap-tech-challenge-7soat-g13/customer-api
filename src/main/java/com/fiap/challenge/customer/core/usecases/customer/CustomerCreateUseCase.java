package com.fiap.challenge.customer.core.usecases.customer;

import com.fiap.challenge.customer.core.common.validator.CustomerCreateValidator;
import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.core.gateways.CustomerGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerCreateUseCase {

    private final CustomerGateway customerGateway;
    private final CustomerCreateValidator validator;

    public Customer execute(Customer customer) {
        validator.validate(customer);
        return customerGateway.save(customer);
    }

}
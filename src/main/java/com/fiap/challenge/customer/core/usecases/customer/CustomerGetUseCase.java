package com.fiap.challenge.customer.core.usecases.customer;

import com.fiap.challenge.customer.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.core.gateways.CustomerGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerGetUseCase {

    private final CustomerGateway customerGateway;

    public Customer execute(UUID id) {
        Customer customer = customerGateway.findById(id);
        if (customer == null)
            throw new EntityNotFoundException();

        return customer;
    }

}
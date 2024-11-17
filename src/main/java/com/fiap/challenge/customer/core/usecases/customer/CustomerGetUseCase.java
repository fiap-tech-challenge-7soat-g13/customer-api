package com.fiap.challenge.customer.core.usecases.customer;

import com.fiap.challenge.customer.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.core.gateways.CustomerGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerGetUseCase {

    private final CustomerGateway customerGateway;

    public Customer execute(Long id) {
        return customerGateway.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
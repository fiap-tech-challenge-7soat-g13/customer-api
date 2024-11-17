package com.fiap.challenge.customer.core.usecases.customer;

import com.fiap.challenge.customer.core.gateways.CustomerGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerRemoveUseCase {

    private final CustomerGateway customerGateway;

    public void execute(Long id) {
        customerGateway.removeById(id);
    }

}
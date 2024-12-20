package com.fiap.challenge.customer.app.adapter.input.web;

import com.fiap.challenge.customer.app.adapter.input.web.dto.CustomerRequest;
import com.fiap.challenge.customer.app.adapter.input.web.dto.CustomerResponse;
import com.fiap.challenge.customer.app.adapter.input.web.mapper.CustomerRequestMapper;
import com.fiap.challenge.customer.app.adapter.input.web.mapper.CustomerResponseMapper;
import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.core.usecases.customer.CustomerCreateUseCase;
import com.fiap.challenge.customer.core.usecases.customer.CustomerGetUseCase;
import com.fiap.challenge.customer.core.usecases.customer.CustomerListUseCase;
import com.fiap.challenge.customer.core.usecases.customer.CustomerRemoveUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerCreateUseCase customerCreateUseCase;
    private final CustomerGetUseCase customerGetUseCase;
    private final CustomerRemoveUseCase customerRemoveUseCase;
    private final CustomerListUseCase customerListUseCase;
    private final CustomerRequestMapper customerRequestMapper;
    private final CustomerResponseMapper customerResponseMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@RequestBody CustomerRequest customerRequest) {
        Customer customer = customerRequestMapper.toCustomer(customerRequest);
        Customer customerSave = customerCreateUseCase.execute(customer);
        return customerResponseMapper.toCustomerResponse(customerSave);
    }

    @GetMapping("/{id}")
    public CustomerResponse get(@PathVariable UUID id) {
        Customer customer = customerGetUseCase.execute(id);
        return customerResponseMapper.toCustomerResponse(customer);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable UUID id) {
        customerRemoveUseCase.execute(id);
    }

    @GetMapping
    public List<CustomerResponse> list(@RequestParam(required = false) String document) {
        List<Customer> customerList = customerListUseCase.execute(document);
        return customerResponseMapper.toCustomer(customerList);
    }

}

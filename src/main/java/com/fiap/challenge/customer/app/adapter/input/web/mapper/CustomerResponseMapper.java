package com.fiap.challenge.customer.app.adapter.input.web.mapper;

import com.fiap.challenge.customer.app.adapter.input.web.dto.CustomerResponse;
import com.fiap.challenge.customer.core.domain.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {

    CustomerResponse toCustomerResponse(Customer customer);

    List<CustomerResponse> toCustomer(List<Customer> customers);

}

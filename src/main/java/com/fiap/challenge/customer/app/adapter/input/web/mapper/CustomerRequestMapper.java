package com.fiap.challenge.customer.app.adapter.input.web.mapper;

import com.fiap.challenge.customer.app.adapter.input.web.dto.CustomerRequest;
import com.fiap.challenge.customer.core.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {

    Customer toCustomer(CustomerRequest customerRequest);

}

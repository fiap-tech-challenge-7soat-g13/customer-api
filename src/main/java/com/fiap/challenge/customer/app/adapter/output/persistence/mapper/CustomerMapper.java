package com.fiap.challenge.customer.app.adapter.output.persistence.mapper;

import com.fiap.challenge.customer.app.adapter.output.persistence.entity.CustomerEntity;
import com.fiap.challenge.customer.core.domain.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerEntity toCustomerEntity(Customer customer);

    Customer toCustomer(CustomerEntity customerEntity);

    List<Customer> toCustomer(List<CustomerEntity> customerEntities);

}

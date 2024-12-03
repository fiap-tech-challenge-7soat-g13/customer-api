package com.fiap.challenge.customer.util;

import com.fiap.challenge.customer.app.adapter.input.web.dto.CustomerRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataHelper {

    public static CustomerRequest createCustomerRequest() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("Nome 1");
        customerRequest.setEmail("teste@teste.com.br");
        customerRequest.setDocument("01234567890");
        customerRequest.setPassword("123456");
        return customerRequest;
    }

}

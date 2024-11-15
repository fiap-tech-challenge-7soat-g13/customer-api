package com.fiap.challenge.customer.app.adapter.input.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    private String name;
    private String email;
    private String document;
    private String password;

}
package com.fiap.challenge.customer.app.adapter.output.externalapis;

public interface CredentialsClient {

    void create(String email, String password);

    void delete(String email);

}

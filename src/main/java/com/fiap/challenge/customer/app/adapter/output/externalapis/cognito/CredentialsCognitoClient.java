package com.fiap.challenge.customer.app.adapter.output.externalapis.cognito;

import com.fiap.challenge.customer.app.adapter.output.externalapis.CredentialsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminDeleteUserRequest;

@Service
public class CredentialsCognitoClient implements CredentialsClient {

    private final String userPoolId;

    private final CognitoIdentityProviderClient cognitoIdentityProviderClient;

    public CredentialsCognitoClient(@Value("${application.cognito.userPoolId}") String userPoolId, CognitoIdentityProviderClient cognitoIdentityProviderClient) {
        this.userPoolId = userPoolId;
        this.cognitoIdentityProviderClient = cognitoIdentityProviderClient;
    }

    @Override
    public void create(String email, String password) {

        AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                .userPoolId(userPoolId)
                .username(email)
                .temporaryPassword(password)
                .build();

        cognitoIdentityProviderClient.adminCreateUser(request);
    }

    @Override
    public void delete(String email) {

        AdminDeleteUserRequest request = AdminDeleteUserRequest.builder()
                .userPoolId(userPoolId)
                .username(email)
                .build();

        cognitoIdentityProviderClient.adminDeleteUser(request);
    }

}

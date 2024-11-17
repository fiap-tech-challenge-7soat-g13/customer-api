package com.fiap.challenge.customer.app.adapter.output.externalapis.cognito;

import com.fiap.challenge.customer.app.adapter.output.externalapis.SignUpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;

@Service
public class SignUpCognitoClient implements SignUpClient {

    private final String userPoolId;

    private final CognitoIdentityProviderClient cognitoIdentityProviderClient;

    public SignUpCognitoClient(@Value("${application.cognito.user-pool-id}") String userPoolId, CognitoIdentityProviderClient cognitoIdentityProviderClient) {
        this.userPoolId = userPoolId;
        this.cognitoIdentityProviderClient = cognitoIdentityProviderClient;
    }

    @Override
    public void signUp(String email, String password) {

        AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                .userPoolId(userPoolId)
                .username(email)
                .temporaryPassword(password)
                .build();

        cognitoIdentityProviderClient.adminCreateUser(request);
    }

}

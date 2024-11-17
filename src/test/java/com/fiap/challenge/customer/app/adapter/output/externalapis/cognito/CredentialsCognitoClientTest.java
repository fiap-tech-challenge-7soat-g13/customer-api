package com.fiap.challenge.customer.app.adapter.output.externalapis.cognito;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminDeleteUserRequest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CredentialsCognitoClientTest {

    private final String userPoolId = "userPoolId";

    private final CognitoIdentityProviderClient cognitoIdentityProviderClient = mock(CognitoIdentityProviderClient.class);

    private final CredentialsCognitoClient credentialsCognitoClient = new CredentialsCognitoClient(userPoolId, cognitoIdentityProviderClient);

    @Test
    void shouldCreate() {

        String email = "email@email.com";
        String password = "password";

        assertDoesNotThrow(() -> credentialsCognitoClient.create(email, password));

        ArgumentCaptor<AdminCreateUserRequest> argument = ArgumentCaptor.forClass(AdminCreateUserRequest.class);

        verify(cognitoIdentityProviderClient).adminCreateUser(argument.capture());

        assertEquals(userPoolId, argument.getValue().userPoolId());
        assertEquals(email, argument.getValue().username());
        assertEquals(password, argument.getValue().temporaryPassword());
    }

    @Test
    void shouldDelete() {

        String email = "email@email.com";

        assertDoesNotThrow(() -> credentialsCognitoClient.delete(email));

        ArgumentCaptor<AdminDeleteUserRequest> argument = ArgumentCaptor.forClass(AdminDeleteUserRequest.class);

        verify(cognitoIdentityProviderClient).adminDeleteUser(argument.capture());

        assertEquals(userPoolId, argument.getValue().userPoolId());
        assertEquals(email, argument.getValue().username());
    }

}

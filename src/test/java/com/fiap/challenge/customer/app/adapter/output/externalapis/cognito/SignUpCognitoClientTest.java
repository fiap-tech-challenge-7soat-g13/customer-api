package com.fiap.challenge.customer.app.adapter.output.externalapis.cognito;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SignUpCognitoClientTest {

    private final String userPoolId = "userPoolId";

    private final CognitoIdentityProviderClient cognitoIdentityProviderClient = mock(CognitoIdentityProviderClient.class);

    private final SignUpCognitoClient signUpCognitoClient = new SignUpCognitoClient(userPoolId, cognitoIdentityProviderClient);

    @Test
    void shouldNotThrowAnyException() {

        String email = "email@email.com";
        String password = "password";

        assertDoesNotThrow(() -> signUpCognitoClient.signUp(email, password));

        ArgumentCaptor<AdminCreateUserRequest> argument = ArgumentCaptor.forClass(AdminCreateUserRequest.class);

        verify(cognitoIdentityProviderClient).adminCreateUser(argument.capture());

        assertEquals(userPoolId, argument.getValue().userPoolId());
        assertEquals(email, argument.getValue().username());
        assertEquals(password, argument.getValue().temporaryPassword());
    }

}

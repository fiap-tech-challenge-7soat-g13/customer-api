package com.fiap.challenge.customer.core.common.validator;

import com.fiap.challenge.customer.core.common.exception.InvalidDataException;
import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.core.gateways.CustomerGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

class CustomerCreateValidatorTest {

    public static Stream<Arguments> shouldValidatePassword() {
        return Stream.of(
                arguments(null, "É obrigatório informar a senha"),
                arguments("aA@1", "A senha deve conter ao menos 8 caracteres"),
                arguments("aaaaaaaa", "A senha deve conter letras maiúsculas, minúsculas, números e caracteres especiais")
        );
    }

    private final CustomerGateway customerGateway = mock(CustomerGateway.class);

    private final CustomerCreateValidator customerCreateValidator = new CustomerCreateValidator(customerGateway);

    @Test
    void shouldBeValid() {

        Customer customer = createValidCustomer();

        when(customerGateway.findByDocument(customer.getDocument())).thenReturn(Collections.emptyList());
        when(customerGateway.findByEmail(customer.getEmail())).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> customerCreateValidator.validate(customer));

        verify(customerGateway).findByDocument(customer.getDocument());
        verify(customerGateway).findByEmail(customer.getEmail());
    }

    @Test
    void shouldValidateEmptyName() {

        Customer customer = createValidCustomer();

        customer.setName(null);

        when(customerGateway.findByDocument(customer.getDocument())).thenReturn(Collections.emptyList());
        when(customerGateway.findByEmail(customer.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> customerCreateValidator.validate(customer));

        assertEquals(List.of("É obrigatório informar o nome"), exception.getMessages());

        verify(customerGateway).findByDocument(customer.getDocument());
        verify(customerGateway).findByEmail(customer.getEmail());
    }

    @Test
    void shouldValidateEmptyEmail() {

        Customer customer = createValidCustomer();

        customer.setEmail(null);

        when(customerGateway.findByDocument(customer.getDocument())).thenReturn(Collections.emptyList());
        when(customerGateway.findByEmail(customer.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> customerCreateValidator.validate(customer));

        assertEquals(List.of("É obrigatório informar o e-mail"), exception.getMessages());

        verify(customerGateway).findByDocument(customer.getDocument());
        verify(customerGateway, never()).findByEmail(customer.getEmail());
    }

    @Test
    void shouldValidateInvalidEmail() {

        Customer customer = createValidCustomer();

        customer.setEmail("email");

        when(customerGateway.findByDocument(customer.getDocument())).thenReturn(Collections.emptyList());
        when(customerGateway.findByEmail(customer.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> customerCreateValidator.validate(customer));

        assertEquals(List.of("O e-mail informado é inválido"), exception.getMessages());

        verify(customerGateway).findByDocument(customer.getDocument());
        verify(customerGateway).findByEmail(customer.getEmail());
    }

    @Test
    void shouldValidateEmailAlreadyExists() {

        Customer customer = createValidCustomer();

        when(customerGateway.findByDocument(customer.getDocument())).thenReturn(Collections.emptyList());
        when(customerGateway.findByEmail(customer.getEmail())).thenReturn(List.of(new Customer()));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> customerCreateValidator.validate(customer));

        assertEquals(List.of(String.format("Já existe um cliente com o e-mail '%s'", customer.getEmail())), exception.getMessages());

        verify(customerGateway).findByDocument(customer.getDocument());
        verify(customerGateway).findByEmail(customer.getEmail());
    }

    @Test
    void shouldValidateEmptyDocument() {

        Customer customer = createValidCustomer();

        customer.setDocument(null);

        when(customerGateway.findByDocument(customer.getDocument())).thenReturn(Collections.emptyList());
        when(customerGateway.findByEmail(customer.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> customerCreateValidator.validate(customer));

        assertEquals(List.of("É obrigatório informar o documento"), exception.getMessages());

        verify(customerGateway, never()).findByDocument(customer.getDocument());
        verify(customerGateway).findByEmail(customer.getEmail());
    }

    @Test
    void shouldValidateInvalidDocument() {

        Customer customer = createValidCustomer();

        customer.setDocument("document");

        when(customerGateway.findByDocument(customer.getDocument())).thenReturn(Collections.emptyList());
        when(customerGateway.findByEmail(customer.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> customerCreateValidator.validate(customer));

        assertEquals(List.of("O documento informado é inválido"), exception.getMessages());

        verify(customerGateway).findByDocument(customer.getDocument());
        verify(customerGateway).findByEmail(customer.getEmail());
    }

    @Test
    void shouldValidateDocumentAlreadyExists() {

        Customer customer = createValidCustomer();

        when(customerGateway.findByDocument(customer.getDocument())).thenReturn(List.of(new Customer()));
        when(customerGateway.findByEmail(customer.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> customerCreateValidator.validate(customer));

        assertEquals(List.of(String.format("Já existe um cliente com o documento '%s'", customer.getDocument())), exception.getMessages());

        verify(customerGateway).findByDocument(customer.getDocument());
        verify(customerGateway).findByEmail(customer.getEmail());
    }

    @ParameterizedTest
    @MethodSource
    void shouldValidatePassword(String password, String message) {

        Customer customer = createValidCustomer();

        customer.setPassword(password);

        when(customerGateway.findByDocument(customer.getDocument())).thenReturn(Collections.emptyList());
        when(customerGateway.findByEmail(customer.getEmail())).thenReturn(Collections.emptyList());

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> customerCreateValidator.validate(customer));

        assertEquals(List.of(message), exception.getMessages());

        verify(customerGateway).findByDocument(customer.getDocument());
        verify(customerGateway).findByEmail(customer.getEmail());
    }

    private Customer createValidCustomer() {

        Customer customer = new Customer();

        customer.setName("Bill Gates");
        customer.setEmail("bill.gates@microsoft.com");
        customer.setDocument("44867508020");
        customer.setPassword("abc1@XYZ");

        return customer;
    }

}
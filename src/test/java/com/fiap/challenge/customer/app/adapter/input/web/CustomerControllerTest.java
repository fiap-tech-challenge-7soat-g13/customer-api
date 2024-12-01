package com.fiap.challenge.customer.app.adapter.input.web;

import com.fiap.challenge.customer.app.adapter.input.web.dto.CustomerRequest;
import com.fiap.challenge.customer.app.adapter.input.web.dto.CustomerResponse;
import com.fiap.challenge.customer.app.adapter.input.web.mapper.CustomerRequestMapper;
import com.fiap.challenge.customer.app.adapter.input.web.mapper.CustomerResponseMapper;
import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.core.usecases.customer.CustomerCreateUseCase;
import com.fiap.challenge.customer.core.usecases.customer.CustomerGetUseCase;
import com.fiap.challenge.customer.core.usecases.customer.CustomerListUseCase;
import com.fiap.challenge.customer.core.usecases.customer.CustomerRemoveUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerCreateUseCase customerCreateUseCase;
    @Mock
    private CustomerGetUseCase customerGetUseCase;
    @Mock
    private CustomerRemoveUseCase customerRemoveUseCase;
    @Mock
    private CustomerListUseCase customerListUseCase;

    private final CustomerRequestMapper customerRequestMapper = mock(CustomerRequestMapper.class);
    private final CustomerResponseMapper customerResponseMapper = mock(CustomerResponseMapper.class);

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSave() {
        CustomerRequest customerRequestMock = getCustomerRequestMock();
        Customer expected = getCustomerMock(null);
        UUID id = UUID.randomUUID();
        Customer expectedWithId = getCustomerMock(id);
        CustomerResponse customerResponseMock = getCustomerResponseMock(id);

        when(customerRequestMapper.toCustomer(customerRequestMock)).thenReturn(expected);
        when(customerCreateUseCase.execute(expected)).thenReturn(expectedWithId);
        when(customerResponseMapper.toCustomerResponse(expectedWithId)).thenReturn(customerResponseMock);

        CustomerResponse customerResponse = customerController.create(customerRequestMock);
        verify(customerRequestMapper, times(1)).toCustomer(any());
        verify(customerCreateUseCase, times(1)).execute(any());
        verify(customerResponseMapper, times(1)).toCustomerResponse(any());
        assertNotNull(customerResponse);
        assertNotNull(customerResponse.getId());
        assertEquals(customerResponse.getId(), id);
    }

    @Test
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        Customer expectedWithId = getCustomerMock(id);
        CustomerResponse customerResponseMock = getCustomerResponseMock(id);

        when(customerGetUseCase.execute(id)).thenReturn(expectedWithId);
        when(customerResponseMapper.toCustomerResponse(expectedWithId)).thenReturn(customerResponseMock);

        CustomerResponse customerResponse = customerController.get(id);
        verify(customerGetUseCase, times(1)).execute(any());
        verify(customerResponseMapper, times(1)).toCustomerResponse(any());
        assertNotNull(customerResponse);
        assertNotNull(customerResponse.getId());
        assertEquals(customerResponse.getId(), id);
    }

    @Test
    void shouldRemove() {
        UUID id = UUID.randomUUID();
        Customer expectedWithId = getCustomerMock(id);
        CustomerResponse customerResponseMock = getCustomerResponseMock(id);

        doNothing().when(customerRemoveUseCase).execute(any());

        customerController.remove(id);
        verify(customerRemoveUseCase, times(1)).execute(any());
    }

    @Test
    void shouldListByDocument() {
        UUID id = UUID.randomUUID();
        List<Customer> customers = List.of(getCustomerMock(id));
        CustomerResponse customerResponseMock = getCustomerResponseMock(id);

        when(customerListUseCase.execute(customerResponseMock.getDocument())).thenReturn(customers);
        when(customerResponseMapper.toCustomer(customers)).thenReturn(List.of(customerResponseMock));

        List<CustomerResponse> customersResponse = customerController.list(customerResponseMock.getDocument());
        verify(customerListUseCase, times(1)).execute(any());
        verify(customerResponseMapper, times(1)).toCustomer(any());
        assertNotNull(customersResponse);
        assertTrue(!customersResponse.isEmpty());
    }

    private CustomerRequest getCustomerRequestMock() {
        CustomerRequest customerRequest = new CustomerRequest();

        customerRequest.setDocument("01234567890");
        customerRequest.setName("Usuário Teste");
        customerRequest.setPassword("Teste@123");
        customerRequest.setEmail("teste123@teste.com.br");

        return customerRequest;
    }

    private Customer getCustomerMock(UUID id) {
        Customer customer = new Customer();

        if (id != null)
            customer.setId(id);

        customer.setDocument("01234567890");
        customer.setName("Usuário Teste");
        customer.setPassword("Teste@123");
        customer.setEmail("teste123@teste.com.br");

        return customer;
    }

    private CustomerResponse getCustomerResponseMock(UUID id) {
        CustomerResponse customerResponse = new CustomerResponse();

        if (id != null)
            customerResponse.setId(id);
        else
            customerResponse.setId(UUID.randomUUID());

        customerResponse.setDocument("01234567890");
        customerResponse.setName("Usuário Teste");
        customerResponse.setEmail("teste123@teste.com.br");

        return customerResponse;
    }

}

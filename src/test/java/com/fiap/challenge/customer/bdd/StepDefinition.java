package com.fiap.challenge.customer.bdd;

import com.fiap.challenge.customer.core.domain.Customer;
import com.fiap.challenge.customer.util.DataHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinition {

    private Response response;

    private Customer customer;

    @Quando("criar um novo usuario")
    public void criar_um_novo_usuario() {
        response = given()
                .contentType(ContentType.JSON)
                .body(DataHelper.createCustomerRequest())
                .when()
                .post();
    }

    @Entao("deve retornar sucesso")
    public void deve_retornar_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Entao("deve retornar os dados do usuario")
    public void deve_retornar_os_dados_do_usuario() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/CustomerResponse.schema.json"));
    }

    @Dado("um usuario que existe")
    public void um_usuario_que_existe() {
        customer = new Customer();
        customer.setName("Nome 1");
        customer.setEmail("teste@teste.com.br");
        customer.setDocument("01234567890");
        customer.setPassword("123456");
    }

    @Quando("obter o usuario")
    public void obter_o_usuario() {
        response = when()
                .get("/{id}", customer.getId());
    }

    @Dado("um usuario que não existe")
    public void um_usuario_que_não_existe() {
        customer = new Customer();
        customer.setName("Nome 99");
        customer.setEmail("teste99@teste99.com.br");
        customer.setDocument("99999999999");
        customer.setPassword("999999");
    }

    @Entao("deve retornar não encontrado")
    public void deve_retornar_não_encontrado() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}

package com.fiap.challenge.customer.app.adapter.output.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Entity
@Document(collection = "customer")
public class CustomerEntity {

    @Id
    private UUID id;
    private String name;
    private String email;
    private String document;

}

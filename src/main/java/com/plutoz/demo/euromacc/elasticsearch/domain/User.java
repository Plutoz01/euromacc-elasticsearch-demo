package com.plutoz.demo.euromacc.elasticsearch.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@Data
@Document(indexName = "user")
public class User {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}

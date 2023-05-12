package com.plutoz.demo.euromacc.elasticsearch.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "user")
public class User {
    // TODO: consider to use UUID instead
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}

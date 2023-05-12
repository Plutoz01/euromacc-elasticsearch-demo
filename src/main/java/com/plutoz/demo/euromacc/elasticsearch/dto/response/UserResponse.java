package com.plutoz.demo.euromacc.elasticsearch.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private String id; // TODO: consider using UUID instead
    private String firstName;
    private String lastName;
    private String email;
}

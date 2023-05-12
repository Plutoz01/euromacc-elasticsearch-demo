package com.plutoz.demo.euromacc.elasticsearch.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSearchRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}

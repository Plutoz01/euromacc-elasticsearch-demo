package com.plutoz.demo.euromacc.elasticsearch.converter;

import com.plutoz.demo.euromacc.elasticsearch.domain.User;
import com.plutoz.demo.euromacc.elasticsearch.dto.request.CreateUserRequest;
import com.plutoz.demo.euromacc.elasticsearch.dto.response.UserResponse;

public interface UserConverter {
    UserResponse toResponse(User user);

    User toModel(CreateUserRequest request);
}

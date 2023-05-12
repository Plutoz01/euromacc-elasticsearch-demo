package com.plutoz.demo.euromacc.elasticsearch.helper;

import com.plutoz.demo.euromacc.elasticsearch.domain.User;
import com.plutoz.demo.euromacc.elasticsearch.dto.response.UserResponse;

import java.util.UUID;

public class UserTestHelper {
    public static User.UserBuilder getTestUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Test firstName")
                .lastName("Test lastName")
                .email("test@email.com");
    }

    public static UserResponse.UserResponseBuilder getTestUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail());
    }
}

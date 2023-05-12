package com.plutoz.demo.euromacc.elasticsearch.controller;


import com.plutoz.demo.euromacc.elasticsearch.converter.UserConverter;
import com.plutoz.demo.euromacc.elasticsearch.dto.request.CreateUserRequest;
import com.plutoz.demo.euromacc.elasticsearch.dto.request.UserSearchRequest;
import com.plutoz.demo.euromacc.elasticsearch.dto.response.UserResponse;
import com.plutoz.demo.euromacc.elasticsearch.dto.response.UserSearchResponse;
import com.plutoz.demo.euromacc.elasticsearch.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/elasticsearch")
public class UserController {
    private final UserService userService;
    private final UserConverter converter;

    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.converter = userConverter;
    }

    @PostMapping("/create")
    // Since there is no additional requirements would require ResponseEntity, I'd prefer to use the simpler solution by returning the response class directly instead of an extra wrapping (YAGNI)
    public UserResponse createUser(@NotNull @Valid @RequestBody final CreateUserRequest request) {
        return converter.toResponse(userService.create(converter.toModel(request)));
    }

    // Since there is no additional requirements would require ResponseEntity, I'd prefer to use the simpler solution by returning the response class directly instead of an extra wrapping (YAGNI)
    @PostMapping("/search")
    public UserSearchResponse searchUser(@NotNull @Valid @RequestBody final UserSearchRequest request) {
        List<UserResponse> userList = userService.findByName(request.getFirstName(), request.getLastName()).stream()
                .map(converter::toResponse)
                .toList();

        return UserSearchResponse.builder()
                .userList(userList)
                .total((long) userList.size())
                .build();
    }
}

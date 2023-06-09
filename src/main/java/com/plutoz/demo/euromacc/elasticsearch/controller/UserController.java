package com.plutoz.demo.euromacc.elasticsearch.controller;


import com.plutoz.demo.euromacc.elasticsearch.converter.UserConverter;
import com.plutoz.demo.euromacc.elasticsearch.domain.User;
import com.plutoz.demo.euromacc.elasticsearch.dto.request.CreateUserRequest;
import com.plutoz.demo.euromacc.elasticsearch.dto.request.UserSearchRequest;
import com.plutoz.demo.euromacc.elasticsearch.dto.response.ErrorResponse;
import com.plutoz.demo.euromacc.elasticsearch.dto.response.UserResponse;
import com.plutoz.demo.euromacc.elasticsearch.dto.response.UserSearchResponse;
import com.plutoz.demo.euromacc.elasticsearch.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    // According to YAGNI, using ResponseEntity<T> would be an overkill, so I'd rather keep code simple and more readable
    public UserResponse createUser(@NotNull @Valid @RequestBody final CreateUserRequest request) {
        User newUser = userService.create(converter.toModel(request));

        return converter.toResponse(newUser);
    }

    @PostMapping("/search")
    // According to YAGNI, using ResponseEntity<T> would be an overkill, so I'd rather keep code simple and more readable
    public UserSearchResponse searchUser(@NotNull @Valid @RequestBody final UserSearchRequest request) {
        List<UserResponse> userList = userService.findByName(request.getFirstName(), request.getLastName()).stream()
                .map(converter::toResponse)
                .toList();

        return UserSearchResponse.builder()
                .userList(userList)
                .total((long) userList.size())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        return new ErrorResponse(MethodArgumentNotValidException.errorsToStringList(ex.getAllErrors()));
    }
}

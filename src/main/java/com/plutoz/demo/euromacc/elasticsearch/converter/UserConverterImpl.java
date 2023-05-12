package com.plutoz.demo.euromacc.elasticsearch.converter;

import com.plutoz.demo.euromacc.elasticsearch.domain.User;
import com.plutoz.demo.euromacc.elasticsearch.dto.request.CreateUserRequest;
import com.plutoz.demo.euromacc.elasticsearch.dto.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserConverterImpl implements UserConverter {
    private final ModelMapper modelMapper;

    public UserConverterImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse toResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public User toModel(CreateUserRequest request) {
        return modelMapper.map(request, User.class);
    }
}

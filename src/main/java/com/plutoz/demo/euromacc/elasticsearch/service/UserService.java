package com.plutoz.demo.euromacc.elasticsearch.service;

import com.plutoz.demo.euromacc.elasticsearch.domain.User;

import java.util.Collection;

public interface UserService {
    User create(User newEntity);
    Collection<User> findByName(String firstName, String lastName);
}

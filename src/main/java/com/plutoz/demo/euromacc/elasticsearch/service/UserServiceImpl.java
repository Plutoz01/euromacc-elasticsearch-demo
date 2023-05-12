package com.plutoz.demo.euromacc.elasticsearch.service;

import com.plutoz.demo.euromacc.elasticsearch.domain.User;
import com.plutoz.demo.euromacc.elasticsearch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User newEntity) {
        return repository.save(newEntity);
    }

    @Override
    public Collection<User> findByName(String firstName, String lastName) {
        return repository.findByFirstNameLikeAndLastNameLike(firstName, lastName);
    }
}

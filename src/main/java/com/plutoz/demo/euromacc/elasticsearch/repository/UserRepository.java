package com.plutoz.demo.euromacc.elasticsearch.repository;

import com.plutoz.demo.euromacc.elasticsearch.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends ElasticsearchRepository<User, UUID> {
    List<User> findByFirstNameLikeAndLastNameLike(String firstName, String lastName);
}

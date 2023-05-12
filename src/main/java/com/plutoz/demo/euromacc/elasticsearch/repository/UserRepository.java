package com.plutoz.demo.euromacc.elasticsearch.repository;

import com.plutoz.demo.euromacc.elasticsearch.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<User, String> {
    List<User> findByFirstNameLikeAndLastNameLike(String firstName, String lastName);
}

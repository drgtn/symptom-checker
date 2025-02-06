package com.healthily.repository;

import com.healthily.model.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findByEmail(String email);

    void remove(String email);

}

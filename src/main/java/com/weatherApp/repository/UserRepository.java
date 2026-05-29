package com.weatherApp.repository;

import com.weatherApp.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(long id);

    Optional<User> findByLogin(String login);

    List<User> findAll();

    void delete(User user);

    void deleteById(long id);
}

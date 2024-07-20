package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);

    Optional<User> findById(Integer id);

    List<User> findAll();

    void update(User user);

    void delete(Integer id);

    User createUser();

    User getUserByUsername(String username);
}

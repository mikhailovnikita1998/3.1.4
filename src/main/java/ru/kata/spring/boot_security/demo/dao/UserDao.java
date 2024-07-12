package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    User findById(Integer id);

    List<User> findAll();

    void update(User updatedUser);

    void delete(int id);
    User getUserByUsername(String username);


}

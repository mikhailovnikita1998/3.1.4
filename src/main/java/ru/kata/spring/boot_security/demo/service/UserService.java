package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

     User findById(Integer id);

    List<User> findAll();

    public void update(User user);

    void delete(int id);
    User createUser();

    User getUserByUsername(String username);


}

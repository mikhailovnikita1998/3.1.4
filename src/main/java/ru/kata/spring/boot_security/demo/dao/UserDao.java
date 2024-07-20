package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    // Добавление пользователя
    void add(User user);

    // Поиск пользователя по ID с использованием Optional для обработки случая отсутствия записи
    Optional<User> findById(Integer id);

    // Получение всех пользователей
    List<User> findAll();

    // Обновление пользователя
    void update(User updatedUser);

    // Удаление пользователя по ID
    void delete(Integer id);

    // Поиск пользователя по имени пользователя (или email)
    Optional<User> getUserByUsername(String username);
}

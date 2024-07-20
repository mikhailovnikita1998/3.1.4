package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    // Конструктор для инъекции EntityManager
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public void update(User updatedUser) {
        if (entityManager.contains(updatedUser)) {
            entityManager.merge(updatedUser);
        } else {
            throw new EntityNotFoundException("User not found with id: " + updatedUser.getId());
        }
    }

    @Override
    public void delete(Integer id) {
        User user = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        entityManager.remove(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", username);
        return query.getResultStream().findFirst();
    }
}


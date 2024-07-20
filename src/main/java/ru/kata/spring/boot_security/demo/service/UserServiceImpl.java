package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleDao.getRolesByName(user.getRoles()));
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Integer id) {
        return userDao.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    @Override
    public void update(User updatedUser) {
        // Retrieve existing user from database
        User existingUser = userDao.findById(updatedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + updatedUser.getId()));

        // Update user fields, except password
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setRoles(roleDao.getRolesByName(updatedUser.getRoles()));

        // If password is changed, encode the new password
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Update user in the database
        userDao.update(existingUser);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public User createUser() {
        User user = new User();
        Role roleUser = roleDao.getRoleByName("ROLE_USER");
        user.addRole(roleUser);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username).orElse(null);
    }
}

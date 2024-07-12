package ru.kata.spring.boot_security.demo.service;



import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    final PasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, @Lazy PasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }
@Transactional
    @Override
    public void add(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleDao.getRolesByName(user.getRoles()));
        userDao.add(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void update(User user) {

        user.setRoles(roleDao.getRolesByName(user.getRoles()));
        userDao.update(user);

    }

    @Override
    @Transactional
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public User createUser() {
        User user = new User();
        Role roleUser = roleDao.getRoleByName("ROLE_USER");
        user.addRole(roleUser);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }



}

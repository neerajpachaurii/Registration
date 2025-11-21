package com.example.service;

import org.springframework.transaction.annotation.Transactional;
import com.example.dao.UserDAO;
import com.example.model.User;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @Transactional
    public boolean authenticate(String username, String password) {
        User u = userDAO.findByUsername(username);
        return u != null && u.getPassword().equals(password);
    }

    @Override
    @Transactional
    public boolean userExists(String username) {
        return userDAO.userExists(username);
    }
}

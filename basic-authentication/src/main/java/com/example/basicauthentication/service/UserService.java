package com.example.basicauthentication.service;

import com.example.basicauthentication.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserByEmail(String name);
    List<User> getAllUsers();
}

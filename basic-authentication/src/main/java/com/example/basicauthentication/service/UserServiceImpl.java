package com.example.basicauthentication.service;

import com.example.basicauthentication.entity.Project;
import com.example.basicauthentication.entity.User;
import com.example.basicauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        User newUser=this.userRepository.save(user);
        return newUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

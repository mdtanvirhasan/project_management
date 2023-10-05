package com.example.basicauthentication.controller;

import com.example.basicauthentication.entity.User;
import com.example.basicauthentication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserFetcherController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @GetMapping("/getAllUsers")
    public List<User> getAllUser(){
        List<User> userList=userServiceImpl.getAllUsers();
        return userList;
    }
}

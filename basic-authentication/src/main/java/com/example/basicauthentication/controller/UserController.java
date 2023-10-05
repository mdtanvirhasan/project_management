package com.example.basicauthentication.controller;

import com.example.basicauthentication.entity.User;
import com.example.basicauthentication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/newuser")
    public String userForm(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "view/addUser";

    }

    @PostMapping(value="/createNewUser",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public  String createNewUser(User user){
        User newUser=userServiceImpl.saveUser(user);
        return "view/projectList";
    }

    @GetMapping("/userList")
    public String userList(Model model){
        List<User> userList=userServiceImpl.getAllUsers();

        return "view/projectList";
    }
}

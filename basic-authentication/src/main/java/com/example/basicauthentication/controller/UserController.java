package com.example.basicauthentication.controller;

import com.example.basicauthentication.entity.User;
import com.example.basicauthentication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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
    public  String createNewUser(@Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "view/addUser";
        }
        User newUser=userServiceImpl.saveUser(user);
        return "view/login";
    }

    @GetMapping("/userList")
    public String userList(Model model){
        List<User> userList=userServiceImpl.getAllUsers();

        return "view/projectList";
    }

    @GetMapping("/login")
    public String login(){ return "view/login";
    }



}

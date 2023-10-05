package com.example.basicauthentication.service;

import com.example.basicauthentication.entity.Project;
import com.example.basicauthentication.entity.Role;
import com.example.basicauthentication.entity.User;
import com.example.basicauthentication.repository.RoleRepository;
import com.example.basicauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role=roleRepository.findByName("ROLE_ADMIN");
        if (role==null){
            role=checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        User newUser=this.userRepository.save(user);
        return newUser;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    public Role checkRoleExist(){
        Role role=new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}

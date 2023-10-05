package com.example.basicauthentication.controller;

import com.example.basicauthentication.dto.ProjectDto;
import com.example.basicauthentication.entity.Project;
import com.example.basicauthentication.entity.User;
import com.example.basicauthentication.repository.UserRepository;
import com.example.basicauthentication.service.ProjectService;
import com.example.basicauthentication.service.ProjectServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/projects")
public class ProjectController {



    @Autowired
    ProjectServiceImpl projectServiceImpl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/newproject")
    public String createNewPage(Model model){
        Project project=new Project();
        model.addAttribute("project",project);
        return "view/addProject";
    }

    @PostMapping(value="/createNewProject",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createNewProject(ProjectDto project){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        User loggedInUser=userRepository.findByEmail(username);
        System.out.println(loggedInUser.toString());
        project.setOwner(loggedInUser.getName());
        projectServiceImpl.saveProject(project);
//        System.out.println(project.toString());
        return "redirect:/projects/projectList";
//        return "view/projectList";
    }

    @GetMapping("/projectList")
    public String projectList(Model model){

        model.addAttribute("projects",projectServiceImpl.getAllProjects());
        return "view/projectList";
    }

    @GetMapping("/showFormForUpdateProject/{id}")
    public String updateProject(@PathVariable("id") Long id,Model model){
//        Optional<Project> project=projectServiceImpl.getById(id)
//        model.addAttribute("project",project);
//        return "view/updateProject";

        projectServiceImpl.getById(id).ifPresent(project -> model.addAttribute("project", project));
        return "view/updateProject";

    }



    @PostMapping(value = "/updateProject/{id}",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String confirmUpdate(@PathVariable("id") Long id, Project project, Model model){
//        project.setId(id);
        Project pe=projectServiceImpl.updateProject(project);

        return "redirect:/projects/projectList";

    }

    @GetMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable("id") Long id){
        Optional<Project> project=projectServiceImpl.getById(id);
        projectServiceImpl.deleteProject(project);
        return "redirect:/projects/projectList";
    }

}

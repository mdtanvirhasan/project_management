package com.example.basicauthentication.controller;

import com.example.basicauthentication.dto.ProjectDto;
import com.example.basicauthentication.entity.Project;
import com.example.basicauthentication.entity.User;
import com.example.basicauthentication.repository.ProjectRepository;
import com.example.basicauthentication.repository.UserRepository;
import com.example.basicauthentication.service.JRepostService;
import com.example.basicauthentication.service.ProjectServiceImpl;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/projects")
public class ProjectController {



    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectServiceImpl projectServiceImpl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JRepostService service;

    @GetMapping("/newproject")
    public String createNewPage(Model model){


        List<User> usersList=userRepository.findAll();
        List<User> updatedList=new ArrayList<User>();
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        User loggedInUser=userRepository.findByEmail(username);
        for(User user:usersList){
            if(user.getId()!=loggedInUser.getId()){
                updatedList.add(user);
            }
        }
        System.out.println(usersList);
        Project project=new Project();
        model.addAttribute("project",project);
        model.addAttribute("allUserList",updatedList);
        return "view/addProject";
    }

    @PostMapping(value="/createNewProject",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createNewProject(ProjectDto project){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        User loggedInUser=userRepository.findByEmail(username);
        System.out.println(loggedInUser.toString());
        project.setOwner(loggedInUser.getId());
        projectServiceImpl.saveProject(project);
//        System.out.println(project.toString());
        return "redirect:/projects/projectList";
//        return "view/projectList";
    }

    @GetMapping("/projectList")
    public String projectList(Model model){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        User loggedInUser=userRepository.findByEmail(username);
        model.addAttribute("projects",projectServiceImpl.getAllProjects());
        model.addAttribute("currentUser",loggedInUser);
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


    @GetMapping("/getProject")
    public List<Project> getProject(){
        List<Project> projectList=(List<Project>) projectRepository.findAll();
        return projectList;
    }
    @GetMapping("/jasperpdf/export")
    public void createPDF(HttpServletResponse response) throws IOException, JRException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport(response);
    }

}

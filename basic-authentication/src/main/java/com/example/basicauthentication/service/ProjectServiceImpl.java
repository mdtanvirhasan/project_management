package com.example.basicauthentication.service;

import com.example.basicauthentication.dto.ProjectDto;
import com.example.basicauthentication.entity.Project;
import com.example.basicauthentication.entity.User;
import com.example.basicauthentication.repository.ProjectRepository;
import com.example.basicauthentication.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void saveProject(ProjectDto project) {
        Project project1 = new Project();
        List<User> members= new ArrayList<>();
        BeanUtils.copyProperties(project,project1);

        List<Long> idList=project.getMembersList();
//        System.out.println(idList.toString());
        for (int i = 0; i < idList.size(); i++) {
//            System.out.println(idList.get(i));
            User user=userRepository.findById(idList.get(i)).orElse(null);
//            System.out.println(user.toString());
            members.add(user);
//            System.out.println(members.toString());
        }

        project1.setMembers(members);
        if(!project.getStartDate().isEmpty()){
            System.out.println(project.getStartDate().substring(0,10));
            project.setStartDate(project.getStartDate().substring(0,10));
            LocalDate stDate=LocalDate.parse(project.getStartDate(),formatter);
            project1.setStartDate(stDate);
        }

        if(project.getEndDate()!=null){
            project.setEndDate(project.getEndDate().substring(0,10));
            LocalDate endDate=LocalDate.parse(project.getEndDate(),formatter);
            project1.setEndDate(endDate);
        }

        project1.setOwnerId(userRepository.findById(project.getOwner()).get().getId());
        String name=userRepository.findById(project.getOwner()).get().getName();
        project1.setOwner(name);
//        System.out.println(project1.toString());
        project1.setStatus(project.getStatus());
        Project pe=this.projectRepository.save(project1);


    }


//    public ListDto resetOwner(Project project){
//        ListDto listDto=new ListDto();
//        listDto.setName(project.getName());
//        listDto.setIntro(project.getIntro());
//        User u=userRepository.findById(project.getOwner()).get();
//        listDto.setOwner(u.getName());
//        listDto.setStatus(project.getStatus());
//        listDto.setStartDate(project.getStartDate());
//        listDto.setEndDate(project.getEndDate());
//        listDto.setMembers(project.getMembersIdList());
//        return listDto;
//    }

    public Project updateProject(Project project) {
        Project newProject = projectRepository.findById(project.getId()).get();
        newProject.setName(project.getName());
        newProject.setIntro(project.getIntro());
        newProject.setOwner(project.getOwner());
        newProject.setStatus(project.getStatus());
        if(project.getStartDate()!=null){
            newProject.setStartDate(project.getStartDate());
        }
        if(project.getEndDate()!=null){
            newProject.setEndDate(project.getEndDate());
        }
//        newProject.getStartDate(project.getStartDate());


        Project updatedProject = projectRepository.save(newProject);
        return updatedProject;
    }

    @Override
    public void deleteProject(Optional<Project> project) {

        projectRepository.deleteById(project.get().getId());
    }


    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getById(Long id) {
        if(projectRepository.existsById(id)){
            Optional<Project> pe=projectRepository.findById(id);
            return pe;
        }

        return null;
    }




}

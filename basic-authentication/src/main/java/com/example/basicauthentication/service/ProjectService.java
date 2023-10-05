package com.example.basicauthentication.service;


import com.example.basicauthentication.dto.ProjectDto;
import com.example.basicauthentication.entity.Project;

import java.util.List;
import java.util.Optional;


public interface ProjectService {
    List<Project>getAllProjects();
    void saveProject(ProjectDto project);
    Project updateProject(Project project);
    void deleteProject(Optional<Project> project);
}

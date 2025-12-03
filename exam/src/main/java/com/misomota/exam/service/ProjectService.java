package com.misomota.exam.service;

import com.misomota.exam.model.Project;
import com.misomota.exam.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        return projectRepository.createProject(project);
    }

    public List<Project> readProject() {
        return projectRepository.readProject();
    }

    public Project findProjectByID(int id) {
        return projectRepository.findProjectByID(id);
    }

    public void updateProject(Project project) {
        projectRepository.updateProject(project);
    }

    public void deleteProject(int id) {
        projectRepository.deleteProject(id);
    }
}
package com.misomota.exam.service;

import com.misomota.exam.model.Project;
import com.misomota.exam.repository.ProjectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.misomota.exam.DRY.DatabaseOperationException;
import com.misomota.exam.DRY.NotFoundException;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        try {
            return projectRepository.createProject(project);
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("Failed to create project: ", dataAccessException);
        }
    }

    public List<Project> readProject() {
        List<Project> projects = projectRepository.readProject();
        if (projects == null) {
            throw new NotFoundException("No projects found");
        }
        return projects;
    }


    public Project findProjectByID(int id) {
        Project projects = projectRepository.findProjectByID(id);
        if (projects == null) {
            throw new NotFoundException("No projects found");
        }
        return projects;
    }

    public Project updateProject(Project project, int id) {
        try {
            Project existing = findProjectByID(id);

            existing.setProjectName(project.getProjectName());

            int rows = projectRepository.updateProject(existing);
            if (rows == 0) throw new NotFoundException(id);
            return existing;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Failed to update project", e);
        }
    }

    public void deleteProject(int id) {
        try {
            int rows = projectRepository.deleteProject(id);
            if (rows == 0) throw new NotFoundException(id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Failed to delete project", e);
        }
    }
}
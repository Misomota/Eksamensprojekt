package com.misomota.exam;

import com.misomota.exam.DRY.NotFoundException;
import com.misomota.exam.model.Project;
import com.misomota.exam.repository.ProjectRepository;
import com.misomota.exam.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectService projectService;


    //Hvis createProject findes
    @Test
    void createProject_ReturnsProject() {
        //Arrange
        String projectName = "projectName";
        int projectID = 1;
        Project p = new Project(projectName, projectID);

        when(projectRepository.createProject(p)).thenReturn(p);

        //Act
        Project result = projectService.createProject(p);

        //Assert
        assertEquals(projectName, result.getProjectName());
    }

    //Hvis createProject ikke findes
    @Test
    void createProject_ReturnsNull() {
        //Arrange
        String projectName = "projectName";
        int projectID = 1;
        Project p = new Project(projectName, projectID);

        when(projectRepository.createProject(p)).thenReturn(null);

        //Act
        Project result = projectService.createProject(p);

        //Assert
        assertNull(result);
    }

    //Hvis readProject findes
    @Test
    void readProject_ReturnsProject() {
        //Arrange
        String projectName = "projectName";
        int projectID = 1;
        Project p = new Project(projectName, projectID);
        List<Project> project = List.of(p);

        when(projectRepository.readProject()).thenReturn(project);

        //Act
        List<Project> result = projectService.readProject();

        //Assert
        assertEquals(1, result.size());
        assertEquals(projectName, result.get(0).getProjectName());
    }

    //Hvis readProject ikke findes
    @Test
    void readProject_ReturnsNull() {
        //Arrange
        when(projectRepository.readProject()).thenReturn(null);

        //Act + Assert
        assertThrows(NotFoundException.class, () -> projectService.readProject());
    }

    //Hvis findProjectByID findes
    @Test
    void findProjectByID_ReturnsProject() {
        // Arrange
        Project p = new Project();
        p.setProjectID(1);
        p.setProjectName("Test");

        when(projectRepository.findProjectByID(1)).thenReturn(p);

        // Act
        Project result = projectService.findProjectByID(1);

        // Assert
        assertEquals("Test", result.getProjectName());
    }

    //Hvis findProjectByID ikke findes
    @Test
    void findProjectByID_ReturnsNull() {
        // Arrange
        when(projectRepository.findProjectByID(10)).thenReturn(null);

        // Act + Assert
        assertThrows(NotFoundException.class, () -> {
            projectService.findProjectByID(10);
        });
    }

    //Hvis updateProject findes
    @Test
    void updateProject_ReturnsProject() {
        // Arrange
        String projectName = "projectName";
        int projectID = 1;
        Project updatedProject = new Project(projectName, projectID);
        Project existingProject = new Project("oldName", projectID);

        when(projectRepository.findProjectByID(projectID)).thenReturn(existingProject);

        when(projectRepository.updateProject(any(Project.class))).thenReturn(1);

        // Act
        Project result = projectService.updateProject(updatedProject, projectID);

        // Assert
        assertEquals(projectName, result.getProjectName());
    }

    //Hvis updateProject ikke findes
    @Test
    void updateProject_ReturnsNull() {
        //Arrange
        String projectName = "projectName";
        int projectID = 1;
        Project p = new Project(projectName, projectID);
        Project existingProject = new Project("oldName", projectID);

//        when(projectService.findProjectByID(projectID)).thenReturn(existingProject);

        when(projectRepository.updateProject(any(Project.class))).thenReturn(0);

        // Act + Assert
        assertThrows(NotFoundException.class, () -> projectService.updateProject(p, projectID));
    }
}
package com.misomota.exam;

import com.misomota.exam.model.Project;
import com.misomota.exam.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(ProjectRepository.class) // Importer dit repository, da det ikke er en Spring Data interface
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // Opretter tabel i H2-databasen
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS project (" +
                "projectID INT AUTO_INCREMENT PRIMARY KEY, " +
                "projectName VARCHAR(255) NOT NULL)");
        // Rydder tabellen før hver test
        jdbcTemplate.execute("TRUNCATE TABLE project");
    }

    @Test
    void testCreateAndFindProject() {
        // Arrange
        Project project = new Project("Test Project", 0);

        // Act
        Project savedProject = projectRepository.createProject(project);
        Project foundProject = projectRepository.findProjectByID(savedProject.getProjectID());

        // Assert
        assertThat(savedProject.getProjectID()).isGreaterThan(0);
        assertThat(foundProject.getProjectName()).isEqualTo("Test Project");
    }

    @Test
    void testReadProject() {
        projectRepository.createProject(new Project("Project A", 0));
        projectRepository.createProject(new Project("Project B", 0));

        List<Project> projects = projectRepository.readProject();

        assertThat(projects).hasSize(2);
        assertThat(projects).extracting("projectName").containsExactlyInAnyOrder("Project A", "Project B");
    }

    @Test
    void testUpdateProject() {
        Project project = projectRepository.createProject(new Project("Old Name", 0));
        project.setProjectName("New Name");

        int updatedRows = projectRepository.updateProject(project);
        Project updatedProject = projectRepository.findProjectByID(project.getProjectID());

        assertThat(updatedRows).isEqualTo(1);
        assertThat(updatedProject.getProjectName()).isEqualTo("New Name");
    }

    @Test
    void testDeleteProject() {
        Project project = projectRepository.createProject(new Project("To Delete", 0));

        int deletedRows = projectRepository.deleteProject(project.getProjectID());
        List<Project> projects = projectRepository.readProject();

        assertThat(deletedRows).isEqualTo(1);
        assertThat(projects).doesNotContain(project);
    }
}
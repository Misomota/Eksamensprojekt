package com.misomota.exam;

import com.misomota.exam.model.Project;
import com.misomota.exam.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:com/misomota/exam/resources/h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void readAll() {
        List<Project> all = projectRepository.readProject();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).getProjectName()).isEqualTo("eksamensprojekt");
        assertThat(all.get(1).getProjectName()).isEqualTo("julepyntning");
    }

    @Test
    void insertAndReadBack() {
        projectRepository.createProject(new Project( "nyProjekt", 3));
        var nyProjekt = projectRepository.findProjectByID(3);
        assertThat(nyProjekt).isNotNull();
        assertThat(nyProjekt.getProjectName()).isEqualTo("nyProjekt");
    }
}
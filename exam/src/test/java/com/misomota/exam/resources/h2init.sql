DROP TABLE IF EXISTS project;
CREATE TABLE IF NOT EXISTS project(
                                      projectID INT AUTO_INCREMENT,
                                      projectName VARCHAR(100) NOT NULL,
    PRIMARY KEY (projectID)
    );

INSERT INTO project (projectID, projectName) VALUES (1, 'eksamensprojekt');
INSERT INTO project (projectID, projectName) VALUES (2, 'julepyntning');
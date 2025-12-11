package com.misomota.exam.model;

public class Project {
    private String projectName;
    private int projectID;
    private int totalHours;

    public Project(String projectName, int projectID) {
        this.projectName = projectName;
        this.projectID = projectID;
    }

    public Project() {}

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getTotalHours() {
        return totalHours; }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours; }
}

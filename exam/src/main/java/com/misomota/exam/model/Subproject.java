package com.misomota.exam.model;

public class Subproject {

   private String subprojectName;
    private int subprojectID;
    private int totalHours;
    private int projectID;

    public Subproject(String subprojectName, int subprojectID, int totalHours, int projectID) {
        this.subprojectName = subprojectName;
        this.subprojectID = subprojectID;
        this.totalHours = totalHours;
        this.projectID = projectID;
    }

    public Subproject() {

    }

    public String getSubprojectName() {
        return subprojectName;
    }
    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
}

package com.misomota.exam.model;

public class Subproject {

   private String subprojectName;
    private int subprojectID;

    public Subproject(String subprojectName, int subprojectID) {
        this.subprojectName = subprojectName;
        this.subprojectID = subprojectID;
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
}

package com.misomota.exam.model;

public class Resource {
    private int resourceID;
    private String resourceName;
    private int taskID;

    public Resource(int resourceID, String resourceName, int taskID) {
        this.resourceID = resourceID;
        this.resourceName = resourceName;
        this.taskID = taskID;
    }

    public Resource() { }


    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}

package com.misomota.exam.model;

public class Resource {
    private int resourceID;
    private String resourceName;

    public Resource(int resourceID, String resourceName) {
        this.resourceID = resourceID;
        this.resourceName = resourceName;
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
}

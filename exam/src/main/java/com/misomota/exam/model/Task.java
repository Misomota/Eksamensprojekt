package com.misomota.exam.model;


import java.time.LocalDate;

public class Task {
    private int taskID;
    private String taskName;
    private LocalDate startDate;
    private LocalDate deadline;
    private int timeEstimate;
    private int personAssigned;
    private int duration;
    private int actualTimeUsed;

    public Task() {

    }


    public Task(int taskID ,String taskName, LocalDate startDate, LocalDate deadline, int timeEstimate,int personAssigned, int duration, int actualTimeUsed) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.startDate = startDate;
        this.deadline = deadline;
        this.timeEstimate = timeEstimate;
        this.personAssigned = personAssigned;
        this.duration = duration;
        this.actualTimeUsed = actualTimeUsed;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public int getTimeEstimate() {
        return timeEstimate;
    }

    public int getDuration() {
        return duration;
    }

    public int getActualTimeUsed() {
        return actualTimeUsed;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public int getPersonAssigned() {
        return personAssigned;
    }

    public void setPersonAssigned(int personAssigned) {
        this.personAssigned = personAssigned;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setActualTimeUsed(int actualTimeUsed) {
        this.actualTimeUsed = actualTimeUsed;
    }
}
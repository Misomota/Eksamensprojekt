package com.misomota.exam.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    private int taskID;
    private String taskName;
    private LocalDate startDate;
    private LocalDateTime deadline;
    private int timeEstimate;
    private String resource;

    public Task() {

    }

    public Task(int taskID ,String taskName, LocalDate startDate, LocalDateTime deadline, int timeEstimate, String resource) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.startDate = startDate;
        this.deadline = deadline;
        this.timeEstimate = timeEstimate;
        this.resource = resource;
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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public int getTimeEstimate() {
        return timeEstimate;
    }

    public String getResource() {
        return resource;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public void setRessource(String resource) {
        this.resource = resource;
    }
}
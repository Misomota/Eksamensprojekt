package com.misomota.exam.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    private int taskID;
    private String taskName;
    private LocalDate startDate;
    private LocalDateTime deadline;
    private int timeEstimate;

    public Task(int taskID ,String taskName, LocalDate startDate, LocalDateTime deadline, int timeEstimate) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.startDate = startDate;
        this.deadline = deadline;
        this.timeEstimate = timeEstimate;
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
}

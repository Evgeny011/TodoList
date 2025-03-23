package com.shalimov.todoapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DeletedTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private boolean priority;
    private String marker;
    private LocalDateTime deletedDate;

    public DeletedTask() {
    }

    public DeletedTask(ToDoItem task) {
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.createdDate = task.getCreatedDate();
        this.priority = task.isPriority();
        this.marker = task.getMarker();
        this.deletedDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public boolean isPriority() {
        return priority;
    }

    public String getMarker() {
        return marker;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}

package com.shalimov.todoapp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ToDoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public ToDoItem() {
    }

    public ToDoItem(Long id,String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public ToDoItem(String title){
        this.title = title;
    }


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}

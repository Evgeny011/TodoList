package com.shalimov.todoapp.repositories;

import com.shalimov.todoapp.model.DeletedTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedTaskRepository extends JpaRepository<DeletedTask, Long> {
}
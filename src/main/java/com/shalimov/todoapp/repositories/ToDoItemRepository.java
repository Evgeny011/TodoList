package com.shalimov.todoapp.repositories;

import com.shalimov.todoapp.model.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long>{


}

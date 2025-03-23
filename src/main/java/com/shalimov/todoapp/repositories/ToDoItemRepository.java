package com.shalimov.todoapp.repositories;

import com.shalimov.todoapp.model.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {
    List<ToDoItem> findByTitleContainingIgnoreCase(String title);

    List<ToDoItem> findByMarker(String marker);

    List<ToDoItem> findByPriority(Boolean priority);

    Optional<ToDoItem> findByTitle(String title);

}

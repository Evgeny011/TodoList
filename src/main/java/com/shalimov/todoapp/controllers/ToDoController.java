package com.shalimov.todoapp.controllers;

import com.shalimov.todoapp.model.ToDoItem;
import com.shalimov.todoapp.repositories.ToDoItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ToDoController implements CommandLineRunner {

    private final ToDoItemRepository toDoItemRepository ;

    public ToDoController(ToDoItemRepository toDoItemRepository) {
        this.toDoItemRepository = toDoItemRepository;
    }

    @GetMapping
    public String index(Model model) {

        List<ToDoItem> allTodos = toDoItemRepository.findAll();
        model.addAttribute("allTodos", allTodos);

        return "index";
    }

    @Override
    public void run(String... args) throws Exception {
        toDoItemRepository.save(new ToDoItem("item 1"));
        toDoItemRepository.save(new ToDoItem("item 2"));
    }
}

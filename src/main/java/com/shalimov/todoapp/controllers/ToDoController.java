package com.shalimov.todoapp.controllers;

import com.shalimov.todoapp.model.ToDoItem;
import com.shalimov.todoapp.repositories.ToDoItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ToDoController implements CommandLineRunner {

    private final ToDoItemRepository toDoItemRepository;

    public ToDoController(ToDoItemRepository toDoItemRepository) {
        this.toDoItemRepository = toDoItemRepository;
    }

    @GetMapping
    public String index(Model model, @RequestParam(required = false) String search, @RequestParam(required = false) String marker, @RequestParam(required = false) Boolean priority) {
        List<ToDoItem> allTodos;
        if (search != null && !search.isEmpty()) {
            allTodos = toDoItemRepository.findByTitleContainingIgnoreCase(search);
        } else if (marker != null && !marker.isEmpty()) {
            allTodos = toDoItemRepository.findByMarker(marker);
        } else if (priority != null) {
            allTodos = toDoItemRepository.findByPriority(priority);
        } else {
            allTodos = toDoItemRepository.findAll();
        }
        model.addAttribute("allTodos", allTodos);
        model.addAttribute("newTodo", new ToDoItem());
        return "index";
    }

    @PostMapping("/add")
    public String addTodoItem(@ModelAttribute ToDoItem toDoItem) {
        toDoItemRepository.save(toDoItem);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id) {
        toDoItemRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteAll() {
        toDoItemRepository.deleteAll();
        return "redirect:/";
    }

    @PostMapping("/updateDescription/{id}")
    public String updateDescription(@PathVariable("id") Long id, @RequestParam("description") String newDescription) {
        ToDoItem toDoItem = toDoItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Невалидный айди задачи:" + id));
        String oldDescription = toDoItem.getDescription();

        if (oldDescription != null && !oldDescription.isEmpty()) {
            toDoItem.setDescription(oldDescription + "\n" + newDescription);
        } else {

            toDoItem.setDescription(newDescription);
        }
        toDoItemRepository.save(toDoItem);
        return "redirect:/";
    }

    @PostMapping("/clearDescription/{id}")
    public String clearDescription(@PathVariable("id") Long id) {
        ToDoItem toDoItem = toDoItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Невалидный айди задачи:" + id));
        toDoItem.setDescription("");
        toDoItemRepository.save(toDoItem);
        return "redirect:/";
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
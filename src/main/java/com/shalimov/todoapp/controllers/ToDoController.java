package com.shalimov.todoapp.controllers;

import com.shalimov.todoapp.model.ToDoItem;
import com.shalimov.todoapp.model.DeletedTask;
import com.shalimov.todoapp.repositories.ToDoItemRepository;
import com.shalimov.todoapp.repositories.DeletedTaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ToDoController implements CommandLineRunner {

    private final ToDoItemRepository toDoItemRepository;
    private final DeletedTaskRepository deletedTaskRepository;

    public ToDoController(ToDoItemRepository toDoItemRepository, DeletedTaskRepository deletedTaskRepository) {
        this.toDoItemRepository = toDoItemRepository;
        this.deletedTaskRepository = deletedTaskRepository;
    }

    @GetMapping
    public String index(Model model, @RequestParam(required = false) String search, @RequestParam(required = false) String marker, @RequestParam(required = false) Boolean priority, @RequestParam(required = false) String error) {
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
        List<DeletedTask> deletedTasks = deletedTaskRepository.findAll(); // Убедитесь, что данные есть
        model.addAttribute("deletedTasks", deletedTasks);

        if (error != null && error.equals("TaskAlreadyExists")) {
            model.addAttribute("errorMessage", "Задача с таким названием уже существует.");
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
        toDoItemRepository.findById(id).ifPresent(task -> {
            deletedTaskRepository.save(new DeletedTask(task));
            toDoItemRepository.deleteById(id);
        });
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteAll() {
        toDoItemRepository.findAll().forEach(task -> deletedTaskRepository.save(new DeletedTask(task)));
        toDoItemRepository.deleteAll();
        return "redirect:/";
    }

    @PostMapping("/updateDescription/{id}")
    public String updateDescription(@PathVariable("id") Long id, @RequestParam("description") String newDescription) {
        ToDoItem toDoItem = toDoItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Невалидный айди задачи:" + id));
        toDoItem.setDescription(newDescription);
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

    @GetMapping("/export")
    @ResponseBody
    public List<ToDoItem> exportToDoItems() {
        return toDoItemRepository.findAll();
    }

    @GetMapping("/deletedTasks")
    @ResponseBody
    public List<DeletedTask> getDeletedTasks() {
        return deletedTaskRepository.findAll();
    }

    @GetMapping("/exportDeletedTasks")
    @ResponseBody
    public List<DeletedTask> exportDeletedTasks() {
        return deletedTaskRepository.findAll();
    }

    @PostMapping("/restore/{id}")
    public String restoreTask(@PathVariable("id") Long id) {
        DeletedTask deletedTask = deletedTaskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Невалидный айди удаленной задачи:" + id));

        if (toDoItemRepository.findByTitle(deletedTask.getTitle()).isPresent()) {
            return "redirect:/?error=TaskAlreadyExists";
        }

        ToDoItem restoredTask = new ToDoItem();
        restoredTask.setTitle(deletedTask.getTitle());
        restoredTask.setDescription(deletedTask.getDescription());
        restoredTask.setPriority(deletedTask.isPriority());
        restoredTask.setMarker(deletedTask.getMarker());
        restoredTask.setCreatedDate(deletedTask.getCreatedDate());

        toDoItemRepository.save(restoredTask);
        deletedTaskRepository.deleteById(id);

        return "redirect:/";
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
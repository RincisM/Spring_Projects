package com.training.httpmethod.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.httpmethod.Entity.Task;

import java.util.ArrayList;
import java.util.List;


@RestController
public class TaskController {
    private List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    @RequestMapping("/task")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(tasks);
    }

    @RequestMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") int id) {
        for(Task task: tasks) {
            if(task.getId() == id) {
                return ResponseEntity.ok(task);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/task", method = RequestMethod.POST)
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setId(nextId++);
        tasks.add(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/task/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@RequestBody Task newTask, @PathVariable("id") int id) {
        for(Task task: tasks) {
            if(task.getId() == id) {
                task.setTitle(newTask.getTitle());
                task.setDescription(newTask.getDescription());
                task.setStatus(newTask.getStatus());
                return ResponseEntity.ok(task);
            }
        }
        return new ResponseEntity<>(newTask, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "task/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Task> deleteTask(@PathVariable("id") int id) {
        for(Task task: tasks) {
            if(task.getId() == id) {
                tasks.remove(task);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

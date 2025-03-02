package top.gsc.requestservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.gsc.requestservice.entity.TodoRequest;
import top.gsc.requestservice.openfeign.NodeService;

@RestController
public class ConsumerController5 {
    @Resource
    private NodeService nodeService;

    @GetMapping("/todos")
    public String getTodos() {
        return nodeService.getTodos();
    }

    @GetMapping("/todos/{id}")
    public String getTodoById(@PathVariable("id") int id) {
        return nodeService.getTodoById(id);
    }

    @PostMapping("/todos")
    public String createTodo(@RequestBody TodoRequest todoRequest) {
        System.out.println("Sending request: " + todoRequest);
        return nodeService.createTodo(todoRequest);
    }

    @PutMapping("/todos/{id}")
    public String updateTodo(@PathVariable("id") int id, @RequestBody TodoRequest todoRequest) {
        return nodeService.updateTodo(id, todoRequest);
    }

    @DeleteMapping("/todos/{id}")
    public String deleteTodo(@PathVariable("id") int id) {
        return nodeService.deleteTodo(id);
    }
}

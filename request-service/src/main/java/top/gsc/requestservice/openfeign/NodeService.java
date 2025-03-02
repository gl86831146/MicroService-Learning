package top.gsc.requestservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.gsc.requestservice.entity.TodoRequest;

@FeignClient(name = "node-service")
public interface NodeService {
    @GetMapping("/todos")
    String getTodos();

    @GetMapping("/todos/{id}")
    String getTodoById(@PathVariable("id") int id);

    @PostMapping(value = "/todos", consumes = "application/json")
    String createTodo(@RequestBody TodoRequest todoRequest);

    @PutMapping(value = "/todos/{id}", consumes = "application/json")
    String updateTodo(@PathVariable("id") int id, @RequestBody TodoRequest todoRequest);

    @DeleteMapping("/todos/{id}")
    String deleteTodo(@PathVariable("id") int id);
}

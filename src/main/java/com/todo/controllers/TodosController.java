package com.todo.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todo.dto.TodoDto;
import com.todo.exception.ResourceNotFoundException;
import com.todo.model.Todo;
import com.todo.services.TodoService;

@RestController
@RequestMapping("/api/v1/todos")
public class TodosController {
	
	
	
	@Autowired
	private TodoService todoService;

	@GetMapping()
	public List<TodoDto> getAllTodos() {
		List<TodoDto> allTodos = todoService.getAllTodos();
		for (TodoDto todo : allTodos) {
			int todoId = todo.getId();
			Link selfLink = WebMvcLinkBuilder.linkTo(TodosController.class).slash(todoId).withSelfRel();
			todo.add(selfLink);
		}
		return allTodos;

	}

	@GetMapping("/user/{name}")
	public List<TodoDto> getTodosyUser(@PathVariable String name) {
		List<TodoDto> userTodoList = todoService.getAllTodosByUser(name);

		if (userTodoList.isEmpty())
			throw new ResourceNotFoundException("Not Found");

		return userTodoList;
	}

	@GetMapping("/{id}")
	public TodoDto getToDoById(@PathVariable int id) {
		TodoDto todo = todoService.getTodoById(id);
		if (todo == null) {
			throw new ResourceNotFoundException("Developer tools enabled 123");
			// throw new ResponseStatusException(HttpStatus.CONFLICT, "Todo Conflict");
		}
		return todo;

	}

	@PostMapping()
	public ResponseEntity<TodoDto> addTodo(@Valid @RequestBody TodoDto todo) {
		TodoDto newTodo = todoService.addTodo(todo);
		if (newTodo == null) {
			return ResponseEntity.noContent().build();
		}
		// return new ResponseEntity<Todo>(newTodo,HttpStatus.CREATED);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTodo.getId())
				.toUri();
		return ResponseEntity.created(location).build();

	}

	@PutMapping("/user/{name}/{id}")
	public ResponseEntity<TodoDto> updateTodo(@PathVariable String name, @PathVariable int id, @RequestBody TodoDto todo) {
		TodoDto updateTodo = todoService.updateTodo(name, id, todo);
		if (updateTodo == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Not Found");
//			throw new ResourceNotFoundException("No Todo matching for given name and id existing");
		}
		return new ResponseEntity<TodoDto>(updateTodo, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable int id) {
		boolean result = todoService.deleteTodo(id);
		if (!result) {
			// throw new ResourceNotFoundException("Resource not found for given id" + id);
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Todo Conflict");
		}
		return new ResponseEntity<String>("Successfully Deleted Todo", HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	};
	
	
	
	
	
	
	
	
	
	
	/*
	@Autowired
	private TodoService todoService;

	@GetMapping()
	public List<Todo> getAllTodos() {
		List<Todo> allTodos = todoService.getAllTodos();
		for (Todo todo : allTodos) {
			int todoId = todo.getId();
			Link selfLink = WebMvcLinkBuilder.linkTo(TodosController.class).slash(todoId).withSelfRel();
			todo.add(selfLink);
		}
		return allTodos;

	}

	@GetMapping("/user/{name}")
	public List<Todo> getTodosyUser(@PathVariable String name) {
		List<Todo> userTodoList = todoService.getAllTodosByUser(name);

		if (userTodoList.isEmpty())
			throw new ResourceNotFoundException("Not Found");

		return userTodoList;
	}

	@GetMapping("/{id}")
	public Todo getToDoById(@PathVariable int id) {
		Todo todo = todoService.getTodoById(id);
		if (todo == null) {
			throw new ResourceNotFoundException("Developer tools enabled 123");
			// throw new ResponseStatusException(HttpStatus.CONFLICT, "Todo Conflict");
		}
		return todo;

	}

	@PostMapping()
	public ResponseEntity<Todo> addTodo(@Valid @RequestBody Todo todo) {
		Todo newTodo = todoService.addTodo(todo);
		if (newTodo == null) {
			return ResponseEntity.noContent().build();
		}
		// return new ResponseEntity<Todo>(newTodo,HttpStatus.CREATED);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTodo.getId())
				.toUri();
		return ResponseEntity.created(location).build();

	}

	@PutMapping("/user/{name}/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String name, @PathVariable int id, @RequestBody Todo todo) {
		Todo updateTodo = todoService.updateTodo(name, id, todo);
		if (updateTodo == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Not Found");
//			throw new ResourceNotFoundException("No Todo matching for given name and id existing");
		}
		return new ResponseEntity<Todo>(updateTodo, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable int id) {
		boolean result = todoService.deleteTodo(id);
		if (!result) {
			// throw new ResourceNotFoundException("Resource not found for given id" + id);
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Todo Conflict");
		}
		return new ResponseEntity<String>("Successfully Deleted Todo", HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	};
	*/

}

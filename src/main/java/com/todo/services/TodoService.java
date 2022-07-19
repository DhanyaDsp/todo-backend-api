package com.todo.services;

import java.util.List;

import com.todo.dto.TodoDto;

public interface TodoService {
	
	List<TodoDto> getAllTodos();
	
	List<TodoDto> getAllTodosByUser(String user);
	
	TodoDto getTodoById(int id);
	
	TodoDto addTodo(TodoDto todo);
	
	TodoDto updateTodo(String name, int id, TodoDto todo);
	
	boolean deleteTodo(int id);

}

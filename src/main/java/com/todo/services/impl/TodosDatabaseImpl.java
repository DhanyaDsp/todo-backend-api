package com.todo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.todo.dto.TodoDto;
import com.todo.dto.mapper.TodoDtoMapper;
import com.todo.model.Todo;
import com.todo.repositories.TodoRepository;
import com.todo.services.TodoService;

@Service
public class TodosDatabaseImpl implements TodoService {

	@Autowired
	private TodoRepository todosRepository;

	@Override
	public List<TodoDto> getAllTodos() {

		return TodoDtoMapper.convertListEntitytoDtos(todosRepository.findAll(Sort.by(Sort.Direction.ASC, "user")));

	}

	@Override
	public List<TodoDto> getAllTodosByUser(String user) {
		return TodoDtoMapper.convertListEntitytoDtos(todosRepository.findByUser(user));
	}

	@Override
	public TodoDto getTodoById(int id) {
		// use the default findbyId method to get by id the todos
		Optional<Todo> todo = todosRepository.findById(id);
		if (todo.isPresent())
			return TodoDtoMapper.convertEntityToDto(todo.get());
		return null;
	}

	@Override
	public TodoDto addTodo(TodoDto todoDto) {
		Todo newCreateTodo = todosRepository.save(TodoDtoMapper.convertDtoToEntity(todoDto));
		return TodoDtoMapper.convertEntityToDto(newCreateTodo);
	}

	@Override
	public TodoDto updateTodo(String name, int id, TodoDto todo) {
		Optional<Todo> existingTodo = todosRepository.findById(id);

		if (existingTodo.isPresent() && existingTodo.get().getUser().equals(name)) {
			TodoDto updatedTodoDto = TodoDtoMapper.convertEntityToDto(existingTodo.get());
			updatedTodoDto.setDescription(todo.getDescription());
			updatedTodoDto.setTargetDate(todo.getTargetDate());
			updatedTodoDto.setStatus(todo.getStatus());
			// save to db
			Todo updatedTodo = TodoDtoMapper.convertDtoToEntity(updatedTodoDto);
			return TodoDtoMapper.convertEntityToDto(updatedTodo);
		}
		return null;
		/*
		 * Optional<Todo> existingTodo = todosRepository.findById(id); if
		 * (existingTodo.isPresent() && existingTodo.get().getUser().equals(name)) {
		 * Todo updatedTodo = existingTodo.get();
		 * updatedTodo.setDescription(todo.getDescription());
		 * updatedTodo.setTargetDate(todo.getTargetDate());
		 * updatedTodo.setDone(todo.isDone()); // save to db
		 * todosRepository.save(updatedTodo); return updatedTodo; } return null;
		 */
	}

	@Override
	public boolean deleteTodo(int id) {
		Optional<Todo> existingTodo = todosRepository.findById(id);
		if (existingTodo.isPresent()) {
			todosRepository.deleteById(id);
			return true;
		}
		return false;
	}

}

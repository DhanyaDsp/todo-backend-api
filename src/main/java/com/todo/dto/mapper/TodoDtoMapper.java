package com.todo.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.todo.dto.TodoDto;
import com.todo.model.Todo;

public class TodoDtoMapper {

	public static Todo convertDtoToEntity(TodoDto todoDto) {
		Todo todo = new Todo();
		todo.setDescription(todoDto.getDescription());
		todo.setUser(todoDto.getUser());
		todo.setTargetDate(todoDto.getTargetDate());
		if (todoDto.getStatus() != null && todoDto.getStatus().equals("Completed")) {
			todo.setDone(true);
		} else {
			todo.setDone(false);
		}
		return todo;

	}

	public static TodoDto convertEntityToDto(Todo todo) {
		TodoDto todoDtos = new TodoDto();
		todoDtos.setDescription(todo.getDescription());
		todoDtos.setUser(todo.getUser());
		todoDtos.setTargetDate(todo.getTargetDate());
		return todoDtos;

	}
	public static List<TodoDto> convertListEntitytoDtos(List<Todo> todos){
		List<TodoDto> todoDtos = new ArrayList<>();
		for(Todo todo:todos) {
			TodoDto todoDto = TodoDtoMapper.convertEntityToDto(todo);
			todoDtos.add(todoDto);
		}
		return todoDtos;
	}

}

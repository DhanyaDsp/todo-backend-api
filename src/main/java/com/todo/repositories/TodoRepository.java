package com.todo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.dto.TodoDto;
import com.todo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
	List<Todo> findByUser(String user);

}

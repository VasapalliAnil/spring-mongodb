package com.test.springbootmongodb.service;

import java.util.List;

import com.test.springbootmongodb.exception.ValidationException;
import com.test.springbootmongodb.model.TodoDTO;

import jakarta.validation.ConstraintViolationException;

public interface TodoService {
	public void createTodo(TodoDTO todo) throws ValidationException,ConstraintViolationException ;
	public List<TodoDTO> getAllTodos();
	public TodoDTO getTodo(String id) throws ValidationException;
	public TodoDTO updateTodybyId(String id,TodoDTO todos) throws ValidationException;
	public void deleteTodoById(String id) throws ValidationException;
}

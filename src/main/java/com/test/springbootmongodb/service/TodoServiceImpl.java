package com.test.springbootmongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.springbootmongodb.exception.ValidationException;
import com.test.springbootmongodb.model.TodoDTO;
import com.test.springbootmongodb.repository.TodoRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	public TodoRepository todoRepo;

	@Override
	public void createTodo(TodoDTO todo) throws ValidationException, ConstraintViolationException {
		Optional<TodoDTO> todoToSave = todoRepo.findByTodo(todo.getTodo());
		if (todoToSave.isPresent()) {
			throw new ValidationException(ValidationException.TodoAlreadyExists());

		} else {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			todoRepo.save(todo);
		}

	}

	@Override
	public List<TodoDTO> getAllTodos() {
		// TODO Auto-generated method stub
		List<TodoDTO> todos = todoRepo.findAll();
		if (todos.size() > 0) {
			return todos;
		} else {
			return new ArrayList<TodoDTO>();
		}
	}

	@Override
	public TodoDTO getTodo(String id) throws ValidationException {
		// TODO Auto-generated method stub
		Optional<TodoDTO> todo = todoRepo.findById(id);
		if (todo.isPresent()) {
			return todo.get();
		} else {
			throw new ValidationException(ValidationException.NotFoundException(id));
		}

	}

	@Override
	public TodoDTO updateTodybyId(String id, TodoDTO todos) throws ValidationException {
		// TODO Auto-generated method stub
		Optional<TodoDTO> todo = todoRepo.findById(id);
		Optional<TodoDTO> duplicatetodo = todoRepo.findByTodo(todos.getTodo());
		

			if (todo.isPresent()) {
				if (duplicatetodo.isPresent() && !duplicatetodo.get().getId().equals(id)) {
					throw new ValidationException(ValidationException.TodoAlreadyExists());
				} 
				
				
				TodoDTO todoToSave = todo.get();

				todoToSave
						.setCompleted(todos.getCompleted() != null ? todos.getCompleted() : todoToSave.getCompleted());
				todoToSave.setTodo(todos.getTodo() != null ? todos.getTodo() : todoToSave.getTodo());
				todoToSave.setDescription(
						todos.getDescription() != null ? todos.getDescription() : todoToSave.getDescription());
				todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
				todoRepo.save(todoToSave);
				return todoToSave;
				
			} 
			else {
				throw new ValidationException(ValidationException.NotFoundException(id));
			}
		

	}

	@Override
	public void deleteTodoById(String id) throws ValidationException {
		// TODO Auto-generated method stub
		Optional<TodoDTO> todo = todoRepo.findById(id);
		if (todo.isPresent()) {
			todoRepo.deleteById(id);
		} else {
			throw new ValidationException(ValidationException.NotFoundException(id));
		}
	}

}

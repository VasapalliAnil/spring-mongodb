package com.test.springbootmongodb.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.springbootmongodb.exception.ValidationException;
import com.test.springbootmongodb.model.TodoDTO;
import com.test.springbootmongodb.repository.TodoRepository;
import com.test.springbootmongodb.service.TodoService;

import jakarta.validation.ConstraintViolationException;

@RestController
public class TodoController {
	@Autowired
	private TodoRepository todoRepo;

	@Autowired
	private TodoService todoService;

	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos() {
		List<TodoDTO> todos = todoService.getAllTodos();
//		if(todos.size() > 0){
//			return new ResponseEntity<List<TodoDTO>>(todos,HttpStatus.OK);
//		}
//		else{
//			return new ResponseEntity<>("No todos available",HttpStatus.NOT_FOUND);
//		}
		return new ResponseEntity<List<TodoDTO>>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@PostMapping("/todos/create")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) throws ValidationException {
		try {
			todoService.createTodo(todo);
			return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (ValidationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getTodo(@PathVariable("id") String id) {
		try {

			return new ResponseEntity<TodoDTO>(todoService.getTodo(id), HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todos) {
		try {

			return new ResponseEntity<TodoDTO>(todoService.updateTodybyId(id, todos), HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable("id") String id) {
		try {
			todoService.deleteTodoById(id);
			return new ResponseEntity<>("Successfully delete the document with id:" + id, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

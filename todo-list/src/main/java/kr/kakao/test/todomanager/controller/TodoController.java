package kr.kakao.test.todomanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import kr.kakao.test.todomanager.model.Todo;
import kr.kakao.test.todomanager.service.TodoService;
import kr.kakao.test.todomanager.util.ApiResponse;
import kr.kakao.test.todomanager.util.ApiResponse.Status;

@RestController
@RequestMapping("/v1")
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// RestTemplate Get
	@GetMapping("rest/todo/{todoId}")
	public Todo testFindTodoWithRestTemplate(@PathVariable String todoId) {
		return restTemplate.getForObject("http://localhost:9090/v1/todos/{todoId}", 
				Todo.class, todoId);
	}
	
	// RestTemplate Post
	@GetMapping("rest/todos")
	public String registerTodoWithRestTemplate() {
//		Todo todo = new Todo(null, 'test_todo_test1', null, null, FALSE, null);
//		restTemplate.postForLocation("http://localhost:9090/v1/todos", todo);
		return "insert OK";
	}

	
	/**
	 * 할일 전체 조회
	 * @return
	 */
	@GetMapping("/todos")
	public ApiResponse viewAllTodos() {
		List<Todo> TodoList = todoService.getAllTodos();
		return new ApiResponse(Status.OK, TodoList); 
	}

	@GetMapping("/todos/{todoId}")
	public Todo viewTodoDetailByTodoId(@PathVariable String todoId) {
		
		Todo findedTodo = todoService.findTodoByTodoId(todoId);
//		return new ApiResponse(Status.OK, findedTodo);
		return findedTodo;
	}

	@PostMapping("/todos")
	public ApiResponse registerTodo(@RequestBody Todo todo) {
//		System.out.println(todo);
		Todo registerTodo = todoService.registTodo(todo);
		return new ApiResponse(Status.OK, registerTodo);
	}


	@PutMapping("/todos")
	public ResponseEntity<Todo> modifyTodoStatus(@RequestBody Todo todo) {
		// TO-DO todoId를 받아서 로그로 출력
//		System.out.println("todoId: " + todoId);
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}

	@DeleteMapping("/todos/{todoId}")
	public ApiResponse removeTodo(@PathVariable String todoId) {
		return new ApiResponse(Status.OK, "remove OK");
	}
}

package kr.kakao.test.todomanager;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.kakao.test.todomanager.model.Todo;
import kr.kakao.test.todomanager.service.TodoService;

/**
 * TodoService 단위테스트
 * @author ryan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoManagerApplicationTests {

	@Autowired
	private TodoService todoService;
	
	/*
	 * 전체 할일 조회
	 */
	@Test
	@Ignore
	public void getAllTodosTest() {
		List<Todo> todoList = todoService.getAllTodos();
		for (Todo tmpTodo : todoList) {
			System.out.println(tmpTodo.toString());
		}
	}
	
	/**
	 * 작성일, 최종수정일, 내용에 따른 조회
	 */
	@Test
	@Ignore
	public void findTodosByConditionsTest() {
		Todo conditions = new Todo();
		conditions.setTodoName("regist");
		conditions.setLastUpdateDate("2019-01-22");
		conditions.setWriteDate("2019-01-22");
		List<Todo> todoList = todoService.findTodosByConditions(conditions);
		for (Todo tmpTodo : todoList) {
			System.out.println(tmpTodo.toString());
		}
	}
	
	/*
	 * 기본 할일 추가
	 */
	@Test
	@Ignore
	public void registTodoTest() {
		Todo todo = new Todo();
		todo.setTodoName("registTodoTest");
		todoService.registTodo(todo);
		
		List<Todo> todoList = todoService.getAllTodos();
		for (Todo tmpTodo : todoList) {
			System.out.println(tmpTodo.toString());
		}
		
	}
	
	/*
	 * 다른 할일 참조된 할일 추가
	 */
	@Test
	@Ignore
	public void registTodoChildTest() {
		Todo todoChild = new Todo();
		todoChild.setTodoName("registTodoChild1");
//		todoChild.setTodoName("registTodoChild2");
//		todoChild.setTodoName("registTodoChild3");
//		todoChild.setTodoName("registTodoChild4");
		todoChild.setReferTodoId("1");
		todoService.registTodo(todoChild);
		
		List<Todo> todoList = todoService.getAllTodos();
		for (Todo tmpTodo : todoList) {
			System.out.println(tmpTodo.toString());
		}
	}
	
	/**
	 * 할일 상태 수정
	 */
	@Test
//	@Ignore
	public void modifyTodoStatusTest() {
		Todo updateReqTodo = new Todo();
//		updateReqTodo.setTodoId(7);
//		updateReqTodo.setTodoId(13);
		updateReqTodo.setTodoId(12);
		String ret = todoService.modifyTodoStatus(updateReqTodo);
		System.out.println(ret);
		
		List<Todo> todoList = todoService.getAllTodos();
		for (Todo tmpTodo : todoList) {
			System.out.println(tmpTodo.toString());
		}
	}
	
}

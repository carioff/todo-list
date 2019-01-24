package kr.kakao.test.todomanager.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.kakao.test.todomanager.model.Todo;

@Repository
public class TodoDao {

	private static final Logger logger = LoggerFactory.getLogger(TodoDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 등록 
	 * @param todo
	 * @return
	 */
	public Todo insertTodo(Todo todo) {
		int updated = jdbcTemplate.update("INSERT INTO TODO VALUES(TODO_ID_SEQ.NEXTVAL, ?, "
				+ "SYSDATE, SYSDATE, FALSE, ?)"
				, todo.getTodoName(), todo.getReferTodoId());
		if(updated > 0) {
			logger.debug("before insertTodo");
			return todo;
		} else {
			return null;
		}
	}
	
	/**
	 * 상태 수정
	 * @param todo
	 * @return
	 */
	public Todo updateTodoStatus(Todo todo) {
		int updated = jdbcTemplate.update("UPDATE TODO SET "
				+ "LAST_UPDATE_DATE = SYSDATE, TODO_STATUS = TRUE WHERE TODO_ID = ?"
				, todo.getTodoId());
		if(updated > 0) {
			logger.debug("before updateTodoStatus");
			return todo;
		} else {
			return null;
		}
	}
	/**
	 * 전체 조회
	 * @return todoList
	 */
	public List<Todo> selectAllTodos() {
		List<Todo> todoList = jdbcTemplate.query("SELECT TODO_ID, TODO_NAME, WRITE_DATE, "
				+ "LAST_UPDATE_DATE, TODO_STATUS, REFER_TODO_ID FROM TODO", 
				new BeanPropertyRowMapper<Todo>(Todo.class));
		
		return todoList;
	}
	
	/**
	 * 페이징용 조회된 할일 수 
	 * @return
	 */
	public long selectTodosCnt() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TODO", Long.class); 

	}

	/**
	 * key값 조회
	 * @param todoId
	 * @return
	 */
	public Todo selectTodoByKey(String todoId) {
		return jdbcTemplate.queryForObject("SELECT TODO_ID, TODO_NAME, WRITE_DATE, LAST_UPDATE_DATE, "
				+ "TODO_STATUS, REFER_TODO_ID FROM TODO WHERE TODO_ID = ?", 
				new Object[] {todoId}, 
				new BeanPropertyRowMapper<>(Todo.class));
		
	}
	
	/**;
	 * 작성일, 최종수정일, 내용, 참조된 key에 따른 조회
	 * @param todoName
	 * @param writeDate
	 * @param lastUpdateDate
	 * @return
	 */
	public List<Todo> selectTodosByConditions(String query) {
		return jdbcTemplate.query(query, 
				new BeanPropertyRowMapper<>(Todo.class));	
	}
	

}

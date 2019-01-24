package kr.kakao.test.todomanager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.kakao.test.todomanager.dao.TodoDao;
import kr.kakao.test.todomanager.model.ListResult;
import kr.kakao.test.todomanager.model.Todo;

import org.apache.commons.lang3.StringUtils;

@Service
public class TodoService {

	@Autowired
	private TodoDao todoDao;
	
	/**
	 * 기본 jdbcTemplate에서 Dynamic where clause를 지원히지 않는 관계로 미리 query를 작성하는 조건
	 */
	private static final String FIND_TODOS_QUERY = "SELECT TODO_ID, "
			+ "TODO_NAME, WRITE_DATE, LAST_UPDATE_DATE, TODO_STATUS, REFER_TODO_ID "
			+ "FROM TODO "; // 기본조회쿼리
	private static final String WHERE_QUERY = "WHERE ";
	private static final String AND_QUERY = "AND ";
	private static final String TODO_NAME_QUERY_HEAD = "TODO_NAME LIKE '%'||'";
	private static final String TODO_NAME_QUERY_TAIL = "'||'%' ";
	private static final String LAST_UPDATE_DATE_QUERY_HEAD = "LAST_UPDATE_DATE = PARSEDATETIME('";
	private static final String WRITE_DATE_QUERY_HEAD = "WRITE_DATE = PARSEDATETIME('";
	private static final String DATE_QUERY_TAIL = "', 'yyyy-MM-dd') ";
	
	private static final String TODO_STATUS_CHANGE_FAIL = "참조가 걸린 할일이 미완료 중입니다. "
	+ "참조가 걸린 할일을 완료 후 다시 완료처리해 주세요."; // 업데이트 요청된 할일의 참조된 할일이 미완료중인 경우
	private static final String REFERED_AT = "@"; // 참조된 할일 ID 추가시 앞에 붙임
	
	/**
	 * jdbcTemplate.query 실행시 파라미터(todoId)가 ?형태로 들어갔을때 like가 실행되지 않았음
	 */
	private static final String CHK_REFER_TODOS_QUERY = "SELECT TODO_ID, TODO_NAME, "
			+ "WRITE_DATE, LAST_UPDATE_DATE, TODO_STATUS, REFER_TODO_ID "
			+ "FROM TODO WHERE REFER_TODO_ID LIKE '%'||'@"; //참조된 key값 조회 쿼리
	/**
	 * Show All
	 * @return
	 */
	public List<Todo> getAllTodos() {
		List<Todo> todoList = todoDao.selectAllTodos();
		return todoList;
	}
	
	/**
	 * Show All With Paging
	 * @return
	 */
	public ListResult<Todo> getAllTodosWithPaging() {
		ListResult<Todo> resultList = new ListResult<>();
		resultList.setList(todoDao.selectAllTodos());
		resultList.setTotalCount(todoDao.selectTodosCnt());
		resultList.setPage(null); // 페이징 세팅 구현중 
//		resultList.setPage(new PageHolder(resultList.getTotalCount(), Todo.getPage(), 
//				Todo.getListSize(), Todo.getPageSize());
		return resultList;
	}
	
	/**
	 * Regist Todo
	 * @param todo
	 * @return
	 */
	public Todo registTodo(Todo todo) {	
		if(StringUtils.isNotBlank(todo.getReferTodoId()))
			todo.setReferTodoId(setReferTodoId(todo.getReferTodoId()));
		Todo insertedTodo = todoDao.insertTodo(todo);
		return insertedTodo;
	}

	/**
	 * modify Todo status
	 * 참조된 자식이 완료처리가 되어있는지 확인 후 완료처리 가능
	 * @param todo
	 * @return
	 */
	public String modifyTodoStatus(Todo todo) {
		String query = CHK_REFER_TODOS_QUERY + Long.toString(todo.getTodoId()) + TODO_NAME_QUERY_TAIL;
		List<Todo> todoList = todoDao.selectTodosByConditions(query);
		
		if(todoList != null && todoList.size() > 0) {
			for(Todo referedTodo : todoList) {
				boolean referedTodoStatus = referedTodo.getTodoStatus();
				if(referedTodoStatus == false) {
					return TODO_STATUS_CHANGE_FAIL;
				}
			}
		} 
		Todo updatedTodo = todoDao.updateTodoStatus(todo);
		return Long.toString(updatedTodo.getTodoId());
	}
	
	/**
	 * find todo by pk(todoId)
	 * @param todoId
	 * @return
	 */
	public Todo findTodoByTodoId(String todoId) {
		return todoDao.selectTodoByKey(todoId);
	}
	
	/**
	 * find todos by todoName or writeDate or lastUpdateDate
	 * @param conditions
	 * @return
	 */
	public List<Todo> findTodosByConditions(Todo conditions) {
		String query = FIND_TODOS_QUERY;
		
		if(StringUtils.isNotBlank(conditions.getTodoName())) {
			query += WHERE_QUERY + TODO_NAME_QUERY_HEAD + conditions.getTodoName() 
			+ TODO_NAME_QUERY_TAIL;
		}
		
		if(StringUtils.isNotBlank(conditions.getLastUpdateDate())) {
			if(query.contains(WHERE_QUERY)) {
				query += AND_QUERY;
			} else {
				query += WHERE_QUERY;
			}
			query += LAST_UPDATE_DATE_QUERY_HEAD + conditions.getLastUpdateDate() 
			+ DATE_QUERY_TAIL;
		}
		
		if(StringUtils.isNotBlank(conditions.getWriteDate())) {
			if(query.contains(WHERE_QUERY)) {
				query += AND_QUERY;
			} else {
				query += WHERE_QUERY;
			}
			query += WRITE_DATE_QUERY_HEAD + conditions.getWriteDate() 
			+ DATE_QUERY_TAIL;
		}
				
		return todoDao.selectTodosByConditions(query);
	}
	
	/**
	 * 등록할 Todo의 ReferTodoId가 있을시 새로운 ReferTodoId 생성 
	 * @param todoId
	 * @return
	 */
	private String setReferTodoId(String todoId) {
		Todo retTodo = findTodoByTodoId(todoId);
		if(StringUtils.isNotBlank(retTodo.getReferTodoId()))
			return mergeReferTodoId(todoId, retTodo.getReferTodoId());
		else
			return REFERED_AT + todoId + " ";
	}
	
	/**
	 * TodoID 순차 정렬 후 @를 붙여 문자열 생성
	 * @param todoId
	 * @param referTodoId
	 * @return
	 */
	private String mergeReferTodoId(String todoId, String referTodoId) {
		String retReferTodoId = "";
		String[] referTodoIds = referTodoId.split(" ");
		List<Long> referTodoIdList = new ArrayList<>(); // Collections.sort 정렬용 List 생성
		
		for(String referTodoIdWithAt : referTodoIds) { // 참조 요청된 ID의 참조 ID들을 하나씩 담는다
			referTodoIdList.add(Long.parseLong(referTodoIdWithAt.replaceAll(REFERED_AT, "")));
		}
		referTodoIdList.add(Long.parseLong(todoId)); // 참조 요청된 ID 추가
		Collections.sort(referTodoIdList, new CompareLongDesc()); // 내림차순 정렬
		
		for(Long sortedReferTodoId : referTodoIdList) { //@을 추가한 정렬된 문자열 생성
			retReferTodoId += REFERED_AT + Long.toString(sortedReferTodoId) + " ";
		}
		
		return retReferTodoId;
	}
	
	/**
	 * long 내림차순 정렬
	 * @author ryan
	 *
	 */
	static class CompareLongDesc implements Comparator<Long> {
		@Override
		public int compare(Long o1, Long o2) {
			return o1.compareTo(o2);
		}
	}

	
}


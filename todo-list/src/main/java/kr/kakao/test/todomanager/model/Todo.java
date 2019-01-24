package kr.kakao.test.todomanager.model;

/**
 * Todo VO
 * @author ryan
 *
 */
public class Todo {

	private long todoId; //할일 ID
	private String todoName; // 할일 
	private String writeDate; // 작성일시 
	private String lastUpdateDate; // 최종수정일시  
	private Boolean todoStatus; // 완료처리 
	private String referTodoId; // 참조 할일 ID 복수
	
	public Todo() {}
	public Todo(long todoId, String todoName, String writeDate, 
			String lastUpdateDate, Boolean todoStatus, String referTodoId) {
		this.todoId = todoId;
		this.todoName = todoName;
		this.writeDate = writeDate;
		this.lastUpdateDate = lastUpdateDate;
		this.todoStatus = todoStatus;
		this.referTodoId = referTodoId;
	}

	public long getTodoId() {
		return todoId;
	}

	public void setTodoId(long todoId) {
		this.todoId = todoId;
	}

	public String getTodoName() {
		return todoName;
	}

	public void setTodoName(String todoName) {
		this.todoName = todoName;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Boolean getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(Boolean todoStatus) {
		this.todoStatus = todoStatus;
	}
	public String getReferTodoId() {
		return referTodoId;
	}
	public void setReferTodoId(String referTodoId) {
		this.referTodoId = referTodoId;
	}
	
	@Override
	public String toString() {
		return "Todo [todoId=" + todoId + ", todoName=" + todoName + ", writeDate=" + writeDate + ", lastUpdateDate="
				+ lastUpdateDate + ", todoStatus=" + todoStatus + ", referTodoId=" + referTodoId + "]";
	}
	
}

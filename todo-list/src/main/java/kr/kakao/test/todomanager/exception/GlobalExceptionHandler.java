package kr.kakao.test.todomanager.exception;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import kr.kakao.test.todomanager.util.ApiResponse;
import kr.kakao.test.todomanager.util.ApiResponse.Status;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = JdbcSQLException.class)
	public ApiResponse handleStatusException(JdbcSQLException e) {
		return new ApiResponse(Status.ERROR, 
				null, 
//				new ApiResponse.ApiError(500, e.getMessage()));
				new ApiResponse.ApiError(500, "참조가 걸린 할일이 미완료 중입니다. "
						+ "참조가 걸린 할일을 완료 후 다시 완료처리해 주세요."));
	}
	
	//EmptyResultDataAccessException
	@ExceptionHandler(value = EmptyResultDataAccessException.class)
	public ApiResponse handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
		return new ApiResponse(Status.ERROR, 
				null, 
				new ApiResponse.ApiError(600, "검색된 할일이 없습니다. "
						+ "다른 키워드로 검색해주세요."));
	}
}

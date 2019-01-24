# todo-list
서버개발자 사전과제
* 기능분석 
	1. 할일 추가 
		1.1 다른 할일 참조가 있다면 ID 체크 후 저장
			1.1.1 참조 확인용 코드 만들기 ex) @1 @3 
	2. 할일 상태 수정
		2.1 참조가 걸린 할일 체크 후 완료 처리 
			2.1.1 자식에 대한 참조 확인 필요 
			2.1.2 참조관계에 따른 오류메시지 출력필요 
	3. 할일 목록 조회 
		3.1 전체 조회
		3.2 작성일, 최종수정일, 내용에 따른 조회
		3.3 조회시 페이징 처리 ==> 구현중 
		
* 개발환경
 개발프레임워크: Spring Boot
 서버: Rest API
 In-memory db : h2 database
 
* 테스트를 위한 테이블 및 시퀀스 생성 쿼리 
CREATE TABLE TODO ( 
   TODO_ID LONG NOT NULL, 
   TODO_NAME VARCHAR(50) NOT NULL, 
   WRITE_DATE DATE NOT NULL,
   LAST_UPDATE_DATE DATE NOT NULL,
   TODO_STATUS BOOLEAN NOT NULL,
);

CREATE SEQUENCE TODO_ID_SEQ START WITH 1 INCREMENT BY 1; 

*********************************************************
* 페이징 개발 중이며 화면 및 Restful연결 미구현 
* TodoService는 TodoManagerApplicationTests에서 단위테스트 가능
*********************************************************

package kr.kakao.test.todomanager.model;

import java.util.List;

/**
 * <E> 목록으로 페이지 구성하려는 vo 클래스
 * 
 * @author ryan
 *
 * @param <E>
 */
public class ListResult<E> {

	private List<E> list; // <E>를 가지는 리스트

	private PageHolder page;

	private long totalCount; // 전체 카운트
	
	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public int getListCount() {
		if (null == list) {
			return 0;
		} else {
			return list.size();
		}
	}

	public PageHolder getPage() {
		return page;
	}

	public void setPage(PageHolder page) {
		this.page = page;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}


}

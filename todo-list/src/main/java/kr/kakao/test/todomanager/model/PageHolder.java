package kr.kakao.test.todomanager.model;

/**
 * <E> 목록으로 구성하려는 vo 클래스
 * @author ryan
 *
 */
public class PageHolder {

	private static final int DEFAULT_LISTSIZE = 10;

	private static final int DEFAULT_PAGESIZE = 10;
	
	private int listSize = DEFAULT_LISTSIZE; // 목록 갯수

	private int pageSize = DEFAULT_PAGESIZE; // 페이지 갯수

	private int currentPage = 0;
	
	private long totalCount = 0; // 전체 카운트
	
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getStartPage() {
		return ((this.currentPage - 1) / this.pageSize) * this.pageSize + 1;
	}

	public int getEndPage() {
		int endPage = getStartPage() + this.pageSize - 1;

		if (endPage > getTotalPage()) {
			endPage = getTotalPage();
		}

		return endPage;
	}

	public int getNextStartPage() {
		if (getEndPage() < getTotalPage()) {
			return getEndPage() + 1;
		} else {
			return 0;
		}
	}

	public int getTotalPage() {
		int totalPage = 0;

		if (0 < this.totalCount) {
			totalPage = (int) (this.totalCount / this.listSize);

			if (0 < (this.totalCount % this.listSize)) {
				totalPage++;
			}
		}

		return totalPage;
	}
	
    public PageHolder(long totalCount, int currentPage) {
		this.totalCount		= totalCount;
		this.currentPage	= currentPage;
	}

    public PageHolder(long totalCount, int currentPage, int listSize) {
		this.totalCount		= totalCount;
		this.currentPage	= currentPage;
		this.listSize		= listSize;
	}

    public PageHolder(long totalCount, int currentPage, int listSize, int pageSize) {
		this.totalCount		= totalCount;
		this.currentPage	= currentPage;
		this.listSize		= listSize;
		this.pageSize		= pageSize;
	}

}

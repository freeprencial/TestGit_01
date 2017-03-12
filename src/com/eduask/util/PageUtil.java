package com.eduask.util;

public class PageUtil {
	
	private Long totalCount;
	
	private Long pageIndex;
	
	private Long pageNum = 3L;
	
	private Long totalPage;

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Long pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Long getTotalPage() {
		
		if(totalCount%pageNum==0){
			
			totalPage = totalCount/pageNum;
		}else{
			System.out.println(totalCount);
			
			System.out.println(pageNum);
			totalPage = totalCount/pageNum+1;
		}
		return totalPage;
	}

	public Long getPageNum() {
		return pageNum;
	}

}

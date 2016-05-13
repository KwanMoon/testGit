package com.kgs7276.board.domain;

public class Criteria {

	private int page;
	private int perPageNum;
	
	public Criteria() {
		
		page = 1;
		perPageNum = 10;
		
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
			return;
		}
		
		this.page = page;
	}

	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0 || perPageNum >= 100) {
			this.perPageNum = 10;
			return;
		}
		
		this.perPageNum = perPageNum;
	}
	
	//sql mapper method
	public int getPageStart() { //#{pageStart}
		return (this.page - 1) * perPageNum;				
	}
	
	public int getPerPageNum() { //#{perPageNum}
		return this.perPageNum;
	}
		
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", "
				+ "perPageNum=" + perPageNum + "]";
	}	
	
}
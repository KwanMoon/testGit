package com.kgs7276.board.persistence;

import java.util.List;

import com.kgs7276.board.domain.BoardVO;
import com.kgs7276.board.domain.SearchCriteria;

public interface BoardDAO {

	//게시글 등록
	public void create(BoardVO board) throws Exception;
	
	//게시글 목록(페이지 적용 유, SearchCriteria 객체<page, perPageNum, searchType, keyword> 사용 유)
	public List<BoardVO> list(SearchCriteria criteria) throws Exception;
	
	//검색 게시글 개수(SearchCriteria 객체<page, perPageNum, searchType, keyword> 사용 유)
	public int listCount(SearchCriteria criteria) throws Exception;
		
	//게시글 조회
	public BoardVO read(Integer bno) throws Exception;
	
	//게시글 수정
	public void update(BoardVO board) throws Exception;
	
	//게시글 삭제
	public void delete(Integer bno) throws Exception;
	
	//댓글 숫자 갱신(+1 또는 -1)
	public void updateReplyCnt(Integer bno, int amount) throws Exception;
	
	//본글 조회수 갱신(게시글 조회마다 viewcnt 컬럼의 값을 +1씩 증가)
	public void updateViewCnt(Integer bno) throws Exception;
	
	//첨부파일 등록
	public void addAttach(String fullName) throws Exception;
	
	//첨부파일 목록 조회
	public List<String> getAttach(Integer bno) throws Exception;
	
}
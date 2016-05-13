package com.kgs7276.board.service;

import java.util.List;

import com.kgs7276.board.domain.BoardVO;
import com.kgs7276.board.domain.SearchCriteria;

public interface BoardService {

	//게시글 등록
	public void regist(BoardVO board) throws Exception;

	//게시글 목록(페이지 적용 유, SearchCriteria 객체<page, perPageNum, searchType, keyword> 사용 유)
	public List<BoardVO> list(SearchCriteria criteria) throws Exception;
	
	//검색 게시글 개수
	public int listCount(SearchCriteria criteria) throws Exception;

	//게시글 조회
	public BoardVO read(Integer bno) throws Exception;
	
	//게시글 수정을 위한 조회
	public BoardVO readForModify(Integer bno) throws Exception;

	//게시글 수정
	public void modify(BoardVO board) throws Exception;

	//게시글 삭제
	public void remove(Integer bno) throws Exception;
	
	//첨부파일 목록 조회
	public List<String> getAttach(Integer bno) throws Exception;

}
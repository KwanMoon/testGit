package com.kgs7276.board.service;

import java.util.List;

import com.kgs7276.board.domain.Criteria;
import com.kgs7276.board.domain.ReplyVO;

public interface ReplyService {

	//댓글 등록	
	public void addReply(ReplyVO reply) throws Exception;
	
	//댓글 목록 조회(Integer bno : 본글의 번호)
	//public List<ReplyVO> list(Integer bno) throws Exception;
	
	//댓글 목록 조회(Integer bno : 본글의 번호), Criteria 사용
	public List<ReplyVO> list(Integer bno, Criteria criteria) throws Exception;
	
	//댓글 개수 조회(Integer bno : 본글의 번호)
	public int listCount(Integer bno) throws Exception;	
	
	//댓글 수정
	public void modify(ReplyVO reply) throws Exception;
	
	//댓글 삭제(Integer rno : 댓글의 번호)
	public void remove(Integer rno) throws Exception;
		
}
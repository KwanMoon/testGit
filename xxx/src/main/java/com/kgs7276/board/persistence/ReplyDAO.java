package com.kgs7276.board.persistence;

import java.util.List;

import com.kgs7276.board.domain.Criteria;
import com.kgs7276.board.domain.ReplyVO;

public interface ReplyDAO {

	//댓글 등록
	public void create(ReplyVO reply) throws Exception;	

	//댓글 리스트(Integer bno 본글의 글번호)
	/*public List<ReplyVO> list(Integer bno) throws Exception;*/
	
	//댓글 리스트(Integer bno 본글의 글번호), Criteria 사용
	public List<ReplyVO> list(Integer bno, Criteria criteria) throws Exception;
	
	//댓글 개수((Integer bno 본글의 글번호)
	public int listCount(Integer bno) throws Exception;

	//댓글 수정
	public void update(ReplyVO reply) throws Exception;

	//댓글 삭제(Integer rno 댓글 번호)
	public void delete(Integer rno) throws Exception;	
	
	//댓글 삭제 시 본글 번호 조회
	public int getBno(Integer rno) throws Exception;

}


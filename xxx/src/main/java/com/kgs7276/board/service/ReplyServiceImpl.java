package com.kgs7276.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kgs7276.board.domain.Criteria;
import com.kgs7276.board.domain.ReplyVO;
import com.kgs7276.board.persistence.BoardDAO;
import com.kgs7276.board.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	private ReplyDAO dao;
	
	@Inject
	private BoardDAO bdao;

	//댓글 등록
	@Transactional
	@Override
	public void addReply(ReplyVO reply) throws Exception {
		
		dao.create(reply);
		bdao.updateReplyCnt(reply.getBno(), 1);

	}	
	
	//댓글 목록 조회(Integer bno : 본글의 번호), Criteria 사용
	@Override
	public List<ReplyVO> list(Integer bno, Criteria criteria) throws Exception {
		
		return dao.list(bno, criteria);
		
	}
	
	//댓글 전체 개수(Integer bno : 본글의 번호)
	@Override
	public int listCount(Integer bno) throws Exception {
		
		return dao.listCount(bno);
		
	}

	//댓글 수정
	@Override
	public void modify(ReplyVO reply) throws Exception {
		
		dao.update(reply);

	}

	//댓글 삭제(Integer rno : 댓글의 번호)
	@Transactional
	@Override
	public void remove(Integer rno) throws Exception {
		
		int bno = dao.getBno(rno);
		dao.delete(rno);		
		bdao.updateReplyCnt(bno, -1);
		
	}	

}
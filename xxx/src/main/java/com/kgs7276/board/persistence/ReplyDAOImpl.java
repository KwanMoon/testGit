package com.kgs7276.board.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kgs7276.board.domain.Criteria;
import com.kgs7276.board.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

	private static final String namespace = "com.kgs7276.board.mapper.ReplyMapper";

	@Inject
	private SqlSession session;

	//댓글 등록
	@Override
	public void create(ReplyVO reply) throws Exception {

		session.insert(namespace + ".create", reply);

	}

	//댓글 리스트(Integer bno : 본글의 글번호)
	/*@Override
	public List<ReplyVO> list(Integer bno) throws Exception {

		return session.selectList(namespace + ".list", bno);

	}*/

	//댓글 리스트(Integer bno : 본글의 글번호), Criteria 사용
	@Override
	public List<ReplyVO> list(
			Integer bno, 
			Criteria criteria) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("criteria", criteria);

		return session.selectList(namespace + ".list", map);

	}
	
	//댓글 개수 조회(Integer bno : 본글의 글번호)
	@Override
	public int listCount(Integer bno) throws Exception {
		
		return session.selectOne(namespace + ".listCount", bno);
		
	}

	//댓글 수정
	@Override
	public void update(ReplyVO reply) throws Exception {

		session.update(namespace + ".update", reply);

	}

	//댓글 삭제(Integer rno : 댓글의 번호)
	@Override
	public void delete(Integer rno) throws Exception {

		session.delete(namespace + ".delete", rno);

	}

	/*댓글 삭제 시 본글 번호 조회*/
	@Override
	public int getBno(Integer rno) throws Exception {
		
		//return session.selectOne(namespace + ".getBno", rno);
		return session.selectOne(namespace + ".getBno", rno);
		
	}

}
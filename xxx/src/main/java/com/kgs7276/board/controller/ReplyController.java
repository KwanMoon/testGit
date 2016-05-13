package com.kgs7276.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kgs7276.board.domain.Criteria;
import com.kgs7276.board.domain.PageMaker;
import com.kgs7276.board.domain.ReplyVO;
import com.kgs7276.board.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {

	private static final Logger logger = 
			LoggerFactory.getLogger(ReplyController.class);

	@Inject
	private ReplyService service;

	//댓글 등록
	//http://localhost:8080/replies
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO reply) {

		logger.info("replies post ...");
		
		ResponseEntity<String> entity = null;

		try {
			service.addReply(reply);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch(Exception e) {			
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}			

		return entity; //클라이언트로 데이터(현재는 String)과 함께 HTTP 상태코드(현재는 200, 404)를 반환

	}

	/*//댓글 목록 조회(Integer bno : 본글의 번호)
	//http://localhost:8080/replies/XXX (XXX 본글 번호)
	@RequestMapping(value="/{bno}", method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(
			@PathVariable("bno") Integer bno
			) {

		ResponseEntity<List<ReplyVO>> entity = null;

		try {
			List<ReplyVO> list = service.list(bno);
			entity = new ResponseEntity<>(list, HttpStatus.OK);
		}catch(Exception e) {			
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}			

		return entity; //클라이언트로 데이터(List<ReplyVO>)와 함께 HTTP 상태코드(현재는 200, 404)를 반환

	}*/

	//댓글 목록 조회(Integer bno : 본글의 번호), Criteria 사용
	//http://localhost:8080/replies/XXX/YYY (XXX 본글 번호, YYY 페이지 번호)
	@RequestMapping(value="/{bno}/{page}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> list(
			@PathVariable("bno") Integer bno,
			@PathVariable("page") Integer page
			) {

		logger.info("/{bno}/{page} get ...");
		
		ResponseEntity<Map<String, Object>> entity = null;

		try {
			Criteria criteria = new Criteria();
			criteria.setPage(page);
					
			List<ReplyVO> list = service.list(bno, criteria);
			int listCount = service.listCount(bno);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(criteria);
			pageMaker.setTotalCount(listCount);
			
			Map<String, Object> map = new HashMap<>();			
			map.put("list", list);
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<>(map, HttpStatus.OK);
		}catch(Exception e) {			
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}			

		return entity; //클라이언트로 데이터(List<ReplyVO>)와 함께 HTTP 상태코드(현재는 200, 404)를 반환

	}

	//댓글 수정(Integer rno : 댓글 번호)
	@RequestMapping(value="/{rno}", method={RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(
			@PathVariable("rno") Integer rno,
			@RequestBody ReplyVO reply //setReplytext(String replytext)만 호출
			) {

		logger.info("/{rno} put or patch ...");
		
		ResponseEntity<String> entity = null;

		try {
			reply.setRno(rno);
			service.modify(reply);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch(Exception e) {			
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}			

		return entity; //클라이언트로 데이터(현재는 String)과 함께 HTTP 상태코드(현재는 200, 404)를 반환

	}

	//댓글 삭제(Integer rno : 댓글의 번호)
	@RequestMapping(value="/{rno}", method=RequestMethod.DELETE)
	public ResponseEntity<String> remove(
			@PathVariable("rno") Integer rno
			) {

		logger.info("/{rno} delete ...");
		
		ResponseEntity<String> entity = null;

		try {
			service.remove(rno);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch(Exception e) {			
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}			

		return entity; //클라이언트로 데이터(현재는 String)과 함께 HTTP 상태코드(현재는 200, 404)를 반환

	}

}
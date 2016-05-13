package com.kgs7276.board;

import java.util.List;
import java.util.Scanner;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kgs7276.board.domain.Criteria;
import com.kgs7276.board.domain.ReplyVO;
import com.kgs7276.board.service.ReplyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				"file:src/main/webapp/WEB-INF/spring/**/*.xml"
		})
public class ReplyServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ReplyServiceTest.class);

	@Inject
	private ReplyService service;

	//댓글 등록 테스트
	@Test
	public void testAddReply() throws Exception {

		Scanner scanner = new Scanner(System.in);
		System.out.println("댓글 작성자를 입력하세요.");
		String replyer = scanner.next();		
		System.out.println("본글 번호를 입력하세요.");
		int bno = scanner.nextInt();
		System.out.println("댓글 내용을 입력하세요.");
		String replytext = scanner.next();

		scanner.close();

		ReplyVO reply = new ReplyVO();
		reply.setBno(bno);
		reply.setReplytext(replytext);
		reply.setReplyer(replyer);		

		service.addReply(reply);

		logger.info("댓글 등록 성공");

	}

	/*//댓글 리스트(Integer bno 본글의 글번호) 테스트
	@Test
	public void testList() throws Exception {

		Scanner scanner = new Scanner(System.in);
		System.out.println("본글 번호를 입력하세요.");
		int bno = scanner.nextInt();
		scanner.close();

		List<ReplyVO> list = service.list(bno);

		for(ReplyVO reply : list) {
			System.out.println(reply.toString());
		}

	}*/

	//댓글 리스트(Integer bno 본글의 글번호, Criteria 사용) 테스트
	@Test
	public void testList() throws Exception {

		Scanner scanner = new Scanner(System.in);
		System.out.println("본글 번호를 입력하세요.");
		int bno = scanner.nextInt();
		System.out.println("페이지 번호를 입력하세요.");
		int page = scanner.nextInt();
		System.out.println("페이지당 보여 줄 댓글 개수를 입력하세요.");
		int perPageNum = scanner.nextInt();
		scanner.close();

		Criteria criteria = new Criteria();
		criteria.setPage(page);
		criteria.setPerPageNum(perPageNum);

		List<ReplyVO> list = service.list(bno, criteria);

		System.out.println(bno + "의 전체 댓글수 : " + service.listCount(bno));

		for(ReplyVO reply : list) {
			System.out.println(reply.toString());
		}

	}

	//댓글 수정
	@Test
	public void testUpdate() throws Exception {

		Scanner scanner = new Scanner(System.in);
		System.out.println("댓글 번호를 입력하세요.");
		int rno = scanner.nextInt();
		System.out.println("댓글 내용을 입력하세요.");
		String replytext = scanner.next();
		scanner.close();

		ReplyVO reply = new ReplyVO();
		reply.setRno(rno);
		reply.setReplytext(replytext);

		service.modify(reply);

		logger.info("댓글 수정 성공");

	}

	//댓글 삭제 테스트
	public void testDelete() throws Exception {

		Scanner scanner = new Scanner(System.in);
		System.out.println("댓글 번호를 입력하세요.");
		int rno = scanner.nextInt();		
		scanner.close();

		service.remove(rno);

		logger.info("댓글 삭제 성공");
	}


}
package com.kgs7276.board;

import java.util.Scanner;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kgs7276.board.domain.MessageVO;
import com.kgs7276.board.persistence.MessageDAO;
import com.kgs7276.board.persistence.PointDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				"file:src/main/webapp/WEB-INF/spring/**/*.xml"
		})
public class MessageDAOTest {

	private static final Logger logger = LoggerFactory.getLogger(MessageDAOTest.class);

	@Inject
	private MessageDAO dao;
	
	@Inject
	private PointDAO pdao;

	@Test
	public void createTest() throws Exception {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("메세지 수신자 아이디를 입력하세요.");
		String targetid = scanner.next();
		System.out.println("메세지 발신자 아이디를 입력하세요.");
		String sender = scanner.next();
		System.out.println("메세지를 입력하세요.");
		String message = scanner.next();
		scanner.close();
		
		MessageVO messageVO = new MessageVO();
		messageVO.setTargetid(targetid);
		messageVO.setSender(sender);
		messageVO.setMessage(message);
		
		dao.create(messageVO); //메세지 생성(쓰기)
		pdao.updatePoint(messageVO.getSender(), 10); //메세지 읽기 포인트(10) 증가
		
		logger.info("메시지 생성 성공");
		
	}
	
	@Test
	public void readTest() throws Exception {
	
		Scanner scanner = new Scanner(System.in);
		System.out.println("메세지 아이디를 입력하세요.");
		int mid = scanner.nextInt();
		scanner.close();
		
		MessageVO message = dao.read(mid); //메세지 읽기
		dao.updateState(mid); //메세지 읽은 날짜 수정
		pdao.updatePoint(message.getTargetid(), 5); //메세지 읽기 포인트(5) 증가
		
		logger.info(message.toString());
		logger.info("메시지 읽기 성공");
		
	}


}
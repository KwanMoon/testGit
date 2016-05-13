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
import com.kgs7276.board.service.MessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				"file:src/main/webapp/WEB-INF/spring/**/*.xml"
		})
public class MessageServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceTest.class);

	@Inject
	private MessageService service;

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
		
		service.add(messageVO);//메세지 생성(쓰기)		
		
		logger.info("메시지 생성 성공");
		
	}
	
	@Test
	public void readTest() throws Exception {
	
		Scanner scanner = new Scanner(System.in);
		System.out.println("메세지 아이디를 입력하세요.");
		int mid = scanner.nextInt();
		scanner.close();
		
		MessageVO message = service.read(mid); //메세지 읽기
		
		logger.info(message.toString());
		logger.info("메시지 읽기 성공");
		
	}


}
package com.kgs7276.board.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kgs7276.board.domain.MessageVO;
import com.kgs7276.board.persistence.MessageDAO;
import com.kgs7276.board.persistence.PointDAO;


@Service
public class MessageServiceImpl implements MessageService {
	
	@Inject
	private MessageDAO dao;
	
	@Inject
	private PointDAO pdao;		
		
	@Transactional
	@Override
	public void add(MessageVO message) throws Exception {
		
		dao.create(message);
		pdao.updatePoint(message.getSender(), 10);
		
	}

	@Override
	public MessageVO read(Integer mid) throws Exception {
		
		MessageVO message = dao.read(mid);
		dao.updateState(mid);
		pdao.updatePoint(message.getTargetid(), 5);
		
		return message;
	}

}

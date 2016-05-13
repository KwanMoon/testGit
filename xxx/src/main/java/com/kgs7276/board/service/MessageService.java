package com.kgs7276.board.service;

import com.kgs7276.board.domain.MessageVO;

public interface MessageService {
	
	public void add(MessageVO message) throws Exception;
	
	public MessageVO read(Integer mid) throws Exception;
	
}
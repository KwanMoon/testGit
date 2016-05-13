package com.kgs7276.board.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kgs7276.board.domain.MessageVO;
import com.kgs7276.board.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

	@Inject
	private MessageService service;
		
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> addMessage(@RequestBody MessageVO message) {
		
		ResponseEntity<String> entity = null;
		
		try{
			service.add(message);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
		
	}	
	
}
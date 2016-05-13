package com.kgs7276.board.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class)
	public ModelAndView common(Exception e) {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", e);
		mv.setViewName("/error_common");
		
		return mv;
		
	}	
	
}
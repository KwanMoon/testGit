package com.kgs7276.board.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class SampleRESTController {

	@RequestMapping("/hello")
	public String sayHello() {
		
		return "Hello World!111";
		
	}
	
	@RequestMapping("/sendVO")
	public SampleVO sendVO() {
		
		SampleVO sample = new SampleVO();
		sample.setMno(1);
		sample.setFirstName("권식");
		sample.setLastName("김");
				
		return sample;
		
	}
	
	@RequestMapping("/sendList")
	public List<SampleVO> sendList() {
		
		SampleVO sample = new SampleVO();
		sample.setMno(1);
		sample.setFirstName("권식");
		sample.setLastName("김");
		
		List<SampleVO> list = new ArrayList<>();
		list.add(sample);
		list.add(sample);
		list.add(sample);
		
		return list;
		
	}
	
	@RequestMapping("/sendMap")
	public Map<String, SampleVO> sendMap() {
		
		SampleVO sample = new SampleVO();
		sample.setMno(1);
		sample.setFirstName("권식");
		sample.setLastName("김");
		
		Map<String, SampleVO> map = new HashMap<>();
		map.put("1", sample);
		map.put("2", sample);
		map.put("3", sample);
		
		return map;
		
	}
	
	@RequestMapping("/sendErrorAuth")
	public ResponseEntity<Void> sendErrorAuth() {
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	@RequestMapping("/sendErrorNot")
	public ResponseEntity<SampleVO> sendErrorNot() {
		
		SampleVO sample = new SampleVO();
		sample.setMno(1);
		sample.setFirstName("권식");
		sample.setLastName("김");
		
		return new ResponseEntity<>(sample, HttpStatus.NOT_FOUND);
		
	}
	
}
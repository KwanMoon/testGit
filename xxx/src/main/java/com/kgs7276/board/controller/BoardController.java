package com.kgs7276.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kgs7276.board.domain.BoardVO;
import com.kgs7276.board.domain.PageMaker;
import com.kgs7276.board.domain.SearchCriteria;
import com.kgs7276.board.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	private static final Logger logger = 
			LoggerFactory.getLogger(BoardController.class);
		
	@Inject
	BoardService service;

	//게시글 목록 조회(페이징 유, SearchCriteria 객체 사용)
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public void list(
			@ModelAttribute("criteria") SearchCriteria criteria,
			Model model
			) throws Exception {
		
		logger.info("list get ...");
		
		model.addAttribute("list", service.list(criteria));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(criteria);
		pageMaker.setTotalCount(service.listCount(criteria));		
		model.addAttribute("pageMaker", pageMaker);		
		
	}
	
	//게시글 조회
	@RequestMapping(value="/read", method=RequestMethod.GET)
	public void read(
			@RequestParam("bno") int bno,
			@ModelAttribute("criteria") SearchCriteria criteria,
			Model model
			) throws Exception {
		
		logger.info("read get ...");
		
		model.addAttribute("board", service.read(bno));
		
	}
	
	//게시글 삭제
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String remove(
			@RequestParam("bno") int bno,
			SearchCriteria criteria,
			RedirectAttributes rttr
			) throws Exception {
				
		logger.info("remove post ...");
		
		service.remove(bno);
		
		rttr.addAttribute("page", criteria.getPage());
		rttr.addAttribute("perPageNum", criteria.getPerPageNum());
		rttr.addAttribute("searchType", criteria.getSearchType());
		rttr.addAttribute("keyword", criteria.getKeyword());
		rttr.addFlashAttribute("result", "success");
				
		return "redirect:/board/list";
		
	}
	
	//게시글 수정 양식 조회
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public void modifyGET(
			@RequestParam("bno") int bno,
			@ModelAttribute("criteria") SearchCriteria criteria,
			Model model
			) throws Exception {
		
		logger.info("modify get ...");
		
		model.addAttribute("board", service.readForModify(bno));
		
	}	
	
	//게시글 수정
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifyPOST(
			BoardVO board,
			@ModelAttribute("criteria") SearchCriteria criteria,
			RedirectAttributes rttrs
			) throws Exception {
		
		logger.info("modify post ...");
		
		service.modify(board);
		
		rttrs.addAttribute("page", criteria.getPage());
		rttrs.addAttribute("perPageNum", criteria.getPerPageNum());
		rttrs.addAttribute("searchType", criteria.getSearchType());
		rttrs.addAttribute("keyword", criteria.getKeyword());
		rttrs.addFlashAttribute("result", "success");
				
		return "redirect:/board/list";
		
	}
	
	//게시글 등록 양식 조회
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public void registerGET(
			@ModelAttribute("criteria") SearchCriteria criteria
			) throws Exception {
		
		logger.info("register get ...");
		
	}
	
	//게시글 등록
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPOST(
			BoardVO board,
			RedirectAttributes rttrs
			) throws Exception {
		
		logger.info("register post ...");
				
		service.regist(board);
		
		rttrs.addFlashAttribute("result", "success");
		
		return "redirect:/board/list";
		
	}
	
	@ResponseBody
	@RequestMapping("/getAttach/{bno}")
	public List<String> getAttach(@PathVariable("bno") Integer bno) throws Exception {
		
		return service.getAttach(bno);
		
	}
	
	
}
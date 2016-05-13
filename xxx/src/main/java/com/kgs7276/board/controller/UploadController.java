package com.kgs7276.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kgs7276.board.util.MimeMediaUtil;
import com.kgs7276.board.util.UploadFileUtils;

@Controller
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Resource(name="uploadpath")
	private String uploadpath; //uploadpath => c:\\uploadpath
			
	@ResponseBody
	@RequestMapping(value="/uploadAjax", method=RequestMethod.POST, 
					produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		
		logger.info("파일 이름 : " + file.getOriginalFilename());
		logger.info("파일 크기 : " + file.getSize() + "Byte");
		logger.info("파일 타입 : " + file.getContentType());
		
		//return new ResponseEntity<>(file.getOriginalFilename(), HttpStatus.CREATED);
		return new ResponseEntity<>(UploadFileUtils.uploadFile(uploadpath,
				file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
		
	}
	
	@ResponseBody
	@RequestMapping(value="/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
				
		//���� ��û�� ������ ���� ���Ͻý��ۿ��� �б� ���� InputStream
		InputStream is = null;
		ResponseEntity<byte[]> entity = null;
		
		//fileName : /��/��/��/���ϸ�.Ȯ����
		logger.info("File Name : " + fileName);
		
		try {
			//formatName : ex) jpg
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			//mType : formatName�� ������ JPG, PNG, GIF�� ���ԵǴ��� Ȯ��(���Ե��� ������  null)
			MediaType mType = MimeMediaUtil.getMediaType(formatName);			
			
			//Ŭ���̾�Ʈ�� ������ �������� ������ ����� ����
			HttpHeaders headers = new HttpHeaders();
			//Ŭ���̾�Ʈ�� ������ ������ ��ǲ��Ʈ������ ����
			is = new FileInputStream(uploadpath + fileName);
			
			if(mType != null) { //Ŭ���̾�Ʈ�� ������ ������ Ȯ���ڰ� JPG, PNG, GIF � ���Ե��� �ʴ� ��� => �Ϲ� ����
				headers.setContentType(mType);
			}else { //Ŭ���̾�Ʈ�� ������ ������ �̹��� ������ ���
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\"" +
						new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\""); 
			}
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(is),
					headers, HttpStatus.CREATED);			
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			is.close();
		}
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteFile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName) {
		
		logger.info("delete file : " + fileName);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		MediaType mType = MimeMediaUtil.getMediaType(formatName);
		
		if(mType != null) { // ������ ��û�� ������ �̹���(����� JPG, PNG, GIF)���
			String front = fileName.substring(0, 12);
			String end = fileName.substring(14);
			
			//���� �̹��� ���� ����
			new File(uploadpath + (front + end).replace('/', File.separatorChar)).delete();
		} 
		
		//�̹����� ��� ����� �̹��� ������, �Ϲ� ������ ���� �Ϲ� ���� ��ü�� ����
		new File(uploadpath + fileName.replace('/', File.separatorChar)).delete();
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
		
	}
	
}
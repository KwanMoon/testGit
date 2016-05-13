package com.kgs7276.board.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(UploadFileUtils.class);
	
	public static String uploadFile(String uploadpath, 
			String originalName, byte[] fileData) throws Exception {
		
		//������ �����̸� ������ ���� UUID ���� �� ����
		UUID uid = UUID.randomUUID();
		
		//���� ���� �̸� �� ���� ����(���� ���� �� �ʿ�)
		originalName = originalName.replace(" ", "");
		
		//UUID ���� �� + _ + ���� ������ ���� �����̸�
		String savedName = uid.toString() + "_" + originalName;
		
		//�⺻ ���(uploadpath) �ؿ� ������ ������ ����(�����)���� �� ��� Ȯ��
		String savedPath = calcPath(uploadpath);
		
		//������ ������ ŸŶ(target) ���� : �⺻ ��� + ���� ��� + ���� �̸����� ���� ��ü ����
		File target = new File(uploadpath + savedPath, savedName);
		
		//������ ����(fileData)��  target�� ����(���� ���� ����)
		FileCopyUtils.copy(fileData, target);
		
		//���� �̸����� . ������ ���ڿ�(���� Ȯ���� ex> jpg ��) ����(���� ����)
		String formatName = originalName.substring(originalName.lastIndexOf(".") + 1);		
		
		String uploadFileName = null;
		
		//���� ������  MimeMediaUtil�� ����Ǿ� �ִ� Ÿ������ �˻�(����� PNG, JPG, GIF)
		if(MimeMediaUtil.getMediaType(formatName) != null) {
			//����� �̹��� ���� �� ������ �̹��� ���� �̸�(�̸��� s_�� ���ԵǾ� �ֵ��� ����Ǿ� ����) Ȯ��
			uploadFileName = makeThumnail(uploadpath, savedPath, savedName);
		}else { //����, ���� ������ ������ ���� ����(���� Ȯ����)�� MimeMediaUtil�� ����� ������ �ƴ϶��
			uploadFileName = makeIcon(uploadpath, savedPath, savedName);
		}
		
		return uploadFileName;
		
	}
	
	//MimeMediaUtil�� ���ԵǾ� ���� ���� ������ ��� ȣ��� �޼���
	private static String makeIcon(String uploadpath, String path, String fileName) throws Exception {
		
		String iconName = uploadpath + path + File.separator + fileName;
		
		return iconName.substring(uploadpath.length()).replace(File.separatorChar, '/');
		
	}
	
	///������ ������ ����(����� ����� ���)������ �����Ͽ� ���� ������ �����ϴ� �޼���(makeDir)�� ȣ��
	private static String calcPath(String uploadpath) {
		
		Calendar cal = Calendar.getInstance();
		
		String year = File.separator + cal.get(Calendar.YEAR);
		String month = year + File.separator + 
				new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String date = month + File.separator + 
				new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		makeDir(uploadpath, year, month, date);
		
		logger.info(date);
		
		return date;
		
	}
	
	//���� ������ �����ϴ� �޼���
	private static void makeDir(String uploadpath, String... paths) {
		
		//������� �ϴ� ������ �����ϴ��� Ȯ��
		if(new File(paths[paths.length - 1]).exists()) {
			return;
		}
		
		//������� �ϴ� ������ �������� �ʴ´ٸ� �Ʒ� ���� ����
		for(String path : paths) {
			File dirPath = new File(uploadpath + path);
			
			//���� ����
			if(!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
		
	}
	
	//����� �̹����� �����ϴ� �޼���
	private static String makeThumnail(String uploadpath,
			String path, String fileName) throws Exception {
		
		BufferedImage soruceImg = ImageIO.read(new File(uploadpath + path, fileName));
		BufferedImage destImg = Scalr.resize(soruceImg, 
				Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		
		String thumnailName = uploadpath + path + File.separator + "s_" + fileName;
		File newFile = new File(thumnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		return thumnailName.substring(uploadpath.length()).
				replace(File.separatorChar, '/');		
		
	}
	
}
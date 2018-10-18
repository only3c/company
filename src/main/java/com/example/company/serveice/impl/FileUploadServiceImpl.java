package com.example.company.serveice.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.company.model.FileStorage;
import com.example.company.repository.FileStorageRepository;
import com.example.company.serveice.FileUploadService;

@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService{
	private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

	
	@Value("${local.storage.root}")
	private String rootPath;
	@Autowired
	private FileStorageRepository fileStorageRepository;

	@Override
	public FileStorage upload(MultipartFile file) {
		if (file.isEmpty())
			throw new RuntimeException("上传失败，因为文件是空的.");
		FileStorage storage = new FileStorage();
		try {
			final String no = UUID.randomUUID().toString();
			String fileSourcePath = rootPath;
			
			String fileName = file.getOriginalFilename();
			logger.info("上传的文件名为：" + fileName);
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			logger.info("上传的后缀名为：" + suffixName);
			logger.info("上传的size为：" + file.getSize());
			
			File fileSource = new File(fileSourcePath, no+suffixName);

			
			if (!fileSource.getParentFile().exists()) {
				fileSource.getParentFile().mkdirs();
			}
			file.transferTo(fileSource);
			
			storage.setFileName(fileName.toString());
			storage.setNo(no);
			storage.setFileSize(file.getSize());
			storage.setSuffixName(suffixName);
			storage = this.fileStorageRepository.save(storage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("上传失败," + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("上传失败," + e.getMessage());
		}
		return storage;
	
	}

	@Override
	public void download(Long id, HttpServletResponse response){
	      FileStorage fileStorage = fileStorageRepository.findOne(id);
	      if(fileStorage==null) {
	    	  throw new RuntimeException("文件不存在");
	      }
	      File file = new File(rootPath, fileStorage.getNo()+fileStorage.getSuffixName());
	      if (file.exists()) {
	        response.setContentType("application/force-download");// 设置强制下载不打开
	        try {
				response.addHeader("Content-Disposition",
				    "attachment;fileName=" + new String(fileStorage.getFileName().getBytes(),"iso-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				throw new RuntimeException("字符集异常");
			}// 设置文件名
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = null;
	        BufferedInputStream bis = null;
	        try {
	          fis = new FileInputStream(file);	
	          bis = new BufferedInputStream(fis);
	          OutputStream os = response.getOutputStream();
	          //PrintWriter pWriter = response.getWriter();
	          int i = bis.read(buffer);
	          while (i != -1) {
	            os.write(buffer, 0, i);
	            i = bis.read(buffer);
	          }
	          //os.flush();//解决 getOutputStream() has already been called for this response 问题
	          os.close();
	          System.out.println("success");
	        } catch (Exception e) {
	          e.printStackTrace();
	        } finally {
	          if (bis != null) {
	            try {
	              bis.close();
	            } catch (IOException e) {
	              e.printStackTrace();
	            }
	          }
	          if (fis != null) {
	            try {
	              fis.close();
	            } catch (IOException e) {
	              e.printStackTrace();
	            }
	          }
	        }
	      }
	  }
	@Override
	public void downloadTempl(HttpServletResponse response){
		String path = "src/main/resources/templates";
	      File file = new File(path, "题库导入模板.xls");
	      if (file.exists()) {
	        response.setContentType("application/force-download");// 设置强制下载不打开
	        try {
				response.addHeader("Content-Disposition",
				    "attachment;fileName=" + new String("题库导入模板.xls".getBytes(),"iso-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				throw new RuntimeException("字符集异常");
			}// 设置文件名
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = null;
	        BufferedInputStream bis = null;
	        try {
	          fis = new FileInputStream(file);	
	          bis = new BufferedInputStream(fis);
	          OutputStream os = response.getOutputStream();
	          //PrintWriter pWriter = response.getWriter();
	          int i = bis.read(buffer);
	          while (i != -1) {
	            os.write(buffer, 0, i);
	            i = bis.read(buffer);
	          }
	          //os.flush();//解决 getOutputStream() has already been called for this response 问题
	          os.close();
	          System.out.println("success");
	        } catch (Exception e) {
	          e.printStackTrace();
	        } finally {
	          if (bis != null) {
	            try {
	              bis.close();
	            } catch (IOException e) {
	              e.printStackTrace();
	            }
	          }
	          if (fis != null) {
	            try {
	              fis.close();
	            } catch (IOException e) {
	              e.printStackTrace();
	            }
	          }
	        }
	      }
	  }

}

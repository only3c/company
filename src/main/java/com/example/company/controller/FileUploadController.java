package com.example.company.controller;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.company.serveice.FileUploadService;

@Controller
public class FileUploadController {

	@Value("${local.storage.root}")
	private String rootPath;
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping("/file")
	public String file() {
		return "/file";
	}
	
	@RequestMapping("/mutifile")
	public String batchFile() {
		return "/mutifile";
	}

	/**
	 * 文件上传具体实现方法;
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		this.fileUploadService.upload(file);
		return "上传成功";
	}
	/**
	 * 批量文件上传具体实现方法;
	 */
	@RequestMapping("/batch/upload")
	public @ResponseBody String batchFileUpload(HttpServletRequest request) {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	    for (MultipartFile file :files) {
            try {
            this.fileUploadService.upload(file);
            } catch (Exception e) {
                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
            }
	    }
	        return "上传成功";
	}

	  @RequestMapping("/download/{id}")
	  public String downloadFile(@PathVariable("id") Long id, HttpServletResponse response) throws UnsupportedEncodingException{  
		  this.fileUploadService.download(id, response);
		  return "下载成功";
	  }
	  
	  @RequestMapping("/downloadTempl")
	  public void downloadTempl(HttpServletResponse response) throws UnsupportedEncodingException{  
		  this.fileUploadService.downloadTempl(response);
	  }
	/***
	 * 读取上传文件中得所有文件并返回
	 */
	@RequestMapping("list")
	public ModelAndView list() {
		String filePath = rootPath;
		ModelAndView mav = new ModelAndView("list");
		File uploadDest = new File(filePath);
		String[] fileNames = uploadDest.list();
		for (int i = 0; i < fileNames.length; i++) {
			System.out.println(fileNames[i]);
		}
		return mav;
	}
}
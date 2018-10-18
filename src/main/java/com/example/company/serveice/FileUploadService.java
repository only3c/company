package com.example.company.serveice;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.example.company.model.FileStorage;

public interface FileUploadService {

	FileStorage upload(MultipartFile file);
	
	void download(Long id,HttpServletResponse response);

	void downloadTempl(HttpServletResponse response);
}

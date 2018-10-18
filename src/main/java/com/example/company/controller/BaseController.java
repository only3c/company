package com.example.company.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public abstract class BaseController {
	
	
	
	protected void writeResponse(HttpServletResponse response, String returnValue) {
		try {
		    //设置页面不缓存
	        response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out = response.getWriter();
	        out.print(returnValue);
	        out.flush();
	        out.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return;
		
	}

}

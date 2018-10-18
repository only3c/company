package com.example.company.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtils<T> {

	private Integer total;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer pages;
	private Map<String, Object> dataMap;
	private List<T> dataList;
	public PageUtils() {
		pageNumber = 1;
		pageSize = 10;
	}
	
	public PageUtils(Integer total, Map<String, Object> paramMap) {
		this.total = total;
		pageNumber = paramMap.get("page") != null ? (Integer)paramMap.get("page") : 1;
		pageSize = paramMap.get("rows") != null ? (Integer)paramMap.get("rows") : 10;
		dataMap = paramMap;
		dataMap.put("startRow", (pageNumber - 1) * pageSize+1);
		dataMap.put("endRow", pageNumber * pageSize);
	}
	
	public PageUtils(Integer total, Integer page,Integer rows,List<T> dataList) {
		this.total = total;
		pageNumber = page != null ? page : 1;
		pageSize = rows != null ? rows : 10;
		this.setDataList(dataList);
		dataMap = new HashMap<String, Object>();
		dataMap.put("startRow", (pageNumber - 1) * pageSize+1);
		dataMap.put("endRow", pageNumber * pageSize);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getResult(List list) {
		Map<String, Object> result  = new HashMap<String, Object>();
		result.put("total", total);
		result.put("page", pageNumber);
		result.put("length", pageSize);
		result.put("pages", ((Double)Math.ceil(Double.valueOf(total) / Double.valueOf(pageSize))).intValue());
		result.put("rows", list);
		
		return result;
	}

	public Integer getTotal() {
		return total;
	}


	public void setTotal(Integer total) {
		this.total = total;
	}


	public Integer getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}


	public Integer getPageSize() {
		return pageSize;
	}


	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	public Integer getPages() {
		return pages;
	}


	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
}

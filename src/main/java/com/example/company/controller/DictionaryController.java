package com.example.company.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.company.model.Dictionary;
import com.example.company.repository.DictionaryRepository;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.PageUtils;
import com.example.company.util.StringUtils;

@Controller
public class DictionaryController extends BaseController {

	@Autowired
	private DictionaryRepository dictionaryRepository;

	@RequestMapping("/dictionaryController_getDictionaryForComboBox")
	public void getDictionaryForComboBox(@RequestParam("datatype") String datatype,
			@RequestParam(value = "parentcode", required = false) String parentcode,
			@RequestParam(value = "required", required = false) String required, HttpServletResponse response) {
		List<Dictionary> dictionaryList = dictionaryRepository.findListByDatatypeAndParentcode(datatype, parentcode);
		if (dictionaryList != null && dictionaryList.size() > 0) {
			if (StringUtils.isNotBlank(required) && "false".equals(required)) {
				Dictionary datadic = new Dictionary();
				datadic.setDatacode("");
				datadic.setDataname("请选择……");
				dictionaryList.add(0, datadic);
			}
			MsgIn<List<Dictionary>> in = new MsgIn<List<Dictionary>>(dictionaryList);
			MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
			writeResponse(response, out.getTjson());
		}
		return;
	}

	/**
	 * 打开部门数据字典列表页
	 */
	@RequestMapping("/dictionaryController_openList")
	public String openList() {
		return "dictionary/dictionaryList";
	}

	/**
	 * 获取数据字典分页列表
	 */
	@RequestMapping("/dictionaryController_dictionaryList")
	public void dictionaryList(@RequestParam(value = "datatype", required = false) String datatype,
			@RequestParam(value = "datatypename", required = false) String datatypename,
			@RequestParam(value = "dataname", required = false) String dataname, @RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletResponse response) {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Dictionary> dicList = dictionaryRepository.findAllByDatatypeLikeAndDatatypenameLikeAndDatanameLike(
				datatype == null ? "%%" : "%" + datatype + "%", datatypename == null ? "%%" : "%" + datatypename + "%",
						dataname == null ? "%%" : "%" + dataname + "%", pageable);
		System.out.println("总条数：" + dicList.getTotalElements());
		System.out.println("总页数：" + dicList.getTotalPages());
		if (dicList != null && dicList.getSize() > 0) {
			PageUtils<Dictionary> pages = new PageUtils<Dictionary>(Integer.valueOf((int) dicList.getTotalElements()),
					page, rows, dicList.getContent());
			// 封装返回信息
			Map<String, Object> result = pages.getResult(dicList.getContent());
			MsgIn<Map<String, Object>> in = new MsgIn<Map<String, Object>>(result);
			String retValue = in.getJsonstring();
			MsgOut<Object> out = new MsgOut<Object>(retValue);
			String retString = out.getTjson();
			writeResponse(response, retString);
		}
		return;
	}

	/**
	 * 打开新增数据字典页
	 */
	@RequestMapping("/dictionaryController_openAddPage")
	public String openAddPage() {
		return "dictionary/dictionaryAdd";
	}

	/**
	 * 保存数据字典信息
	 */
	@RequestMapping("/dictionaryController_saveDictionary")
	public void saveDictionary(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		String datatype = request.getParameter("datatype");
		String datacode = request.getParameter("datacode");
		String datatypename = request.getParameter("datatypename");
		String dataname = request.getParameter("dataname");
		String parentcode = request.getParameter("parentcode");
		String displayorder = request.getParameter("displayorder");
		Dictionary dictionary = new Dictionary();
		Integer id = dictionaryRepository.findMaxId();
		dictionary.setId(id==null?0l:id+1);
		dictionary.setDatatype(datatype);
		dictionary.setDatacode(datacode);
		dictionary.setDatatypename(datatypename);
		dictionary.setDataname(dataname);
		dictionary.setParentcode(parentcode);
		dictionary.setDisplayorder(displayorder!=null && !"".equals(displayorder)?Integer.valueOf(displayorder):null);
		dictionary = dictionaryRepository.save(dictionary);
		MsgIn<Dictionary> in = new MsgIn<Dictionary>("0000","更新成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
	}

	/**
	 * 修改数据字典信息
	 */
	@RequestMapping("/dictionaryController_updateDictionary")
	public void updateDictionary(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		String datatype = request.getParameter("datatype");
		String datacode = request.getParameter("datacode");
		String datatypename = request.getParameter("datatypename");
		String dataname = request.getParameter("dataname");
		String parentcode = request.getParameter("parentcode");
		String displayorder = request.getParameter("displayorder");
		Long id = Long .valueOf(request.getParameter("id"));
		Dictionary dictionary = dictionaryRepository.findOne(id);
		if(dictionary!=null){
			dictionary.setDatatype(datatype);
			dictionary.setDatacode(datacode);
			dictionary.setDatatypename(datatypename);
			dictionary.setDataname(dataname);
			dictionary.setParentcode(parentcode);
			dictionary.setDisplayorder(displayorder!=null && !"".equals(displayorder)?Integer.valueOf(displayorder):null);
			dictionary = dictionaryRepository.save(dictionary);
		}
		MsgIn<Dictionary> in = new MsgIn<Dictionary>("0000","更新成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
	}

	/**
	 * 删除数据字典
	 */
	@RequestMapping("/dictionaryController_deleteDictionarys")
	public void deleteDictionarys(@RequestParam("ids") String ids, HttpServletResponse response) {
		String[] idsArray = ids.split(",");
		for(String id :idsArray){
			dictionaryRepository.delete(Long.valueOf(id));
		}
		MsgIn<Dictionary> in = new MsgIn<Dictionary>("0000","删除成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}

	/**
	 * 打开修改页面
	 */
	@RequestMapping("/dictionaryController_openUpdatePage")
	public String openUpdatePage(@RequestParam("id") Long id,Model model) {
		Dictionary dictionary = dictionaryRepository.findOne(id);
		MsgIn<Dictionary> in = new MsgIn<Dictionary>(dictionary);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());

		model.addAttribute("dictionary", out.getTobj());
		return "dictionary/dictionaryUpdate";
	}

}

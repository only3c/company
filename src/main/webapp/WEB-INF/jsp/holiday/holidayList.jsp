<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${basePath}scripts/easyui/themes/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}scripts/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}scripts/easyui/themes/demo.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}styles/common.css" />
<script type="text/javascript"
	src="${basePath}scripts/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${basePath}scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${basePath}scripts/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${basePath}scripts/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${basePath}scripts/chose_theme.js"></script>
<script type="text/javascript">
	$(function() {
		doQuery();
	});
	//查询
	function doQuery() {
		$("#holidayList").datagrid('reload', {
			holidaytype : $("#holidaytype").combobox('getValue'),
			flowtype : $("#flowtype").combobox('getValue'),
			cause : $("#cause").val(),
			username : $("#username").val()
		});
		// 				alert( $("#flowtype"));
	}
	//链接处理
	function go(val, row) {
		var datatype = row["datatype"];
		var datacode = row["datacode"];
		var parentcode = row["parentcode"];
		if (!parentcode) {
			parentcode = '';
		}
		return "<a href='javascript:void(0)' onclick=\"opencodeListWin('"
				+ datatype + "','" + datacode + "','" + parentcode + "')\">"
				+ val + "</a>";
	}
	//新增请假
	function openAddWin() {
		$('#winuser').window({
			title : "新增请假",
			width : 700,
			height : 205,
			minimizable : false,
			collapsible : false,
			modal : true
		});

		$("#msguser").html("");
		$("#msguser").load("holidayController_openAddPage");
		$('#winuser').window('open');
	}

	//通过请假
	function agreeHolidays() {
		var rows = $("#holidayList").datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', '请选择一条记录！', 'warning');
			return false;
		}
		var ids = "";
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				ids += rows[i].id;
			} else {
				ids += "," + rows[i].id;
			}
		}

		$.messager
				.confirm(
						'请确认',
						'确认要通过选中请假？',
						function(r) {
							if (r) {
								$
										.ajax({
											type : "POST",
											url : "holidayController_agreeHolidays",
											data : {
												ids : ids
											},
											dataType : "json",
											success : function(result) {
												if (result._ResponseCode_ == '0000') {
													doQuery();
													$.messager
															.show({
																title : '提示',
																msg : '请假通过成功！',
																style : {
																	left : '',
																	right : 0,
																	top : document.body.scrollTop
																			+ document.documentElement.scrollTop,
																	bottom : ''
																}
															});
												} else {
													$.messager.alert('错误',
															'请假通过失败，请联系管理员！',
															'error');
												}
											},
											error : function(msg) {
												$.messager.alert('错误',
														'请假通过出错，请联系管理员！',
														'error');
											}
										});
							}
						});
	}

	//拒绝请假
	function refuseHolidays() {
		var rows = $("#holidayList").datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', '请选择一条记录！', 'warning');
			return false;
		}
		var ids = "";
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				ids += rows[i].id;
			} else {
				ids += "," + rows[i].id;
			}
		}

		$.messager
				.confirm(
						'请确认',
						'确认要拒绝选中请假？',
						function(r) {
							if (r) {
								$
										.ajax({
											type : "POST",
											url : "holidayController_refuseHolidays",
											data : {
												ids : ids
											},
											dataType : "json",
											success : function(result) {
												if (result._ResponseCode_ == '0000') {
													doQuery();
													$.messager
															.show({
																title : '提示',
																msg : '请假拒绝成功！',
																style : {
																	left : '',
																	right : 0,
																	top : document.body.scrollTop
																			+ document.documentElement.scrollTop,
																	bottom : ''
																}
															});
												} else {
													$.messager.alert('错误',
															'请假拒绝失败，请联系管理员！',
															'error');
												}
											},
											error : function(msg) {
												$.messager.alert('错误',
														'请假拒绝出错，请联系管理员！',
														'error');
											}
										});
							}
						});
	}

	//判断是否选择一条记录
	function isSelectOne() {
		var rows = $("#holidayList").datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', '请选择一条记录！', 'warning');
			return false;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', '只能选择一条记录！', 'warning');
			return false;
		}

		return true;
	}
</script>
</head>
<body class="easyui-layout">
	<div id="msg" data-options="region:'center',title:'操作区'"
		style="padding: 5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'"
				style="height: 60px; padding: 8px 5px; border: 0px;">
				<div style="padding: 10px; border: 1px solid #95B8E7;">

					请假类型:<input class="easyui-combobox" type="text" id="holidaytype"
						name="holidaytype" style="width: 180px; height: 22px"
						data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
							url:'dictionaryController_getDictionaryForComboBox?datatype=holidaytype&required=false'"></input>&nbsp;
					请假原因:<input class="easyui-textbox" id="cause"
						style="width: 180px; height: 22px" />&nbsp; 请假历程:<input
						class="easyui-combobox" type="text" id="flowtype" name="flowtype"
						style="width: 180px; height: 22px"
						data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
							url:'dictionaryController_getDictionaryForComboBox?datatype=flowtype&required=false'"></input>&nbsp;
					<c:forEach items="${sessionScope.session_user.roles}" var="role">
						<c:if test="${role.rolecode == '0'||role.rolecode == '1'}">
							申请人:<input class="easyui-textbox" id="username"
								style="width: 180px; height: 22px" />&nbsp;
							<c:set var="flag" value="false" />
						</c:if>
					</c:forEach>

					<a class="easyui-linkbutton" href="javascript:void(0);"
						data-options="iconCls:'icon-search'" onclick="doQuery()">查询</a> <a
						class="easyui-linkbutton" href="javascript:void(0);"
						data-options="iconCls:'icon-add'" onclick="openAddWin();">新增</a>
					<c:forEach items="${sessionScope.session_user.roles}" var="role">
						<c:if test="${role.rolecode == '0'||role.rolecode == '1'}">
							<a class="easyui-linkbutton" href="javascript:void(0);"
								data-options="iconCls:'icon-ok'" onclick="agreeHolidays()">同意</a>
							<a class="easyui-linkbutton" href="javascript:void(0);"
								data-options="iconCls:'icon-no'" onclick="refuseHolidays()">拒绝</a>
							<c:set var="flag" value="false" />
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div data-options="region:'center',iconCls:'icon-ok'"
				style="padding: 5px; border: 0px;">
				<table id="holidayList" class="easyui-datagrid" title="请假列表"
					data-options="rownumbers:true,singleSelect:false,collapsible:false,pagination:true,
						pageSize:10,url:'holidayController_holidayList',method:'post',fit:true,fitColumns:true">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th
								data-options="field:'usercode',width:80,halign:'center',align:'center',hidden:'false'">用户编码</th>
							<th
								data-options="field:'username',width:80,halign:'center',align:'center'">用户</th>
							<th
								data-options="field:'instcode',width:80,halign:'center',align:'center',hidden:'false'">部门编码</th>
							<th
								data-options="field:'instname',width:80,halign:'center',align:'center'">部门</th>
							<th
								data-options="field:'holidaytype',width:80,halign:'center',align:'center',hidden:'false'">类型</th>
							<th
								data-options="field:'holidaytypename',width:80,halign:'center',align:'center'">请假类型</th>
							<th
								data-options="field:'timelength',width:80,halign:'center',align:'center'">请假时长(小时)</th>
							<th
								data-options="field:'starttime',width:80,halign:'center',align:'center'">开始时间</th>
							<th
								data-options="field:'endtime',width:80,halign:'center',align:'center'">结束时间</th>
							<th
								data-options="field:'cause',width:80,halign:'center',align:'center'">请假原因</th>
							<th
								data-options="field:'flowtype',width:80,halign:'center',align:'center',hidden:'false'">审核</th>
							<th
								data-options="field:'flowtypename',width:80,halign:'center',align:'center'">审核历程</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>

	<div id="winuser" data-options="closed:true"
		style="width: 60%; text-align: center">
		<div id="msguser"></div>
	</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/bootstrap/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/demo.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}styles/common.css" />
		<script type="text/javascript" src="${basePath}scripts/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${basePath}scripts/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${basePath}scripts/easyui/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.cookie.js"></script>
		<script type="text/javascript" src="${basePath}scripts/chose_theme.js"></script>
		<script type="text/javascript">
			$(function() {
				doQuery();
			}) ;
			//查询
			function doQuery() {
				$("#noteList").datagrid('reload', {
					meetingname: $("#meetingname").val(),
					notes: $("#notes").val()
				});
			}
			//链接处理
			function  go(val,row){
				var datatype = row["datatype"];
				var datacode = row["datacode"];
				var parentcode = row["parentcode"];
				if (!parentcode) {
					parentcode = '';	
				}
				return "<a href='javascript:void(0)' onclick=\"opencodeListWin('" + datatype + "','" + datacode + "','" + parentcode + "')\">"+val+"</a>";
			}
			
			//链接处理
			function downloadFile(val,row){
				var id=row["fileid"];
				return "<a href='javascript:void(0)";

			}
			
			//新增笔记
			function openAddWin() {
               	$('#winuser').window({
   					title : "新增笔记",
   					width: 580,
   					height: 180,
   					minimizable:false,
   					collapsible:false,
   					modal:true
   				});
    				
   				$("#msguser").html("");
   				$("#msguser").load("noteController_openAddPage");
   				$('#winuser').window('open');
			}
			
			//删除笔记
			function deleteNotes() {
				var rows = $("#noteList").datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示','请选择一条记录！','warning');
					return false;
				}
				var ids = "";
				for (var i = 0; i < rows.length; i++) {
					if (i == 0) {
						ids+=rows[i].id;
					} else {
						ids+=","+rows[i].id;
					}
				}
				
				$.messager.confirm('请确认', '确认要删除选中笔记？', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: "noteController_deleteNotes",
							data: {ids: ids},
							dataType: "json",
							success: function(result) {
								if (result._ResponseCode_ == '0000') {
									doQuery();
									$.messager.show({
										title: '提示',
										msg: '笔记删除成功！',
										style:{
											left: '',
											right: 0,
											top: document.body.scrollTop + document.documentElement.scrollTop,
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','笔记删除失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','笔记删除出错，请联系管理员！','error');
							}
						});
					}
				});
			}
			
			function openUpdateWin() {
				if (!isSelectOne()) {
					return;
				}
				
				var rows = $("#noteList").datagrid('getSelections');
				
				$("#msguser").html("");
				
				$('#winuser').window({
					title:"添加笔记",
					width: 1100,
					height: 400,
					modal:true,
					closable:false,
					minimizable:false,
					resizable:false,
					maximizable:false,
					collapsible:false
				});
				
				var datatype = rows[0].datatype;
				var datacode = rows[0].datacode;
				var parentcode = rows[0].parentcode;
				var id = rows[0].id;
				if (!parentcode) {
					parentcode = '';
				}
				
				$("#msguser").html("");
				$("#msguser").load("noteController_openEdit",{id:id});
				$('#winuser').window('open');
			}
			
			//判断是否选择一条记录
			function isSelectOne() {
				var rows = $("#noteList").datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示','请选择一条记录！','warning');
					return false;
				}
				if (rows.length > 1) {
					$.messager.alert('提示','只能选择一条记录！','warning');
					return false;
				}
				
				return true;
			}
			
		</script>
</head>
	<body class="easyui-layout" >
    <div id="msg" data-options="region:'center',title:'操作区'" style="padding:5px;">
   		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="height:60px;padding:8px 5px;border:0px;">
				<div style="padding:10px;border:1px solid #95B8E7;">
					会议主题:<input class="easyui-textbox" id="meetingname" style="width:180px;height:22px"/>&nbsp;
					笔记内容:<input class="easyui-textbox" id="notes" style="width:180px;height:22px"/>&nbsp;
					<a class="easyui-linkbutton" href="javascript:void(0);"  data-options="iconCls:'icon-search'" onclick="doQuery()">查询</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-add'" onclick="openAddWin();">新增</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-edit'" onclick="openUpdateWin()">修改笔记</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-remove'" onclick="deleteNotes()">删除</a>
				</div>
			</div>
			<div data-options="region:'center',iconCls:'icon-ok'" style="padding:5px;border:0px;">
				<table id="noteList" class="easyui-datagrid" title="笔记列表"
					data-options="rownumbers:true,singleSelect:false,collapsible:false,pagination:true,
						pageSize:10,url:'noteController_noteList',method:'post',fit:true,fitColumns:true">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'usercode',width:80,halign:'center',align:'center',hidden:'false'">用户编码</th>
							<th data-options="field:'username',width:80,halign:'center',align:'center'">用户</th>
							<th data-options="field:'instcode',width:80,halign:'center',align:'center',hidden:'false'">部门编码</th>
							<th data-options="field:'instname',width:80,halign:'center',align:'center'">部门</th>
							<th data-options="field:'notename',width:80,halign:'center',align:'center'">笔记名称</th>
							<th data-options="field:'meetingid',width:80,halign:'center',align:'center'">会议ID</th>
							<th data-options="field:'meetingname',width:80,halign:'center',align:'center'">会议主题</th>
							<th data-options="field:'updatetime',width:80,halign:'center',align:'center'">修改时间</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
    </div>
    
    <div id="winuser" data-options="closed:true" style="width:60%; text-align:center">
			<div id="msguser"></div>
	</div>
</body>
</html>

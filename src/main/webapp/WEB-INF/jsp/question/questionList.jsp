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
			//查询
			function doQuery() {
				$("#questionList").datagrid('reload', {
					datatype: $("#dataType").val(),
					dataname: $("#dataName").val(),
					datatypename: $("#dataTypeName").val()
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
			
			//查看部门详细信息
			function opencodeListWin(datatype, datacode, parentcode) {
				$("#msguser").html("");
				$('#winuser').window({
					title:"试题详情",
					modal:true,
					minimizable:false,
					collapsible:false,
					width: 700,
					height: 205
				});
				
				$("#msguser").load("questionController_openQuestionPage?datatype=" + datatype + "&datacode=" + datacode + "&parentcode=" + parentcode, function() {
					$.messager.progress('close');
				});
				$('#winuser').window('open');
			}
			
			//新增试题
			function openAddWin() {
               	$('#winuser').window({
   					title : "新增试题",
   					width: 700,
   					height: 235,
   					minimizable:false,
   					collapsible:false,
   					modal:true
   				});
    				
   				$("#msguser").html("");
   				$("#msguser").load("questionController_openAddPage");
   				$('#winuser').window('open');
			}
			function downloadTempl(){
// 		        $.ajax({
// 		            cache: true,
// 		            type: "POST",
// 		            url: "downloadTempl",
// 		            error: function(request) {
// 		                alert("下载失败！请联系管理员");
// 		            }
// 		        });
		        var form = $("<form></form>").attr("action", "/downloadTempl").attr("method", "post");
		        form.append($("<input></input>").attr("type", "hidden").attr("name", "fileName").attr("value", ""));
		        form.appendTo('body').submit().remove();
			}
			
			function uploadTempl(){
				$("#msguser").html("");
				
				$('#winuser').window({
					title:'上传',
					width: 700,
					height: 150,
					modal:true,
					minimizable:false,
					collapsible:false
				});
				
				$("#msguser").html("");
				$("#msguser").load("questionController_openUpload");
				$('#winuser').window('open');
			}
			//删除试题
			function deleteQuestions() {
				var rows = $("#questionList").datagrid('getSelections');
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
				
				$.messager.confirm('请确认', '确认要删除选中试题？', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: "questionController_deleteQuestions",
							data: {ids: ids},
							dataType: "json",
							success: function(result) {
								if (result._ResponseCode_ == '0000') {
									doQuery();
									$.messager.show({
										title: '提示',
										msg: '试题删除成功！',
										style:{
											left: '',
											right: 0,
											top: document.body.scrollTop + document.documentElement.scrollTop,
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','试题删除失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','试题删除出错，请联系管理员！','error');
							}
						});
					}
				});
			}
			
			//修改部门
			function openUpdateWin() {
				if (!isSelectOne()) {
					return;
				}
				
				var rows = $("#questionList").datagrid('getSelections');
				
				$("#msguser").html("");
				
				$('#winuser').window({
					title:"修改试题信息",
					width: 700,
					height: 205,
					modal:true,
					minimizable:false,
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
				$("#msguser").load("questionController_openUpdatePage",{id:id});
				$('#winuser').window('open');
			}
			
			//判断是否选择一条记录
			function isSelectOne() {
				var rows = $("#questionList").datagrid('getSelections');
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
					试题名称：<input class="easyui-textbox" id="dataTypeName" style="width:180px;height:22px"/>&nbsp;
					<input type="hidden"  id="parentcode" value=""/>
					<a class="easyui-linkbutton" href="javascript:void(0);"  data-options="iconCls:'icon-search'" onclick="doQuery()">查询</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-add'" onclick="openAddWin();">新增</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-edit'" onclick="openUpdateWin()">修改</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-cancel'" onclick="deleteQuestions()">删除</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-filter'" onclick="downloadTempl()">下载模板</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-reload'" onclick="uploadTempl()">上传试题</a>
				</div>
			</div>
			<div data-options="region:'center',iconCls:'icon-ok'" style="padding:5px;border:0px;">
				<table id="questionList" class="easyui-datagrid" title="试题列表"
					data-options="rownumbers:true,singleSelect:false,collapsible:false,pagination:true,
						pageSize:10,url:'questionController_questionList',method:'post',fit:true,fitColumns:true">
					<thead>
						<tr>
							
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'examid',width:80,halign:'center',align:'center'">试题ID</th>
							<th data-options="field:'examname',width:80,halign:'center',align:'center'">试卷名称</th>
							<th data-options="field:'quesubject',width:80,halign:'center',align:'center'">试题</th>
							<th data-options="field:'queone',width:60,halign:'center',align:'center'">答案1</th>
							<th data-options="field:'quetwo',width:80,halign:'center',align:'center'">答案2</th>
							<th data-options="field:'quethree',width:80,halign:'center',align:'center'">答案3</th>
							<th data-options="field:'quefour',width:60,halign:'center',align:'center'">答案4</th>
							<th data-options="field:'rightque',width:60,halign:'center',align:'center'">正确答案</th>
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

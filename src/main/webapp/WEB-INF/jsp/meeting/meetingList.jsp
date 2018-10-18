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
				$("#meetingList").datagrid('reload', {
					meetingtype: $("#meetingtype").combobox('getValue'),
// 					flowtype: $("#flowtype").combobox('getValue'),
					summary: $("#summary").val()
// 					username: $("#username").val()
				});
// 				alert( $("#flowtype"));
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
			
// 			function opencodeListWin(datatype, datacode, parentcode) {
// 				$("#msguser").html("");
// 				$('#winuser').window({
// 					title:"会议详情",
// 					modal:true,
// 					minimizable:false,
// 					collapsible:false,
// 					width: 700,
// 					height: 205
// 				});
				
// 				$("#msguser").load("meetingController_openMeetingPage?datatype=" + datatype + "&datacode=" + datacode + "&parentcode=" + parentcode, function() {
// 					$.messager.progress('close');
// 				});
// 				$('#winuser').window('open');
// 			}


			
			//链接处理
			function downloadFile(val,row){
				var id=row["fileid"];
				return "<a href='javascript:void(0)";// onclick=\"downloadFile('"+val+"')\">"+val+"

			}
			
			//查看用户详细信息
// 			function downloadFile(fileId) {
// 				$("#msguser").html("");
// 				$('#winuser').window({
// 					title:"用户信息详情",
// 					modal:true,
// 					minimizable:false,
// 					collapsible:false,
// 					width:800,
// 					height:320
// 				});
				///download/{id}
// 				$("#msguser").load("download/{"+val+"}");
// 				$('#winuser').window('open');
// 			}
			
			
			//新增会议
			function openAddWin() {
               	$('#winuser').window({
   					title : "新增会议",
   					width: 700,
   					height: 225,
   					minimizable:false,
   					collapsible:false,
   					modal:true
   				});
    				
   				$("#msguser").html("");
   				$("#msguser").load("meetingController_openAddPage");
   				$('#winuser').window('open');
			}
			
			//删除会议
			function deleteMeetings() {
				var rows = $("#meetingList").datagrid('getSelections');
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
				
				$.messager.confirm('请确认', '确认要删除选中会议？', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: "meetingController_deleteMeetings",
							data: {ids: ids},
							dataType: "json",
							success: function(result) {
								if (result._ResponseCode_ == '0000') {
									doQuery();
									$.messager.show({
										title: '提示',
										msg: '会议删除成功！',
										style:{
											left: '',
											right: 0,
											top: document.body.scrollTop + document.documentElement.scrollTop,
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','会议删除失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','会议删除出错，请联系管理员！','error');
							}
						});
					}
				});
			}
			
			//通过会议
			function agreeMeetings() {
				var rows = $("#meetingList").datagrid('getSelections');
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
				
				$.messager.confirm('请确认', '确认要通过选中会议？', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: "meetingController_agreeMeetings",
							data: {ids: ids},
							dataType: "json",
							success: function(result) {
								if (result._ResponseCode_ == '0000') {
									doQuery();
									$.messager.show({
										title: '提示',
										msg: '会议通过成功！',
										style:{
											left: '',
											right: 0,
											top: document.body.scrollTop + document.documentElement.scrollTop,
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','会议通过失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','会议通过出错，请联系管理员！','error');
							}
						});
					}
				});
			}
			
			//拒绝会议
			function refuseMeetings() {
				var rows = $("#meetingList").datagrid('getSelections');
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
				
				$.messager.confirm('请确认', '确认要拒绝选中会议？', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: "meetingController_refuseMeetings",
							data: {ids: ids},
							dataType: "json",
							success: function(result) {
								if (result._ResponseCode_ == '0000') {
									doQuery();
									$.messager.show({
										title: '提示',
										msg: '会议拒绝成功！',
										style:{
											left: '',
											right: 0,
											top: document.body.scrollTop + document.documentElement.scrollTop,
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','会议拒绝失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','会议拒绝出错，请联系管理员！','error');
							}
						});
					}
				});
			}
			
			function openUpdateWin() {
				if (!isSelectOne()) {
					return;
				}
				
				var rows = $("#meetingList").datagrid('getSelections');
				
				$("#msguser").html("");
				
				$('#winuser').window({
					title:"添加纪要",
					width: 700,
					height: 240,
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
				$("#msguser").load("meetingController_openUpdatePage",{id:id});
				$('#winuser').window('open');
			}
			
			function downloadfile(){
				if (!isSelectOne()) {
					return;
				}
				var rows = $("#meetingList").datagrid('getSelections');
				var fileid = rows[0].fileid;
// 		        console.log("ajaxDownloadDirectly");
		        var url = "/download/"+fileid+"";
// 		        var url = "http://localhost:8080/ajaxDownloadServlet.do";
// 		        var fileName = "testAjaxDownload.txt";
		        var form = $("<form></form>").attr("action", url).attr("method", "post");
		        form.append($("<input></input>").attr("type", "hidden").attr("name", "fileName").attr("value", ""));
		        form.appendTo('body').submit().remove();
			}
			
			//判断是否选择一条记录
			function isSelectOne() {
				var rows = $("#meetingList").datagrid('getSelections');
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
					
					会议类型:<input class="easyui-combobox" type="text" id="meetingtype" name="meetingtype" style="width:180px;height:22px"
							data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
							url:'dictionaryController_getDictionaryForComboBox?datatype=meetingtype&required=false'"></input>&nbsp;
					会议纪要:<input class="easyui-textbox" id="summary" style="width:180px;height:22px"/>&nbsp;
<!-- 					会议历程:<input class="easyui-combobox" type="text" id="flowtype" name="flowtype" style="width:180px;height:22px" -->
<!-- 							data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false, -->
<!-- 							url:'dictionaryController_getDictionaryForComboBox?datatype=flowtype&required=false'"></input>&nbsp;	 -->
<%-- 					<c:forEach items="${sessionScope.session_user.roles}" var="role"> --%>
<%-- 						<c:if test="${role.rolecode == '0'||role.rolecode == '1'}"> --%>
<!-- 							申请人:<input class="easyui-textbox" id="username" style="width:180px;height:22px"/>&nbsp; -->
<%-- 							<c:set var="flag" value="false"/> --%>
<%-- 						</c:if> --%>
<%-- 					</c:forEach> --%>
					
					<a class="easyui-linkbutton" href="javascript:void(0);"  data-options="iconCls:'icon-search'" onclick="doQuery()">查询</a>
					<c:forEach items="${sessionScope.session_user.roles}" var="role">
						<c:if test="${role.rolecode == '0'||role.rolecode == '1'}">
							<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-add'" onclick="openAddWin();">新增</a>
							<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-edit'" onclick="openUpdateWin()">修改</a>
							<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-remove'" onclick="deleteMeetings()">删除</a>
<!-- 							<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-no'" onclick="refuseMeetings()">拒绝</a> -->
							<c:set var="flag" value="false"/>
						</c:if>
					</c:forEach>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-cut'" onclick="downloadfile()">下载纪要</a>
				</div>
			</div>
			<div data-options="region:'center',iconCls:'icon-ok'" style="padding:5px;border:0px;">
				<table id="meetingList" class="easyui-datagrid" title="会议列表"
					data-options="rownumbers:true,singleSelect:false,collapsible:false,pagination:true,
						pageSize:10,url:'meetingController_meetingList',method:'post',fit:true,fitColumns:true">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'usercode',width:80,halign:'center',align:'center',hidden:'false'">用户编码</th>
							<th data-options="field:'username',width:80,halign:'center',align:'center',hidden:'false'">用户</th>
							<th data-options="field:'instcode',width:80,halign:'center',align:'center',hidden:'false'">部门编码</th>
							<th data-options="field:'instname',width:80,halign:'center',align:'center'">部门</th>
							<th data-options="field:'meetingtype',width:80,halign:'center',align:'center',hidden:'false'">类型</th>
							<th data-options="field:'meetingtypename',width:80,halign:'center',align:'center'">会议类型</th>
							<th data-options="field:'subject',width:80,halign:'center',align:'center'">会议主题</th>
							<th data-options="field:'timelength',width:80,halign:'center',align:'center'">会议时长(小时)</th>
							<th data-options="field:'starttime',width:80,halign:'center',align:'center'">开始时间</th>
							<th data-options="field:'endtime',width:80,halign:'center',align:'center'">结束时间</th>
							<th data-options="field:'meetingroom',width:80,halign:'center',align:'center'">会议室</th>
							<th data-options="field:'summary',width:80,halign:'center',align:'center'">纪要说明</th>
							<th data-options="field:'fileid',width:80,halign:'center',align:'center',hidden:'false'">文件ID</th>
							<th data-options="field:'filename',width:80,halign:'center',align:'center'">纪要文件</th>
							<th data-options="field:'flowtype',width:80,halign:'center',align:'center',hidden:'false'">审核</th>
<!-- 							<th data-options="field:'flowtypename',width:80,halign:'center',align:'center'">审核历程</th> -->
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

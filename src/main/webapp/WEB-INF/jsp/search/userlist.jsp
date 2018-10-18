<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/bootstrap/easyui.css" />
		<link rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/icon.css" />
		<link rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/demo.css" />
		<link rel="stylesheet" type="text/css" href="${basePath}styles/common.css" />
		<script type="text/javascript" src="${basePath}scripts/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${basePath}scripts/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${basePath}scripts/easyui/easyui-lang-zh_CN.js"></script>	
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.cookie.js"></script>
		<script type="text/javascript" src="${basePath}scripts/chose_theme.js"></script>	
		<script type="text/javascript">
			$(function() {
				initTree();
// 				chengeThemeUrl();
			}) ;
		    
		    //初始化菜单树
			function initTree() {
				$('#tree').tree({
					url : 'getTreeNode',
					cascadeCheck : true, //层叠选中  
					animate: true, //展开或折叠的时候是否显示动画效果
					lines : true, //显示虚线效果  
					loadFilter : function(data) {
						if (data) {
							return data.root;
						} else {
							return data;
						}
					},
					onClick : function(node) {
						$("#username").val("");
						$("#usercode").val("");
						$("#username").textbox('setText', "");
						$("#usercode").textbox('setText', "");
						$("#instcode").val(node.id);
						doQuery();
					},
					onLoadSuccess : function(node, param) {
						var rootNode = $("#tree").tree('getRoot'); //获取根节点
						if (rootNode) {
							$("#tree").tree('expand', rootNode.target); //调用expand方法
						}
					}
				});
			}
			
			//查询
			function doQuery() {
				$("#userList").datagrid('reload', {
					usercode: $("#usercode").val(),
					username: $("#username").val(),
					instcode: $("#instcode").val()
				});
			}
			
			//新增用户
			function openAddWin() {
				var treeNode = $("#tree").tree("getSelected");
				var parentcode = (treeNode == null ? "" : treeNode.id);
				
				if (parentcode == null || parentcode == "") {
					$.messager.alert('提示','请选择部门树节点作为新增用户的所属部门！');
					return;
				}

				$.ajax({
                    type: "POST",
                    url: "instInfoController_getInstInfo?instcode="+ parentcode,
                    dataType: "json",
                    success: function(result) {
                        // 0000 查询成功
                        if (result) {
            				var parentinstflag = result.instflag;
            				if(parentinstflag && parentinstflag != 1){
            			    	$.messager.alert('友情提示','您选择的部门已注销，请先执行恢复操作！');
            			    	return;
            			    }
            				
            				var instcode = $("#instcode").val();
            			    $('#winuser').window({
            					title : "新增用户信息",
            					height: 390,
            					minimizable:false,
            					collapsible:false,
            					modal:true
            				});
            			    $("#msguser").html("");	
            			    $("#msguser").load("userController_openAddPage?instcode="+instcode, function() {
            					$.messager.progress('close');
            				});
            				$('#winuser').window('open');
                        } else {
                        	var parentname = treeNode.text;
                        	$.messager.alert('获取部门['+ parentname +']失败！');
							return;
                        }
                    },
                    error: function(msg) {
                        $.messager.alert('错误','数据保存异常，请联系管理员！','error');
                    }
                });

			}
			
			//判断是否选择一条记录
			function isSelectOne() {
				var rows = $("#userList").datagrid('getSelections');
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
			
			//修改用户
			function openUpdateWin() {
				if (!isSelectOne()) {
					return;
				}
				
				var rows = $("#userList").datagrid('getSelections');
				
				$('#winuser').window({
					title:"修改用户信息",
					height: 390,
					minimizable:false,
					collapsible:false,
					modal:true
				});
				
				$("#msguser").html("");
				$("#msguser").load("userController_openUpdatePage", {usercode:rows[0].usercode});
				$('#winuser').window('open');
			}
			//注销用户
			function logoffUser() {
				var rows = $("#userList").datagrid('getSelections');
				var usercodes = ""; //后台接收用
				if (rows.length == 0) {
					$.messager.alert('提示','请选择一条记录！','warning');
					return false;
				}
				for(var i = 0; i<rows.length;i++){
					var userflag = rows[i].userflag;
					var usercode1 = rows[i].usercode;
					if (i == 0) {
						usercodes += rows[i].usercode;
					} else {
						usercodes += "," + rows[i].usercode;
					}
					if(userflag == "2"){
						$.messager.alert('提示','您选择的用户已注销，不能再进行注销操作！','warning');
						return;
					}
					if (usercode1 == "admin") {
						$.messager.alert('提示','系统管理员不能进行注销操作！','warning');
						return;
					}
				}
				$.messager.confirm('请确认', '确认要注销选中用户？注销后，该用户将不能登录系统。', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: "userController_logoffUser",
							data: {usercode : usercodes},
							dataType: "json",
							success: function(result) {
								// 0000 注销成功, 2注销失败
								if (result && result._ResponseCode_ == '0000') {
									doQuery(); //刷新列表
									$.messager.show({
										title: '提示',
										msg: '用户信息注销成功！',
										style:{
											left: '',
											right: 0,
											top: document.body.scrollTop + document.documentElement.scrollTop,
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','用户信息注销失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','用户信息注销出错，请联系管理员！','error');
							}
						});
					}
				});
			}
			
			//恢复用户
			function recoverUser() {
				var rows = $("#userList").datagrid('getSelections');
				var usercodes = ""; //后台接收用
				if (rows.length == 0) {
					$.messager.alert('提示','请选择一条记录！','warning');
					return false;
				}
				for(var i = 0; i<rows.length;i++){
					if (i == 0) {
						usercodes += rows[i].usercode;
					} else {
						usercodes += "," + rows[i].usercode;
					}
					var instflag = rows[i].userflag;
					if(instflag == "1"){
						$.messager.alert('提示','您选择的用户为正常状态，不能再进行恢复操作！','warning');
						return;
					}
				}
				$.ajax({
					type: "POST",
					url: "userController_recoverUser",
					data: {usercode : usercodes},
					dataType: "json",
					success: function(result) {
						// 0000 恢复成功, 2恢复失败
						if (result && result._ResponseCode_ == '0000') {
							doQuery(); //刷新列表
							$.messager.show({
								title: '提示',
								msg: '用户信息恢复成功！',
								style:{
									left: '',
									right: 0,
									top: document.body.scrollTop + document.documentElement.scrollTop,
									bottom: ''
								}
							});
						} else {
							$.messager.alert('错误','用户信恢复失败，请联系管理员！','error');
						}
					},
					error: function(msg) {
						$.messager.alert('错误','用户信息恢复出错，请联系管理员！','error');
					}
				});
			}
			
			//删除用户
			function deleteUser() {
				var rows = $("#userList").datagrid('getSelections');
				var usercodes = ""; //后台接收用
				if (rows.length == 0) {
					$.messager.alert('提示','请选择一条记录！','warning');
					return false;
				}
				for(var i = 0; i<rows.length;i++){
					var usercode1 = rows[i].usercode;
					if (i == 0) {
						usercodes += rows[i].usercode;
					} else {
						usercodes += "," + rows[i].usercode;
					}
					if (usercode1 == "admin") {
						$.messager.alert('提示','系统管理员不能进行注销操作！','warning');
						return;
					}
				}
				$.messager.confirm('请确认', '确认要删除选中用户？删除后，该用户将不能登录系统。', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: "userController_deleteUser",
							data: {usercode : usercodes},
							dataType: "json",
							success: function(result) {
								// 0000 删除成功
								if (result && result._ResponseCode_ == '0000') {
									doQuery(); //刷新列表
									$.messager.show({
										title: '提示',
										msg: '用户信息删除成功！',
										style:{
											left: '',
											right: 0,
											top: document.body.scrollTop + document.documentElement.scrollTop,
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','用户信息删除失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','用户信息删除出错，请联系管理员！','error');
							}
						});
					}
				});
			}
			
			//重置密码
			function resetPwd() {
				var rows = $("#userList").datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示','请至少选择一条记录！','warning');
					return;
				} else {
					var userStr = "";
					for (var i = 0; i < rows.length; i++) {
						var userinfo = rows[i];
						if (i == 0) {
							userStr += userinfo.usercode;
						} else {
							userStr += "," + userinfo.usercode;
						}
					}
					if (userStr != "") {
						$.messager.confirm('请确认', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确认要对选中用户重置密码？', function(r){
							if (r){
								$.ajax({
									type: "POST",
									async: "false",
									url: "userController_adminResetPwd",
									data: {usercode: userStr},
									dataType: "json",
									success: function(result) {
										if (result._ResponseCode_ == '0000') {
											doQuery(); //刷新列表
											$.messager.alert('提示','重置密码成功！重置的密码为“123456”。','warning');
										} else {
											$.messager.alert('错误','重置密码失败，请联系管理员！','error');
										}
									},
									error: function() {
										$.messager.alert('错误','重置密码出错，请联系管理员！','error');
									}
								});
							}
						});
					}
				}
			}
			
			//链接处理
			function  go(val,row){
				return "<a href='javascript:void(0)' onclick=\"opencodeListWin('"+val+"')\">"+val+"</a>";
			}
			
			//查看用户详细信息
			function opencodeListWin(usercode) {
				$("#msguser").html("");
				$('#winuser').window({
					title:"用户信息详情",
					modal:true,
					minimizable:false,
					collapsible:false,
					width:800,
					height:320
				});
				
				$("#msguser").load("userController_openUserPage?usercode="+usercode, function() {
					$.messager.progress('close');
				});
				$('#winuser').window('open');
			}
		</script>
	</head>
	<body class="easyui-layout" >
	    <div id="instinfo" data-options="region:'west',title:'部门',split:true" style="width:20%;">
			<ul id="tree" ></ul>
	    </div>
	    
	    <div id="msg" data-options="region:'center',title:'操作区'" style="padding:5px;">
	    	<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north'" style="height:60px;padding:8px 5px;border:0px;">
					<div style="padding:10px;border:1px solid #95B8E7;">
						用户名：<input class="easyui-textbox" id="usercode" style="width:180px;height:22px" />&nbsp;
						昵称：<input class="easyui-textbox" id="username" style="width:180px;height:22px" />&nbsp;
						<input type="hidden" id="instcode" value="" />
						<a class="easyui-linkbutton" href="javascript:void(0);"  data-options="iconCls:'icon-search'" onclick="doQuery()">查询</a>
					</div>
				</div>
				<div data-options="region:'center',iconCls:'icon-ok'" style="padding:5px;border:0px;">
					<table id="userList" class="easyui-datagrid" title="用户列表"
						data-options="rownumbers:true,singleSelect:false,collapsible:false,pagination:true,
							pageSize:10,url:'userController_userList',method:'post',fit:true,fitColumns:true">
						<thead>
							<tr>
								<th data-options="field:'ck',checkbox:true"></th>
								<th data-options="field:'usercode',width:90,halign:'center',align:'center',formatter:go">用户名</th>
								<th data-options="field:'username',width:120,halign:'center',align:'center'">昵称</th>
								<th data-options="field:'instname',width:120,halign:'center',align:'center'">所属部门</th>
								<th data-options="field:'parentinstflag',width:100,halign:'center',align:'center',hidden:'true'">所属部门状态</th>
								<th data-options="field:'instcode',width:100,halign:'center',align:'center',hidden:'true'">所属部门</th>
								<th data-options="field:'rolename',width:150,halign:'center',align:'center'">用户角色</th>
								<th data-options="field:'certtype',width:80,halign:'center',align:'center',hidden:'true'">证件类型</th>
								<th data-options="field:'certno',width:150,halign:'center',align:'center'">证件号码</th>
								<th data-options="field:'telno',width:100,halign:'center',align:'center'">联系电话</th>
								<th data-options="field:'mobilephone',width:100,halign:'center',align:'center'">手机号码</th>
								<th data-options="field:'email',width:100,halign:'center',hidden:'true'">电子邮箱</th>
								<th data-options="field:'qq',width:100,halign:'center',hidden:'true'">QQ</th>
								<th data-options="field:'postcode',width:100,halign:'center',hidden:'true'">邮政编码</th>
								<th data-options="field:'userflag',width:80,halign:'center',align:'left',hidden:'true'">用户状态</th>
								<th data-options="field:'userflagname',width:80,halign:'center',align:'center'">用户状态</th>
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

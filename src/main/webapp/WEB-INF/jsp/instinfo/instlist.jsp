<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
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
				initTree();
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
						$("#instname").val("");
						$("#instcode").val("");
						$("#instname").textbox('setText', "");
						$("#instcode").textbox('setText', "");
						$("#parentcode").val(node.id);
						doQuery();
					},
					onLoadSuccess : function(node, param) {
						var rootNode = $("#tree").tree('getRoot'); //获取根节点
						$("#tree").tree('expand', rootNode.target); //调用expand方法
					}
				});
			}
			//查询
			function doQuery() {
				$("#instInfoList").datagrid('reload', {
					parentcode: $("#parentcode").val(),
					instname: $("#instname").val(),
					instcode: $("#instcode").val()
				});
			}
			
			//链接处理
			function  go(val,row){
				return "<a href='javascript:void(0)' onclick=\"opencodeListWin('"+val+"')\">"+val+"</a>";
			}
			
			// 获取上级部门信息
			function getPinstinfo(val, row) {
				if (val) {
					return val.instname;
				} else {
					return '无';
				}
			}
			
			// 获取数据字典项目
			function getDictionaryName(val, row) {
				if (val) {
					return val.dataname;
				} else {
					return '';
				}
			}
			
			//查看部门详细信息
			function opencodeListWin(instcode) {
				$("#msguser").html("");
				$('#winuser').window({
					title:"部门信息详情",
					modal:true,
					minimizable:false,
					collapsible:false,
					width: 800,
					height: 280,
				});
				
				$("#msguser").load("instInfoController_openInstInfoPage?instcode="+instcode, function() {
					$.messager.progress('close');
				});
				$('#winuser').window('open');
			}
			
			//新增部门
			function openAddWin() {
				var treeNode = $("#tree").tree("getSelected");
				var parentcode = (treeNode == null ? "" : treeNode.id);
				
				if (parentcode == null || parentcode == "") {
					$.messager.alert('提示','请选择部门树节点作为新增部门所属的上级部门！');
					return;
				}
				
				$.ajax({
                    type: "POST",
                    url: "instInfoController_getInstInfo?instcode="+ parentcode,
                    dataType: "json",
                    success: function(result) {
                        // 0000 查询成功
                        if (result) {
                        	var instflag = result.instflag;
                        	if (instflag && instflag != 1) {
                        		$.messager.alert('友情提示','您选择的部门已注销请先执行恢复操作！');
            			    	return;
                        	}
                        	var instclass = result.instclass;
                        	if (instclass && instclass == 2) {
                        		$.messager.alert('友情提示','该部门为银行部门不能添加下属子部门！');
            			    	return;
                        	}
                        	$('#winuser').window({
            					title : "新增部门信息",
            					width: 800,
            					height: 280,
            					minimizable:false,
            					collapsible:false,
            					modal:true
            				});
            				
            				$("#msguser").html("");
            				$("#msguser").load("instInfoController_openAddPage",{parentcode:parentcode});
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
			
			//注销部门
			function logoffUser() {
				var rows = $("#instInfoList").datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示','请选择一条记录！','warning');
					return false;
				}
				var instcodes = ""; //后台接收用
				var parentcodes = ""; //用于刷新树节点
				for (var i = 0; i < rows.length; i++) {
					if (i == 0) {
						instcodes += rows[i].instcode;
					} else {
						instcodes += "," + rows[i].instcode;
					}
					
					if (parentcodes.indexOf(rows[i].pinstcode) < 0) {
						parentcodes = parentcodes + "," + rows[i].pinstcode;
					}
				}
				
				if (parentcodes.length > 0) {
					parentcodes = parentcodes.substr(1);
				}
				for(var i = 1; i<rows.length;i++){
					var instflag = rows[i].instflag;
					if(instflag == "2"){
						$.messager.alert('提示','您选择的部门已注销，不能再进行注销操作！','warning');
						return;
					}
				}
				$.messager.confirm('请确认', '确认要注销选中部门？', function(r){
					if (!r){
						return;
					}
					$.ajax({
						type: "POST",
						url: "instInfoController_logoffInstInfo",
						data: {instcode: instcodes},
						dataType: "json",
						success: function(result) {
							// 1 注销成功, 2注销失败
							if (result && result._ResponseCode_ == '0000') {
								doQuery(); //刷新列表
								//刷新父节点
								flushParentNodes(parentcodes);
								initTree();
								$.messager.show({
									title: '提示',
									msg: '部门信息注销成功！',
									style:{
										left: '',
										right: 0,
										top: document.body.scrollTop + document.documentElement.scrollTop,
										bottom: ''
									}
								});
							} else {
								$.messager.alert('错误','部门信息注销失败，请联系管理员！','error');
							}
						},
						error: function(msg) {
							$.messager.alert('错误','部门信息注销出错，请联系管理员！','error');
						}
					});
					
				});
			}
			
			//恢复部门
			function recoverUser() {
				var rows = $("#instInfoList").datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示','请选择一条记录！','warning');
					return false;
				}
				var instcodes = ""; //后台接收用
				var parentcodes = ""; //用于刷新树节点
				for (var i = 0; i < rows.length; i++) {
					if (i == 0) {
						instcodes += rows[i].instcode;
					} else {
						instcodes += "," + rows[i].instcode;
					}
					
					if (parentcodes.indexOf(rows[i].pinstcode) < 0) {
						parentcodes = parentcodes + "," + rows[i].pinstcode;
					}
				}
				
				if (parentcodes.length > 0) {
					parentcodes = parentcodes.substr(1);
				}
				
				for(var i = 1; i<rows.length;i++){
					var instflag = rows[i].instflag;
					if(instflag == "1"){
						$.messager.alert('提示','您选择的部门已恢复，不能再进行恢复操作！','warning');
						return;
					}
				}
				$.ajax({
					type: "POST",
					url: "instInfoController_recoverInstInfo",
					data: {instcode: instcodes},
					dataType: "json",
					success: function(result) {
						// 1 恢复成功, 2恢复失败
						if (result && result._ResponseCode_ == '0000') {
							doQuery(); //刷新列表
							//刷新父节点
							initTree();
							flushParentNodes(parentcodes);
							$.messager.show({
								title: '提示',
								msg: '部门信息恢复成功！',
								style:{
									left: '',
									right: 0,
									top: document.body.scrollTop + document.documentElement.scrollTop,
									bottom: ''
								}
							});
						} else {
							$.messager.alert('错误','部门信恢复失败，请联系管理员！','error');
						}
					},
					error: function(msg) {
						$.messager.alert('错误','部门信息恢复出错，请联系管理员！','error');
					}
				});
			}
			
			//删除部门
			function deleteInst() {
				var rows = $("#instInfoList").datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示','请选择一条记录！','warning');
					return false;
				}
				var instcodes = ""; //后台接收用
				var parentcodes = ""; //用于刷新树节点
				for (var i = 0; i < rows.length; i++) {
					if (i == 0) {
						instcodes += rows[i].instcode;
					} else {
						instcodes += "," + rows[i].instcode;
					}
					
					if (parentcodes.indexOf(rows[i].pinstcode) < 0) {
						parentcodes = parentcodes + "," + rows[i].pinstcode;
					}
				}
				
				if (parentcodes.length > 0) {
					parentcodes = parentcodes.substr(1);
				}
				$.messager.confirm('请确认', '确认要删除选中部门？删除所选部门，其下子部门及用户将同时被删除。', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: "instInfoController_deleteInstInfo",
							data: {instcode: instcodes},
							dataType: "json",
							success: function(result) {
								// 1 注销成功, 2注销失败
								if (result._ResponseCode_ == '0000') {
									doQuery(); //刷新列表
									//刷新父节点
									initTree();
									flushParentNodes(parentcodes);
									initTree();
									$.messager.show({
										title: '提示',
										msg: '部门信息删除成功！',
										style:{
											left: '',
											right: 0,
											top: document.body.scrollTop + document.documentElement.scrollTop,
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','部门信息删除失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','该部门可能还存在用户或已经不存在！','error');
							}
						});
					}
				});
			}
			
// 			//刷新父节点
// 			function flushParentNodes(parentcodes) {
// 				var parentNodes = parentcodes.split(",");
// 				for (var item in parentNodes) {
// 					var node = $('#tree').tree('find', parentNodes[item]);
// 					if (node) {
// 						$('#tree').tree('reload', node.target);
// 					}
// 				}
// 			}
			
			//修改部门
			function openUpdateWin() {
				if (!isSelectOne()) {
					return;
				}
				
				var rows = $("#instInfoList").datagrid('getSelections');
				
				$("#msguser").html("");
				
				$('#winuser').window({
					title:"修改部门信息",
					width: 800,
					height: 280,
					modal:true,
					minimizable:false,
					collapsible:false
				});
				
				$("#msguser").html("");
				$("#msguser").load("instInfoController_openUpdatePage", {instcode:rows[0].instcode});
				$('#winuser').window('open');
			}
			
			//判断是否选择一条记录
			function isSelectOne() {
				var rows = $("#instInfoList").datagrid('getSelections');
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
    <div id="instinfo" data-options="region:'west',title:'部门',split:true" style="width:20%;">
		<ul id="tree" ></ul>
    </div>
    
    <div id="msg" data-options="region:'center',title:'操作区'" style="padding:5px;">
   		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="height:60px;padding:8px 5px;border:0px;">
				<div style="padding:10px;border:1px solid #95B8E7;">
					部门代码：<input class="easyui-textbox" id="instcode" style="width:180px;height:22px"/>&nbsp;
					部门名称：<input class="easyui-textbox" id="instname" style="width:180px;height:22px"/>&nbsp;
					<input type="hidden"  id="parentcode" value=""/>
					<a class="easyui-linkbutton" href="javascript:void(0);"  data-options="iconCls:'icon-search'" onclick="doQuery()">查询</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-add'" onclick="openAddWin();">新增</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-edit'" onclick="openUpdateWin()">修改</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-remove'" onclick="logoffUser()">注销</a>
					<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-back'" onclick="recoverUser()">恢复</a>
					<c:set var="flag" value="true"></c:set>
					<c:forEach items="${sessionScope.session_user.roles}" var="role">
						<c:if test="${flag == true && role.rolecode == '0'}">
							<c:set var="flag" value="false"/>
						</c:if>
					</c:forEach>
					<c:if test="${flag==false}"><!-- 公共管理用户 -->
						<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-cancel'" onclick="deleteInst()">删除</a>
					</c:if>
				</div>
			</div>
			<div data-options="region:'center',iconCls:'icon-ok'" style="padding:5px;border:0px;">
				<table id="instInfoList" class="easyui-datagrid" title="部门列表"
					data-options="rownumbers:true,singleSelect:false,collapsible:false,pagination:true,
						pageSize:10,url:'instInfoController_instList',method:'post',fit:true,fitColumns:true">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'instcode',width:80,halign:'center',align:'center',formatter:go">部门代码</th>
							<th data-options="field:'instname',width:100,halign:'center',align:'center'">部门名称</th>
							<th data-options="field:'instsname',width:100,halign:'center',align:'center',hidden:'true'">部门简称</th>
							<th data-options="field:'pinstcode',width:80,halign:'center',align:'center',hidden:'true'">上级部门代码</th>
							<th data-options="field:'pinstinfo',width:120,halign:'center',align:'center',formatter:getPinstinfo">上级部门</th>
							<th data-options="field:'parentinstflag',width:80,halign:'center',align:'center',hidden:'true'">上级部门状态</th>
							<th data-options="field:'finstcode',width:80,halign:'center',align:'center',hidden:'true'">内部金融部门编码</th>
							<th data-options="field:'binstcode',width:200,halign:'center',align:'left',hidden:'true'">金融部门编码</th>
							<th data-options="field:'telno',width:100,halign:'center'">部门联系电话</th>
							<th data-options="field:'province',width:80,halign:'center',hidden:'true'">所属省份</th>
							<th data-options="field:'provincename',width:80,halign:'center',align:'center'">所属省份</th>
							<th data-options="field:'city',width:80,halign:'center',hidden:'true'">所属区市</th>
							<th data-options="field:'cityname',width:80,halign:'center',align:'center'">所属区市</th>
							<th data-options="field:'instclass',width:100,halign:'center',hidden:'true'">部门类别</th>
							<th data-options="field:'instclassname',width:120,halign:'center',align:'center'">部门类别</th>
							<th data-options="field:'contactname',width:80,halign:'center',align:'center'">负责人</th>
							<th data-options="field:'contacttelno',width:100,halign:'center'">联系电话</th>
							<th data-options="field:'instflag',width:80,halign:'center',align:'lefg',hidden:'true'">部门状态</th>
							<th data-options="field:'instflagname',width:80,halign:'center',align:'lefg',align:'center'">部门状态</th>
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

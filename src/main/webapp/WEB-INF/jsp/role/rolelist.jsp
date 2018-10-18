<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/bootstrap/easyui.css">
		<link rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/demo.css">
		<link rel="stylesheet" type="text/css" href="${basePath}styles/common.css" />
		<script type="text/javascript" src="${basePath}scripts/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${basePath}scripts/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${basePath}scripts/easyui/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${basePath}scripts/easyui/easyui-extends-custom.js"></script>
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.cookie.js"></script>
		<script type="text/javascript" src="${basePath}scripts/chose_theme.js"></script>
		<script type="text/javascript">
			//查询
			function doQuery() {
				$("#roleList").datagrid('reload', {
					rolecode: $("#rolecode").val(),
					rolename: $("#rolename").val()
				});
				$("#roleList1").datagrid('reload');
			}
			
			//新增角色
			function openAddWin() {
				$('#winuser').window({
					title : "新增角色信息",
					height: 210,
					minimizable:false,
					collapsible:false,
					modal:true
				});
					
				$("#msguser").html("");
				$("#msguser").load("roleController_openAddPage");
				$('#winuser').window('open');
			}
			
			//判断是否选择一条记录
			function isSelectOne() {
				var rows = $("#roleList").datagrid('getSelections');
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
				
				var rows = $("#roleList").datagrid('getSelections');
				if(rows[0].rolecode=='0'){
					$.messager.alert('提示','系统管理员不能进行修改操作！','warning');
					return;
				}
				
				$('#winuser').window({
					title:"修改角色信息",
					height: 220,
					modal:true,
					minimizable:false,
					collapsible:false
				});
				$("#msguser").html("");
				$("#msguser").load("roleController_openUpdatePage", {rolecode:rows[0].rolecode});
				$('#winuser').window('open');
			}
			
			//删除角色
			function logoffRole() {
				if (!isSelectOne()) {
					return;
				}
				var selectedRow = $("#roleList").datagrid('getSelected');
				
				if (selectedRow.rolecode == "0") {
					$.messager.alert('提示','系统管理员不能进行删除操作！','warning');
					return;
				}
				$.messager.confirm('请确认', '确认要删除选中角色？删除后，该角色的用户将不再拥有相应权限。', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: "roleController_logoffRole",
							data: {rolecode: selectedRow.rolecode},
							dataType: "json",
							success: function(result) {
								// 1 删除成功, 2删除失败
								if (result._ResponseCode_ == '0000') {
									doQuery(); //刷新列表
									$.messager.show({
										title: '提示',
										msg: '角色信息删除成功！',
										style:{
											right: '',
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','角色信息删除失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','角色信息删除出错，请联系管理员！','error');
							}
						});
					}
				});
			}
			
			//日期格式化
			function formatdate(value) {
				if (value!=null)
					return new Date(value).getUTCFullYear()+"-"+(new Date(value).getUTCMonth()+1)+"-"+new Date(value).getUTCDate();
				else return value;
			}
			
			$(function(){ 
		    	initTree();
		    });
		    
			function initTree() {
				$('#tree').tree({
					url : 'roleController_generateTreeNode',
					animate : true,
					checkbox : true,
					cascadeCheck : false,
					lines : true, //显示虚线效果  
					loadFilter : function(data) {
						if (data) {
							return data.root;
						} else {
							return data;
						}
					},
					onCheck: function (node, checked) {
						var parentNode = $("#tree").tree('getParent', node.target);
						var childNode = $("#tree").tree('getChildren', node.target);
						
						if (checked) {
							//父节点选中
							if (parentNode != null) {
								$("#tree").tree('check', parentNode.target);
							}
						} else {
							//所有子节点不选中
							if (childNode != null && childNode.length > 0) {
								for (var i = 0; i < childNode.length; i++) {
									$("#tree").tree('uncheck', childNode[i].target);
								}
							}
						}
					},
					onLoadSuccess : function(node, param) {
						var rootNode = $("#tree").tree('getRoots'); //获取所有节点
						$("#tree").tree('expandAll', rootNode.target); //调用expandAll方法
					}
				});
			}
			
			//保存授权信息
			function doSaveImpower(){
				var menus = new Array();
				var rolecode = $("#role").val();
				var treemenu = $('#tree').tree('getChecked');
				for (var i = 0; i < treemenu.length; i++) {
					menus[i] = treemenu[i].id;
				}
				if (rolecode == '' || rolecode == null) {
					$.messager.alert('错误','请选择要授权的角色！','error');
					return;
				}
				$.messager.confirm('请确认', '确认要为该角色授权？', function(r){
					if (r) {
						$.ajax({
							type: "POST",
							url: "roleController_saveImpowerInfo?rolecode=" + rolecode + "&menus=" + menus,
							dataType: "json",
							success: function(result) {
								if (result._ResponseCode_ == '0000') {
									$.messager.show({
										title: '提示',
										msg: '授权信息保存成功,请重新登录！',
										style:{
											right: '',
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','授权信息保存失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','授权信息保存出错，请联系管理员！','error');
							}
						});
					}
				});
			}
			
			//设置角色菜单选中状态
			function setRoleMenu(rowIndex, rowData) {
				$.ajax({
					type: 'POST',
					url: 'roleController_roleTree?rolecode=' + rowData.rolecode,
					dataType: 'json',
					success: function(result) {
						$('#role').val(rowData.rolecode);
						var roots = $('#tree').tree('getChecked');
						if (roots && typeof(roots) != 'undefined') {
							for ( var i = 0; i < roots.length; i++) {  
								var nodeone = $('#tree').tree('find', roots[i].id); //查找节点  
								if(nodeone!=null){
									$('#tree').tree('uncheck', nodeone.target); //将得到的节点选中  
								}
							}
						}
						var json = result._body_;
						if (json && typeof(json) != 'undefined') {
							for (var i=0; i < json.length; i++) {  
								var node = $('#tree').tree('find',json[i].menucode);
								if(node!=null){
									$('#tree').tree('check', node.target);	
								}
							}
						}
					},
					error: function(msg) {
						$.messager.alert('错误','查询信息出错，请联系管理员！','error');
					}
				});
			}
		</script>
	</head>

	<body>
		<div class="easyui-tabs" data-options="fit:true">
			<div title="角色维护" style="padding:5px;">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north'" style="height:60px;padding:8px 5px;border:0px;">
						<div style="padding:10px;border:1px solid #95B8E7;">
							角色代码：<input class="easyui-textbox" id="rolecode" style="width:180px;height:22px">&nbsp;
							角色名称：<input class="easyui-textbox" id="rolename" style="width:180px;height:22px">&nbsp;
							<a class="easyui-linkbutton" href="javascript:void(0);"  data-options="iconCls:'icon-search'" onclick="doQuery()">查询</a>
							<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-add'" onclick="openAddWin();">新增</a>
							<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-edit'" onclick="openUpdateWin()">修改</a>
							<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-cancel'" onclick="logoffRole()">删除</a>
						</div>
					</div>
					<div data-options="region:'center',iconCls:'icon-ok'" style="padding:5px;border:0px;">
						<table id="roleList" class="easyui-datagrid" title="角色列表"
								data-options="rownumbers:true,singleSelect:false,collapsible:false,pagination:true,
								pageSize:10,url:'roleController_roleList',method:'post',fit:true,fitColumns:true">
							<thead>
								<tr>
									<th data-options="field:'ck',checkbox:true"></th>
									<th data-options="field:'rolecode',width:80,halign:'center',align:'center'">角色代码</th>
									<th data-options="field:'rolename',width:100,halign:'center',align:'center'">角色名称</th>
									<th data-options="field:'instclass',width:100,halign:'center',align:'center',hidden:'true'">所属类型</th>
									<th data-options="field:'instclassname',width:100,halign:'center',align:'center'">所属类型</th>
									<th data-options="field:'opinstcode',width:100,halign:'center',align:'center',hidden:'true'">操作部门</th>
									<th data-options="field:'opinstname',width:100,halign:'center',align:'center'">操作部门</th>
									<th data-options="field:'oprcode',width:100,halign:'center',align:'center',hidden:'true'">操作员</th>
									<th data-options="field:'oprname',width:100,halign:'center',align:'center'">操作员</th>
									<th data-options="field:'opdate',width:80,halign:'center',align:'center',formatter:formatdate">操作日期</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
			<div title="角色授权" style="padding:5px;">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'west',iconCls:'icon-ok'" style="width:50%;padding:5px;border:0px;">
						<table id="roleList1" class="easyui-datagrid" title="角色列表（单击某个角色查看角色菜单授权信息）" 
								data-options="rownumbers:true,singleSelect:true,collapsible:false,pagination:true,onClickRow:function(rowIndex, rowData){setRoleMenu(rowIndex, rowData);},
								pageSize:10,url:'roleController_roleList',method:'post',fit:true,fitColumns:true">
							<thead>
								<tr>
									<th data-options="field:'rolecode',width:80,halign:'center',align:'center'">角色代码</th>
									<th data-options="field:'rolename',width:100,halign:'center',align:'center'">角色名称</th>
									<th data-options="field:'instclass',width:100,halign:'center',align:'center',hidden:'true'">所属类型</th>
									<th data-options="field:'instclassname',width:100,halign:'center',align:'center'">所属类型</th>
								</tr>
							</thead>
						</table>
					</div>
					<input id="role" name="rolecode"  type="hidden"/>
					<div id="menutree" data-options="region:'center',iconCls:'icon-ok'" style="width:50%;padding:5px;overflow:auto;border:0px;">
						<div class="easyui-layout" data-options="fit:true">
							<div data-options="region:'north',iconCls:'icon-ok'" style="width:50%;padding:5px;border:1px solid #95b8e7;">
								<div class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="doSaveImpower()">保存</div>
							</div>
							<div data-options="region:'center',iconCls:'icon-ok',title:'请在下方菜单树中选择要保存的菜单项'" style="width:50%;padding:5px;border:1px solid #95b8e7;">
								<div style="overflow:auto;">	
									<ul id="tree" ></ul>
						   		</div>
					   		</div>
				   		</div>
				   	</div>
				</div>
			</div>
		</div>
		
		<div id="winuser" data-options="closed:true" style="width:50%; text-align:center">
			<div id="msguser"></div>
		</div>
	</body>
</html>

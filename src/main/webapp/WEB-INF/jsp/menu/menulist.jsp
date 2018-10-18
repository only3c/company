<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
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
					url : 'menuController_generateTreeNode',
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
						$("#menuname").val("");
						$("#menucode").val("");
						$("#menuname").textbox('setText', "");
						$("#menucode").textbox('setText', "");
						$("#parentcode").val(node.id);
						doQuery();
					}
				});
			}
			
			//日期格式化
			function formatdate(value) {
				if (value!=null)
					return new Date(value).getUTCFullYear()+"-"+(new Date(value).getUTCMonth()+1)+"-"+new Date(value).getUTCDate();					
				else return value;
			}

			//查询
			function doQuery() {
				$("#menuList").datagrid('reload', {
					parentcode: $("#parentcode").val(),
					menuname: $("#menuname").val(),
					menucode: $("#menucode").val()
				});
			}
			
			// 获取上级部门信息
			function getPmenuinfo(val, row) {
				if (val) {
					return val.menucode;
				} else {
					return '';
				}
			}
			
			//页面按钮查询
			function queryList(){
				$("#parentcode").val("");
				doQuery();
			}
			
			//新增菜单
			function openAddWin() {
				var treeNode = $("#tree").tree("getSelected");
				var parentcode = (treeNode == null ? "" : treeNode.id);
				
				if (parentcode == null || parentcode == "") {
					$.messager.alert('提示','请选择菜单树节点作为新增菜单所属的上级菜单！');
					return;
				}
				var menulevel = treeNode.menulevel;//treeNode.menulevel;//
				if (menulevel == null || menulevel >= 6) {
					$.messager.alert('提示','当前菜单已经达到了最大6级，不能再添加子菜单！');
					return;
				}
				
				$("#win").window({
					title: "新增菜单信息",
					height: 310,
					minimizable: false,
					collapsible: false,
					modal: true
				});
				
				$("#wincontent").html("");
				$("#wincontent").load("menuController_openAddPage",{parentcode:parentcode});
				$("#win").window("open");
			}
			
			//修改菜单
			function openUpdateWin() {
				var rows = $("#menuList").datagrid('getChecked');
				if (rows.length == 0) {
					$.messager.alert('提示','请选择一条记录！','warning');
					return;
				}
				if (rows.length > 1) {
					$.messager.alert('提示','只能选择一条记录！','warning');
					return;
				}
				
				$('#win').window({
					title: "修改菜单信息",
					height: 310,
					modal: true,
					minimizable: false,
					collapsible: false
				});
				
				$("#wincontent").html("");
				$("#wincontent").load("menuController_openUpdatePage", {menucode: rows[0].menucode});
				$('#win').window('open');
			}
			
			//删除菜单
			function deleteMenu() {
				var rows = $("#menuList").datagrid('getChecked');
				if (rows.length == 0) {
					$.messager.alert('提示','请至少选择一条记录！','warning');
					return;
				}
				
				var menucodes = ""; //后台接收用
				var parentcodes = ""; //用于刷新树节点
				for (var i = 0; i < rows.length; i++) {
					if (i == 0) {
						menucodes += rows[i].menucode;
					} else {
						menucodes += "," + rows[i].menucode;
					}
					
					if (parentcodes.indexOf(rows[i].parentcode) < 0) {
						parentcodes = parentcodes + "," + rows[i].parentcode;
					}
				}
				
				if (parentcodes.length > 0) {
					parentcodes = parentcodes.substr(1);
				}
								
				$.messager.confirm('确认', '确认要删除选中菜单？（删除选中菜单将删除选中菜单下所有子菜单。）', function(r){
					if (r) {
						$.ajax({
							type: "POST",
							url: "menuController_deleteMenu",
							data: {menucodes: menucodes},
							dataType: "json",
							success: function(result) {
								// 1 注销成功, 2注销失败
								if (result._ResponseCode_ == '0000') {
									doQuery(); //刷新列表
									initTree();
// 									//刷新父节点
// 									var parentNodes = parentcodes.split(",");
// 									for (var item in parentNodes) {
// 										var node = $('#tree').tree('find', parentNodes[item]);
// 										if (node) {
// 											$('#tree').tree('reload', node.target);
// 										}
// 									}
									
									$.messager.show({
										title: '提示',
										msg: '删除菜单成功！',
										style:{
											right: '',
											bottom: ''
										}
									});
								} else {
									$.messager.alert('错误','删除菜单失败，请联系管理员！','error');
								}
							},
							error: function(msg) {
								$.messager.alert('错误','删除菜单出错，请联系管理员！','error');
							}
						});
					}
				});
			}
		</script>
	</head>
	<body class="easyui-layout" >
	    <div id="menutree" data-options="region:'west',title:'菜单树',split:true" style="width:20%;">
			<ul id="tree" ></ul>
	    </div>
	    
	    <div id="menulist" data-options="region:'center',title:'操作区'" style="padding:5px;">
	    	<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north'" style="height:60px;padding:8px 5px;border:0px;">
					<div style="padding:10px;border:1px solid #95B8E7;">
						菜单代码：<input class="easyui-textbox" id="menucode" style="width:180px;height:22px" />&nbsp;
						菜单名称：<input class="easyui-textbox" id="menuname" style="width:180px;height:22px" />&nbsp;
						<input type="hidden" id="parentcode" value="" />
						<a class="easyui-linkbutton" href="javascript:void(0);"  data-options="iconCls:'icon-search'" onclick="doQuery()">查询</a>
						<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-add'" onclick="openAddWin();">新增</a>
						<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-edit'" onclick="openUpdateWin()">修改</a>
						<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-cancel'" onclick="deleteMenu()">删除</a>
					</div>
				</div>
				<div data-options="region:'center',iconCls:'icon-ok'" style="padding:5px;border:0px;">
					<table id="menuList" class="easyui-datagrid" title="菜单列表"
							data-options="rownumbers:true,singleSelect:false,collapsible:false,pagination:true,
							pageSize:10,url:'menuController_queryMenuList',method:'post',fit:true,fitColumns:true">
						<thead>
							<tr>
								<th data-options="field:'ck',checkbox:true"></th>
								<th data-options="field:'menucode',width:80,halign:'center',align:'center'">菜单代码</th>
								<th data-options="field:'menuname',width:80,halign:'center',align:'center'">菜单名称</th>
								<th data-options="field:'url',width:130,halign:'center',align:'center'">菜单url</th>
								<th data-options="field:'pmenuinfo',width:80,halign:'center',align:'center',formatter:getPmenuinfo">上级菜单代码</th>
								<th data-options="field:'menulevel',width:80,halign:'center',align:'center'">菜单级别</th>
								<th data-options="field:'isleaf',width:40,halign:'center',align:'center',hidden:'true'">是否叶节点</th>
								<th data-options="field:'isleafname',width:40,halign:'center',align:'center'">是否叶节点</th>
								<th data-options="field:'disporder',width:80,halign:'center',align:'center'">显示顺序</th>
								<th data-options="field:'instclass',width:80,halign:'center',align:'center',hidden:'true'">所属类型</th>
								<th data-options="field:'instclassname',width:80,halign:'center',align:'center'">所属类型</th>
								<th data-options="field:'opinstcode',width:80,halign:'center',align:'center',hidden:'true'">操作部门</th>
								<th data-options="field:'opinstname',width:80,halign:'center',align:'center'">操作部门</th>
								<th data-options="field:'oprcode',width:80,halign:'center',align:'center',hidden:'true'">操作员</th>
								<th data-options="field:'oprname',width:80,halign:'center',align:'center'">操作员</th>
								<th data-options="field:'opdate',width:80,halign:'center',align:'center',formatter:formatdate">操作日期</th>
							</tr> 
						</thead>
					</table>
				</div>
			</div>
	    </div>
		
		<div id="win" data-options="closed:true" style="width:700px; height:355px; text-align:center">
			<div id="wincontent"></div>
		</div>
    
	</body>
</html>

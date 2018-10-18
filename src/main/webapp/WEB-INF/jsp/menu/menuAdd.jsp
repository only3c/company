<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>菜单新增页面</title>
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.form.js"></script>
<%-- 		<script type="text/javascript" src="${basePath}scripts/client/updateinfo.js"></script> --%>
<%-- 		<script type="text/javascript" src="${basePath}scripts/common/easyui-ext/validateEx.js"></script> --%>
		<script type="text/javascript">
			$(function() {
				//默认是解析页面中所有定义为easyui组件的节点，解决弹出子页面不能使用jquery easyui方法
				$.parser.parse("#addDiv");
			});
			
			//重置表单
			function doReset() {
				$("#addMenu").form('reset');
			}
			
			//保存
			function doSave() {
				if (!$("#addMenu").form('validate')) {
					return;
				}
				
				var options = {
					type: "post",
					url: "menuController_saveMenu",
					dataType: "json",
					success: function(result){
						// 0 保存成功, 1存在的用户名, 2 无效的用户对象
						if (result._ResponseCode_ == '0000') {
							$.messager.show({
								title: '提示',
								msg: '菜单信息保存成功！',
								style:{
			             		    left: '',
									right: 0,
									top: document.body.scrollTop + document.documentElement.scrollTop,
									bottom: ''
								}
							});
							
							$('#win').window('close'); //关闭窗口
							doQuery(); //刷新列表
							initTree();//tree刷新
						} else if (result._ResponseCode_ == '1002') {
							$.messager.alert('提示','该菜单代码已经存在！','warning');
						} else if (result._ResponseCode_ == '1003') {
							$.messager.alert('错误','无效的菜单信息，请联系管理员！','error');
						}else if (result.message == 3) {
							$.messager.alert('错误','处理菜单排序失败，请联系管理员！','error');
						}
						return false;
					}
				};
				$("#addMenu").ajaxSubmit(options);
			}
		</script>
	</head>
	<body>
		<div id="addDiv" class="easyui-panel" title="" data-options="fit:true" style="padding:20px;">
			<form id="addMenu" name="addMenu" method="post" action="menuController_saveMenu" >
				<table>
					<tr>
						<td align="right" style="width:130px;">菜单名称：</td>
						<td align="left" colspan="3">
							<input type="text" name="menuname" class="easyui-textbox" style="width:500px;"
								id="menuname" data-options="required:true,validType:'length[0,60]'"></input>
						</td>
					</tr>
					<tr>
						<td align="right">上级菜单代码：</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="parentcode" id="parentcode" 
								value="${menuinfo.menucode}" readonly="readonly" style="width:180px;" data-options="required:false"></input>
						</td>
						<td align="right" style="width:130px;">上级菜单：</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="pinstname" style="width:180px;"
								id="pinstname" value="${menuinfo.menuname}" readonly="readonly" data-options="required:false"></input>
						</td>
					</tr>
					<tr>
						<td align="right">菜单级别：</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="menulevel" style="width:180px;" readonly="readonly"
								id="menulevel" data-options="required:true" value="${menulevel}"></input>
						</td>
						<td align="right">显示顺序：</td>
						<td align="left">
							<input class="easyui-numberbox" type="text" name="disporder" style="width:180px;"
								id="disporder" data-options="required:true,validType:'PositiveIntegerFormat'"></input>								
						</td>
					</tr>
					<tr>
						<td align="right">所属类型：</td>
						<td>
							<input class="easyui-combobox" type="text" id="instclass" name="instclass" style="width:180px;"
								data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
								url:'dictionaryController_getDictionaryForComboBox?datatype=instclass&required=true'"></input>
						</td>
						<td align="right">是否为叶子节点：</td>
						<td>
							<input class="easyui-combobox" type="text" id="isleaf" name="isleaf" style="width:180px;" readonly="readonly"
								data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
								url:'dictionaryController_getDictionaryForComboBox?datatype=yesno&required=true',value:'1'"></input>
						</td>
					</tr>
					<tr>
						<td align="right">菜单URL：</td>
						<td align="left" colspan="4">
							<input class="easyui-textbox" type="text" id="url" name="url" data-options="validType:'length[0,80]'" style="width:500px;">
						</td>
					</tr>
					<tr>
						<td align="right">菜单说明：</td>
						<td align="left" colspan="4">
							<input class="easyui-textbox" type="text" id="description" name="description" data-options="multiline:true,validType:'length[0,200]'" style="width:500px;height:50px">
						</td>
					</tr>
				</table>
				<div style="text-align: center; padding: 5px">
					<div class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="doSave()">保存</div>
					&nbsp;&nbsp;
					<div class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="doReset();">重置</div>
				</div>
			</form>
		</div>
	</body>
</html>

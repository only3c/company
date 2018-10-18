<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head> 
		<title>角色新增页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="${basePath}scripts/client/updateinfo.js"></script>
		<script type="text/javascript">
			$(function() {
				//默认是解析页面中所有定义为easyui组件的节点，解决弹出子页面不能使用jquery easyui方法
				$.parser.parse("#addDiv");
			});
			
			//重置表单
			function doReset() {
				$("#addPrPubRole").form('reset');
			}
			//保存
			function doSave() {
				if (!$("#addPrPubRole").form('validate')) {
					return;
				}
				
				var options = {
					type: "post",
					url: "roleController_saveRole",
					dataType: "json",
					success: function(result){
						// 0 保存成功, 1存在的用户名, 2 无效的用户对象
						if (result._ResponseCode_ == '0000') {
							$.messager.show({
								title: '提示',
								msg: '角色信息保存成功！',
								style:{
		             		    left: '',
								right: 0,
								top: document.body.scrollTop + document.documentElement.scrollTop,
								bottom: ''
								}
							});
							
							$('#winuser').window('close'); //关闭窗口
							doQuery();
						} else if (result._ResponseCode_ == '1004') {
							$.messager.alert('提示','该角色代码已经存在！','warning');
						} else if (result._ResponseCode_ == '1003') {
							$.messager.alert('错误','无效的角色信息，请联系管理员！','error');
						}
						return false;
					}
				};
				$("#addPrPubRole").ajaxSubmit(options);
			}
			
		</script>
	</head>
	<body>
		<div id="addDiv" class="easyui-panel" title="" data-options="fit:true" style="padding:20px;">
			<div>
				<form action="roleController_saveRole" id="addPrPubRole"
					name="addPrPubRole" method="post" >
						<table>
							<tr>
								<td align="right" style="width:110px;">
									角色名称:
								</td>
								<td align="left">
									<input type="text" name="rolename" class="easyui-textbox"
										id="rolename"  data-options="required:true,validType:'length[0,40]'" style="width:180px;"></input>
								</td>
								<td align="right" style="width:130px;">
									所属类型:
								</td>
								<td align="left">
									<input class="easyui-combobox" type="text" id="instclass" name="instclass" style="width:180px;"
									data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
									url:'dictionaryController_getDictionaryForComboBox?datatype=instclass&required=true'"></input>
								</td>
							</tr>
							<tr>
								<td align="right">
									描述:
								</td>
								<td align="left" colspan="4">
									<input id="description" name="description" class="easyui-textbox" type="text" data-options="multiline:true,validType:'length[0,100]'" style="width:100%;height:50px">
								</td>
							</tr>
						</table>
						<div style="text-align: center; padding: 5px">
							<div class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="doSave()">保存</div>&nbsp;&nbsp;
							<div class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="doReset();">重置</div>
						</div>
				</form>
			</div>
		</div>
	</body>
</html>

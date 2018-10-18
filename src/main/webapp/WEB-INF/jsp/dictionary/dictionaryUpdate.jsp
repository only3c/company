<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>数据字典修改页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.form.js"></script>
		<script type="text/javascript">
			$(function() {
				//默认是解析页面中所有定义为easyui组件的节点，解决弹出子页面不能使用jquery easyui方法
				$.parser.parse("#updateDiv");
			});
			
			//重置表单
			function doReset() {
				$("#updateDictionary").form('reset');
			}
			
			//保存 
			function doSave() {
				if (!$("#updateDictionary").form('validate')) {
					return;
				}
				
				var options = {
					type : "post",
					url : "dictionaryController_updateDictionary",
					dataType : "json",
					success : function(result) {
						$.messager.progress("close");
						// '0000' 修改成功
						if (result._ResponseCode_ == '0000') {
							$.messager
									.show({
										title : '提示',
										msg : '数据字典保存成功！',
										style : {
											left : '',
											right : 0,
											top : document.body.scrollTop
													+ document.documentElement.scrollTop,
											bottom : ''
										}
									});

							$('#winuser').window('close'); //关闭窗口
							doQuery(); //刷新列表
						} else {
							$.messager.alert(result._ResponseMessage_);
						}
						return false;
					}
				};
				//进度框
				$.messager.progress({
					title: "请稍后",
					msg: "正在保存数据字典,请稍后..."
				});
				$("#updateDictionary").ajaxSubmit(options);
			}
		</script>
	</head>
	<body>
		<div id="updateDiv" class="easyui-panel" title="" data-options="fit:true" style="padding:20px">
			<form action="" id="updateDictionary" name="updateDictionary" method="post" >
				<table>
					<input value="${dictionary.id}" id="id" name="id" hidden="false"/>
					<tr>
						<td align="right" width="19%">
							数据类型：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${dictionary.datatype}" id="datatype" name="datatype" 
								data-options="validType:'length[0,20]',required:true" style="width:180px;"/>
						</td>
						<td align="right">
							数据类型名称：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${dictionary.datatypename}" id="datatypename" name="datatypename" 
								data-options="validType:'length[0,40]'" style="width:180px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							数据编码：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${dictionary.datacode}" id="datacode" name="datacode" 
								data-options="validType:'length[0,20]',required:true" style="width:180px;"/>
						</td>
						<td align="right">
							数据名称：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${dictionary.dataname}" id="dataname" name="dataname" 
								data-options="validType:'length[0,100]'" style="width:180px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							父数据编码：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${dictionary.parentcode}" id="parentcode" name="parentcode" 
								data-options="validType:'length[0,20]'" style="width:180px;"/>
						</td>
						<td align="right">
							顺序：
						</td>
						<td align="left" width="25%">
							<input class="easyui-numberbox" value="${dictionary.displayorder}" id="displayorder" name="displayorder" 
								data-options="min:0" style="width:180px;"/>
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

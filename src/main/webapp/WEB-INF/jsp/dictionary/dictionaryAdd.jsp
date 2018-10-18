<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <title>部门新增页面</title>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="${basePath}scripts/dateformat.js"></script>
		<script type="text/javascript">
			$(function() {
				//默认是解析页面中所有定义为easyui组件的节点，解决弹出子页面不能使用jquery easyui方法
				$.parser.parse("#addDiv");
			});
			
			
			//重置表单
			function doReset() {
				$("#addDictionary").form('reset');
			}
			
			//保存
			function doSave() {
				if (!$("#addDictionary").form('validate')) {
					return;
				}
				
				var options = {
					type : "post",
					url : "dictionaryController_saveDictionary",
					dataType : "json",
					success : function(result) {
						$.messager.progress("close");
						// '0000' 保存成功
						if (result._ResponseCode_ == '0000') {
							$.messager.show({
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
							doQuery();
						} else {
							$.messager.alert('错误', result._ResponseMessage_, 'error');
						} 
						return false;
					}
				};
				//进度框
				$.messager.progress({
					title: "请稍后",
					msg: "正在数据字典信息,请稍后..."
				});
				$("#addDictionary").ajaxSubmit(options);
			}
		</script>
	</head>
	<body >
		<div id="addDiv" class="easyui-panel" title="" data-options="fit:true" style="padding:20px;">
			<form action="instInfoController_saveInstInfo" id="addDictionary" name="addDictionary" method="post" >
				<table>
					<tr>
						<td align="right" width="19%">
							类型：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" id="datatype" name="datatype" 
								data-options="validType:'length[0,20]',required:true" style="width:180px;"/>
						</td>
						<td align="right">
							类型名称：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" id="datatypename" name="datatypename" 
								data-options="validType:'length[0,40]'" style="width:180px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							编码：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" id="datacode" name="datacode" 
								data-options="validType:'length[0,20]',required:true" style="width:180px;"/>
						</td>
						<td align="right">
							编码名称：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" id="dataname" name="dataname" 
								data-options="validType:'length[0,100]'" style="width:180px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							上级编码：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" id="parentcode" name="parentcode" 
								data-options="validType:'length[0,20]'" style="width:180px;"/>
						</td>
						<td align="right">
							顺序：
						</td>
						<td align="left" width="25%">
							<input class="easyui-numberbox" id="displayorder" name="displayorder" 
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

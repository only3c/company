<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <title>笔记新增页面</title>
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
			
			$("#meetingtype").combobox({
				url : 'dictionaryController_getDictionaryForComboBox?datatype=meetingtype&required=true',
				valueField : 'datacode',
				textField : 'dataname',
				panelHeight : '120',
				editable : false,
				required : true,
				onSelect : function() {
					var id = $("#meetingtype").combobox('getValue');
					var url = 'meetingController_getAllByType?meetingtype=' + id;
					$('#subject').combobox('reload', url);
				}
			});
			
			
			//重置表单
			function doReset() {
				$("#addNote").form('reset');
			}
			
			//保存
			function doSave() {
				if (!$("#addNote").form('validate')) {
					return;
				}
				
				var options = {
					type : "post",
					url : "noteController_saveNote",
					dataType : "json",
					success : function(result) {
						$.messager.progress("close");
						// '0000' 保存成功
						if (result._ResponseCode_ == '0000') {
							$.messager.show({
								title : '提示',
								msg : '会议保存成功！',
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
// 				//进度框
// 				$.messager.progress({
// 					title: "请稍后",
// 					msg: "正在查询会议信息,请稍后..."
// 				});
				$("#addNote").ajaxSubmit(options);
			}

		</script>
	</head>
	<body >
		<div id="addDiv" class="easyui-panel" title="" data-options="fit:true" style="padding:20px;">
			<form action="instInfoController_saveInstInfo" id="addNote" name="addNote" method="post" >
				<table>
					<tr>
						<td align="right">笔记名称：</td>
						<td align="left" colspan="4">
							<input class="easyui-textbox" type="text" id="notename" name="notename" data-options="validType:'length[0,100]',required:true" style="width:428px">
						</td>
					</tr>
					<tr>
						<td align="right">
							会议类型：
						</td>
						<td align="left">
								<input class="easyui-combobox" type="text" id="meetingtype" name="meetingtype" style="width:180px;" style="width:180px;" >
						</td>
						<td align="right">
							会议主题：
						</td>
						<td>
							<input class="easyui-combobox" type="text" name="meetingid" id="subject" style="width:180px;"
								data-options="editable:false,valueField:'id',textField:'subject',required:true"></input>
						</td>
					</tr>
				</table>
				<div style="text-align: center; padding: 5px">
					<div class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="doSave()">保存</div>
					&nbsp;&nbsp;
					<div class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="doReset()">重置</div>
				</div>
			</form>
		</div>
	</body>
</html>

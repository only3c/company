<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <title>会议新增页面</title>
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
				$("#addMeeting").form('reset');
			}
			
			//保存
			function doSave() {
				if (!$("#addMeeting").form('validate')) {
					return;
				}
				
				var options = {
					type : "post",
					url : "meetingController_saveMeeting",
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
				//进度框
				$.messager.progress({
					title: "请稍后",
					msg: "正在会议信息,请稍后..."
				});
				$("#addMeeting").ajaxSubmit(options);
			}
		</script>
	</head>
	<body >
		<div id="addDiv" class="easyui-panel" title="" data-options="fit:true" style="padding:20px;">
			<form action="instInfoController_saveInstInfo" id="addMeeting" name="addMeeting" method="post" >
				<table>
					<tr>
						<td align="right" width="10%">
							会议类型：
						</td>
						<td align="left" width="25%">
							<input class="easyui-combobox" id="meetingtype" name="meetingtype" style="width:180px;"
								data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
							url:'dictionaryController_getDictionaryForComboBox?datatype=meetingtype&required=true'"></input>
						</td>
						<td align="right">
							会议时长(小时)：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" id="timelength" name="timelength" 
								data-options="validType:'length[0,40]',required:true" style="width:180px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							开始时间：
						</td>
						<td align="left" width="25%">
							<input class="easyui-datetimebox" id="starttime" name="starttime" style="width:180px;"
								data-options="required:true" />
						</td>
						<td align="right">
							结束时间：
						</td>
						<td align="left" width="25%">
							<input class="easyui-datetimebox" id="endtime" name="endtime"  style="width:180px;"
								data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							会议主题：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" id="subject" name="subject" 
								data-options="validType:'length[0,100]',required:true" style="width:180px;"/>
						</td>
						<td align="right">
							会议室：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" id="meetingroom" name="meetingroom" 
								data-options="validType:'length[0,100]',required:true" style="width:180px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							会议纪要：
						</td>
						<td align="left" width="25%" colspan="4">
							<input class="easyui-textbox" id="summary" name="summary" 
								data-options="validType:'length[0,100]',required:false" style="width:542px;"/>
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

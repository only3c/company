<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>试题修改页面</title>
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
				$("#updateQuestion").form('reset');
			}
			
			//保存 
			function doSave() {
				if (!$("#updateQuestion").form('validate')) {
					return;
				}
				
				var options = {
					type : "post",
					url : "questionController_updateQuestion",
					dataType : "json",
					success : function(result) {
						$.messager.progress("close");
						// '0000' 修改成功
						if (result._ResponseCode_ == '0000') {
							$.messager
									.show({
										title : '提示',
										msg : '试题保存成功！',
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
					msg: "正在保存试题,请稍后..."
				});
				$("#updateQuestion").ajaxSubmit(options);
			}
		</script>
	</head>
	<body>
		<div id="updateDiv" class="easyui-panel" title="" data-options="fit:true" style="padding:20px">
			<form action="" id="updateQuestion" name="updateQuestion" method="post" >
				<table>
					<input value="${question.id}" id="id" name="id" hidden="false"/>
					<tr>
						<td align="right" width="19%">
							题目：
						</td>
						<td align="left" width="25%" colspan="4">
							<input class="easyui-textbox" value="${question.quesubject}" id="quesubject" name="quesubject" 
								data-options="validType:'length[0,20]',required:true" style="width:475px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							答案1 ：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${question.queone}" id="queone" name="queone" 
								data-options="validType:'length[0,20]',required:true" style="width:180px;"/>
						</td>
						<td align="right">
							答案2：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${question.quetwo}" id="quetwo" name="quetwo" 
								data-options="validType:'length[0,20]',required:true" style="width:180px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							答案3：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${question.quethree}" id="quethree" name="quethree" 
								data-options="validType:'length[0,20]',required:true" style="width:180px;"/>
						</td>
						<td align="right">
							答案4：
						</td>
						<td align="left" width="25%">
							<input class="easyui-numberbox" value="${question.quefour}" id="quefour" name="quefour" 
								data-options="validType:'length[0,20]',required:true" style="width:180px;"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="19%">
							正确答案：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${question.rightque}" id="rightque" name="rightque" 
								data-options="validType:'length[0,20]',required:true" style="width:180px;"/>
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

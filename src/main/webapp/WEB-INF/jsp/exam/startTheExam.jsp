<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>考试修改页面</title>
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
				$("#startExam").form('reset');
			}
			
			//保存 
			function doSave() {
				if (!$("#startExam").form('validate')) {
					return;
				}
				
				var options = {
					type : "post",
					url : "examController_startExam",
					dataType : "json",
					success : function(result) {
						$.messager.progress("close");
						// '0000' 修改成功
						if (result._ResponseCode_ == '0000') {
							$.messager
									.show({
										title : '提示',
										msg : '考试提交成功！',
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
					msg: "正在提交考试,请稍后..."
				});
				$("#startExam").ajaxSubmit(options);
			}
		</script>
	</head>
	<body>
		<div id="updateDiv" class="easyui-panel" title="" data-options="fit:true" style="padding:20px">
			<form action="" id="startExam" name="startExam" method="post" >
				<table>
					<input value="${exam.id}" id="id" name="id" hidden="false"/>
					
					
				</table>
				<div style="text-align: center; padding: 5px">
					<div class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="doSave()">提交</div>
					&nbsp;&nbsp;
					<div class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="doReset();">重置</div>
				</div>
			</form>
		</div>
	</body>
</html>

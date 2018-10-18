<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/icon.css">
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="${basePath}scripts/easyui/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript">
			$(function() {
				//默认是解析页面中所有定义为easyui组件的节点，解决弹出子页面不能使用jquery easyui方法
				$.parser.parse("#updatePwdDiv");
			});
			//关闭窗口
			function closeWin() {	
				$('#win').window('close');
			}
			
			//修改
			function doOperate(){
				//alert("=====");
				if (!$("#updatePwdForm").form('validate')) {
					return;
				}
				//alert("=====");
				var password = $("#password").val();
				var newpassword = $("#newpassword").val();
				var passwordagain = $("#passwordagain").val();
				//alert(newpassword+"==="+passwordagain);
				if(passwordagain != newpassword){
					$.messager.alert('错误','两次新密码不一致请重新输入！','error');
					return;
				}
				
				if(password == newpassword){
					$.messager.alert('错误','新密码不能与旧密码相同！','error');
					return;
				}
				
				var options = {
					type: "post",
					url: "userController_savePassword",
					dataType: "json",
					success: function(result){
						// 1 审批成功, -1审批失败
						if (result._ResponseCode_ == '0000') {
							//$('#win').window('close');
							$.messager.show({
								title: '提示',
								msg: '修改密码成功！',
								style:{
			             		    left: '',
									right: 0,
									top: document.body.scrollTop + document.documentElement.scrollTop,
									bottom: ''
								}
							});
						$('#win').window('close'); 
						} else if (result._ResponseCode_ == '1002') {
							$.messager.alert('错误','原密码输入有误，请联系管理员！','error');
						} else if (result._ResponseCode_ == '1004') {
							$.messager.alert('错误','密码修改失败，请联系管理员！','error');
						}
					},
					error: function(msg) {
						$.messager.alert('错误','密码修改失败，请联系管理员！','error');
					}
				};
				$("#updatePwdForm").ajaxSubmit(options);
			}
		</script>
	</head>
	<body>
		<div id="updatePwdDiv" class="easyui-panel" title="" data-options="fit:true" style="border:0px;">
			<div style="padding:10px 20px 10px 20px;">
				<form action="userController_savePassword" id="updatePwdForm" name="updatePwdForm" method="post" >
					<table>
						<td align="right" style="width:130px;display:none;">用户代码：</td>
						<td align="left" style="display:none;">
							<input type="text" name="usercode" class="easyui-textbox" readonly="readonly"
								id="usercode1" value="${userinfo.usercode}" data-options="required:true" style="width:180px;"></input>
						</td>
						<tr align="center">
							<td align="right" width="120">原密码：</td>
							<td align="left">
								<input type="password" id="password" name="password" class="easyui-textbox" data-options="required:true" style="width:180px;" />
							</td>
						</tr>
						<tr>
							<td align="right">新密码：</td>
							<td align="left">
								<input type="password" id="newpassword" name="newpassword" class="easyui-textbox" data-options="required:true" style="width:180px;" />
							</td>
						</tr>
						<tr>
							<td align="right">确认密码：</td>
							<td align="left">
								<input type="password" id="passwordagain" name="passwordagain" class="easyui-textbox" data-options="required:true" style="width:180px;" />
							</td>
						</tr>
					</table>
					<div style="text-align: center; padding: 5px">
						<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-save'" onclick="doOperate()">保存</a>
						<a class="easyui-linkbutton" href="javascript:void(0);" data-options="iconCls:'icon-clear'" onclick="closeWin()">取消 </a>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>

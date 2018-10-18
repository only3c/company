<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户新增页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<script type="text/javascript" src="${basePath}scripts/jquery/jquery.form.js"></script>
<%-- 	<script type="text/javascript" src="${basePath}scripts/common/easyui-ext/validateEx.js"></script> --%>
	<script type="text/javascript">
			$(function() {
				//默认是解析页面中所有定义为easyui组件的节点，解决弹出子页面不能使用jquery easyui方法
				$.parser.parse("#addDiv");
			});
			//重置表单
			function doReset() {
				$("#addUser").form('reset');
			}

			//保存
			function doSave() {
				if (!$("#addUser").form('validate')) {
					return;
				}
				var str = "";
	            $("input[name='userrole']:checkbox").each(function(){ 
	                if ($(this).attr("checked")) {
	                    str += $(this).val()+",";
	                }
	            });
	            
	            if (str == "") {
	            	$.messager.alert('提示','请对该用户授权角色！','warning');
					return;
	            }
	            
				var options = {
					type: "post",
					url: "userController_createUser?userroles="+str,
					dataType: "json",
					success: function(result){
						// 0 保存成功, 1存在的用户名, 2 无效的用户对象
						$.messager.progress("close");
						if (result._ResponseCode_ == '0000') {
							$.messager.show({
								title: '提示',
								msg: '用户信息添加成功！',
								style:{
			             		    left: '',
									right: 0,
									top: document.body.scrollTop + document.documentElement.scrollTop,
									bottom: ''
								}
							});
							
							$('#winuser').window('close'); //关闭窗口
							doQuery(); //刷新列表
						} else if (result.message == 1) {
							$.messager.alert('提示','该用户代码已经存在！','warning');
						} else if (result.message == 2) {
							$.messager.alert('错误','无效的用户信息，请联系管理员！','error');
						}else if (result._ResponseCode_ == '9999') {
							$.messager.alert('错误','系统错误，请联系管理员！','error');
						}
						
						return false;
					}
				};
				//进度框
				$.messager.progress({
					title: "请稍后",
					msg: "正在保存用户信息,请稍后..."
				});
				$("#addUser").ajaxSubmit(options);
			}
			

			function checkusercode(){
				//alert("ok");
				var patt1=/^[a-zA-Z0-9]{5,60}$/;
				var code = $('#_usercode').textbox('getValue');
				if(!patt1.test(code)){
					$("#_usercode").textbox('setValue','');
					$.messager.alert('提示','用户名为不能小于5位的字母和数值组合！','warning');
				}else {
					$.ajax({
						type: "POST",
						url: "userController_usercodeCheck",
						data: {usercode : code},
						dataType: "json",
						success: function(result) {
							// false usercode已存在, true注销失败
							if (result == false) {
								$("#_usercode").textbox('setValue','');
								$.messager.alert('提示','用户已经存在,请重新输入！','warning');
							}
						},
						error: function(msg) {
							$.messager.alert('错误','用户信息注销出错，请联系管理员！','error');
						}
					});
				}
			}
		</script>
	</head>
	<body >
		<div id="addDiv" class="easyui-panel" title="" data-options="" style="height:310px;padding:20px;border:0px;">
			<form action="userController_createUser" id="addUser" name="addUser" method="post" >
				<table>
					<tr>
						<td align="right" style="width:130px;">
							用户名:
						</td>
						<td align="left">
							<input type="text" name="usercode" class="easyui-textbox" 
								id="_usercode" value="" data-options="events:{blur:checkusercode},required:true,validType:'length[0,60]'" style="width:180px;"></input>
						</td>
						<td align="right" style="width:130px;">
							昵称:
						</td>
						<td align="left">
							<input type="text" name="username" class="easyui-textbox"
								id="username" value="" data-options="required:true,validType:'length[0,60]'" style="width:180px;"></input>
						</td>
					</tr>
					<tr>
						<td align="right" style="width:130px;">
							联系电话:
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="telno"
								id="telno" value=""  style="width:180px;" data-options="validType:'phoneRex'"></input>
						</td>
					
					
						<td align="right">
							证件类型:
						</td>
						<td>
							<input class="easyui-combobox" type="text" id="certtype" name="certtype" style="width:180px;"
							data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
							url:'dictionaryController_getDictionaryForComboBox?datatype=certtype&required=false'"></input>
						</td>
					</tr>
					<tr>
						<td align="right">证件号码:</td>
						<td align="left">
							<input class="easyui-textbox" type="text" maxlength="40" id="certno" name="certno" data-options="required:false,validType:'certnoRex'" style="width:180px;"></input>
						</td>
					
					
						<td align="right">
							手机号码:
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="mobilephone" style="width:180px;"
								id="mobilephone" value="${userinfo.mobilephone}"  data-options="required:false,validType:'phoneRex'"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							电子邮箱:
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="email" style="width:180px;"
								id="email" value="${userinfo.email}"  data-options="required:false,validType:'email'"></input>
						</td>
					
					
						<td align="right">
							QQ:
						</td>
						<td align="left">
							<input class="easyui-numberbox" type="text" name="qq" style="width:180px;"
								id="qq" value="${userinfo.qq}"  data-options="required:false,validType:'length[0,20]'"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							邮政编码:
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="postcode" style="width:180px;"
								id="postcode" value="${userinfo.postcode}"  data-options="required:false,validType:'ZipFormat'"></input>
						</td>
						<td align="right">
							所属部门:
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="instname" maxlength="200" readonly="readonly"
								id="instname" value="${userinfo.instname}" style="width:180px;"></input>
							<input  type="hidden" name="instcode" maxlength="200" readonly="readonly"
								id="instcode" value="${userinfo.instcode}" style="width:180px;"></input>	
						</td>
					</tr>
					<tr>
						<td align="right">
							联系地址:
						</td>
						<td align="left" colspan="4">
							<input id="address" name="address" class="easyui-textbox" type="text" data-options="validType:'length[0,200]'" style="width:500px;height:25px">
						</td>
					</tr>
				</table>
				<fieldset class="from_fieldset" >
					<legend class="from_legend">用户角色授权</legend>
					<table align="center" style="width:100%;border:0;">
						<tr>
							<c:forEach items="${role}" var="role" varStatus="status">
								<td width="20%">
									<input type="checkbox" id="${role.rolecode}" name="userrole" value="${role.rolecode}" /><label>${role.rolename}</label>
								</td>
								<c:if test="${status.index != 0 && status.index % 4 == 0}">
									</tr>
									<tr>
								</c:if>
							</c:forEach>
						</tr>
					</table>
				</fieldset>
				<div style="text-align: center; padding: 5px">
					<div class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="doSave()">保存</div>
					&nbsp;&nbsp;
					<div class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="doReset();">重置</div>
				</div>
			</form>
		</div>
	</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户修改页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<script type="text/javascript" src="${basePath}scripts/jquery/jquery.form.js"></script>
<%-- 	<script type="text/javascript" src="${basePath}scripts/common/easyui-ext/validateEx.js"></script> --%>
		<script type="text/javascript">
			$(function() {
				//默认是解析页面中所有定义为easyui组件的节点，解决弹出子页面不能使用jquery easyui方法
				$.parser.parse("#updateDiv");
			});
            
			//保存
			function doSave() {
				if (!$("#addUser").form('validate')) {
					return;
				}
				var str="";
				$("input[name='userrole']:checkbox").each(function(){ 
					if($(this).attr("checked")){
						str += $(this).val()+",";
					}
				});
				
	            if (str == "") {
	            	$.messager.alert('提示','请对该用户授权角色！','warning');
					return;
	            }
				
				var options = {
					type: "post",
					url: "userController_updateUser?userroles="+str,
					dataType: "json",
					success: function(result){
						// 0000 保存成功, 1存在的用户名, 2 无效的用户对象
						if (result._ResponseCode_ == '0000') {
							$.messager.show({
								title: '提示',
								msg: '用户信息修改成功！',
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
						}
						return false;
					}
				};
				$("#addUser").ajaxSubmit(options);
			}
			
			//重置表单
			function doReset() {
				$("#addUser").form('reset');
			}
			
		</script>
	</head>
	<body >
		<div id="updateDiv" class="easyui-panel" title="" data-options="" style="height:atuo;padding:20px;border:0px;">
			<form action="userController_updateUser" id="addUser" name="addUser" method="post" >
				<table>
					<tr>
						<td align="right" style="width:130px;">
							用户名:
						</td>
						<td align="left">
							<input type="text" name="usercode" class="easyui-textbox" readonly="readonly"
								id="usercode1" value="${userinfo.usercode}" data-options="required:true" style="width:180px;"></input>
						</td>
						<td align="right" style="width:130px;">
							昵称:
						</td>
						<td align="left">
							<input type="text" name="username" class="easyui-textbox" 
								id="username" value="${userinfo.username}" data-options="required:true,validType:'length[0,60]'" style="width:180px;"></input>
						</td>
					</tr>
					<tr>
						<td align="right" style="width:130px;">
							用户状态:
						</td>
						<td align="left">
							<input class="easyui-combobox" type="text" id="userflag" name="userflag" style="width:180px;" readonly="readonly"
							data-options="required:false,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
							url:'dictionaryController_getDictionaryForComboBox?datatype=logoffflag&required=false',value:'${userinfo.userflag}',data:[{value:'',label:'请选择……'}]"></input>
						</td>
						<td align="right" style="width:180px;">
							联系电话:
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="telno"
								id="telno" value="${userinfo.telno}"  style="width:180px;" data-options="validType:'phoneRex'"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							证件类型:
						</td>
						<td>
							<input class="easyui-combobox" type="text" id="certtype" name="certtype" style="width:180px;"
								data-options="required:false,valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
								url:'dictionaryController_getDictionaryForComboBox?datatype=certtype&required=false',value:'${userinfo.certtype}'"></input>
						</td>
						<td align="right">证件号码:</td>
						<td align="left">
							<input class="easyui-textbox" type="text" maxlength="40" id="certno" name="certno" value="${userinfo.certno }" data-options="required:false,validType:'certnoRex'" style="width:180px;"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							手机号码:
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="mobilephone" style="width:180px;"
								id="mobilephone" value="${userinfo.mobilephone}" data-options="required:false,validType:'phoneRex'"></input>
						</td>
						<td align="right">
							电子邮箱:
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="email" style="width:180px;"
								id="email" value="${userinfo.email}" data-options="required:false,validType:'email'"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							QQ:
						</td>
						<td align="left">
							<input class="easyui-numberbox" type="text" name="qq" style="width:180px;"
								id="qq" value="${userinfo.qq}" data-options="required:false,validType:'length[0,20]'"></input>
						</td>
						<td align="right">
							邮政编码:
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="postcode" style="width:180px;"
								id="postcode" value="${userinfo.postcode}" data-options="required:false,validType:'ZipFormat'"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							所属部门:
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="instname" maxlength="200" readonly="readonly"
								id="instname" value="${userinfo.inst.instname}" style="width:180px;"></input>
							<input  type="hidden" name="instcode" maxlength="200" readonly="readonly"
								id="instcode" value="${userinfo.inst.instcode}" style="width:180px;"></input>	
						</td>
					</tr>
					<tr>
						<td align="right">
							联系地址:
						</td>
						<td align="left" colspan="4">
							<input id="address" name="address" value="${userinfo.address}" class="easyui-textbox" type="text" data-options="validType:'length[0,200]'" style="width:500px;height:25px">
						</td>
					</tr>
				</table>
				<fieldset class="from_fieldset">
					<legend class="from_legend">用户角色授权</legend>
					<table align="center" style="width:100%;border:0;">
						<tr>
							<c:forEach items="${role}" var="role" varStatus="status">
								<td width="20%">
									<c:set var="flag" value="false" />
									<c:forEach items="${userinfo.roles}" var="userrole">
										<c:if test="${userrole.rolecode==role.rolecode}">
											<c:set var="flag" value="true" />
										</c:if>
									</c:forEach>
									<c:choose>
										<c:when test="${flag == true}">
											<input type="checkbox" id="${role.rolecode}" name="userrole" checked="checked" value="${role.rolecode}" /><label>${role.rolename}</label>
										</c:when>
										<c:otherwise>
											<input type="checkbox" id="${role.rolecode}" name="userrole" value="${role.rolecode}" /><label>${role.rolename}</label>
										</c:otherwise>
									</c:choose>
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

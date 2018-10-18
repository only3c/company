<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<style>
			/* 表格 样式 */
			.table_tb {
				width: 100%;
				border: none;
			} 
			.table_tb tr {
				height: 30px;
			}
			
			.table_tb td {
				padding: 3px;
			}
		</style>
		<script type="text/javascript">
			$(function() {
				//默认是解析页面中所有定义为easyui组件的节点，解决弹出子页面不能使用jquery easyui方法
				$.parser.parse("#viewDiv");
			});
		</script>
	</head>
	<body>
		<div id="viewDiv" class="easyui-panel" title="" data-options="fit:true" style="border:0px;">
			<div style="padding:20px;">
				<table class="table_tb">
					<tr>
						<td align="right" width="15%">
							用户名：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${userinfo.usercode}" data-options="readonly:true" style="width:200px;"/>
						</td>
						<td align="right" width="15%">
							昵称：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${userinfo.username}" data-options="readonly:true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							证件类型：
						</td>
						<td align="left">	
							<input class="easyui-textbox" value="${userinfo.certtypename}" data-options="readonly:true" style="width:200px;"/>
						</td>
						<td align="right">
							证件号码：
						</td>
						<td align="left">	
							<input class="easyui-textbox" value="${userinfo.certno}" data-options="readonly:true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							联系电话：
						</td>
						<td align="left">
							<input class="easyui-textbox" value="${userinfo.telno}" data-options="readonly:true" style="width:200px;"/>
						</td>
						<td align="right">
							手机号码：
						</td>
						<td align="left">	
							<input class="easyui-textbox" value="${userinfo.mobilephone}" data-options="readonly:true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							电子邮箱：
						</td>
						<td align="left">
							<input class="easyui-textbox" value="${userinfo.email}" data-options="readonly:true" style="width:200px;"/>
						</td>
						<td align="right">
							QQ：
						</td>
						<td align="left">
							<input class="easyui-textbox" value="${userinfo.qq}" data-options="readonly:true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							邮政编码：
						</td>
						<td align="left">
							<input class="easyui-textbox" value="${userinfo.postcode}" data-options="readonly:true" style="width:200px;"/>
						</td>
						<td align="right">
							所属部门：
						</td>
						<td align="left">
							<input class="easyui-textbox" value="${userinfo.inst.instname}" data-options="readonly:true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							用户状态：
						</td>
						<td align="left">
							<input class="easyui-textbox" value="${userinfo.userflagname}" data-options="readonly:true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							联系地址:
						</td>
						<td align="left" colspan="4">
							<input  class="easyui-textbox" value="${userinfo.address}" data-options="readonly:true" style="width:573px;">
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>

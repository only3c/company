<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
						<td align="right" width="19%">
							部门代码：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${instinfo.instcode}" data-options="readonly:true" style="width:200px;"/>
						</td>
						<td align="right">
							部门名称：
						</td>
						<td align="left">	
							<input class="easyui-textbox" value="${instinfo.instname}" data-options="readonly:true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							部门简称：
						</td>
						<td align="left">	
							<input class="easyui-textbox" value="${instinfo.instsname}" data-options="readonly:true" style="width:200px;"/>
						</td>
						<td align="right" width="17%">
							上级部门：
						</td>
						<td align="left" width="25%">
							<input class="easyui-textbox" value="${instinfo.pinstinfo.instname}" data-options="readonly:true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							部门类别：
						</td>
						<td align="left">
							<input class="easyui-combobox" type="text" style="width:200px;" readonly="readonly"
								data-options="valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
								url:'dictionaryController_getDictionaryForComboBox?datatype=instclass',value:'${instinfo.instclass}'">
						</td>
						<td align="right">
							部门联系电话：
						</td>
						<td align="left">
							<input class="easyui-textbox" value="${instinfo.telno}" data-options="readonly:true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							所属省份：
						</td>
						<td align="left">
							<input class="easyui-combobox" type="text" style="width:200px;" readonly="readonly"
								data-options="valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
								url:'dictionaryController_getDictionaryForComboBox?datatype=province',value:'${instinfo.province}'">
						</td>
						<td align="right">
							所属区市：
						</td>
						<td align="left">
							<input class="easyui-combobox" type="text" style="width:200px;" readonly="readonly"
								data-options="valueField:'datacode',textField:'dataname',panelHeight:120,editable:false,
								url:'dictionaryController_getDictionaryForComboBox?datatype=city&parentcode=${instinfo.province}',value:'${instinfo.city}'">
						</td>
					</tr>
					<tr>
						<td align="right">
							部门地址:
						</td>
						<td align="left" colspan="4">
							<input class="easyui-textbox" value="${instinfo.address}" data-options="readonly:true" style="width:566px;">
						</td>
					</tr>
					<tr>
						<td align="right">
							负责人：
						</td>
						<td align="left">
							<input class="easyui-textbox" value="${instinfo.contactname}" data-options="readonly:true" style="width:200px;"/>
						</td>
						<td align="right">
							联系电话：
						</td>
						<td align="left">	
							<input class="easyui-textbox" value="${instinfo.contacttelno}" data-options="readonly:true" style="width:200px;"/>
						</td>
					</tr>
<%-- 					<c:if test="${instinfo.instclass == '2'}"> --%>

<%-- 					</c:if> --%>
				</table>
			</div>
		</div>
	</body>
</html>

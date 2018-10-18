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
				$("#addInstInfo").form('reset');
			}
			
			//保存
			function doSave() {
				if (!$("#addInstInfo").form('validate')) {
					return;
				}
				
				var options = {
					type : "post",
					url : "instInfoController_saveInstInfo",
					dataType : "json",
					success : function(result) {
						$.messager.progress("close");
						// 1 保存成功, 3, 2 无效的用户对象
						if (result._ResponseCode_ == '0000') {
							$.messager.show({
								title : '提示',
								msg : '部门信息保存成功！',
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
							initTree();
// 							//刷新父节点
// 							var node = $('#tree').tree('find', '${instinfo.instcode}');
// 							alert(node.pinstinfo);
// 							if ($('#tree').tree('isLeaf', node.target)) {
// 								var parentcode = node.attributes.pinstinfo.instcode;
// 								var parentNode = $('#tree').tree('find', parentcode);
// 								if (parentNode) {
// 									$('#tree').tree('reload', parentNode.target);
// 								} else {
// 									$('#tree').tree('reload');
// 								}
// 							} else {
// 								$('#tree').tree('reload', node.target);
// 							}
						} else {
							$.messager.alert(result._ResponseMessage_);
						} 
						return false;
					}
				};
				//进度框
				$.messager.progress({
					title: "请稍后",
					msg: "正在保存部门信息,请稍后..."
				});
				$("#addInstInfo").ajaxSubmit(options);
			}

			$("#province").combobox({
				url : 'dictionaryController_getDictionaryForComboBox?datatype=province&required=true',
				valueField : 'datacode',
				textField : 'dataname',
				panelHeight : '120',
				editable : false,
				required : true,
				onSelect : function() {
					var id = $("#province").combobox('getValue');
					var url = 'dictionaryController_getDictionaryForComboBox?datatype=city&parentcode=' + id;
					$('#city').combobox('reload', url);
				}
			});

		</script>
	</head>
	<body >
		<div id="addDiv" class="easyui-panel" title="" data-options="fit:true" style="padding:20px;">
			<form action="instInfoController_saveInstInfo" id="addInstInfo" name="addInstInfo" method="post" >
				<table>
					<tr>
						<td align="right" style="width:140px;">
							部门名称：
						</td>
						<td align="left">
							<input type="text" name="instname" class="easyui-textbox"
								id="instname"  data-options="required:true,validType:'length[0,200]'" style="width:180px;"></input>
						</td>
						<td align="right" style="width:130px;">
							部门简称：
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="instsname" data-options="validType:'length[0,100]'"
								id="instsname"   style="width:180px;"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							上级部门：
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="pinstname" style="width:180px;"
								id="pinstname" value="${instinfo.instname}" readonly="readonly" data-options="required:false"></input>
						</td>
						<td align="right">
							上级部门代码：
						</td>
						<td align="left">
							<input  type="text" class="easyui-textbox" name="pinstcode" style="width:180px;"
								id="pinstcode" value="${instinfo.instcode}" readonly="readonly" ></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							部门类别：
						</td>
						<c:set var="flags" value="true" />
						<c:forEach items="${sessionScope.session_user.roles}" var="role">
							<c:if test="${flags == true && role.rolecode == '00000000'}">
								<c:set var="flags" value="false"/>
							</c:if>
						</c:forEach>
						<c:if test="${flags == false}"><!-- 公共管理用户 -->
							<td>
								<input class="easyui-combobox" type="text" id="instclass" name="instclass" style="width:180px;"  
								data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:75,editable:false,
								url:'dictionaryController_getDictionaryForComboBox?datatype=instclass&required=true',
								onSelect: function(rec){    
							            var instclass = rec.datacode;
							    		if (instclass == '2') {
							    			$('#finst').show();
											$('#auth').show();
										} else {
											$('#finst').hide();
											$('#auth').hide();
										} 
										if (instclass == '1') {
											$('#inst').show();
										} else {
											$('#inst').hide();
										}
							        }"></input>
							</td>
						</c:if>
						<c:if test="${flags == true}">
							<td>
								<input class="easyui-combobox" type="text" id="instclass" name="instclass" style="width:180px;" readonly="readonly"
								data-options="required:true,valueField:'datacode',textField:'dataname',panelHeight:75,editable:false,
								url:'dictionaryController_getDictionaryForComboBox?datatype=instclass&required=true',value:'1',
								onSelect: function(rec){    
							    		var instclass = rec.datacode;
							    		if (instclass == '2') {
							    			$('#finst').show();
											$('#auth').show();
										} else {
											$('#finst').hide();
											$('#auth').hide();
										} 
										if (instclass == '1') {
											$('#inst').show();
										} else {
											$('#inst').hide();
										}
							        }"></input>
							</td>
						</c:if>	
						<td align="right">
							部门电话：
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="telno" maxlength="20" data-options="validType:'phoneRex'"
								id="telno"  style="width:180px;"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							联系人：
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="contactname" data-options="validType:'length[0,60]'"
								id="contactname"  style="width:180px;"></input>
						</td>
						<td align="right">
							联系电话：
						</td>
						<td align="left">
							<input class="easyui-textbox" type="text" name="contacttelno" data-options="validType:'phoneRex'"
								id="contacttelno" value="" style="width:180px;"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							所属省份：
						</td>
						<td align="left">
								<input id="province" name="province" style="width:180px;" style="width:180px;" >
						</td>
						<td align="right">
							所属区市：
						</td>
						<td>
							<input class="easyui-combobox" type="text" name="city" id="city" style="width:180px;"
								data-options="editable:false,valueField:'datacode',textField:'dataname',required:true"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
							部门地址：
						</td>
						<td align="left" colspan="4">
							<input id="serviceurl" name="address" class="easyui-textbox" type="text" data-options="validType:'length[0,200]'" style="width:500px;height:25px">
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

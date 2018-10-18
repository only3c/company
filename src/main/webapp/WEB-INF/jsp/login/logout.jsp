<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>连队管理系统综合平台</title>
		<link rel="icon" href="images/logo.png" type="image/x-icon"/>
		<link rel="shortcut icon" href="images/logo.png" type="image/x-icon"/>
		<link rel="stylesheet" type="text/css" href="styles/logout.css" />
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery-1.11.3.min.js"></script>
		<script language="javascript">
			$(document).ready(function() {
				if (window.top.location.href != window.location.href) {
					window.top.location.href = window.location.href;
				}
			});
			
			function closewin(){
				var userAgent = navigator.userAgent;
				if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1) {
				   window.location.href="about:blank";
				} else {
				   window.opener = null;
				   window.open("", "_self");
				   window.close();
				}
			};
		</script>
	</head>

	<body onload="">
		<div class="main-area">
			<div class="clearfix input-line">
				<img class="logo" src="images/login/logout_logo.png"/>
			</div>
			<hr style="border-top:1px solid #1870af;"/>
			<div class="text-area">
				<table class="tbl-msg">
					<tr>
						<td class="mstitle">
							<c:out value="${title}"></c:out>
						</td>
					</tr>
					<tr>
						<td class="desc">
							<c:out value="${message}"></c:out>
						</td>
					</tr>
					<tr>
						<td class="action">
							<p>您可以进行如下操作:</p>
							<p class="act">
								<span class="center">
									<a class="xlink" href="login">返回登录页</a>
								</span>
								<span class="center">
									<a class="xlink" href="#" onclick="closewin()">关闭窗口</a>
								</span>
							</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>

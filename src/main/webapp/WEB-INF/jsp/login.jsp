<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>连队日常管理系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" type="text/css" href="/styles/login.css" />
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.cookie.js"></script>
		<script type="text/javascript">
			$(document).ready(function(e) {
				$("#usercode").focus(function(e) {
					$(this).select();
					$(this).addClass("highlight");
					$("#warningmsg").html("");
				}).blur(function(e) {
					$(this).removeClass("highlight");
				});
			
				$("#password").focus(function(e) {
					$(this).select();
					$(this).addClass("highlight");
					$("#warningmsg").html("");
				}).blur(function(e) {
					$(this).removeClass("highlight");
				});
				
				$("#loginform").bind("submit", function(e){
					var _uc = $.trim($("#usercode").val()),
						_pw = $("#password").val(),
						_cc = $("#checkcode").val();
					
					if (_uc == "" && _pw == "" && _cc == "") {
						$("#warningmsg").html("请输入用户名、密码及验证码！");
						return false;
					} else if (_uc == "") {
						$("#warningmsg").html("请输入用户名！");
						return false;
					} else if (_pw == "") {
						$("#warningmsg").html("请输入密码！");
						return false;
					} else if (_cc == "") {
						$("#warningmsg").html("请输入验证码！");
						return false;
					} else if (_cc.length != 4) {
						$("#warningmsg").html("验证码必须为4位！");
						return false;
					}
					
					$(".loading-bar").show();
					$("#loginform").ajaxSubmit({
						"dataType" : "json",
						"success" : function(result){
							// 0 正常登陆, 1不存在的用户名, 2密码错误
							if (result._ResponseCode_ == '0000') {
								//是否记住用户名
// 								if ($("#chkrmb").attr("checked") == "checked") {
								if ($("input:checkbox:checked").val() == "true") {
									$.cookie("chkrmb", "true", { expires: 30, path: '/' }); 
									$.cookie("usercode", $("#usercode").val(), { expires: 30, path: '/' });
								} else {        // 删除 cookie
									$.cookie("chkrmb", null, { expires: -1, path: '/' });
									$.cookie("usercode", null, { expires: -1, path: '/' });
								}
								location.href = "login_login";
							} else {
								$("#warningmsg").html(result._ResponseMessage_);
								$(".loading-bar").hide();
								changeCheckNum($("#imgchkcode")[0]);
							}
							
							return false;
						}
					});
					
					return false;
				});
			});
			
			//读取cookie
			$(function() {
				if ($.cookie("usercode") != null) {
					 $("#chkrmb").attr("checked", true);
					 $("#usercode").val($.cookie("usercode"));
				}
			});
			
			//刷新验证码
			function changeCheckNum(obj) {
				var timenow = new Date().getTime();
				obj.src = "checkNumber?d=" + timenow;
			}
			
		</script>
	</head>

	<body>
		<form id="loginform" method="post" action="checkUser">
			<div class="main" style="">
				<div class="title"></div>
				<div class="loginarea">
					<div class="aera-mid">
						<p class="clearfix input-line">
							<label class="text-caption">用户名：</label>
							<input type="text" class="text-input" id="usercode" name="usercode" value="" />
						</p>
						<p class="clearfix input-line">
							<label class="text-caption">密&nbsp;&nbsp;&nbsp;码：</label>
							<input type="password" class="text-input" id="password" name="password" value="" />
						</p>
						<p class="clearfix input-line">
							<label class="text-caption">验证码：</label>
							<input type="text" class="text-chknum" id="checkcode" name="checkcode" maxlength="4" value="1111"/>
							<img id="imgchkcode" src="checkNumber" onclick="changeCheckNum(this);" title="点击图片刷新验证码"/>
						</p>
						<p id="warningmsg"></p>
						<p class="checkbox-all">
							<input type="checkbox" id="chkrmb" name="chkrmb" value="true"/>记住用户名
						</p>
						<p class="login-p">
							<input type="submit" class="login-but" id="loginsub" value="登&nbsp;&nbsp;&nbsp;&nbsp;陆" />
						</p>
					</div>
					<div class="loading-bar">
						<div class="loading-bg"></div>
						<div class="loading-icon"></div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>

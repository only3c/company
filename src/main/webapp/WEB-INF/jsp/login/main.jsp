<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>连队管理系统综合平台</title>
		<link rel="icon" href="${basePath}images/logo.png" type="image/x-icon"/>
		<link rel="shortcut icon" href="${basePath}images/logo.png" type="image/x-icon"/>
		<link rel="stylesheet" type="text/css" href="${basePath}styles/menu_red.css" />
		<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/bootstrap/easyui.css" />
		<link rel="stylesheet" type="text/css" href="${basePath}scripts/easyui/themes/icon.css" />
		<link rel="stylesheet" type="text/css" href="${basePath}styles/main.css" />
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.metadata.js"></script>
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.hoverIntent.js"></script>
		<script type="text/javascript" src="${basePath}scripts/jquery/jquery.cookie.js"></script>
		<script type="text/javascript" src="${basePath}scripts/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${basePath}scripts/mbMenu.js"></script>
		<script type="text/javascript" src="${basePath}scripts/main.js"></script>
		<script type="text/javascript" src="${basePath}scripts/chose_theme.js"></script>
		<script type="text/javascript">
// 			var a_theme = cookie_themeName;
// 			if(a_theme==null){a_theme="Theme"};
// 			alert(a_theme);//*[@id="atheme"]/span/span[1]
// 			$('#atheme').html(a_theme);
// 			$('#atheme').textbox('setValue',a_theme);
// 			$("#atheme").textbox('setText', a_theme);

			function downloadManual(){};
		</script>
	</head>
	
	<body onresize="resizeIframes();">
		<div class="header">
			<div class="logo"></div>
			<div class="button">
				<ul id="fastbutton">
					<li>
						<a href="javascript:void(0);" onclick="javascript:openMainTab();">
							<img src="images/main/home.png" /><span>主页</span>
						</a>
					</li>
					<li>
						<a href="#" onclick="changePassword('${sessionScope.session_user.usercode}')">
							<img src="images/main/lock.png" /><span>修改密码</span>
						</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="javascript:downloadManual();">
							<img src="images/main/help.png" /><span>帮助</span>
						</a>
					</li>
					<li>
						<a href="login_logout" >
							<img src="images/main/exit.png" /><span>退出</span>
						</a>
					</li>
				</ul>
			</div>
			<div class="user-inf">
				<span>登陆账号：${sessionScope.session_user.usercode}</span>
				<span>用户名： ${sessionScope.session_user.username}</span>
				<span>部门：${sessionScope.session_user.inst.instname}</span>
			</div>
		</div>
		<div style="position: absolute;left: 0px;top:0px;">
            <div class="cb-theme" style="padding:0px;font-size: 20px;font-weight: 900;">
             	<a id="atheme" href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'icon-tip'">Theme</a>
            </div>
        </div>
		<div id="layout_north_pfMenu" style=" display: none;">
        	<div onclick="changeTheme('default');">default</div>
        	<div onclick="changeTheme('black');">black</div>
        	<div onclick="changeTheme('bootstrap');">bootstrap</div>
        	<div onclick="changeTheme('gray');">gray</div>
<!--         	<div onclick="changeTheme('metro');">metro</div> -->
<!--         	<div onclick="changeTheme('metro-blue');">metro-blue</div> -->
<!--         	<div onclick="changeTheme('metro-gray');">metro-gray</div> -->
<!--         	<div onclick="changeTheme('metro-green');">metro-green</div> -->
<!--         	<div onclick="changeTheme('metro-red');">metro-red</div> -->
<!--         	<div onclick="changeTheme('metro-orange');">metro-orange</div> -->
<!--         	<div onclick="changeTheme('ui-cupertino');">ui-cupertino</div> -->
<!--         	<div onclick="changeTheme('ui-dark-hive');">ui-dark-hive</div> -->
<!--         	<div onclick="changeTheme('ui-pepper-grinder');">ui-pepper-grinder</div> -->
<!--         	<div onclick="changeTheme('ui-sunny');">ui-sunny</div> -->
    	</div>
		<div class="mainmenu">
			<div id="menubar">
	   			<div id="arr_l"></div>
	   			<div id="arr_r"></div>
	    		<div id="menu_a">
	    		<div id='mainmenu'>
	    			<table id='xmenu' style='position: relative;' class='rootVoices'  cellspacing='0' cellpadding='0' border='0'>
	    				<tr>
	    				<c:set scope="page" value="${''}" var="lastpc"></c:set>
	    				<c:forEach var="menu" items="${menuList}" varStatus="status">
	   						<c:choose>
   							<c:when test="${menu.pmenuinfo.menucode eq '0'}">
   								<c:choose>
   								<c:when test="${status.index != 0}">
   									<td style="width:10px;" class="splitBar"></td>
   								</c:when>
   								</c:choose>
   								<td style='text-align:center;width:135px;min-width:100px;' class="rootVoice {menu: 'menu_${menu.menucode}'}" >
   									${menu.menuname}
   								</td>
   							</c:when>
   							<c:otherwise>
   							
	   							<c:choose>
   								<c:when test="${menu.pmenuinfo.menucode ne lastpc}">
   									<c:choose>
   										<c:when test="${'' eq lastpc}">
   											<div id='menu_${menu.pmenuinfo.menucode}' class='menu'>
   										</c:when>
   										<c:otherwise>
   											</div><div id='menu_${menu.pmenuinfo.menucode}' class='menu'>
   										</c:otherwise>
   									</c:choose>
   								</c:when>
	   							</c:choose>
								
   								<%-- 0为非叶子菜单,1为叶菜单 --%>
   								<c:choose>
   								<c:when test="${menu.isleaf eq '1'}">
   									<a class="{action: 'addTab(\'${menu.menucode}\', \'${menu.menuname}\', \'${menu.url}\');'}">${menu.menuname}</a>
   								</c:when>
   								<c:otherwise>
   									<a class="{menu: 'menu_${menu.menucode}', img: 'icon_14.png'}">${menu.menuname}</a>
   								</c:otherwise>
   								</c:choose>
   								
   								<c:set scope="page" value="${menu.pmenuinfo.menucode}" var="lastpc"></c:set>
   								
   								<c:choose>
   									<c:when test="${status.index == (fn:length(menuList) - 1)}">
   										</div>
   									</c:when>
   								</c:choose>
   								
   							</c:otherwise>
	   						</c:choose>
	    				</c:forEach>
	    				</tr>
	    			</table>
	    		</div>
	        </div>
        </div>
		<div class="pagecontent">
			<ul id="tabs" class="clearfix">
				<li class="current">
					<div id="tabl">
						<div id="tabr">
							<div id="tabm"><a class="tab" id="tab_main" href="javascript:void(0);" onclick="changeTab(this);">首页</a></div>
						</div>
					</div>
				</li>
			</ul>
			<div id="contents">
				<iframe id="ifm_main" class="workarea" frameborder="0" onload="initIframeSize(this);" src="login_openWelcomePage" ></iframe>
			</div>
		<div id="win" data-options="closed:true" >
			<div id="msg"></div>
		</div>
	</body>
</html>

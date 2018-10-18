/**
 * 处理首页
 * author haojingwei
 */

var tabSize = 12; //标签页卡总共可以打开多少个
var tabNum = 0; //标签页卡计数器

/**
 * 新建标签页卡
 * @param menuid
 * @param menuname
 * @param menuurl
 * @return
 */
function addTab(menuid, menuname, menuurl) {
	var tabId = "tab_" + menuid;
	var iframeId = "ifm_" + menuid;
	
	//如果标签页已经存在，置为当前，并返回
	if ($("#" + tabId).length != 0) {
		//隐藏其它所有标签页
		$("#contents > iframe ").hide();
		$("#tabs > li").removeClass("current");

		//显示当前标签页及内容
		$("#" + iframeId).show();
		$("#" + tabId).parents("li").addClass("current");
		
		return;
	}
    
	if (tabNum < tabSize) {
		//隐藏其它标签页
		$("#tabs > li").removeClass("current");
		$("#contents > iframe").hide();
		
		//添加新标签页，并关联内容
		$("#tabs").append("<li class='current'><div id='tabl'><div id='tabr'><div id='tabm'><a class='tab' id='" + tabId
				+ "' href='javascript:void(0);' onclick='changeTab(this);'>" + menuname
				+ "</a><a href='javascript:void(0);' class='remove' title='关闭' onclick='closeTab(this);'>x</a></div></div></div></li>");
		
		$("#contents").append("<iframe id='" + iframeId + "' class='workarea' frameborder='0' onload='initIframeSize(this);' src='" + menuurl + "'/>");
		
		//设置新添加的标签页为当前页
		$("#" + iframeId).show();
		
		//标签页卡数+1
		tabNum++;
	} else {
		alert("对不起，本系统最多可以打开" + tabSize + "个标签页卡。请关闭其它标签页卡，以打开此功能！");
	}
}

/**
 * 标签页卡点击事件
 * @param tab
 * @return
 */
function changeTab(tab) {
	var iframeId = "ifm_" + $(tab).attr("id").substr(4);

	//隐藏其它所有标签页
	$("#contents > iframe").hide();
	$("#tabs > li").removeClass("current");

	//显示当前标签页及内容
	$("#" + iframeId).show();
	$(tab).parents("li").addClass("current");
}

/**
 * 关闭标签页
 * @param tab
 * @return
 */
function closeTab(tab) {
	//获取标签页
	var tabid = $(tab).parent().find(".tab").attr("id");
	
	//移除标签页及相关的iframe内容
	var iframeId = "ifm_" + tabid.substr(4);
	$("#" + iframeId).remove();
	$(tab).parents("li").remove();
	
	//标签页卡数-1
	tabNum--;
	
	//如果没有当前标签页，并且当前页左边还有其它标签页，则显示第一个标签页
	if ($("#tabs li.current").length == 0 && $("#tabs li").length > 0) {
		//找到第一个标签页
		var firsttab = $("#tabs li:first-child");
		firsttab.addClass("current");
		
		//获取它的链接名，并显示相关内容
		var firsttabid = $(firsttab).find("a.tab").attr("id");
		$("#ifm_" + firsttabid.substr(4)).show();
	}
}

/**
 * 初始化iframe
 * @param {Object} iframe
 */
function initIframeSize(iframe) {
	var winHeight = 0;
	
	//获取窗口高度
	if (window.innerHeight) {
		winHeight = window.innerHeight;
	} else if ((document.body) && (document.body.clientHeight)){
		winHeight = document.body.clientHeight;
	}
	
	//通过深入Document内部对body进行检测，获取窗口大小
	if (document.documentElement && document.documentElement.clientHeight){
		winHeight = document.documentElement.clientHeight;
	}
	
	//设置iframe尺寸
	iframe.height = winHeight - 152; //header:90;mainmenu:30;contents:33
	
	resizeTreeDiv(iframe); //设置iframe里面tree所在Div的高度
}

/**
 * 设置iframe里面tree所在Div的高度
 */
function resizeTreeDiv(iframe) {
	iframe.contentWindow.onresize = function() {
		if (iframe.contentWindow.document.getElementById("treeroot") 
				&& iframe.contentWindow.document.getElementById("treeroot").parentNode) {
			var iframeClientHeight = iframe.contentWindow.document.documentElement.clientHeight;
			
			//必须先设置一下高度，防止div已经撑开页面高度
			iframe.contentWindow.document.getElementById("treeroot").parentNode.style.height = iframeClientHeight - 18 + "px";
			
			var iframeBodyHeight = iframe.contentWindow.document.documentElement.scrollHeight;
		
			if (iframeBodyHeight > iframeClientHeight) {
				iframe.contentWindow.document.getElementById("treeroot").parentNode.style.height = iframeBodyHeight + "px";
			} else {
				iframe.contentWindow.document.getElementById("treeroot").parentNode.style.height = iframeClientHeight - 18 + "px";
			}
		}
	};
}

/**
 * 重置iframe大小
 */
function resizeIframes() {
	var winHeight = 0;
	
	//获取窗口高度
	if (window.innerHeight) {
		winHeight = window.innerHeight;
	} else if ((document.body) && (document.body.clientHeight)) {
		winHeight = document.body.clientHeight;
	}
	
	//通过深入Document内部对body进行检测，获取窗口大小
	if (document.documentElement && document.documentElement.clientHeight){
		winHeight = document.documentElement.clientHeight;
	}
	
	for (var i = 0; i < $("#contents > iframe").length; i++) {
		$("#contents > iframe")[i].style.height = (winHeight - 152) + "px"; //header:90;mainmenu:30;contents:33
	}
}

/**
 * 打开主页
 */
function openMainTab() {
	var tabId = "tab_main";
	var iframeId = "ifm_main";

	//隐藏其它所有标签页
	$("#contents > iframe").hide();
	$("#tabs > li").removeClass("current");

	//显示当前标签页及内容
	$("#" + iframeId).show();
	$("#" + tabId).parents("li").addClass("current");
}

/**
 * 定义菜单
 */
$(function(){
	$("#mainmenu").buildMenu({
		additionalData:"pippo=1",
		menuWidth:131,
		openOnRight:false,
		menuSelector: ".menuContainer",
		iconPath:"ico/",
		hasImages:false,
		fadeInTime:100,
		fadeOutTime:100,
		adjustLeft:2,
		minZindex:"auto",
		adjustTop:10,
		shadow:false,
		hoverIntent:100,
		openOnClick:false,
		closeOnMouseOut:true,
		closeAfter:100,
		submenuHoverIntent:100
	});
});

(function(window){
	var arr_l, arr_r, mainmenu, xmenu, menu_a,
		old_w = 0, _tm, _sw = 1;
	
	var reclacSize = function _rs(timeout){
		var new_w = mainmenu.width();
		if(new_w != old_w){
			var menu_w = xmenu.width();
			if(new_w >= menu_w){
				xmenu.css("left", "0px");
				menu_a.css({"marginLeft" : "0px",
					"marginRight" : "0px"});
				arr_l.hide();
				arr_r.hide();
			}else{
				menu_a.css({"marginLeft" : "20px",
					"marginRight" : "20px"});
				arr_l.show();
				arr_r.show();
			}
			
			old_w = new_w;
		}
		
		setTimeout(function(){
			_rs(timeout);
		}, timeout);
	};
	
	var dmc = function(step){
		if(_sw == 0){
			return;
		}
		
		var _left = xmenu.offset().left;
		if(_left >=20 && step >0){ return; }
		if(_left < -(xmenu.width() - mainmenu.width() - 40) && step <0){ return; }
		
		xmenu.offset({"left" : _left + step});
			
		tm = setTimeout(function(){
			dmc(step);
		}, 1);
	};
	
	$(document).ready(function(){
		arr_l = $("#arr_l");
		arr_r = $("#arr_r");
		mainmenu = $("#mainmenu");
		xmenu = $("#xmenu");
		menu_a = $("#menu_a");
		
		arr_r.bind("mousedown", function(){
			_sw = 1;
			dmc(-5);
		}).bind("mouseup", function(){
			_sw = 0;
		}).bind("mouseout", function(){
			_sw = 0;
		});
		
		arr_l.bind("mousedown", function(){
			_sw = 1;
			dmc(5);
		}).bind("mouseup", function(){
			_sw = 0;
		}).bind("mouseout", function(){
			_sw = 0;
		});
		
		reclacSize(300);
	});
	
}(window, undefined));

//打开修改密码
function changePassword(usercode){
	$('#win').window({
		title : "修改密码",
		width: 450,
		height: 200,
		modal:true,
		minimizable:false,
		collapsible:false
	});
	$('#win').window('open');
	$("#msg").load("userController_resetPwd?userstr="+usercode);
}
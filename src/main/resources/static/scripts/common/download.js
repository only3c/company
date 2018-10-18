(function(win, $, undefined){
	/**
	 * 下载一个URL,主要为了监控是否下载成功
	 * @param url
	 * @param errfun
	 */
	var _download = function(url, errfun){
		var _if = $("<iframe></iframe>");
		_if.appendTo($(document.body));
		_if.bind("load", function(){
			if(errfun && errfun instanceof Function){
				errfun.call(this);
				_if.remove();
			}
		});
		
		_if.attr("src", url);
	};
	
	
	win._download = _download;
}(window, jQuery));
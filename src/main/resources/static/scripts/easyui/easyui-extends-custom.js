/**
 *	EasyUI custom extends
 *	EasyUI 扩展
 *	by haojw
 *
 */
;(function(win, $, undefined){
	//tree扩展：获取当前节点的儿子节点，具体的用法和getChildren方法一样
	$.extend($.fn.tree.methods,{
		getSubChildren : function(jq, params){
			var nodes = [];
			$(params).next().children().children("div.tree-node").each(function(){
				nodes.push($(jq[0]).tree('getNode', this));
			});
			return nodes;
		}
	});
	
}(window, jQuery));
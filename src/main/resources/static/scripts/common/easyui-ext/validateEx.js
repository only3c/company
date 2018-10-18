$.extend($.fn.validatebox.defaults.rules, {
	minLength : {
		validator : function(value, param) {
			return value.length >= param[0];
		},
		message : '输入内容长度不能小于{0}.'
	},
	maxLength : {
		validator : function(value, param) {
			var length = 0;
			for (var i = 0; i < value.length; i++) {
				var c = value.charCodeAt(i);
				if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
					length++;
				} else {
					length+=2;
				}
			}
			if (length <= param[0]) {
				return length <= param[0];
			}
		},
		message : '输入内容长度不能大于{0}个字节（中文为2字节）.'
	},	
	DateOnlyFormat : {
		validator : function(value) {
			return /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$/
			.test(value);
		},
		message : "请输入有效的日期，例如2016-01-11."
	},
	TimeOnlyFormat : {
		validator : function(value) {
			return /^(0?[0-9]|1?[0-9]|2?[0-4]):[0-5][0-9]:[0-5][0-9]$/.test(value);
		},
		message : "请输入有效的时间，例如15:26:30"
	},
	DateTimeFormat : {
		validator : function(value) {
			return /^(((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d)$/
			.test(value);
		},
		message : "请输入有效的日期与时间，例2016-01-11 15:26:30 ."
	},
	ZipFormat: {
		validator : function(value) {
			return /^[0-9]\d{5}$/.test(value);
		},
		message : "请输入有效的邮政编码."		
	},
	CurrencyFormat: {
		validator : function(value) {
			return /^\d+(\.\d+)?$/.test(value);
		},
		message : "请输入有效的货币数值."	
	},
	DateOfNumber:{
		validator:function(value){
		return /^\d{8}$/.test(value);
		},
		message:"请选择有效的日期."
	},
	YearOfNumber:{
		validator:function(value){
		return /^\d{4}$/.test(value);
		},
		message:"请输入有效的年份，例如2016."
	},
	ChineseTelNumber: {
		validator : function(value) {
			return /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/.test(value);
		},
		message : "请输入有效的电话号码."	
	},
	TimeAfter: {
		validator : function(value, param) {
		    var s = $(param[0]).datetimebox('getValue');
		    return value > s;
	    },
	    message:"请输入较大的日期"
	},
	IntegerFormat: {
		validator : function(value) {
			return /^\d+$/.test(value);
		},
		message:"请输入正确的整数，例###"
	},
	FloatFormat: {
		validator : function(value) {
			
		},
		message:"请输入正确的浮点数，例###.#"
	},
	etimeRex : {
		validator : function(value) {
			var validbegindate =  $('#validbegindate').datebox('getValue');
	       	var d1 = new Date(value); 
	       	if (validbegindate != null && validbegindate != '') {
	       		var d2 = new Date(validbegindate);
	       		if (Date.parse(d1) - Date.parse(d2) < 0) {
	       			return false;
	       		} else {
	       			return true;
	       		}
	       	} else {
	       		return true;
	       	}
		},
		message : '结束日期不能小于开始日期'
	},
	btimeRex : {
		validator : function(value) {
			var validenddate =  $('#validenddate').datebox('getValue');
	       	var d1 = new Date(value);   
	       	if (validenddate != null && validenddate != '') {
	       		var d2 = new Date(validenddate);
	       		if (Date.parse(d1) - Date.parse(d2) > 0) {
	       			return false;
	       		} else {
	       			return true;
	       		}
	       	} else {
	       		return true;
	       	}
		},
		message : '开始日期不能大于结束日期'
	},
	certnoRex : {
		validator : function(value) {
			var rex = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
			var rex2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
			var certtype = $('#certtype').attr('value');
			if (certtype && certtype == '1') {
				if (rex.test(value) || rex2.test(value)) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		},
		message : '请输入正确的证件号码'
	},
	phoneRex : {
		validator : function(value) {
			var rex = /^1[3-8]+\d{9}$/;
			var rex2 = /^0\d{2,3}-?\d{7,8}$/;
			if (rex.test(value) || rex2.test(value)) {
				return true;
			} else {
				return false;
			}

		},
		message : '请输入正确的电话或手机格式'
	},
	PositiveIntegerFormat : {
		validator : function(value) {
			var rex = /^[1-9]*[1-9][0-9]*$/;
			var rex2=/^[1-9]\d*$/;
			if (rex.test(value) || rex2.test(value)) {
				return true;
			} else {
				return false;
			}

		},
		message : '请输入正整数！'
	},
	RepaydateRex : {
		validator : function(value) {
			if (value < 31){
				return true;
			} else {
				return false;
			}
		},
		message : '请输入正确的还款日！'
	}
});
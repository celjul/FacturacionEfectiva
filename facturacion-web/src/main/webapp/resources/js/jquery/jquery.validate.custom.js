/**
 * 
 */
$.validator.addMethod("curp", function(value, element) {
	return this.optional(element) || /^[A-Z]{4}\d{6}[HM](AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[A-Z]{3}([A-Z]\d|\d{2})$/i.test(value);
});

$.validator.addMethod("rfc", function(value, element) {
	return this.optional(element) || /^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?$/i.test(value);
});


$.validator.addMethod("requiredIf",function(value, element, params) {
	return !(params && $.trim($(params).val()) != "" && $.trim(value) == "" )
}, $.validator.messages.required);

$.validator.addMethod("future", function(value, element, params) {
	if(params.length > 0) {
		 if ($.trim($(params[0]).val()) != "" && $.trim(value) != "") {
			var arrInicial = $(params[0]).val().split("-");
			var arrFinal = value.split("-");
			
			var fInicial = new Date(parseInt(arrInicial[2]), parseInt(arrInicial[1]) - 1, parseInt(arrInicial[0]));
			var fFinal = new Date(parseInt(arrFinal[2]), parseInt(arrFinal[1]) - 1, parseInt(arrFinal[0]));
			
			return fFinal >= fInicial;
		 } 
		 return this.optional(element);
	}
	
}, $.validator.format("Debe ser posterior a la {1}"));

if (!Date.parseTime) {
	Date.parseTime = function (hour) {
		var auxHour = $.map(hour.split(":"), function(n) {
			return parseInt(n)
		});
		
		var hour = new Date();
		
		hour.setTime(0);
		hour.setHours(auxHour[0]);
		hour.setMinutes(auxHour[1]);
		
		return hour;
	}
}

$.validator.addMethod("futureTime", function(value, element, params) {
	var getAuxDate = function(date) {
		return $.map(date, function(n) {
			return parseInt(n);
		});
	}
	if (params) {
		//El parametro 0: es el campo que contiene la hora contra el que se va a comparar el valor del campo.
		//El parametro 1: es el campo que contiene la fecha contra el que se va a comparar la hora, en caso de que se trate de una hora del dia siguiente.
		var params = $.map(params.split(","), function(item) {return $.trim(item)});
		
		if ($.trim(value) != "") {
			var hInicio = Date.parseTime($.trim($(params[0]).val()));
			var hFin = Date.parseTime($.trim(value));
			var valid = hFin >= hInicio;
			
			if (!valid && params.length == 3) {
				var auxInicio = getAuxDate($.trim($(params[1]).val()).split("-"));
				
				hInicio.setFullYear(auxInicio[0]);
				hInicio.setMonth(auxInicio[1] -1);
				hInicio.setDate(auxInicio[2]);
				
				auxInicio = getAuxDate($.trim($(params[2]).val()).split("-"));
				
				hFin.setFullYear(auxInicio[0]);
				hFin.setMonth(auxInicio[1] -1);
				hFin.setDate(auxInicio[2]);
				
				return hFin >= hInicio;
			}
			
			return valid;
		} 
	 } 
	 return false;
}, "Verfique que la hora es correcta");
/**
 * 
 */
if (!String.format) {
	String.format = function(format) {
		var args = Array.prototype.slice.call(arguments, 1);
		return format.replace(/{(\d+)}/g, function(match, number) {
			return typeof args[number] != 'undefined' ? args[number] : match;
		});
	}	
}




if (typeof String.prototype.startsWith != 'function') {
  // see below for better implementation!
  String.prototype.startsWith = function (str){
    return this.indexOf(str) === 0;
  };
}

if (typeof String.prototype.endsWith !== 'function') {
    String.prototype.endsWith = function(suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
    };
}

if(!Object.get) {
	Object.get = function(data, prop) {
		return data ? data[prop] : undefined;
	}
}

if(!Object.getDefault) {
	Object.getDefault = function(data, prop, value) {
		var valor = Object.get(data, prop);
		return ((valor != undefined && value != null) ? valor : value);
	}
}

if(!Object.getArray) {
	Object.getArray = function(data, prop) {
		return data && $.isArray(data[prop]) ? data[prop] : []
	}
}

if (!Object.only) {
	Object.only = function(object, properties) {
		for (var key in object) {
			if (properties.indexOf(key) == -1 ) {
				delete object[key];
			}
		}
		
		return object;
	}
}

if (!Object.deleteProperties) {
	Object.deleteProperties = function(object, properties) {
		for (var i = 0; i < properties.length; i++ ) {
			delete object[properties[i]];
		}
		
		return object;
	}
}

if(!Object.validarEmail){
	Object.validarEmail = function(email){
		expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		return expr.test(email);
	}
}


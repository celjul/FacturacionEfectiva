var Usuario = function() {
	var self = this;
	self.id = ko.observable(undefined);
	self.nombre = ko.observable(undefined);
	self.apellidoPaterno = ko.observable(undefined);
	self.apellidoMaterno = ko.observable(undefined);
	self.roles = ko.observableArray([]);
	self.emisores = ko.observableArray([]);
	self.login = ko.observable(undefined);
	self.password = ko.observable(undefined);
	self.telefono = ko.observable(undefined);
	self.extension = ko.observable(undefined);
	
	self.clean = function () {
		self.nombre(undefined);
		self.apellidoPaterno(undefined);
		self.apellidoMaterno(undefined);
		self.login(undefined);
		self.roles([]);
		self.emisores([]);
		self.password(undefined);
		$("#txtConfirmPassword").val("");
		self.telefono(undefined);
		self.extension(undefined);
	}

	self.cargar = function(data) {
		self.id(data.id);
		self.nombre(data.nombre);
		self.apellidoPaterno(data.apellidoPaterno);
		self.apellidoMaterno(data.apellidoMaterno);
		self.roles($.map(data, function(item) {
			return item.id
		}));
		self.telefono(data.telefono);
		self.extension(data.extension);
	}
	
	self.toJSON = function() {
		var usuario = ko.toJS(self);
		
		usuario.roles = $.map(usuario.roles, function(item) {
			return {id : item};
		});
		
		usuario.emisores = $.map(usuario.emisores, function(item) {
			return {id : item, "@class" : "com.entich.ezfact.emisores.model.EmisorFisica"};
		});
		
		return usuario;
	}
}
	
var AppViewModel = function() {
	var self = this;
	self.usuario = new Usuario();
	self.cambio = ko.observable(false);
	self.roles = ko.observableArray([]);
	self.emisores = ko.observableArray([]);
	
	$.get(String.format("{0}/app/catalogos/roles", contextPath), function(items) {
		self.roles(items);
	});
	
	$.get(String.format("{0}/app/emisores/", contextPath), function(data) {
		self.emisores(data);
    })
	
	if (window.session.usuario) {
		$.get(String.format("{0}/app/usuarios/{1}", contextPath, window.session.usuario), function(item) {
			self.usuario.cargar(item);
		});
	}

	self.cambiarPassword = function() {
		self.cambio(true);
	}


	self.actualizar = function() {
		if ($("#form-usuario").valid()) {
			
			$.ajax({ 
			    url: String.format("{0}/app/usuarios/{1}", contextPath, self.usuario.id()), 
			    type: 'PUt', 
			    dataType: 'json', 
			    data: ko.toJSON(self.usuario), 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) { 
			    	toastr.success("El usuario se ha actualizado correctamente", "¡Éxito!");
			    	self.cambio(false);
			    }
			});
		}
	}

	self.guardar = function() {
		if ($("#form-usuario").valid()) {
			var usuario = self.usuario.toJSON();
			
			$.ajax({ 
			    url: String.format("{0}/app/usuarios/", contextPath), 
			    type: 'POST', 
			    dataType: 'json', 
			    data: JSON.stringify(usuario), 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) { 
			    	toastr.success("Se ha registrado el usuario correctamente", "¡Éxito!");
			    	self.usuario.clean();
			    }
			});
		}
	}
}
	
$(function () {
/*
	$("#frmUser").validationEngine();
	$("#btnNewUser").button();
	*/
	var modelView = new AppViewModel()
	ko.applyBindings(modelView);
	
	/*
	$("#btnNewUser").click(function() {
		if ($("#frmUser").validationEngine('validate')) {
			
			$.ajax({ 
			    url: $("#frmUser").attr("action"), 
			    type: 'POST', 
			    dataType: 'json', 
			    data: ko.toJSON(modelView.usuario), 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) { 
			    	alertify.success("Se ha registrado el usuario correctamente");
			    	modelView.clean();
					return false;
			    },
			    error:function(data,status,er) { 
			    	alertify.error(er); 
			    	return false;
			    }
			});
		}
		return false;
	});
*/
	
});
 
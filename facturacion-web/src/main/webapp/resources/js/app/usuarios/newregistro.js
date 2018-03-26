var UsuarioCliente = function() {
	var self = this;
	self.id = ko.observable(undefined);
	self.nombre = ko.observable(undefined);
	self.nombre.subscribe(function(value) {
		document.getElementById("txtNombre").style.textTransform = "capitalize";	
	});
	self.apellidoPaterno = ko.observable(undefined);
	self.apellidoPaterno.subscribe(function(value) {
		document.getElementById("txtApellidoPaterno").style.textTransform = "capitalize";	
	});
	self.apellidoMaterno = ko.observable(undefined);
	self.apellidoMaterno.subscribe(function(value) {
		document.getElementById("txtApellidoMaterno").style.textTransform = "capitalize";	
	});
	self.roles = ko.observableArray([]);
	self.emisores = ko.observableArray([]);
	self.login = ko.observable(undefined);
	self.password = ko.observable(undefined);
	self.challengeField = ko.observable(undefined);
	self.responseField = ko.observable(undefined);
	self.telefono = ko.observable(undefined);
	self.codigo = ko.observable(undefined);
	
	self.clean = function () {
		self.nombre(undefined);
		self.apellidoPaterno(undefined);
		self.apellidoMaterno(undefined);
		self.login(undefined);
		self.roles([]);
		self.emisores([]);
		self.password(undefined);
		$("#txtConfirmPassword").val("");
		self.challengeField(undefined);
		self.responseField(undefined);
		self.telefono(undefined);
		self.codigo(undefined);
	}

	self.toJSON = function() {
		var usuario = ko.toJS(self);
		return usuario;
	}
}
	
var ClienteViewModel = function(data) {
	var self = this;
	self.usuarioCliente = new UsuarioCliente();
//	self.roles = ko.observableArray(data ? data.roles : []);
	
	self.guardar = function() {
		if ($("#form-registro").valid()) {
			self.usuarioCliente.roles.push({id: 2});
			self.usuarioCliente.challengeField($("#form-registro")[0][8].value);
			self.usuarioCliente.responseField($("#form-registro")[0][9].value);
			var usuario = self.usuarioCliente.toJSON();
			usuario = JSON.stringify(usuario);
			console.log(usuario);
			$.ajax({ 
			    url: String.format("{0}/autoregistro", contextPath), 
			    type: 'POST', 
			    dataType: 'json', 
			    data: usuario, 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) { 
			    	toastr.success("Se ha registrado el usuario correctamente", "¡Éxito!");
			    	self.usuarioCliente.clean();
			    	location.href = String.format(contextPath)
			    }
			});
		}
	}
}
 
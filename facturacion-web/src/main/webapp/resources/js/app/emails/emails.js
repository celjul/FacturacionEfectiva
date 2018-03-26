/**
 * 
 */
var Email = function(data) {
	var self = this;
	
	self.id = ko.observable(Object.get(data, "id"));
	self.nombre = ko.observable(Object.get(data, "nombre"));
	self.correoElectronico = ko.observable(Object.get(data, "correoElectronico"));
	self.password = new Catalogo(Object.get(data, "password"));

	self.emisor = new Emisor(Object.get(data, "emisor"));
	
	self.cargar = function(data) {
		self.id(Object.get(data, "id"));
		self.nombre(Object.get(data, "nombre"));
		self.correoElectronico(Object.get(data, "correoElectronico"));
		self.password(Object.get(data, "password"));

		self.emisor.cargar(Object.get(data, "emisor"));
	}
	
	self.limpiar = function() {
		self.id(undefined);
		self.nombre(undefined);
		self.correoElectronico(undefined);
		self.password(undefined);

		self.emisor.limpiar();
	}
	
	self.getJson = function() {
		var email = ko.toJS(self);
		delete email.emisor;
		
		return email;
	}
}
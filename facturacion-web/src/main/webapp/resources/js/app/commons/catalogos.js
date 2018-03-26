var Catalogo = function(data) {
	var self = this;
	
	self.id = ko.observable(Object.get(data, "id"));
	self.descripcion = ko.observable(Object.get(data, "descripcion"));
	self.clave = ko.observable(Object.get(data, "clave"));
	
	self.limpiar = function() {
		self.id(undefined);
		self.descripcion(undefined);
		self.clave(undefined);
	}
	
	self.cargar = function(data) {
		self.id(Object.get(data, "id"));
		self.descripcion(Object.get(data, "descripcion"));
		self.clave(Object.get(data, "clave"));
	}
}
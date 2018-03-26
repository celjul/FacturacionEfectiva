var PaqueteUsuario = function(data) {
	var self = this;

	self.id = ko.observable(Object.get(data, "id"));
	self.usuario = ko.observable(Object.get(data, "usuario"));
	self.paquete = new Paquete(Object.get(data, "paquete"));
	self.adquirido = ko.observable(Object.get(data, "adquirido"));
	self.vencimiento = ko.observable(Object.get(data, "vencimiento"));
	self.activo = ko.observable(Object.get(data, "activo"));
	
	self.cargar = function(data) {
		self.id(Object.get(data, "id"));
		self.usuario(Object.get(data, "usuario"));
		self.paquete.cargar(Object.get(data, "paquete"));
		self.adquirido(Object.get(data, "adquirido"));
		self.vencimiento(Object.get(data, "vencimiento"));
		self.activo(Object.get(data, "activo"));
	}
	
	self.limpiar = function() {
		self.id(undefined);
		self.usuario(undefined);
		self.paquete.limpiar();
		self.adquirido(undefined);
		self.vencimiento(undefined);
		self.activo(undefined);
	}
	
	self.toJSON = function() {
		var paqueteVigente = ko.toJS(self);
		return paqueteVigente; 
	}
}

var Paquete = function(data) {
	var self = this;

	self.id = ko.observable(Object.get(data, "id"));
	self.nombrePaquete = ko.observable(Object.get(data, "nombrePaquete"));
	self.facturas = ko.observable(Object.get(data, "facturas"));
	self.precio = ko.observable(Object.get(data, "precio"));
	self.tipo = new Catalogo(Object.get(data, "tipo"));
	
	self.cargar = function(data) {
		self.id(Object.get(data, "id"));
		self.nombrePaquete(Object.get(data, "nombrePaquete"));
		self.facturas(Object.get(data, "facturas"));
		self.precio(Object.get(data, "precio"));
		self.tipo.cargar(Object.get(data, "tipo"));
	}
	
	self.limpiar = function() {
		self.id(undefined);
		self.nombrePaquete(undefined);
		self.facturas(undefined);
		self.precio(undefined);
		self.tipo.limpiar();
	}
	
	self.toJSON = function() {
		var paquete = ko.toJS(self);
		return paquete; 
	}
}


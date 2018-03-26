/**
 * 
 */
var Producto = function(data) {
	var self = this;
	
	self.id = ko.observable(Object.get(data, "id"));
	self.codigo = ko.observable(Object.get(data, "codigo"));
	self.nombre = ko.observable(Object.get(data, "nombre"));
	self.unidadDeMedida = new Catalogo(Object.get(data, "unidadDeMedida"));
	self.precio = ko.observable(Object.get(data, "precio"));
	self.exentoIVA = ko.observable(data ? Object.get(data, "exentoIVA") : false);
	self.emisor = new Emisor(Object.get(data, "emisor"));
	
	self.cargar = function(data) {
		self.id(Object.get(data, "id"));
		self.codigo(Object.get(data, "id"));
		self.nombre(Object.get(data, "id"));
		self.unidadDeMedida(Object.get(data, "id"));
		self.precio(Object.get(data, "id"));
		self.exentoIVA(Object.get(data, "id"));
		
		self.emisor.cargar(Object.get(data, "emisor"));
	}
	
	self.limpiar = function() {
		self.id(undefined);
		self.codigo(undefined);
		self.nombre(undefined);
		self.unidadDeMedida.limpiar();
		self.precio(undefined);
		self.exentoIVA(undefined);
		
		self.emisor.limpiar();
	}
	
	self.getJson = function() {
		var producto = ko.toJS(self);
		
		delete producto.emisor;
		
		return producto;
	}
}
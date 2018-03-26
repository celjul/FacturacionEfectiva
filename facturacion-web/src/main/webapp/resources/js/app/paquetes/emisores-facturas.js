var PaqueteEmisor = function(data) {
	var self = this;

	self.id = Object.get(data, "id");
	self.paquete = Object.get(data, "paquete");
	self.emisor = Object.get(data, "emisor");
	self.foliosAutorizados = ko.observable(Object.get(data, "foliosAutorizados"));
	
	self.cargar = function(data) {
		self.id = Object.get(data, "id");
		self.paquete = Object.get(data, "paquete");
		self.emisor = Object.get(data, "emisor");
		self.foliosAutorizados(Object.get(data, "foliosAutorizados"));
	}
	
	self.limpiar = function() {
		self.id = undefined;
		self.paquete = undefined;
		self.emisor = undefined;
		self.foliosAutorizados(undefined);
	}
	
	self.toJSON = function() {
		var emisorFactura = ko.toJS(self);
		return emisorFactura; 
	}
}
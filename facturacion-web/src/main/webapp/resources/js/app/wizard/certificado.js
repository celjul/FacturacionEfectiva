var CertificadoPablo = function(data) {
	var self = this;
	
	self.certificado = ko.observable(Object.get(data, "certificado"));
	self.clave = ko.observable(Object.get(data, "clave"));
	self.pfx = ko.observable(Object.get(data, "pfx"));
	self.emisor = new Emisor(Object.get(data, "emisor"));
	self.id = ko.observable(Object.get(data, "id"));
	self.inicio = ko.observable(Object.get(data, "inicio"));
	self.fin = ko.observable(Object.get(data, "fin"));
	self.nombre = ko.observable(Object.get(data, "nombre"));
	self.password = ko.observable(Object.get(data, "password"));
	self.serie = ko.observable(Object.get(data, "serie"));
	
	self.limpiar = function() {
		self.certificado(undefined);
		self.clave(undefined);
		self.pfx(undefined);
		self.emisor.limpiar();
		self.id(undefined);
		self.inicio(undefined);
		self.fin(undefined);
		self.nombre(undefined);
		self.password(undefined);
		self.serie(undefined);
	}
	
	self.cargar = function(data){
		self.certificado(Object.get(data, "certificado"));
		self.clave(Object.get(data, "clave"));
		self.pfx(Object.get(data, "pfx"));
		self.emisor.cargar(Object.get(data, "emisor"));
		self.id(Object.get(data, "id"));
		self.inicio(Object.get(data, "inicio"));
		self.fin(Object.get(data, "fin"));
		self.nombre(Object.get(data, "nombre"));
		self.password(Object.get(data, "password"));
		self.serie(Object.get(data, "serie"));
	}
	
	self.getJson = function() {
		var certificadoPablo = ko.toJS(self);
		
		if (certificadoPablo.certificado) {
			delete certificadoPablo.certificado;
        }
		
		if (certificadoPablo.clave) {
			delete certificadoPablo.clave;
        }
		
		if (certificadoPablo.pfx) {
			delete certificadoPablo.pfx;
        }
		
		if (certificadoPablo.emisor) {
			delete certificadoPablo.emisor;
        }
		
		return certificadoPablo;
	}
}

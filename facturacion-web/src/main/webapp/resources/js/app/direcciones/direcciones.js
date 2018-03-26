var Direccion = function(data) {
	var self = this;
	
	self.nombrePais = ko.observable(Object.get(data, "nombrePais"));
	self.nombrePais.subscribe(function(value) {
		document.getElementById("txt-pais").style.textTransform = "capitalize";	
	});
	self.nombreEstado = ko.observable(Object.get(data, "nombreEstado"));
	self.nombreEstado.subscribe(function(value) {
		document.getElementById("txt-estado").style.textTransform = "capitalize";	
	});
	self.nombreMunicipio = ko.observable(Object.get(data, "nombreMunicipio"));
	self.nombreMunicipio.subscribe(function(value) {
		document.getElementById("txt-municipio").style.textTransform = "capitalize";	
	});
	self.nombreColonia = ko.observable(Object.get(data, "nombreColonia"));
	self.nombreColonia.subscribe(function(value) {
		document.getElementById("txt-colonia").style.textTransform = "capitalize";	
	});
	self.codigoPostal = ko.observable(Object.get(data, "codigoPostal"));
	self.calle = ko.observable(Object.get(data, "calle"));
	self.calle.subscribe(function(value) {
		document.getElementById("txt-calle").style.textTransform = "capitalize";	
	});
	self.id = ko.observable(Object.get(data, "id"));
	self.noExterior = ko.observable(Object.get(data, "noExterior"));
	self.noInterior = ko.observable(Object.get(data, "noInterior"));
	self.referencias = ko.observable(Object.get(data, "referencias"));
	self.localidad = ko.observable(Object.get(data, "localidad"));
	self.localidad.subscribe(function(value) {
		document.getElementById("txt-localidad").style.textTransform = "capitalize";	
	});
	
	self.cargar = function(data) {		
		self.nombrePais(Object.get(data, "nombrePais"));
		self.nombreEstado(Object.get(data, "nombreEstado"));
		self.nombreMunicipio(Object.get(data, "nombreMunicipio"));
		self.nombreColonia(Object.get(data, "nombreColonia"));
		self.codigoPostal(Object.get(data, "codigoPostal"));
		self.calle(Object.get(data, "calle"));
		self.id(Object.get(data, "id"));
		self.noExterior(Object.get(data, "noExterior"));
		self.noInterior(Object.get(data, "noInterior"));
		self.referencias(Object.get(data, "referencias"));
		self.localidad(Object.get(data, "localidad"));
	}
	
	self.limpiar = function() {
		self.nombrePais(undefined);
		self.nombreEstado(undefined);
		self.nombreMunicipio(undefined);
		self.nombreColonia(undefined);
		self.codigoPostal(undefined);		
		self.calle(undefined);
		self.id(undefined);
		self.noExterior(undefined);
		self.noInterior(undefined);
		self.referencias(undefined);
		self.localidad(undefined);
	}
}
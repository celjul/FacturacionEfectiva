var Emisor = function(data) {
	var self = this;

	var getTipo = function(tipo) {
		switch (tipo) {
		case "com.entich.ezfact.emisores.model.EmisorFisica":
			return "fisica"

		case "com.entich.ezfact.emisores.model.EmisorMoral":
			return "moral"

		default:
			return undefined;
		}
	}

	self.id = ko.observable(Object.get(data, "id"));
	//self.direcciones = ko.observableArray(Object.getArray(data, "direcciones"));
	self.rfc = ko.observable(Object.get(data, "rfc"));
	self.rfc.subscribe(function(value) {
		if (value) {
			self.rfc(value.toUpperCase());
		}
	});
	self.apellidoMaterno = ko.observable(Object.get(data, "apellidoMaterno"));
	self.apellidoMaterno
			.subscribe(function(value) {
				document.getElementById("txt-apellido-materno").style.textTransform = "capitalize";
			});
	self.apellidoPaterno = ko.observable(Object.get(data, "apellidoPaterno"));
	self.apellidoPaterno
			.subscribe(function(value) {
				document.getElementById("txt-apellido-paterno").style.textTransform = "capitalize";
			});
	self.nombre = ko.observable(Object.get(data, "nombre"));
	self.nombre
			.subscribe(function(value) {
				document.getElementById("txt-nombre").style.textTransform = "capitalize";
			});
	self.razonSocial = ko.observable(Object.get(data, "razonSocial"));
	//self.regimenes = ko.observableArray(Object.getArray(data, "regimenes"));
	self.tipo = ko.observable(getTipo(Object.get(data, "@class")));
	self.facturas = ko.observable(Object.get(data, "facturas"));
	self.colorPlantilla = ko.observable(data ? Object.get(data,
			"colorPlantilla") : "#ffffff");
	self.logo = ko.observable(Object.get(data, "logo"));
	self.plantilla = ko.observable(Object.get(data, "plantilla"));
	
	self.regimen = new Catalogo(Object.get(data, "regimen"));

	self.nombreCompleto = ko.computed(function() {
		if (self.tipo() === "moral" || (!self.tipo() && self.razonSocial())) {
			return self.razonSocial();
		} else {
			return self.nombre() + " " + self.apellidoPaterno()
					+ self.apellidoMaterno() ? (" " + self.apellidoMaterno())
					: "";
		}
	}, self);

	self.limpiar = function() {
		self.id(undefined);
		//self.direcciones([]);
		self.rfc(undefined);
		self.apellidoMaterno(undefined);
		self.apellidoPaterno(undefined);
		self.nombre(undefined);
		self.razonSocial(undefined);
		//self.regimenes([]);
		self.tipo(undefined);
		self.facturas(undefined);
		self.colorPlantilla(undefined);
		self.logo(undefined);
		self.plantilla(undefined);
		// document.getElementById("uploadPreview").style.visibility = "hidden";
		
		self.regimen.limpiar();
	}

	self.cargar = function(data) {
		self.rfc(Object.get(data, "id"));
		//self.direcciones(Object.getArray(data, "direcciones"));
		self.rfc(Object.get(data, "rfc"));
		self.apellidoMaterno(Object.get(data, "apellidoMaterno"));
		self.apellidoPaterno(Object.get(data, "apellidoPaterno"));
		self.nombre(Object.get(data, "nombre"));
		self.razonSocial(Object.get(data, "razonSocial"));
		//self.regimenes(Object.getArray(data, "regimenes"));
		self.facturas(Object.get(data, "facturas"));
		self.colorPlantilla(Object.get(data, "colorPlantilla"));
		self.logo(Object.get(data, "logo"));
		self.plantilla(Object.get(data, "plantilla"));
		
		console.log(Object.get(data, "regimen"));
		self.regimen.cargar(Object.get(data, "regimen"));
	}

	self.toJSON = function() {
		var emisor = ko.toJS(self);

		/*$
				.each(
						emisor.direcciones,
						function(i, item) {
							delete item.pais;
							delete item.estado;
							delete item.municipio;
							delete item.localidad;

							item["@class"] = "com.entich.ezfact.emisores.model.DireccionEmisor";
						});*/

		delete emisor.nombreCompleto;
		delete emisor.tipo;
		if(emisor.razonSocial){
            emisor["@class"] = "com.entich.ezfact.emisores.model.EmisorMoral";
            delete emisor.nombre;
            delete emisor.apellidoPaterno;
            delete emisor.apellidoMaterno;
        } else {
             emisor["@class"] = "com.entich.ezfact.emisores.model.EmisorFisica";
             delete emisor.razonSocial;
        }
		//emisor["@class"] = emisor.razonSocial ? "com.entich.ezfact.emisores.model.EmisorMoral" : "com.entich.ezfact.emisores.model.EmisorFisica";
		return emisor;
	}
}

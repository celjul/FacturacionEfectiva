var Cliente = function(data) {
	var self = this;

	var getTipo = function(tipo) {
		switch (tipo) {
		case "com.entich.ezfact.clientes.model.ClientePersonaFisica":
			return "fisica"

		case "com.entich.ezfact.clientes.model.ClientePersonaMoral":
			return "moral"

		default:
			return undefined;
		}
	}

	self.id = ko.observable(Object.get(data, "id"));
	self.rfc = ko.observable(Object.get(data, "rfc"));
	self.rfc.subscribe(function(value) {
		if (value) {
			self.rfc(value.toUpperCase());
		}
	});
	self.razonSocial = ko.observable(Object.get(data, "razonSocial"));
	self.nombre = ko.observable(Object.get(data, "nombre"));
//	self.nombre
//			.subscribe(function(value) {
//				document.getElementById("txt-nombre").style.textTransform = "capitalize";
//			});
	self.apellidoPaterno = ko.observable(Object.get(data, "apellidoPaterno"));
//	self.apellidoPaterno
//			.subscribe(function(value) {
//				document.getElementById("txt-apellido-paterno").style.textTransform = "capitalize";
//			});
	self.apellidoMaterno = ko.observable(Object.get(data, "apellidoMaterno"));
//	self.apellidoMaterno
//			.subscribe(function(value) {
//				document.getElementById("txt-apellido-materno").style.textTransform = "capitalize";
//			});
	self.razonComercial = ko.observable(Object.get(data, "razonComercial"));
	self.paginaWeb = ko.observable(Object.get(data, "paginaWeb"));
	self.observaciones = ko.observable(Object.get(data, "observaciones"));
	self.tipo = ko.observable(getTipo(Object.get(data, "@class")));

	self.nombreCompleto = ko.computed(function() {
		if (self.tipo() === "moral") {
			return self.razonSocial();
		} else {
			return self.nombre() + " " + self.apellidoPaterno()
					+ self.apellidoMaterno() ? (" " + self.apellidoMaterno())
					: "";
		}
	}, self);

	self.emisor = new Emisor(Object.get(data, "emisor"));

	//self.direcciones = ko.observableArray(Object.getArray(data, "direcciones"));
	self.contactos = ko.observableArray(Object.getArray(data, "contactos"));
	
	self.pais = new Catalogo(Object.get(data, "pais"));
    self.usoCFDi = new Catalogo(Object.get(data, "usoCFDi"));


	self.cargar = function(data) {
		self.id(Object.get(data, "id"));
		self.rfc(Object.get(data, "rfc"));
		self.razonSocial(Object.get(data, "razonSocial"));
		self.nombre(Object.get(data, "nombre"));
		self.apellidoPaterno(Object.get(data, "apellidoPaterno"));
		self.apellidoMaterno(Object.get(data, "apellidoMaterno"));
		self.razonComercial(Object.get(data, "razonComercial"));
		self.paginaWeb(Object.get(data, "paginaWeb"));
		self.observaciones(Object.get(data, "observaciones"));
		self.emisor.cargar(Object.get(data, "emisor"));
		//self.direcciones(Object.getArray(data, "direcciones"));
		// self.contactos(Object.getArray(data, "contactos"));
		self.contactos([]);
		ko.utils.arrayForEach(data.contactos, function(item) {
			var contacto = new Contacto(item);
			self.contactos.push(contacto);
		});
		
		self.pais.cargar(Object.get(data, "pais"));
        self.usoCFDi.cargar(Object.get(data, "usoCFDi"));
	}

	self.limpiar = function() {
		self.id(undefined);
		self.rfc(undefined);
		self.razonSocial(undefined);
		self.nombre(undefined);
		self.apellidoPaterno(undefined);
		self.apellidoMaterno(undefined);
		self.razonComercial(undefined);
		self.paginaWeb(undefined);
		self.observaciones(undefined);
		self.emisor.limpiar();
		//self.direcciones([]);
		self.contactos([]);
		
		self.pais.limpiar();
        self.usoCFDi.limpiar();
	}

	self.toJSON = function() {
		var cliente = ko.toJS(self);

		/*$
				.each(
						cliente.direcciones,
						function(i, item) {
							delete item.pais;
							delete item.estado;
							delete item.municipio;

							item["@class"] = "com.entich.ezfact.clientes.model.DireccionCliente";
						});*/
		if(cliente.razonSocial){
            cliente["@class"] = "com.entich.ezfact.clientes.model.ClientePersonaMoral";
            delete cliente.nombre;
            delete cliente.apellidoPaterno;
            delete cliente.apellidoMaterno;
        } else {
             cliente["@class"] = "com.entich.ezfact.clientes.model.ClientePersonaFisica";
             delete cliente.razonSocial;
        }
		//cliente["@class"] = cliente.razonSocial ? "com.entich.ezfact.clientes.model.ClientePersonaMoral" : "com.entich.ezfact.clientes.model.ClientePersonaFisica";
		delete cliente.tipo;
		delete cliente.nombreCompleto;
		delete cliente.emisor;
		ko.utils.arrayForEach(cliente.contactos, function(item) {
			delete item.marcado;
		});






		return cliente;
	}
}

var Contacto = function(data) {
	var self = this;

	self.id = ko.observable(Object.get(data, "id"));
	self.nombre = ko.observable(Object.get(data, "nombre"));
	self.apellidoPaterno = ko.observable(Object.get(data, "apellidoPaterno"));
	self.apellidoMaterno = ko.observable(Object.get(data, "apellidoMaterno"));
	self.puesto = ko.observable(Object.get(data, "puesto"));
	self.email = ko.observable(Object.get(data, "email"));
    self.marcado = ko.observable(Object.getDefault(data, "marcado", true));

	self.cargar = function(data) {
		self.id(Object.get(data, "id"));
		self.nombre(Object.get(data, "nombre"));
		self.apellidoPaterno(Object.get(data, "apellidoPaterno"));
		self.apellidoMaterno(Object.get(data, "apellidoMaterno"));
		self.puesto(Object.get(data, "puesto"));
		self.email(Object.get(data, "email"));
        self.marcado(Object.getDefault(data, "marcado", true));
    }

	self.limpiar = function() {
		self.id(undefined);
		self.nombre(undefined);
		self.apellidoPaterno(undefined);
		self.apellidoMaterno(undefined);
		self.puesto(undefined);
		self.email(undefined);

	}
}

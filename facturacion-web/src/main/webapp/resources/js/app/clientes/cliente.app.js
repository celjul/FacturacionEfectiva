/**
 * 
 */
var ClienteViewModel = function(data) {
	var self = this;
	self.residenciasFiscales = ko.observableArray([]);
    self.usosCfdi = ko.observableArray([]);
    
    /*$.ajax({
        async: false, url: String.format("{0}/app/catalogos/paises", contextPath), success: function (items) {
            self.promotores(items);
        }
    });*/
    
    $.ajax({
        async: false, url: String.format("{0}/app/catalogos/paises", contextPath), success: function (items) {
            self.residenciasFiscales(items);
        }
    });

    $.ajax({
        async: false, url: String.format("{0}/app/catalogos/usosCfdi", contextPath), success: function (items) {
            self.usosCfdi(items);
        }
    });

	self.cliente = new Cliente(data);
	self.paises = ko.observableArray([]);
	self.direccion = new Direccion();
	self.contacto = new Contacto();

	self.visibleMoral = ko.computed(function() {
		return self.cliente.tipo() === "moral";
	}, self);

	$.get(String.format("{0}/app/direcciones/paises", contextPath), function(
			data) {
		self.paises(data);
	});

	self.clear = function() {
		if (self.visibleMoral()) {
			self.cliente.nombre(undefined);
			self.cliente.apellidoPaterno(undefined);
			self.cliente.apellidoMaterno(undefined);
		} else {
			self.cliente.razonSocial(undefined);
		}
	}

	// self.agregarDireccion = function() {
	// if ($("#form-modal-direccion").valid()) {
	// self.cliente.direcciones.push(ko.toJS(self.direccion));
	// var dire = self.cliente.direcciones();
	// self.direccion.limpiar();
	// }
	// };

	self.agregarDireccion = function() {
		if ($("#form-modal-direccion").valid()) {
			self.cliente.direcciones.push(ko.toJS(self.direccion));
			self.direccion.limpiar();
		}
	};



	self.eliminarDireccion = function(direccion) {
		bootbox
				.confirm(
						"Está seguro que desea eliminar la dirección",
						function(result) {
							if (result) {
								if (direccion.id) {

									$
											.ajax({
												cache : false,
												url : String
														.format(
																"{0}/app/clientes/{1}/direcciones/{2}",
																contextPath,
																self.cliente
																		.id(),
																direccion.id),
												type : 'DELETE',
												dataType : 'json',
												contentType : 'application/json',
												mimeType : 'application/json',
												success : function(data) {
													toastr
															.success(
																	"La dirección se eliminó correctamente.",
																	"!Éxito¡");
													self.cliente.direcciones
															.remove(direccion);
												},
												error : function(data, status,
														er) {
													toastr
															.error(
																	"Problemas para eliminar la dirección.",
																	"!Error¡");
												}
											});
								} else {
									self.cliente.direcciones.remove(direccion);
								}
							}
						});
	}
	var validarContactos = function() {
		var validacion = self.cliente.contactos().length < 1;
		if (validacion) {
			toastr.warning("Debe capturar al menos un contacto.",
					"¡Precaución!");
		}
		return !validacion;
	}
	var validarDirecciones = function() {
		var validacion = self.cliente.direcciones().length < 1;
		if (self.cliente.direcciones().length < 1) {
			toastr.warning("Debe capturar al menos una dirección.",
					"¡Precaución!");
		}
		return !validacion;
	}
	self.guardar = function() {
		if ($("#form-cliente").valid() && validarContactos()) {
			var cliente = self.cliente.toJSON();
			console.log(JSON.stringify(cliente));
			var metodo = "POST";
			if (cliente.id) {
				metodo = "PUT";
			}
			$.ajax({
				url : String.format("{0}/app/clientes/", contextPath),
				type : metodo,
				dataType : 'json',
				data : JSON.stringify(cliente),
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					switch (metodo) {
					case "POST":
						toastr.success("Se registró al cliente correctamente.",
								"!Éxito¡");
						self.cliente.limpiar();
						break;
					default:
						toastr.success(
								"Se actualizó al cliente correctamente.",
								"!Éxito¡");
						break;
					}
				}
			});
		}
	}
	self.agregarContacto = function() {
		if ($("#form-modal-contacto").valid()) {
			self.cliente.contactos.push(ko.toJS(self.contacto));
			self.contacto.limpiar();
		}
	};
	self.eliminarContacto = function(contacto) {
		bootbox
				.confirm(
						"Está seguro que desea eliminar el contacto",
						function(result) {
							if (result) {
								if (contacto.id) {
									$
											.ajax({
												cache : false,
												url : String
														.format(
																"{0}/app/clientes/{1}/contactos/{2}",
																contextPath,
																self.cliente
																		.id(),
																contacto.id),
												type : 'DELETE',
												dataType : 'json',
												contentType : 'application/json',
												mimeType : 'application/json',
												success : function(data) {
													toastr
															.success("El contacto se eliminó correctamente");
													self.cliente.contactos
															.remove(contacto);
												}
											});
								} else {
									self.cliente.contactos.remove(contacto);
								}
							}
						});
	}
}
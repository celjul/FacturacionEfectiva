var WizardViewModel = function(data) {
	var self = this;

	self.emisorViewModel = new EmisorViewModel(data);
	self.emisorSelectViewModel = new EmisorSelectViewModel();
	self.direccion = new Direccion();
	self.clienteViewModel = new ClienteViewModel();
	self.productoViewModel = new ProductoViewModel(data);
	self.emisores = ko.observableArray([]);
	self.clientes = ko.observableArray([]);
	self.productos = ko.observableArray([]);
	self.certificados = ko.observableArray([]);
	self.emisor = ko.observable();
	self.visibleFormEmisor = ko.observable(false);
	self.visibleFormCliente = ko.observable(false);
	self.visibleFormProducto = ko.observable(false);
	self.visibleFormCertificado = ko.observable(false);
	self.checkedEmisor = ko.observable(false);
	self.checkedCliente = ko.observable(false);
	self.checkedProducto = ko.observable(false);
	self.checkedCertificado = ko.observable(false);

	var validarRegimenesEmisor = function() {
		var validacion = self.emisorViewModel.emisor.regimenes().length < 1;
		if (validacion) {
			toastr
					.warning("Debe capturar al menos un régimen.",
							"¡Precaución!");
		}

		return !validacion;
	}

	var validarDireccionesEmisor = function() {
		var validacion = self.emisorViewModel.emisor.direcciones().length < 1;
		if (self.emisorViewModel.emisor.direcciones().length < 1) {
			toastr.warning("Debe capturar al menos una dirección.",
					"¡Precaución!");
		}

		return !validacion;
	}

	var validarContactosCliente = function() {
		var validacion = self.clienteViewModel.cliente.contactos().length < 1;
		if (validacion) {
			toastr.warning("Debe capturar al menos un contacto.",
					"¡Precaución!");
		}

		return !validacion;
	}

	var validarDireccionesCliente = function() {
		var validacion = self.clienteViewModel.cliente.direcciones().length < 1;
		if (self.clienteViewModel.cliente.direcciones().length < 1) {
			toastr.warning("Debe capturar al menos una dirección.",
					"¡Precaución!");
		}

		return !validacion;
	}

	self.visibleTrueEmisor = function() {
		return self.visibleFormEmisor(true);
	}

	self.visibleTrueCliente = function() {
		return self.visibleFormCliente(true);
	}

	self.visibleTrueProducto = function() {
		return self.visibleFormProducto(true);
	}

	self.visibleTrueCertificado = function() {
		return self.visibleFormCertificado(true);
	}

	self.visibleFalseEmisor = function() {
		return self.visibleFormEmisor(false);
	}

	self.visibleFalseCliente = function() {
		return self.visibleFormCliente(false);
	}

	self.visibleFalseProducto = function() {
		return self.visibleFormProducto(false);
	}

	self.visibleFalseCertificado = function() {
		return self.visibleFormCertificado(false);
	}

	self.guardarEmisor = function() {
		var metodo = self.emisorViewModel.emisor.id() ? "PUT" : "POST";

		if ($("#form-emisor").valid() && validarRegimenesEmisor()
				&& validarDireccionesEmisor()) {
			var emisor = self.emisorViewModel.emisor.toJSON();

			$
					.ajax({
						cache : false,
						url : String.format("{0}/app/emisores/", contextPath),
						type : metodo,
						dataType : 'json',
						data : JSON.stringify(emisor),
						contentType : 'application/json',
						mimeType : 'application/json',
						success : function(data) {
							var mensaje = self.emisorViewModel.emisor.id() ? "Se ha actualizado al emisor correctamente"
									: "Se ha registrado al emisor correctamente.";
							toastr.success(mensaje, "¡Éxito!");
							self.emisores.push(data);
							self.emisorSelectViewModel.actualizarLista();
							self.visibleFormEmisor(false);
							self.checkedEmisor(true);
							self.emisorViewModel.emisor.limpiar();

						}
					});
		}
	}

	self.colocarEmisor = function() {
		if (self.emisor()) {
			$.ajax({
				url : String.format("{0}/app/wizard/selected/{1}", contextPath,
						self.emisor()),
				type : 'GET',
				dataType : 'json',
				data : '',
				success : function(data) {

				}
			});
		}
	}

	self.guardarCliente = function() {
		if ($("#form-cliente").valid() && validarDireccionesCliente()
				&& validarContactosCliente()) {
			var cliente = self.clienteViewModel.cliente.toJSON();
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
						self.clientes.push(data);
						self.visibleFormCliente(false);
						self.checkedCliente(true);
						self.clienteViewModel.cliente.limpiar();
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

//	self.agregarDireccionCliente = function() {
//		if ($("#form-modal-direccion-cliente").valid()) {
//			self.clienteViewModel.cliente.direcciones.push(ko
//					.toJS(self.direccion));
//			var dire = self.clienteViewModel.cliente.direcciones();
//			self.clienteViewModel.direccion.limpiar();
//		}
//	};

	self.guardarProducto = function() {
		if ($("#form-producto").valid()) {
			var metodo = "POST";
			var producto = self.productoViewModel.producto.getJson();
			producto.exentoIVA = producto.exentoIVA ? true : false;
			if (producto.id) {
				metodo = "PUT";
			}

			$.ajax({
				url : String.format("{0}/app/productos/", contextPath),
				type : metodo,
				dataType : 'json',
				data : JSON.stringify(producto),
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					toastr.success(
							"El producto se ha registrado correctamente.",
							"¡Éxito!");
					self.productos.push(data);
					self.visibleFormProducto(false);
					self.checkedProducto(true);
					if (!producto.id) {
						self.productoViewModel.producto.limpiar();
					}
				}
			});
		}
	}

	// lo siguiente es para guardar un certificado

	self.certificado = new CertificadoPablo(Object.get(data, "certificado"));

	self.editableCer = function() {
		return true;
	}

	$(function() {
		$('#fileuploadcer').fileupload(
				{
					maxFileSize : 5000000,
					dataType : 'json',
					done : function(e, data) {
						$("#uploaded-files-cer tr:has(td)").remove();
						$("#uploaded-files-cer").append(
								$('<tr/>').append(
										$('<td/>').text(data.result.name))
										.append(
												$('<td/>').text(
														data.result.size))
										.append(
												$('<td/>').text(
														data.result.type)));
					},
					progressall : function(e, data) {
						var progress = parseInt(data.loaded / data.total * 100,
								10);
						$('#progresscer .progress-bar').css('width',
								progress + '%');
					}
				});
	});

	self.editableClave = function() {
		return true;
	}

	$(function() {
		$('#fileuploadclave').fileupload(
				{
					maxFileSize : 5000000,
					dataType : 'json',
					done : function(e, data) {
						$("#uploaded-files-clave tr:has(td)").remove();
						$("#uploaded-files-clave").append(
								$('<tr/>').append(
										$('<td/>').text(data.result.name))
										.append(
												$('<td/>').text(
														data.result.size))
										.append(
												$('<td/>').text(
														data.result.type)));
					},
					progressall : function(e, data) {
						var progress = parseInt(data.loaded / data.total * 100,
								10);
						$('#progressclave .progress-bar').css('width',
								progress + '%');
					}
				});
	});

	self.actualizarLista = function() {
		$.get(String.format("{0}/app/emisores/", contextPath), function(data) {
			self.emisores(data);
		});
	}

	self.actualizarLista();

	self.guardarCertificado = function() {
		if ($("#form-certificado").valid()) {
			var certificado = self.certificado.getJson();
			console.log(certificado);
			$.ajax({
				url : String.format("{0}/app/wizard/guardar/certificado",
						contextPath),
				type : 'POST',
				dataType : 'json',
				data : JSON.stringify(certificado),
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					toastr.success(
							"El certificado se ha registrado correctamente.",
							"¡Éxito!");
					self.certificados.push(data);
					self.visibleFormCertificado(false);
					self.checkedCertificado(true);
					if (!certificado.id) {
						self.certificado.limpiar();
					}
				}
			});
		}
	}
}
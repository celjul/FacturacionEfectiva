var EmisorViewModel = function(data) {
	var self = this;
	self.emisor = new Emisor(data ? data.emisor : undefined);
	self.paises = ko.observableArray([]);
	self.regimenes = ko.observableArray([]);
	//self.regimen = ko.observable(undefined);
	//self.direccion = new Direccion();
	self.logo = ko.observable(data ? ("data:image/jpeg;base64," + data.logo)
			: undefined);
	self.visibleMoral = ko.computed(function() {
		return self.emisor.tipo() === "moral";
	}, self);
	$.get(String.format("{0}/app/direcciones/paises", contextPath), function(
			data) {
		self.paises(data);
	});
	
	$.ajax({
        async: false, url: String.format("{0}/app/catalogos/regimenes", contextPath), success: function (items) {
            self.regimenes(items);
        }
    });

	self.clear = function() {
		if (self.visibleMoral()) {
			self.emisor.nombre(undefined);
			self.emisor.apellidoPaterno(undefined);
			self.emisor.apellidoMaterno(undefined);
		} else {
			self.emisor.razonSocial(undefined);
		}
	}
	self.agregarRegimen = function() {
		if (self.regimen() && $.trim(self.regimen()) != "") {
			self.emisor.regimenes.push(ko.toJS(self.regimen));
			self.regimen(undefined);
		}
	};
	self.eliminarRegimen = function(regimen) {
		bootbox.confirm("Está seguro que desea eliminar el régimen", function(
				result) {
			if (result) {
				self.emisor.regimenes.remove(regimen);
			}
		});
	}
	var validarRegimenes = function() {
		var validacion = self.emisor.regimenes().length < 1;
		if (validacion) {
			toastr
					.warning("Debe capturar al menos un régimen.",
							"¡Precaución!");
		}
		return !validacion;
	}
	var validarDirecciones = function() {
		var validacion = self.emisor.direcciones().length < 1;
		if (self.emisor.direcciones().length < 1) {
			toastr.warning("Debe capturar al menos una dirección.",
					"¡Precaución!");
		}
		return !validacion;
	}
	self.guardar = function() {
		var metodo = self.emisor.id() ? "PUT" : "POST";
		if ($("#form-emisor").valid()) {
			var emisor = self.emisor.toJSON();
			console.log(JSON.stringify(emisor));
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
							var mensaje = self.emisor.id() ? "Se ha actualizado al emisor correctamente"
									: "Se ha registrado al emisor correctamente.";
							toastr.success(mensaje, "¡Éxito!");
							self.emisor.limpiar();
						}
					});
		}
	}
}
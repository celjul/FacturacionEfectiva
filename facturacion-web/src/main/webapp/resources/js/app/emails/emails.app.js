var EmailViewModel = function(data) {
	var self = this;
	self.producto = new Producto(data);
	self.unidadesMedida = ko.observableArray([]);

	$.get(String.format("{0}/app/catalogos/{1}", contextPath, "unidadMedida"), function(items) {
		self.unidadesMedida(items);
		
		if (data.producto) {
			self.producto.unidadDeMedida.id(data.producto.unidadDeMedida.id);
		}
	});
	
	self.guardar = function() {
		if ($("#form-producto").valid()) {
			var metodo = "POST";
			var producto = self.producto.getJson();
			
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
					toastr.success("El producto se ha registrado correctamente.", "¡Éxito!");
					
					if (!producto.id) {
						self.producto.limpiar();
					}
				}
			});
		}
	}
}
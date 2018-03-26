var EmisorFacturaViewModel = function(data) {
	var self = this;
	self.emisor = new Emisor(data? data.emisor: undefined);
	self.emisores = ko.observableArray([]);
	self.paquete = new PaqueteUsuario();

	$.get(String.format("{0}/app/emisores/", contextPath), function(data) {
		$.each(data, function(i, item) {
			self.emisores.push(new Emisor(
				item
			));
		})
	});

	$.get(String.format("{0}/app/folios/paquete", contextPath), function(data) {
		self.paquete.cargar(data);
	});

	//Se modifica para que guarde los folios en la entidad factura, ya no existe paquete emisor.
	self.guardar = function() {
		var metodo = "POST";

		var emisores = [];

		ko.utils.arrayForEach(self.emisores(), function(item) {
			emisores.push({
				id: item.id(),
				facturas: item.facturas()
			});
		});

		var emisoresStr = JSON.stringify(emisores);

		console.log(emisoresStr);
		$.ajax({
			cache : false,
			url : String.format("{0}/app/folios/actualizar", contextPath),
			type : metodo,
			dataType : 'json',
			data : emisoresStr,
			contentType : 'application/json',
			mimeType : 'application/json',
			success : function(data) {
				if(data){
					self.emisores.removeAll();
					$.each(data, function(i, item) {
						self.emisores.push(new Emisor(
							item
						));
					});
				}
				toastr.success("Se han actualizado los timbres correctamente", "¡Éxito!");
			}
		});
	}

	/*self.guardar = function() {
		var metodo = self.emisor.id() ? "PUT" : "POST";

		var emisor = self.emisor.toJSON();

		$
				.ajax({
					cache : false,
					url : String.format("{0}/app/folios/guardarPaquete", contextPath),
					type : metodo,
					dataType : 'json',
					data : JSON.stringify(emisor),
					contentType : 'application/json',
					mimeType : 'application/json',
					success : function(data) {
						var mensaje = self.emisor.id() ? "Se han actualizado los timbres correctamente"
								: "Se han registrado los timbres correctamente.";
						toastr.success(mensaje, "¡Éxito!");
						self.emisor.limpiar();
					}
				});
	}*/

	// self.guardar = function() {
	// console.log(ko.toJS(self.emisores));
	//		
	// var datos = ko.toJS(self.emisores);
	//		
	// $.post(url, datos, function(data) {
	//			
	// });
	// }
}

$(function() {
	var modelView = new EmisorFacturaViewModel();
	ko.applyBindings(modelView);
});
var UsuarioListaViewModel = function() {
	var self = this;
	self.usuarios = ko.observableArray([]);

	$.get(String.format("{0}/app/usuarios/", contextPath), function(items) {

		self.usuarios(items);
	});
	
	self.cambiarEstatus = function(data) {
		var message = String.format("Está seguro que desea {0} al usuario", (data.activo ? "deshabilitar" : "habilitar"));
		bootbox.confirm(message, function(result) {
			if (result) {
				$.ajax({ 
					url: String.format("{0}/app/usuarios/{1}/status", contextPath, data.id), 
					type: 'PATCH', 
					dataType: 'json', 
					data: data, 
					contentType: 'application/json',
					mimeType: 'application/json',
					success: function(item) { 
						data.activo = !data.activo;

						$(String.format("td:contains('{0}')", data.login) ).parent()
								.find("td:eq(2)").text(data.activo ? "Si":"No");
						$(String.format("td:contains('{0}')", data.login) ).parent()
								.find("td:eq(3) a").text(data.activo ? "Deshabilitar" : "Habilitar");

						toastr.success("El estatus del usuario fue actualizado", "¡Éxito!");
					}
				});
			}
		});
	}
}
	
$(function () {
	var modelView = new UsuarioListaViewModel()
	ko.applyBindings(modelView);
});
 
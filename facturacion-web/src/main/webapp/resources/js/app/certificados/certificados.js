var CertificadoListaViewModel = function() {
	var self = this;

	self.certificados = ko.observableArray([]);

	$.get(String.format('{0}/app/certificados/', contextPath), function(items) {
		self.certificados(items);
	});

	self.eliminar = function(data) {
		bootbox.confirm("Está seguro que desea eliminar el certificado.", function(result) {
			if (result) {
				$.ajax({
					url : String.format('{0}/app/certificados/{1}', contextPath, data.id),
					type : 'DELETE',
					dataType : 'json',
					success : function(response) {
						if (response) {
							self.certificados.remove(data);
							toastr.success("El certificado ha sido eliminado correctamente.", "!Éxito¡");
						}
					}
				});
			}
		});
		
	}
}

$(function() {
	var modelView = new CertificadoListaViewModel()
	ko.applyBindings(modelView);
});

/*
var AppViewModel = function() {
	var self = this;

	self.certificados = ko.observableArray([]);

}

$(function() {
	$("#frmCertificado").validationEngine();
	$("#btnNewCertificado").button();
	$("#btnNewCertificado").click(function() {
		if (!$("#frmCertificado").validationEngine('validate')) {
			return false;
		}
	});

	$("#selectEmisor").change(function() {
		modelView.borrarCertificados();
		idEmisor = $(this).val();
		if (idEmisor != '') {
			$.ajax({
				cache : false,
				url : String.format('{0}/app/certificados/emisor/{1}', contextPath, idEmisor),
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					if (data.length > 0) {
						$("#divSinCertificados").hide();
						$("#divCertificados").show();
						$.each(data, function(i, item) {
							modelView.agregarCertificado(item);
						});
					} else {
						$("#divCertificados").hide();
						$("#divSinCertificados").show();
					}
				}
			});
		} else {
			$("#divCertificados").hide();
			$("#divSinCertificados").hide();
		}
	});

	var modelView = new AppViewModel()
	ko.applyBindings(modelView);
});
*/
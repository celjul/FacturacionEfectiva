 /**
 *
 */
var FacturasViewModel = function(data) {
	var self = this;
		self.lblclave = ko.observable(Object.get(data,"lblclave"));
		self.fechaInicio = ko.observable(Object.get(data, "fi"));
		self.fechaFin = ko.observable(Object.get(data, "ff"));
		self.tipoDocumento = ko.observable(Object.get(data, "t"));
		self.estatus = ko.observable(Object.get(data, "e"));
		self.montoInicial = ko.observable(Object.get(data, "mi"));
		self.montoFinal = ko.observable(Object.get(data, "mf"));
		self.cliente = new Cliente();
		self.tiposDocumento = ko.observableArray([]);
		self.comprobantes = ko.observableArray([]);
		self.tiposEstatus = ko.observableArray([{id : 1, descripcion : "Activo"}, {id : 0, descripcion : "Cancelado"}]);

		self.cc  = ko.observableArray([]);
	 	self.ccaux=ko.observable();
	 	self.mensaje =ko.observable();
	 	self.id =ko.observable();
	 	self.seleccionadas = ko.observable(false);

	$.get(String.format("{0}/app/catalogos/tipoDocumentos/", contextPath), function(data) {
		self.tiposDocumento(data);
    });

	$.get(String.format("{0}/app/comprobantes/", contextPath), function(data) {
		self.comprobantes(data);
    });

	self.enviarPorEmail = function(data) {
		$.get(String.format("{0}/app/clientes/leer/" + data.idCliente, contextPath), function(data) {
			self.cliente.cargar(data);
		});
		self.id(data.id);
	}

	 self.enviarEmail = function(data) {

		 var variablex = "";
		 var names = "";
		 ko.utils.arrayForEach(data.cliente.contactos(), function(item) {
			 if(item.marcado()){
				 variablex = variablex.concat(item.email()).concat(";");
                 names = names.concat(item.nombre()).concat(" ").concat(item.apellidoPaterno()).concat(",");
			 }
		 });
		 ko.utils.arrayForEach(data.cc(), function(item) {
				 variablex = variablex.concat(item).concat(";");
		 });
		 if (typeof data.mensaje() === 'undefined') {
             data.mensaje(variablex);
		 }
		 console.log(variablex);
			  $.post(String.format("{0}/app/comprobantes/sendMail", contextPath), {id : data.id(), mensaje : data.mensaje(), correos : variablex, nombres : names}, function(response) {
			  		toastr.success("El comprobante ha sido enviado a los emails seleccionados.", "¡Éxito!")
			  });


	 }
	 
	self.cancelarComprobante = function(data) {
		bootbox.confirm("Está seguro que desea cancelar el comprobante", function(result) {
			if (result) {
				$.post(String.format("{0}/app/comprobantes/cancel", contextPath), {id : data.id}, function(response) {
					toastr.success("El comprobante ha sido cancelado.");
					var a = String.format("<a href=\"{0}/app/comprobantes/{1}/file/cancelacion\" target=\"_blank\"><img alt=\"Comprobante Cancelado\" src=\"{0}/resources/img/icon/inactivo.png\"/></a>", contextPath, data.id);
					$("#activo-" + data.id).html(a);
					$("#email-" + data.id).html("");
					//window.location.reload(true);
				});
			}
		});
	}

	self.buscar = function() {
		if ($("#form-list").valid()) {

			var path = String.format("{0}/app/comprobantes/", contextPath);
			var ban = true;
			if (self.fechaInicio() && self.fechaFin()) {
				path = path + String.format("{0}fi={1}&ff={2}", ban ? "?" : "&", self.fechaInicio(), self.fechaFin());
				ban = false;
			}

			if (self.montoInicial() && self.montoFinal()) {
				path = path + String.format("{0}mi={1}&mf={2}", ban ? "?" : "&", self.montoInicial(), self.montoFinal());
				ban = false;
			}

			if (self.tipoDocumento()) {
				path = path + String.format("{0}t={1}", ban ? "?" : "&", self.tipoDocumento() );
				ban = false;
			}

			if (self.cliente.rfc()) {
				path = path + String.format("{0}rfc={1}", ban ? "?" : "&", self.cliente.rfc());
				ban = false;
			}

			if (self.cliente.nombre()) {
				path = path + String.format("{0}nc={1}", ban ? "?" : "&", self.cliente.nombre());
				ban = false;
			}

			/**if (self.cliente.razonSocial()) {
				path = path + String.format("{0}rs={1}", ban ? "?" : "&", self.cliente.razonSocial());
				ban = false;
			}**/

			if (self.estatus() != undefined) {
				path = path + String.format("{0}e={1}", ban ? "?" : "&", self.estatus() == 1);
				ban = false;
			}

			path = path + String.format("{0}_={1}", ban ? "?" : "&", new Date().getTime());

			$.get(path, function(data) {
				self.comprobantes(data);
			});
		}
	}


	self.agregarcc = function(){
		if(self.ccaux() !== undefined){
			 if(Object.validarEmail(self.ccaux())) {
				 self.cc.push(self.ccaux());
				 self.ccaux(undefined);
			 }
			else {
				 toastr.warning("Favor de ingresar un email válido.");
			 }
		}
	 }

	self.eliminarCC = function(data){

		var index = self.cc().indexOf(data);
		if(index >= 0) {
			self.cc.splice(index,1);
		}

		/*ko.utils.arrayForEach(self.cc(), function(item) {
			if(item == data){
				self.cc.remove();

			}
		});*/

	}


	self.linkPDF = ko.computed(function() {
		return link("pdf");
	}, self);



	self.linkExcel = ko.computed(function() {
		return link("xls");
	}, self);

	function link(format) {
		var link = String.format("{0}/app/reportes/{1}/format/{2}?EMISOR={3}", contextPath, "ReporteGeneral", format, window.session.emisor);
		if (self.fechaInicio() && self.fechaFin()) {
			link += String.format("&F_INI={0}&F_FIN={1}", self.fechaInicio(), self.fechaFin());
		}

		if (self.tipoDocumento()) {
			link += String.format("&TIPO={0}", self.tipoDocumento())
		}

		if (self.cliente.id()) {
			link += String.format("&CLIENTE={0}", self.cliente.id())
		}

		if (self.cliente.rfc()) {
			link += String.format("&RFC={0}", self.cliente.rfc())
		}

		if (self.estatus() != undefined) {
			link += String.format("&ACTIVO={0}", self.estatus() == 1)
		}

		link += String.format("&_={0}", new Date().getTime());
		return link;
	}
	
	self.seleccionar = function() {
		var checkboxes = $('input[name=ids')
	    checkboxes.prop('checked', self.seleccionadas());
		return true;
	}
	
	self.descargar = function() {
		var ids = $('input[name="ids"]:checked').serialize();
		console.log(ids);
		if (ids.length > 0) {
			$( "#frmDownload" ).submit();
		}
	}
}

$(document).ready(function() {

	$("#text-fecha-inicio").datepicker();
	$("#text-fecha-fin").datepicker();
	var modelView = new FacturasViewModel();
	ko.applyBindings(modelView);

	var autocompleteClientes = {
			minLength : 3,
			source : function(request, response) {
				$.ajax({
					url: String.format("{0}/app/clientes/find", contextPath),
					dataType: "json",
					data : {
						nombre: $("#text-cliente").val()
					},
					success: function(data) {
						response($.map(data, function(item) {
							return {
								label: item.nombreCompleto,
								value: item.id
							};
						}));
					}
				});
			},
			select: function(event, ui) {
				modelView.cliente.id(ui.item.value);
				$("#text-cliente").val(ui.item.label);
				return false;
			},
			focus: function(event, ui) {
				$("#text-cliente").val(ui.item.label);
				return false;
			},
			change: function(event, ui) {
				if (!ui.item) {
					modelView.cliente.limpiar();
				} else {
					modelView.cliente.id(ui.item.value);
					$("#text-cliente").val(ui.item.label);
				}
				return false;
			}
	};

	$("#text-cliente").autocomplete(autocompleteClientes);
	$("button[datepicker-clean]").click(function() {
		var campo = $(this).attr("datepicker-clean");

		switch(campo) {
			case "text-fecha-inicio":
				modelView.fechaInicio(undefined);
				break;

			case "text-fecha-fin":
				modelView.fechaFin(undefined);
				break;
		}
	});
});
var FacturaViewModel = function (data) {
    var self = this;
    self.nuevo = ko.observable(true);
    var validarConceptos = function (arr) {
        if (arr.length == 0) {
            toastr.warning("El comprobante no tiene conceptos capturados.");
        }
        return arr.length > 0;
    }
    var validarCliente = function (cliente) {
        if (!cliente.id()) {
            toastr.warning("Debe capturar un cliente del catálogo.");
            return false;
        }
        return true;
    }
    self.facturable = ko.observable(data.facturable);
    self.tiposDocumento = ko.observableArray([]);
    self.metodosPago = ko.observableArray([]);
    self.formasPagos = ko.observableArray([]);
    self.clavesUnidad = ko.observableArray([]);
    self.clavesProdServs = ko.observableArray([]);
    self.listaClientes = ko.observableArray([]);
    self.listaSeries = ko.observableArray([]);
    self.plantilla = ko.observable(false);
    self.alertaCertificado = ko.observable(false);
    self.certificado = ko.observable(false);
    self.sinCertificado = ko.observable(false);
    self.disponibles = ko.observable(undefined);
    self.timbres = ko.observable(false);
    self.dias = ko.observable(undefined);
    self.comprobante = new Comprobante(data.comprobante);
    self.partida = new Partida();
    self.setSeccionCompleta = function () {
        ko.utils.arrayForEach(self.listaSeries(), function (item) {
            if (item.id == self.comprobante.serie.id()) {
                self.comprobante.serie.cargar(item);
            }
        });
    }
    $.get(
        String.format("{0}/app/catalogos/{1}", contextPath,
            "tipoDocumentos"), function (items) {
            self.tiposDocumento(items);
            if (data.comprobante && data.comprobante.tipo
                && data.comprobante.tipo.id) {
                self.comprobante.tipo.id(data.comprobante.tipo.id);
            }
        });
    
    $.ajax({
        async: false, url: String.format("{0}/app/catalogos/formasPagos", contextPath), success: function (items) {
            self.formasPagos(items);
        }
    });

    $.ajax({
        async: false, url: String.format("{0}/app/catalogos/clavesUnidad", contextPath), success: function (items) {
            self.clavesUnidad(items);
        }
    });

    $.ajax({
        async: false, url: String.format("{0}/app/catalogos/clavesProdServs", contextPath), success: function (items) {
            self.clavesProdServs(items);
        }
    });
    
    if (session.emisor) {
        $.get(String.format("{0}/app/emisores/{1}/plantilla", contextPath,
            session.emisor), function (plantilla) {
            self.plantilla(plantilla);
        });
        $.get(String.format("{0}/app/certificados/{1}/caducidad", contextPath,
            session.emisor), function (dias) {
            if (dias != null && dias !== "") {
                self.alertaCertificado(dias > 0 && dias <= 20);
                self.certificado(dias <= 0);
                self.dias(dias);
            } else {
                self.sinCertificado(true);
            }
        });
        $
            .get(
            String.format("{0}/app/folios/verificacion",
                contextPath),
            function (data) {
                if (data) {
                    if (data.mensaje) {
                        self.timbres(true);
                        self.disponibles(data.mensaje);
                    }
                } else {
                    self.timbres(true);
                    self
                        .disponibles("No tiene timbres disponibles para poder generar su CDFI, por favor comuniquese con el proveedor.")
                }
            });
    }
    $.get(String.format("{0}/app/catalogos/{1}", contextPath, "metodoPago"),
        function (items) {
            self.metodosPago(items);
            if (data.comprobante && data.comprobante.metodo
                && data.comprobante.metodo.id) {
                self.comprobante.metodo.id(data.comprobante.metodo.id);
            }
        });
    $.get(String.format("{0}/app/series/", contextPath), function (items) {
        self.listaSeries(items);
        if (data.comprobante && data.comprobante.serie
            && data.comprobante.serie.id) {
            self.comprobante.serie.cargar(data.comprobante.serie);
        }
    });
    $.get(String.format("{0}/app/clientes/", contextPath), function (items) {
        self.listaClientes(items);
        if (data.comprobante && data.comprobante.cliente
            && data.comprobante.cliente.id) {
            self.comprobante.cliente.id(data.comprobante.cliente.id);
        }
    });
    
    self.validarCodigoPostal = function () {
    //TODO
        /*if(self.comprobante.lugarDeExpedicion() && self.comprobante.lugarDeExpedicion().length == 5){
            $.ajax({
                async: false, url: String.format("{0}/app/catalogos/formasPagos", contextPath), success: function (items) {
                    self.formasPagos(items);
                }
            });
        }*/
    };
    
    self.agregarConcepto = function () {
    	 if ($("#form-modal-concepto").valid()) {
             //var partida = self.partida.toJS();
             var partida = new Partida(self.partida.toJS());
             self.comprobante.conceptos.push(partida);
             self.partida.limpiar();
         }
    };
    self.eliminarPartida = function (partida) {
        bootbox
            .confirm(
            "Está seguro que desea eliminar esta partida",
            function (result) {
                if (result) {
                    if (partida.id) {
                        $
                            .ajax({
                                cache: false,
                                url: String
                                    .format(
                                    "{0}/app/comprobantes/{1}/partida/{2}",
                                    contextPath,
                                    self.comprobante
                                        .id(),
                                    partida.id()),
                                type: 'DELETE',
                                dataType: 'json',
                                contentType: 'application/json',
                                mimeType: 'application/json',
                                success: function (data) {
                                    toastr
                                        .success(
                                        "La partida se eliminó correctamente.",
                                        "!Éxito¡");
                                    self.comprobante.conceptos
                                        .remove(partida);
                                }
                            });
                    } else {
                        self.comprobante.conceptos.remove(partida);
                    }
                }
            });
    }
    self.cargarPartida = function (partida, index) {
        self.index = index();
        self.nuevo(false);
        self.partida.cargar(self.comprobante.conceptos()[self.index].toJS());
        $("#modal-concepto").modal("show");
    }
    self.editarConcepto = function () {
        if (self.index != undefined && $("#form-modal-concepto").valid()) {
            self.changeStatus();
            var partida = self.partida.toJS();
            self.comprobante.conceptos.replace(
                self.comprobante.conceptos()[self.index], partida);
            self.partida.limpiar();
            self.nuevo(true);
            delete self.index;
            $("#modal-concepto").modal("hide");
        }
    }
    self.changeStatus = function () {
        self.facturable(false);
    }
    self.changeNumeroCuenta = function () {
        if (self.comprobante.metodo.id() == 3
            || self.comprobante.metodo.id() == 4
            || self.comprobante.metodo.id() == 15
            || self.comprobante.metodo.id() == 16
            || self.comprobante.metodo.id() == 17
            || self.comprobante.metodo.id() == 18
            || self.comprobante.metodo.id() == 27) {
            self.comprobante.numeroCuenta("N.A.");
        } else {
            self.comprobante.numeroCuenta("");
        }
    }
    self.guardar = function () {
        if ($("#form-comprobante").valid()
            && validarConceptos(self.comprobante.conceptos())
            && validarCliente(self.comprobante.cliente)) {
            var metodo = "POST";
            var cliente = self.comprobante.cliente.toJSON();
            var comprobante = self.comprobante.getJson();
            if (comprobante.id) {
                metodo = "PUT";
            }
            $.ajax({
                url: String.format("{0}/app/comprobantes/", contextPath),
                type: metodo,
                dataType: 'json',
                data: JSON.stringify(comprobante),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    self.facturable(true);
                    data.cliente = cliente;
                    toastr.success(
                        "El comprobante esta listo para ser timbrado.",
                        "¡Éxito!");
                    self.comprobante.cargar(data);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    toastr.error(errorThrown);
                }
            });
        }
    }
    self.facturar = function () {
        if (self.facturable()) {
            if (self.disponibles() == null || self.disponibles() == undefined
                || self.disponibles() == "") {
                $.blockUI({
                    message: "Cargando...",
                    css: {
                        border: 'none',
                        padding: '15px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: .5,
                        color: '#fff',
                    }
                });
                $.form(
                    String.format("{0}/app/comprobantes/timbrar",
                        contextPath), {
                        id: self.comprobante.id()
                    }, "POST").submit();
            } else {
                toastr.warning(self.disponibles());
            }
        } else {
            toastr
                .warning(
                "El documento ha sido modificado y no es facturable, debe guardarlo nuevamente antes de facturar.",
                "¡Advertencia!");
        }
    }
}
$(document)
    .ready(
    function () {
        $("#txt-fecha-entrega").datepicker({
            minDate: new Date()
        });
        var modelView = new FacturaViewModel(data);
        ko.applyBindings(modelView);
        var autocompleteClientes = {
            minLength: 3,
            source: function (request, response) {
                $.ajax({
                    url: String.format("{0}/app/clientes/find",
                        contextPath),
                    dataType: "json",
                    data: {
                        nombre: $("#txt-cliente").val()
                    },
                    success: function (data) {
                        response($.map(data, function (item) {
                            return {
                                label: item.nombreCompleto,
                                value: item.id
                            };
                        }));
                    }
                });
            },
            select: function (event, ui) {
                modelView.comprobante.cliente.id(ui.item.value);
                $("#txt-cliente").val(ui.item.label);
                return false;
            },
            focus: function (event, ui) {
                $("#txt-cliente").val(ui.item.label);
                return false;
            },
            change: function (event, ui) {
                if (!ui.item) {
                    modelView.comprobante.cliente.limpiar();
                } else {
                    modelView.comprobante.cliente.id(ui.item.value);
                    $("#txt-cliente").val(ui.item.label);
                }
                return false;
            }
        };
        var autocompleteProductos = {
            minLength: 3,
            source: function (request, response) {
                $
                    .ajax({
                        url: String.format(
                            "{0}/app/catalogos/filtro/clavesProdServs",
                            contextPath),
                        dataType: "json",
                        data: {
                        	key: $("#txt-prodserv").val()
                        },
                        success: function (data) {
                            response($
                                .map(
                                data,
                                function (item) {
                                    return {
                                        label: String.format("{0} - {1}", item.clave, item.descripcion),
                                        value: item.id,
                                        id: item.id,
                                        clave: item.clave,
                                        descripcion: item.descripcion
                                    };
                                }));
                        }
                    });
            },
            select: function (event, ui) {
            	modelView.partida.claveProdServ.cargar(
                        {
                            id: ui.item.id,
                            clave: ui.item.clave,
                            descripcion: ui.item.descripcion
                        }
                    );
                $("#txt-prodserv").val(ui.item.label);
                return false;
            },
            focus: function (event, ui) {
            	modelView.partida.claveProdServ.cargar(
                        {
                            id: ui.item.id,
                            clave: ui.item.clave,
                            descripcion: ui.item.descripcion
                        }
                    );
                $("#txt-prodserv").val(ui.item.label);
                return false;
            }
        }
        $("#txt-cliente").autocomplete(autocompleteClientes);
//        $("#txt-codigo").autocomplete(autocompleteProductos);
//        $("#txt-descripcion").autocomplete(autocompleteProductos);
        $("#txt-prodserv").autocomplete(autocompleteProductos);
        $("#modal-concepto").on("hide.bs.modal", function () {
            modelView.partida.limpiar();
            modelView.nuevo(true);
        });
        $("#txt-prodserv").autocomplete(autocompleteProductos);
    });
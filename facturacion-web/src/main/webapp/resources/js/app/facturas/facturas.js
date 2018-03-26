var Comprobante = function(data) {
	var self = this;
	self.id = ko.observable(Object.get(data, "id"));
	self.cliente = new Cliente(Object.get(data, "cliente"));
	self.tipo = new Catalogo(Object.get(data, "tipo"));
	self.serie = new Serie(Object.get(data, "serie"));
	self.fechaCreacion = ko.observable(Object.get(data, "fechaCreacion"));
	self.fechaEntrega = ko.observable(Object.get(data, "fechaEntrega"));
	self.descuento = ko.observable(Object.getDefault(data, "descuento", 0.0));
	self.iva = ko.observable(Object.getDefault(data, "iva", 0.16) * 100);
	self.isr = ko.observable(0.0);
	self.condicion = ko.observable(Object.get(data, "condicion"));
	self.metodo = new Catalogo(Object.get(data, "metodo"));
	self.forma = new Catalogo(Object.get(data, "forma"));
	self.numeroCuenta = ko.observable(Object.get(data, "numeroCuenta"));
	self.motivoDescuento = ko.observable(Object.get(data, "motivoDescuento"));
	self.leyendaComprobante = ko.observable(Object.get(data,"leyendaComprobante"));
	self.lugarDeExpedicion = ko.observable(Object.get(data, "lugarDeExpedicion"));
	self.conceptos = ko.observableArray([]);
    if(data && data.conceptos){
        ko.utils.arrayForEach(data.conceptos, function (item) {
            var conc = new Partida(item);
            self.conceptos.push(conc);
        });
    }
	self.retencionIva = ko.observable(Object.getDefault(data, "retencionIva", 0.0) * 100);
	self.retencionIsr = ko.observable(Object.getDefault(data, "retencionIsr", 0.0) * 100);
	self.subtotal = ko.computed(function() {
		var subtotal = 0;
		ko.utils.arrayForEach(self.conceptos(), function(item) {
			subtotal += parseFloat(item.cantidad()*item.precioUnitario());
		});
		return subtotal.toFixed(2);
	});
	self.montoDelDescuento = ko.computed(function() {
		//Ahora se suman los descuentos de los conceptos
        var descuento = 0;
        ko.utils.arrayForEach(self.conceptos(), function (item) {
        	descuento += parseFloat(item.descuento() * item.importe());
        });

        return descuento.toFixed(2);
        /*var montoDescuento = 0;
        montoDescuento = self.subtotal() * self.descuento() / 100;

        return montoDescuento.toFixed(2);*/
	});
	
	self.montoIva = ko.computed(function() {
		//Ahora se suman los impuestos de los conceptos
        var monto = 0;
        ko.utils.arrayForEach(self.conceptos(), function (item) {
        	monto += parseFloat((item.ivaTrasladado()) * (item.importe() - (item.descuento() * item.importe())));
        });

        return monto.toFixed(2);
    /*return ((self.subtotal() - self.montoDelDescuento())
    * self.iva() / 100).toFixed(2);*/
	});
	
	self.montoIeps = ko.computed(function () {
        //Ahora se suman los impuestos de los conceptos
        var monto = 0;
        ko.utils.arrayForEach(self.conceptos(), function (item) {
        	monto += parseFloat((item.iepsTrasladado()) * (item.importe() - (item.descuento() * item.importe())));
        });

        return monto.toFixed(2);
    //return (self.montoDelDescuento() * (self.isr() / 100)).toFixed(2);
	});
	
	self.montoRetIva = ko.computed(function () {
        //Ahora se suman los impuestos de los conceptos
        var monto = 0;
        ko.utils.arrayForEach(self.conceptos(), function (item) {
        	monto += parseFloat((item.ivaRetenido()) * (item.importe() - (item.descuento() * item.importe())));
        });

        return monto.toFixed(2);
    /*var subtotal = (self.subtotal() - self.montoDelDescuento());

    return (subtotal * (self.retencionIva() / 100)).toFixed(2);*/
	});
	
	self.montoRetIsr = ko.computed(function() {
		//Ahora se suman los impuestos de los conceptos
        var monto = 0;
        ko.utils.arrayForEach(self.conceptos(), function (item) {
        	 monto += parseFloat((item.isrRetenido()) * (item.importe() - (item.descuento() * item.importe())));
        });

        return monto.toFixed(2);
        /*var subtotal = (self.subtotal() - self.montoDelDescuento());

        return (subtotal * (self.retencionIsr() / 100)).toFixed(2);*/
	})
	
	self.total = ko.computed(function() {
		return (parseFloat(self.subtotal())
		        - parseFloat(self.montoDelDescuento())
		        + parseFloat(self.montoIva()) + parseFloat(self.montoIeps()) - self.montoRetIva() - self
		            .montoRetIsr()).toFixed(2);
	});
	
	self.cargar = function(data) {
		self.id(Object.get(data, "id"));
		self.cliente.cargar(Object.get(data, "cliente"))
		self.tipo.cargar(Object.get(data, "tipo"));
		self.serie.cargar(Object.get(data, "serie"));
		self.fechaCreacion(Object.get(data, "fechaCreacion"));
		self.fechaEntrega(Object.get(data, "fechaEntrega"));
		self.descuento(Object.getDefault(data, "descuento", 0.0) * 100);
		self.iva(Object.getDefault(data, "iva", 0.16) * 100);
		self.isr(0.0);
		self.condicion(Object.get(data, "condicion"));
		self.metodo.cargar(Object.get(data, "metodo"));
		self.forma.cargar(Object.get(data, "forma"));
		self.numeroCuenta(Object.get(data, "numeroCuenta"));
		self.motivoDescuento(Object.get(data, "motivoDescuento"));
		self.leyendaComprobante(Object.get(data, "leyendaComprobante"));
		self.lugarDeExpedicion(Object.get(data, "lugarDeExpedicion"));
		self.conceptos = ko.observableArray([]);
        if(data && data.conceptos){
            ko.utils.arrayForEach(data.conceptos, function (item) {
                var conc = new Partida(item);
                self.conceptos.push(conc);
            });
        }
		self.retencionIva(Object.getDefault(data, "retencionIva", 0.0) * 100);
		self.retencionIsr(Object.getDefault(data, "retencionIsr", 0.0) * 100);
	}
	self.getJson = function() {
		var comprobante = ko.toJS(self);
        Object.only(comprobante.cliente, ["id"]);
        if(comprobante.cliente.razonSocial){
            comprobante.cliente["@class"] = "com.entich.ezfact.clientes.model.ClientePersonaMoral";
        } else {
            comprobante.cliente["@class"] = "com.entich.ezfact.clientes.model.ClientePersonaFisica";
        }

        delete comprobante.retencionIva;
        delete comprobante.retencionIsr;
        delete comprobante.iva;
        delete comprobante.isr;

        $.each(comprobante.conceptos, function (i, item) {
            delete item.importe;
        });

        Object
            .deleteProperties(comprobante, ["montoDelDescuento",
                "montoIsr", "montoIeps", "montoIva", "montoRetIsr", "montoRetIva",
                "subtotal", "total"])
        Object.only(comprobante.tipo, ["id"]);
        Object.only(comprobante.metodo, ["id"]);

        return comprobante;
	}
}
var Partida = function(data) {
	var self = this;
	self.id = ko.observable(Object.get(data, "id"));
	self.cantidad = ko.observable(Object.getDefault(data, "cantidad", "1"));
	self.claveProdServ = new Catalogo(Object.get(data, "claveProdServ"));
	self.codigo = ko.observable(Object.get(data, "codigo"));
	self.descripcion = ko.observable(Object.get(data, "descripcion"));
	self.descuento = ko.observable(Object.getDefault(data, "descuento", "0.00"));
	self.unidadDeMedida = ko.observable(Object.get(data, "unidadDeMedida"));
	self.claveUnidad = new Catalogo(Object.get(data, "claveUnidad"));
	self.precioUnitario = ko.observable(Object.getDefault(data, "precioUnitario", "0.00"));
	self.ivaTrasladado = ko.observable(Object.getDefault(data, "ivaTrasladado", "16.00"));
    self.iepsTrasladado = ko.observable(Object.getDefault(data, "iepsTrasladado", "0.00"));
    self.ivaRetenido = ko.observable(Object.getDefault(data, "ivaRetenido", "0.00"));
    self.isrRetenido = ko.observable(Object.getDefault(data, "isrRetenido", "0.00"));
	
	self.importe = ko.computed(function() {
		var imp = 0.00;
		 if (self.precioUnitario() && self.cantidad()) {
	            imp = self.precioUnitario() * self.cantidad();
	            //El importe ya no implica descuentos
	            //imp = imp - (imp * (self.descuento() / 100));
	    }
		return imp.toFixed(6)
	});
	self.limpiar = function() {
		self.id(undefined);
		self.cantidad(1);
		self.claveProdServ.limpiar();
		self.codigo(undefined);
		self.descripcion(undefined);
		self.descuento(0.0);
		self.unidadDeMedida(undefined);
		self.claveUnidad.limpiar();
		self.precioUnitario(undefined);
		
		self.ivaTrasladado(16);
        self.iepsTrasladado(0);
        self.ivaRetenido(0);
        self.isrRetenido(0);
	}
	self.cargar = function(data) {
		self.id(Object.get(data, "id"));
		self.cantidad(Object.get(data, "cantidad"));
		self.claveProdServ.cargar(Object.get(data, "claveProdServ"));
		self.codigo(Object.get(data, "codigo"));
		self.descripcion(Object.get(data, "descripcion"));
		self.descuento(Object.getDefault(data, "descuento", "0.00") * 100);
		self.unidadDeMedida(Object.get(data, "unidadDeMedida"));
		self.claveUnidad.cargar(Object.get(data, "claveUnidad"));
		self.precioUnitario(Object.getDefault(data, "precioUnitario", "0.00"));
		self.ivaTrasladado(Object.getDefault(data, "ivaTrasladado", "0.00") * 100);
        self.iepsTrasladado(Object.getDefault(data, "iepsTrasladado", "0.00") * 100);
        self.ivaRetenido(Object.getDefault(data, "ivaRetenido", "0.00") * 100);
        self.isrRetenido(Object.getDefault(data, "isrRetenido", "0.00") * 100);
	}
	self.toJS = function() {
		var partida = ko.toJS(self);
		partida.descuento = partida.descuento / 100;
		
		partida.ivaTrasladado = partida.ivaTrasladado / 100;
        partida.iepsTrasladado = partida.iepsTrasladado / 100;
        partida.ivaRetenido = partida.ivaRetenido / 100;
        partida.isrRetenido = partida.isrRetenido / 100;

        partida.precioUnitario = parseFloat(partida.precioUnitario);
	    partida.cantidad = parseInt(partida.cantidad);
	    
		return partida;
	}
}
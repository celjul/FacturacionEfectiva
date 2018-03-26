package com.entich.ezfact.facturacion.factory;

import java.util.Date;

import com.entich.ezfact.facturacion.model.Comprobante;

public class ComprobanteFactory {
	public static Comprobante newInstance() {
		Comprobante comprobante = new Comprobante();
		comprobante.setFechaCreacion(new Date());
		comprobante.setFechaEntrega(new Date());

		return comprobante;
	}
}

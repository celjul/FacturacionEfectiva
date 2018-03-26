package com.entich.ezfact.facturacion.factory;

import java.math.BigDecimal;

import com.entich.ezfact.facturacion.model.Concepto;

public class ConceptoFactory {
	public static Concepto newInstance() {
		Concepto concepto = new Concepto();
		concepto.setCantidad(Double.valueOf(1.0D));
		concepto.setDescuento(BigDecimal.ZERO);

		return concepto;
	}

	public static Concepto newInstance(Long id) {
		Concepto concepto = new Concepto();
		concepto.setId(id);
		
		return concepto;
	}
}

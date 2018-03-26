package com.entich.ezfact.facturacion.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

@Entity
@DiscriminatorValue("metodoPago")
public class MetodoPago extends OpcionDeCatalogo {
	private static final long serialVersionUID = 7410266239039897120L;
}

package com.entich.ezfact.folios.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

@Entity
@DiscriminatorValue("tipoPaquete")
public class TipoPaquete extends OpcionDeCatalogo {
	private static final long serialVersionUID = -5907955998575447778L;
}

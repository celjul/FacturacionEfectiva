package com.entich.ezfact.facturacion.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

@Entity
@DiscriminatorValue("tipoDocto")
public class TipoDocumento extends OpcionDeCatalogo {
	private static final long serialVersionUID = 7410266099039897120L;
}

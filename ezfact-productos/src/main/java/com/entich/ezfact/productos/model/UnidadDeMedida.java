package com.entich.ezfact.productos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

@Entity
@DiscriminatorValue("unidadMedida")
public class UnidadDeMedida extends OpcionDeCatalogo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 155784932638567012L;

}

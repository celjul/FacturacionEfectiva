package com.entich.ezfact.facturacion.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 17-11-2017
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue("claveUnidad")
public class ClaveUnidad extends OpcionDeCatalogo {
	
	private static final long serialVersionUID = -7281824942950243147L;
}

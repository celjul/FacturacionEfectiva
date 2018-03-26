package com.entich.ezfact.facturacion.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 14-11-2017
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue("formaPago")
public class FormaPago extends OpcionDeCatalogo {
	
	private static final long serialVersionUID = -7702546490114490612L;
}

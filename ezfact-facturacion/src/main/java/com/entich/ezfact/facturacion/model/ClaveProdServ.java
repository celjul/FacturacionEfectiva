package com.entich.ezfact.facturacion.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 16-11-2017
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue("claveProdServ")
public class ClaveProdServ extends OpcionDeCatalogo {
	
	private static final long serialVersionUID = 1036004305136910997L;

}

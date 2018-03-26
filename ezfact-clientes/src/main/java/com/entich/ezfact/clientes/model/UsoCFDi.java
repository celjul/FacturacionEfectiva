package com.entich.ezfact.clientes.model;

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
@DiscriminatorValue("usoCFDi")
public class UsoCFDi extends OpcionDeCatalogo {
	
	private static final long serialVersionUID = -5123239583126023877L;
}

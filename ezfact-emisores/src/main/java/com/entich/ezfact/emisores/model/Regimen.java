package com.entich.ezfact.emisores.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 15-11-2017
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue("regimen")
public class Regimen extends OpcionDeCatalogo {
	
	private static final long serialVersionUID = -6080254294344405628L;
}

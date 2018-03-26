package com.entich.test.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

/**
 * Clase de pruebas para realizar lo test de la factoria actualizada
 * 
 * @author Luis Angel Cardenas
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue(value = "test")
public class OpcionTest extends OpcionDeCatalogo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3483509835695308280L;
}

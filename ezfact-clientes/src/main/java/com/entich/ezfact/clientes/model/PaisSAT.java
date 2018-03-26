package com.entich.ezfact.clientes.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 18-11-2017
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue("pais")
public class PaisSAT extends OpcionDeCatalogo {
	
	private static final long serialVersionUID = -6323147879774235207L;
}

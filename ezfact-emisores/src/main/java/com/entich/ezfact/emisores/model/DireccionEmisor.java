package com.entich.ezfact.emisores.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.entich.commons.direcciones.model.Direccion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@Entity
@Table(name = "tdireccionesemisor")
@JsonIgnoreProperties({"emisor"})
public class DireccionEmisor extends Direccion {

	/**
	 * Atributo que representa el número de versión único de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo que representa el emisor al que pertenece la dirección. No puede
	 * ser nulo.
	 */
	@NotNull
	@JoinColumn(name = "NIdEmisor", nullable = false, referencedColumnName = "NIdEmisor")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Emisor emisor;

	/**
	 * Método constructor por defecto.
	 */
	public DireccionEmisor() {
	}

	/**
	 * Método que obtiene el emisor de la dirección.
	 * 
	 * @return el emisor
	 * @see #emisor
	 */
	public Emisor getEmisor() {
		return emisor;
	}

	/**
	 * Método que asigna el emisor a la direcicón.
	 * 
	 * @param emisor
	 *            el emisor
	 * @see #emisor
	 */
	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}
}
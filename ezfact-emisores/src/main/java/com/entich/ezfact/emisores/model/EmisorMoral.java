package com.entich.ezfact.emisores.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entidad que representa un emisor persona moral.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@Entity
@DiscriminatorValue("Moral")
public class EmisorMoral extends Emisor {

	/**
	 * Atributo que representa el número de versión único de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo que representa la razón social del emisor. No puede ser nulo o
	 * vacío. La longitud máxima es de 128 caracteres.
	 */
	@Size(max = 128, message = "{com.entich.ezfact.emisores.model.EmisorMoral.razonSocial.Size}")
	@NotEmpty(message = "{com.entich.ezfact.emisores.model.EmisorMoral.razonSocial.NotEmpty}")
	@Column(length = 128, name = "VRazonSocial", nullable = true)
	private String razonSocial;

	/**
	 * Método constructor por defecto.
	 */
	public EmisorMoral() {
	}

	/**
	 * Método que obtiene la razón social del emisor.
	 * 
	 * @return la razón social
	 * @see #razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Método que asigna la razón social al emisor.
	 * 
	 * @param razonSocial
	 *            la razón social
	 * @see #razonSocial
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
}
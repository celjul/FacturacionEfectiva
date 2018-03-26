package com.entich.ezfact.clientes.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que extiende de <code>Cliente</code> para representar los Clientes del
 * tipo Persona Moral
 * 
 * @author Pedro Josue Mendoza Islas
 */
@Entity
@DiscriminatorValue("Moral")
public class ClientePersonaMoral extends Cliente {

	private static final long serialVersionUID = -7522782141094615076L;

	@Size(message = "{com.entich.ezfact.clientes.model.ClientePersonaMoral.razonSocial.Size}", max = 255)
	@NotEmpty(message = "{com.entich.ezfact.clientes.model.ClientePersonaMoral.razonSocial.NotEmpty}")
	@Column(name = "VRazonSocial", length = 255, nullable = true)
	private String razonSocial;

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
}
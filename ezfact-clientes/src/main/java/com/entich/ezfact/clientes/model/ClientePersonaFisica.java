package com.entich.ezfact.clientes.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que extiende de <code>Cliente</code> para representar los Clientes del
 * tipo Persona Fisica
 * 
 * @author Pedro Josue Mendoza Islas
 */
@Entity
@DiscriminatorValue("Fisica")
public class ClientePersonaFisica extends Cliente {

	private static final long serialVersionUID = 7483669189556273654L;

	@Size(message="{com.entich.ezfact.clientes.model.ClientePersonaFisica.nombre.Size}",
			max = 255)
	@NotEmpty(message = "{com.entich.ezfact.clientes.model.ClientePersonaFisica.nombre.NotEmpty}")
	@Column(name = "VNombre", length = 255, nullable = true)
	private String nombre;

	@Size(message = "{com.entich.ezfact.clientes.model.ClientePersonaFisica.apellidoPaterno.Size}",
			max = 255)
	@NotEmpty(message = "{com.entich.ezfact.clientes.model.ClientePersonaFisica.apellidoPaterno.NotEmpty}")
	@Column(name = "VApellidoPaterno", length = 255, nullable = true)
	private String apellidoPaterno;

	@Size(message = "{com.entich.ezfact.clientes.model.ClientePersonaFisica.apellidoMaterno.Size}", 
			max = 255)
	@Column(name = "VApellidoMaterno", length = 255, nullable = true)
	private String apellidoMaterno;

	public ClientePersonaFisica() {}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
}
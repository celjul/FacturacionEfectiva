package com.entich.ezfact.clientes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.entich.commons.direcciones.model.Direccion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase para representar las Direcciones de los Clientes
 * 
 * @author Pedro Josue Mendoza Islas
 */
@Entity
@Table(name = "tdireccionescliente")
@JsonIgnoreProperties({"cliente"})
public class DireccionCliente extends Direccion {

	private static final long serialVersionUID = 3968182997641218032L;

	@Size(max = 255)
	@NotEmpty
	@Column(name = "VLocalidad", length = 255, nullable = false)
	private String localidad;

	@NotNull
	@JoinColumn(name = "NIdCliente", nullable = false, referencedColumnName = "NIdCliente")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Cliente cliente;

	public DireccionCliente() {
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}

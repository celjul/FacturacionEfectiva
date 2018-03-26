package com.entich.ezfact.emisores.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entidad que representa un emisor persona física.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@Entity
@DiscriminatorValue("Fisica")
public class EmisorFisica extends Emisor {

	/**
	 * Atributo que representa el número de versión único de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo que representa el apellido materno del emisor. La longitud
	 * máxima es de 64 caracteres.
	 */
	@Size(max = 64, message = "{com.entich.ezfact.emisores.model.EmisorFisica.apellidoMaterno.Size}")
	@Column(length = 64, name = "VApellidoMaterno", nullable = true)
	private String apellidoMaterno;

	/**
	 * Atributo que representa el apellido paterno del emisor. No puede ser nulo
	 * o vacío. La longitud máxima es de 64 caracteres.
	 */
	@Size(max = 64, message = "{com.entich.ezfact.emisores.model.EmisorFisica.apellidoPaterno.Size}")
	@NotEmpty(message = "{com.entich.ezfact.emisores.model.EmisorFisica.apellidoPaterno.NotEmpty}")
	@Column(length = 64, name = "VApellidoPaterno", nullable = true)
	private String apellidoPaterno;

	/**
	 * Atributo que representa el nombre del emisor. No puede ser nulo o vacío.
	 * La longitud máxima es de 64 caracteres.
	 */
	@Size(max = 64, message = "{com.entich.ezfact.emisores.model.EmisorFisica.nombre.Size}")
	@NotEmpty(message = "{com.entich.ezfact.emisores.model.EmisorFisica.nombre.NotEmpty}")
	@Column(length = 64, name = "VNombre", nullable = true)
	private String nombre;

	/**
	 * Método constructor por defecto.
	 */
	public EmisorFisica() {
	}

	/**
	 * Método que obtiene el apellido materno del emisor.
	 * 
	 * @return el apellido materno
	 * @see #apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * Método que asigna el apellido materno al emisor.
	 * 
	 * @param apellidoMaterno
	 *            el apellido materno
	 * @see #apellidoMaterno
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * Método que obtiene el apellido paterno del emisor.
	 * 
	 * @return el apellido paterno
	 * @see #apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * Método que asigna el apellido paterno al emisor.
	 * 
	 * @param apellidoPaterno
	 *            el apellido paterno
	 * @see #apellidoPaterno
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * Método que obtiene el nombre del emisor.
	 * 
	 * @return el nombre
	 * @see #nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que asigna el nombre al emisor.
	 * 
	 * @param nombre
	 *            el nombre
	 * @see #nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
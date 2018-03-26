package com.entich.commons.direcciones.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entidad que representa una colonia.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@Entity
@Table(name = "tcolonias")
public class Colonia implements Serializable {

	/**
	 * Atributo que representa el número de versión único de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo que representa el código postal de la colonia. No puede ser nulo
	 * o vacío. Su longitud es de 5 caracteres.
	 */
	@Size(min = 5, max = 5)
	@Column(length = 5, name = "VCodigoPostal", nullable = false)
	private String codigoPostal;

	/**
	 * Atributo que representa el identificador de la colonia. Es único.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdColonia", nullable = false, unique = true)
	private Long id;

	/**
	 * Atributo que representa el municipio de la colonia. No puede ser nulo.
	 */
	@JsonIgnore
	@NotNull
	@JoinColumn(name = "NIdMunicipio", nullable = false, referencedColumnName = "NIdMunicipio")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Municipio municipio;

	/**
	 * Atributo que representa el nombre de la colonia. No puede ser nulo o
	 * vacío. La longitud máxima es de 256 caracteres.
	 */
	@Size(min = 1, max = 256)
	@Column(length = 256, name = "VNombre", nullable = false)
	private String nombre;

	/**
	 * Método constructor por defecto.
	 */
	public Colonia() {
	}

	/**
	 * Método que obtiene el código postal de la colonia.
	 * 
	 * @return el código postal
	 * @see #codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * Método que asigna el código postal a la colonia.
	 * 
	 * @param codigoPostal
	 *            el código postal
	 * @see #codigoPostal
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * Método que obtiene el id de la colonia.
	 * 
	 * @return el id
	 * @see #id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método que asigna el id a la colonia.
	 * 
	 * @param id
	 *            el id
	 * @see #id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método que obtiene el municipio de la colonia.
	 * 
	 * @return el municipio
	 * @see #municipio
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * Método que asigna el municipio a la colonia.
	 * 
	 * @param municipio
	 *            el municipio
	 * @see #municipio
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	/**
	 * Método que obtiene el nombre de la colonia.
	 * 
	 * @return el nombre
	 * @see #nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que asigna el nombre a la colonia.
	 * 
	 * @param nombre
	 *            el nombre
	 * @see #nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Colonia other = (Colonia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
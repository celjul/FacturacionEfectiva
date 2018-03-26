package com.entich.commons.direcciones.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entidad que representa un país.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@Entity
@Table(name = "tpaises")
@JsonIgnoreProperties("estados")
public class Pais implements Serializable {

	/**
	 * Atributo que representa el número de versión único de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo que representan los estados asociados al país.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pais", orphanRemoval = true)
	private Collection<Estado> estados;

	/**
	 * Atributo que representa el identificador del país. Es único.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdPais", nullable = false, unique = true)
	private Long id;

	/**
	 * Atributo que representa el nombre del país. No puede ser nulo o vacío. La
	 * longitud máxima es de 64 caracteres.
	 */
	@Size(max = 64)
	@NotEmpty
	@Column(length = 64, name = "VNombre", nullable = false)
	private String nombre;

	/**
	 * Método constructor por defecto.
	 */
	public Pais() {
	}

	/**
	 * Método que obtiene los estados del país.
	 * 
	 * @return los estados
	 * @see #estados
	 */
	public Collection<Estado> getEstados() {
		return estados;
	}

	/**
	 * Método que asigna estados al país.
	 * 
	 * @param estados
	 *            los estados
	 * @see #estados
	 */
	public void setEstados(Collection<Estado> estados) {
		this.estados = estados;
	}

	/**
	 * Método que obtiene el id del país-
	 * 
	 * @return el id
	 * @see #id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método que asigna el id al país.
	 * 
	 * @param id
	 *            el id
	 * @see #id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método que obtiene el nombre del país.
	 * 
	 * @return el nombre
	 * @see #nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que asigna el nombre al país.
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
		Pais other = (Pais) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
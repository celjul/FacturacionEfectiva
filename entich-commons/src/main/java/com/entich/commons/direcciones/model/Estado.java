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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entidad que representa un estado.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@Entity
@Table(name = "testados")
@JsonIgnoreProperties({"municipios", "pais"})
public class Estado implements Serializable {

	/**
	 * Atributo que representa el número de versión único de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo que representa el identificador del estado. Es único.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdEstado", nullable = false, unique = true)
	private Long id;

	/**
	 * Atributo que representan los municipios asociados al estado.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "estado", orphanRemoval = true)
	private Collection<Municipio> municipios;

	/**
	 * Atributo que representa el nombre del estado. No puede ser nulo o vacío.
	 * La longitud máxima es de 96 caracteres.
	 */
	@Size(max = 96)
	@NotEmpty
	@Column(length = 96, name = "VNombre", nullable = false)
	private String nombre;

	/**
	 * Atributo que representa el país del estado. No puede ser nulo.
	 */
	@NotNull
	@JoinColumn(name = "NIdPais", nullable = false, referencedColumnName = "NIdPais")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Pais pais;

	/**
	 * Método constructor por defecto.
	 */
	public Estado() {
	}

	/**
	 * Método que obtiene el id del estado.
	 * 
	 * @return el id
	 * @see #id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método que asigna el id al estado.
	 * 
	 * @param id
	 *            el id
	 * @see #id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método que obtiene los municipios del estado.
	 * 
	 * @return lso municipios
	 * @see #municipios
	 */
	public Collection<Municipio> getMunicipios() {
		return municipios;
	}

	/**
	 * Método que asigna los municipios al estado.
	 * 
	 * @param municipios
	 *            los municipios
	 * @see #municipios
	 */
	public void setMunicipios(Collection<Municipio> municipios) {
		this.municipios = municipios;
	}

	/**
	 * Método que obtiene el nombre del estado.
	 * 
	 * @return el nombre
	 * @see #nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que asigna el nombre al estado.
	 * 
	 * @param nombre
	 *            el nombre
	 * @see #nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método que obtiene el país del estado.
	 * 
	 * @return el pais
	 * @see #pais
	 */
	public Pais getPais() {
		return pais;
	}

	/**
	 * Método que asigna el país al estado.
	 * 
	 * @param pais
	 *            el pais
	 * @see #pais
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
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
		Estado other = (Estado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
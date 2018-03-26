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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entidad que representa un municipio.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@Entity
@Table(name = "tmunicipios")
@JsonIgnoreProperties({"colonias", "estado"})
public class Municipio implements Serializable {

	/**
	 * Atributo que representa el número de versión único de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo que representan las colonias asociados al municipio.
	 */
	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "municipio", orphanRemoval = true)
	private Collection<Colonia> colonias;

	/**
	 * Atributo que representa el estado del muncipio. No puede ser nulo.
	 */
	@NotNull
	@JoinColumn(name = "NIdEstado", nullable = false, referencedColumnName = "NIdEstado")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Estado estado;

	/**
	 * Atributo que representa el identificador del municipio. Es único.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdMunicipio", nullable = false, unique = true)
	private Long id;

	/**
	 * Atributo que representa el nombre del municipio. No puede ser nulo o
	 * vacío. La longitud máxima es de 128 caracteres.
	 */
	@Size(max = 128)
	@NotEmpty
	@Column(length = 128, name = "VNombre", nullable = false)
	private String nombre;

	/**
	 * Método constructor por defecto.
	 */
	public Municipio() {
	}

	/**
	 * Método que obtiene las colonias del municipio.
	 * 
	 * @return las colonias
	 * @see #colonias
	 */
	public Collection<Colonia> getColonias() {
		return colonias;
	}

	/**
	 * Método que asigna las colonias al municipio.
	 * 
	 * @param colonias
	 *            las colonias
	 * @see #colonias
	 */
	public void setColonias(Collection<Colonia> colonias) {
		this.colonias = colonias;
	}

	/**
	 * Método que obtiene el estado del municipio.
	 * 
	 * @return el estado
	 * @see #estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * Método que asigna el estado al municipio.
	 * 
	 * @param estado
	 *            el estado
	 * @see #estado
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	/**
	 * Método que obtiene el id del municipio.
	 * 
	 * @return el id
	 * @see #id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método que asigna el id al municipio.
	 * 
	 * @param id
	 *            el id
	 * @see #id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método que obtiene el nombre del municipio.
	 * 
	 * @return el nombre
	 * @see #nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que asigna el nombre al municipio.
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
		Municipio other = (Municipio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
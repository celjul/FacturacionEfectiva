package com.entich.ezfact.usuarios.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Abstraccion del puesto que puede tener un usuario.
 * 
 * @author Luis Angel Cardenas
 *
 */
@Entity
@Table(name = "troles")
public class Rol implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3099885737988537968L;
	
	/**
	 * Identificador del puesto que tiene un usuario.
	 */
	@Id
	@Column(name = "NIdRol")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Nombre del puesto del usuario
	 */
	@NotNull
	@Size(max = 50)
	@Column(name = "VNombre", length=50, nullable = false)
	private String nombre;
	
	/**
	 * Descripcion de las actividades del puesto.
	 */
	@Size(max = 255)
	@Column(name = "VDescripcion", length=255)
	private String descripcion;
	
	public Rol() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return String.format("{id : %s, nombre : %s}", this.id, this.descripcion);
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Rol)) {
			return false;
		}
		Rol other = (Rol) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}

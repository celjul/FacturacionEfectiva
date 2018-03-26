package com.entich.commons.direcciones.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Clase abstracta que representa una dirección.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Direccion implements Serializable {

	/**
	 * Atributo que representa el número de versión único de la clase.
	 */
	private static final long serialVersionUID = 1L;
	
	/******************************************************************************************************/
	/**
	 * Atributo que representa el nombre del país. No puede ser nulo o vacío. La
	 * longitud máxima es de 64 caracteres.
	 */
	@Size(max = 64)
	@NotEmpty
	@Column(length = 64, name = "VNombrePais", nullable = false)
	private String nombrePais;
	
	/**
	 * Atributo que representa el nombre del estado. No puede ser nulo o vacío.
	 * La longitud máxima es de 96 caracteres.
	 */
	@Size(max = 96)
	@NotEmpty
	@Column(length = 96, name = "VNombreEstado", nullable = false)
	private String nombreEstado;
	
	/**
	 * Atributo que representa el nombre del municipio. No puede ser nulo o
	 * vacío. La longitud máxima es de 128 caracteres.
	 */
	@Size(max = 128)
	@NotEmpty
	@Column(length = 128, name = "VNombreMunicipio", nullable = false)
	private String nombreMunicipio;
	
	/**
	 * Atributo que representa el nombre de la colonia. No puede ser nulo o
	 * vacío. La longitud máxima es de 256 caracteres.
	 */
	@Size(min = 1, max = 256)
	@Column(length = 256, name = "VNombreColonia", nullable = false)
	private String nombreColonia;
	
	/**
	 * Atributo que representa el código postal de la colonia. No puede ser nulo
	 * o vacío. Su longitud es de 5 caracteres.
	 */
	@Size(min = 5, max = 5)
	@Column(length = 5, name = "VCodigoPostal", nullable = false)
	private String codigoPostal;
	
//	/**
//	 * Atributo que representa la colonia de la dirección. No puede ser nula.
//	 */
//	//@NotNull(message = "{com.entich.commons.direcciones.model.Direccion.colonia.NotNull}")
//	@JoinColumn(name = "NIdColonia", nullable = true, referencedColumnName = "NIdColonia")
//	@ManyToOne(fetch = FetchType.EAGER, optional = true)
//	protected Colonia colonia;
	/******************************************************************************************************/

	/**
	 * Atributo que representa la calle de la dirección. No puede ser nula o
	 * vacía. Su longitud máxima es de 128 caracteres.
	 */
	@Size(max = 128, message = "{com.entich.commons.direcciones.model.Direccion.calle.Size}")
	@NotEmpty(message = "{com.entich.commons.direcciones.model.Direccion.calle.NotEmpty}")
	@Column(length = 128, name = "VCalle", nullable = false)
	protected String calle;

	/**
	 * Atributo que representa el identificador de la dirección. Es único.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdDireccion", nullable = false, unique = true)
	protected Long id;

	/**
	 * Atributo que representa el número exterior de la direccón. No puede ser
	 * nulo o vacío. Su longitud máxima es de 32 caracteres.
	 */
	@Size(max = 32, message = "{com.entich.commons.direcciones.model.Direccion.noExterior.Size}")
	@NotEmpty(message = "{com.entich.commons.direcciones.model.Direccion.noExterior.NotEmpty}")
	@Column(length = 32, name = "VNoExterior", nullable = false)
	protected String noExterior;

	/**
	 * Atributo que representa el número interior de la dirección.Su longitud
	 * máxima es de 32 caracteres.
	 */
	@Size(max = 32, message = "{com.entich.commons.direcciones.model.Direccion.noInterior.Size}")
	@Column(length = 32, name = "VNoInterior", nullable = true)
	protected String noInterior;

	/**
	 * Atributo que representa las referencias de la dirección.Su longitud máxima
	 * es de 512 caracteres.
	 */
	@Size(max = 512, message = "{com.entich.commons.direcciones.model.Direccion.referencias.Size}")
	@Column(length = 512, name = "VReferencias", nullable = true)
	protected String referencias;
	
	/**
	 * Método constructor por defecto.
	 */
	public Direccion() {
	}

	/**
	 * Método que obtiene la calle de la dirección.
	 * @return la calle
	 * @see #calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * Método que asigna la calle a la dirección.
	 * @param calle
	 * @see #calle
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

//	/**
//	 * Método que obtiene la colonia de la dirección.
//	 * 
//	 * @return la colonia
//	 * @see #colonia
//	 */
//	public Colonia getColonia() {
//		return colonia;
//	}
//
//	/**
//	 * Método que asigna la colonia a la dirección.
//	 * 
//	 * @param colonia
//	 *            la colonia
//	 * @see #colonia
//	 */
//	public void setColonia(Colonia colonia) {
//		this.colonia = colonia;
//	}

	/**
	 * Método que obtiene el id de la dirección.
	 * @return el id
	 * @see #id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método que asigna el id a la dirección.
	 * @param id
	 * @see #id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método que obtiene el número exterior de la dirección.
	 * @return el número exterior
	 * @see #noExterior
	 */
	public String getNoExterior() {
		return noExterior;
	}

	/**
	 * Método que asigna el número exterior a la dirección.
	 * @param noExterior
	 * @see #noExterior
	 */
	public void setNoExterior(String noExterior) {
		this.noExterior = noExterior;
	}

	/**
	 * Método que obtiene el número interior de la dirección.
	 * @return el número interior
	 * @see #noInterior
	 */
	public String getNoInterior() {
		return noInterior;
	}

	/**
	 * Método que asigna el número interior a la dirección.
	 * @param noInterior
	 * @see #noInterior
	 */
	public void setNoInterior(String noInterior) {
		this.noInterior = noInterior;
	}

	/**
	 * Método que obtiene las referencias de la dirección.
	 * @return las referencias
	 * @see #referencias
	 */
	public String getReferencias() {
		return referencias;
	}

	/**
	 * Método que asigna las referencia a la dirección.
	 * @param referencias
	 * @see #referencias
	 */
	public void setReferencias(String referencias) {
		this.referencias = referencias;
	}
	/******************************************************************************************************/
	/**
	 * Método que obtiene el nombre del país.
	 * @return el nombre
	 * @see #nombre
	 */
	public String getNombrePais() {
		return nombrePais;
	}

	/**
	 * Método que asigna el nombre al país.
	 * @param nombre
	 * @see #nombre
	 */
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	
	/**
	 * Método que obtiene el nombre del estado.
	 * @return el nombre
	 * @see #nombre
	 */
	public String getNombreEstado() {
		return nombreEstado;
	}

	/**
	 * Método que asigna el nombre al estado.
	 * @param nombre
	 * @see #nombre
	 */
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	
	/**
	 * Método que obtiene el nombre del municipio.
	 * @return el nombre
	 * @see #nombre
	 */
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	/**
	 * Método que asigna el nombre al municipio.
	 * @param nombre
	 * @see #nombre
	 */
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}
	
	/**
	 * Método que obtiene el nombre de la colonia.
	 * @return el nombre
	 * @see #nombre
	 */
	public String getNombreColonia() {
		return nombreColonia;
	}

	/**
	 * Método que asigna el nombre a la colonia.
	 * @param nombre
	 * @see #nombre
	 */
	public void setNombreColonia(String nombreColonia) {
		this.nombreColonia = nombreColonia;
	}
	
	/**
	 * Método que obtiene el código postal de la colonia.
	 * @return el código postal
	 * @see #codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * Método que asigna el código postal a la colonia.
	 * @param codigoPostal
	 * @see #codigoPostal
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	/******************************************************************************************************/

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
		Direccion other = (Direccion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
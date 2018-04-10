package com.entich.ezfact.emisores.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.entich.commons.direcciones.model.Direccion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Super clase que representa un emisor.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@Entity
@Table(name = "temisores")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "VTipo", 
		discriminatorType = DiscriminatorType.STRING)
@JsonIgnoreProperties({"logo"})
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, 
		property = "@class")
public abstract class Emisor implements Serializable {

	/**
	 * Atributo que representa el número de versión único de la clase.
	 */
	private static final long serialVersionUID = 1L;

	// protected Collection<Certificado> certificados;

	/**
	 * Atributo que representa las direcciones del emisor. No puede ser nulo o
	 * vacío. Deben ser direcciones válidas.
	 */
//	@Valid
//	@NotEmpty(message = "{com.entich.ezfact.emisores.model.Emisor.direcciones.NotEmpty}")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, 
			mappedBy = "emisor", orphanRemoval = true, 
			targetEntity = DireccionEmisor.class)
	protected Collection<Direccion> direcciones;

	/**
	 * Atributo que representa el identificador del emisor. Es único.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdEmisor", nullable = false, unique = true)
	protected Long id;

	/**
	 * Atributo que representa el logo del emisor.
	 */
	@Column(columnDefinition = "longblob", name = "VLogo", nullable = true)
	protected byte[] logo;

	@NotNull(message = "{com.entich.ezfact.emisores.model.Emisor.regimen.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdRegimen", referencedColumnName = "NIdCatalogo")
    protected Regimen regimen;

	/**
	 * Atributo que representa el RFC del emisor. No puede ser nulo o vacío. La
	 * longitud máxima es de 13 caracteres y la mínima de 12 caracteres. Debe
	 * cumplir con el patrón especificado.
	 */
	@Pattern(regexp = "[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?", 
			message = "{com.entich.ezfact.emisores.model.Emisor.rfc.Pattern}")
	@Size(message = "{com.entich.ezfact.emisores.model.Emisor.rfc.Size}",
			min = 12, max = 13)
	@NotEmpty(message = "{com.entich.ezfact.emisores.model.Emisor.rfc.NotEmpty}")
	@Column(length = 13, name = "VRFC", nullable = false, unique = true)
	protected String rfc;
	
	@NotEmpty(message="{com.entich.ezfact.emisores.model.Emisor.plantilla.NotEmpty}")
	@Column(length = 255, name = "VPlantilla", nullable = false)
	protected String plantilla;
	
	@Column(length = 255, name = "VColor", nullable = false)
	protected String colorPlantilla;
	
	@Column(name = "NFacturas", nullable = true)
	private Integer facturas;

	public Integer getFacturas() {
		return facturas;
	}

	public void setFacturas(Integer facturas) {
		this.facturas = facturas;
	}

	/**
	 * Método constructor por defecto.
	 */
	public Emisor() {}

	/**
	 * Método que obtiene las direcciones del emisor.
	 * 
	 * @return las direcciones
	 * @see #direcciones
	 */
	public Collection<Direccion> getDirecciones() {
		return direcciones;
	}

	/**
	 * Método que asigna las direcciones al emisor.
	 * 
	 * @param direcciones
	 *            las direcciones
	 * @see #direcciones
	 */
	public void setDirecciones(Collection<Direccion> direcciones) {
		this.direcciones = direcciones;
	}

	/**
	 * Método que obtiene el id del emisor.
	 * 
	 * @return el id
	 * @see #id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método que asigna el id al emisor.
	 * 
	 * @param id
	 *            el id
	 * @see #id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método que obtiene el logo del emisor.
	 * 
	 * @return el logo
	 */
	public byte[] getLogo() {
		return logo;
	}

	/**
	 * Método que asigna el logo del emisor.
	 * 
	 * @param logo
	 *            el logo
	 */
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	
	public String getColorPlantilla() {
		return colorPlantilla;
	}

	public void setColorPlantilla(String colorPlantilla) {
		this.colorPlantilla = colorPlantilla;
	}

	public Regimen getRegimen() {
		return regimen;
	}

	public void setRegimen(Regimen regimen) {
		this.regimen = regimen;
	}

	/**
	 * Método que obtiene el RFC del emisor.
	 * 
	 * @return el RFC
	 * @see #rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * Método que asigna el RFC al emisor.
	 * 
	 * @param rfc
	 *            el RFC
	 * @see #rfc
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
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
		Emisor other = (Emisor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Método que agrega una dirección al emisor.
	 * 
	 * @param imagen
	 *            imagen
	 * @see #imagenes
	 */
	public void addDireccion(DireccionEmisor direccion) {
		if (this.direcciones == null) {
			this.direcciones = new ArrayList<Direccion>();
		}
		((DireccionEmisor) direccion).setEmisor(this);
		this.direcciones.add(direccion);
	}
	
	/**
	 * Método que devuelve el nombre completo del emisor.
	 * 
	 * @return nombre del emisor
	 */
	public String getNombreCompleto() {
		String nombre = null;
		if (this instanceof EmisorFisica) {
			EmisorFisica emisor = (EmisorFisica) this;
			nombre = emisor.getNombre() + " " + emisor.getApellidoPaterno()
					+ (emisor.getApellidoMaterno()!=null?" " + emisor.getApellidoMaterno():"");
		} else if (this instanceof EmisorMoral) {
			EmisorMoral emisor = (EmisorMoral) this;
			nombre = emisor.getRazonSocial();
		}
		return nombre;
	}
	
	public String getPlantilla() {
		return plantilla;
	}
	
	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}
	
	public String getClavelista() {
		return clavelista;
	}

	public void setClavelista(String clavelista) {
		this.clavelista = clavelista;
	}

	private String clavelista;
	
}
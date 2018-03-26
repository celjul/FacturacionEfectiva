package com.entich.ezfact.emisores.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 27/11/2013
 */
@Entity
@Table(name = "tcertificadosemisor")

@JsonIgnoreProperties({"certificado", "clave", "pfx"})
public class Certificado implements Serializable {

	/**
	 * Atributo que representa el número de versión único de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo que representa el certificado (archivo .cer). No puede ser nulo.
	 */
	@NotNull(message = "{com.entich.ezfact.emisores.model.Certificado.certificado.NotNull}")
	@Column(columnDefinition = "longblob", name = "VCertificado",
			nullable = false)
	private byte[] certificado;

	/**
	 * Atributo que representa la llave del certificado (archivo .key). No puede
	 * ser nulo.
	 */
	@NotNull(message = "{com.entich.ezfact.emisores.model.Certificado.clave.NotNull}")
	@Column(columnDefinition = "longblob", name = "VClave", nullable = false)
	private byte[] clave;

	/**
	 * Atributo que representa el archivo PFX necesario para la cancelacion de 
	 * comprobantes.
	 */
	@Column(columnDefinition = "longblob", name = "VPfx", nullable = true)
	private byte[] pfx;
	
	/**
	 * Atributo que representa el emisor al que pertenece el certificado. No
	 * puede ser nulo.
	 */
	@JsonIgnoreProperties({"direcciones", "regimenes"})
	@NotNull(message = "{com.entich.ezfact.emisores.model.Certificado.emisor.NotNull}")
	@JoinColumn(name = "NIdEmisor", nullable = false, referencedColumnName = "NIdEmisor")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Emisor emisor;

	/**
	 * Atributo que representa el identificador del certificado. Es único.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdCertificado", nullable = false, unique = true)
	private Long id;

	/**
	 * Atributo que representa la fecha de inicio de vigencia del certificado.
	 * No puede ser nula.
	 */
	@JsonFormat(pattern = "dd-MM-yyyy", shape = Shape.STRING, timezone = "America/Mexico_City")
	@NotNull(message = "{com.entich.ezfact.emisores.model.Certificado.inicio.NotNull}")
	@Column(name = "DInicio", nullable = false)
	private Date inicio;

	/**
	 * Atributo que representa la fecha de fin de vigencia del certificado. No
	 * puede ser nula.
	 */
	@JsonFormat(pattern = "dd-MM-yyyy", shape = Shape.STRING, timezone = "America/Mexico_City")
	@NotNull(message = "{com.entich.ezfact.emisores.model.Certificado.fin.NotNull}")
	@Future(message = "{com.entich.ezfact.emisores.model.Certificado.fin.Future}")
	@Column(name = "DFin", nullable = false)
	private Date fin;

	/**
	 * Atributo que representa el nombre del certificado. No puede ser nulo o
	 * vacío. La longitud máxima es de 64 caracteres.
	 */
	@Size(message = "{com.entich.ezfact.emisores.model.Certificado.nombre.Size}",
			max = 64)
	@NotEmpty(message = "{com.entich.ezfact.emisores.model.Certificado.nombre.NotEmpty}")
	@Column(length = 64, name = "VNombre", nullable = false)
	private String nombre;

	/**
	 * Atributo que representa el password del certificado. No puede ser nula o
	 * vacía. La longitud máxima es de 32 caracteres.
	 */
	@Size(message = "{com.entich.ezfact.emisores.model.Certificado.password.Size}",
			max = 32)
	@NotEmpty(message = "{com.entich.ezfact.emisores.model.Certificado.password.NotEmpty}")
	@Column(length = 32, name = "VPassword", nullable = false)
	private String password;

	/**
	 * Atributo que representa la serie del certificado. Es única. No puede ser
	 * nula o vacía. La longitud máxima es de 32 caracteres.
	 */
	@Size(message = "{com.entich.ezfact.emisores.model.Certificado.serie.Size}",
			max = 32)
	@NotEmpty(message = "{com.entich.ezfact.emisores.model.Certificado.serie.NotEmpty}")
	@Column(length = 32, name = "VSerie", nullable = false, unique = true)
	private String serie;

	/**
	 * Método constructor por defecto.
	 */
	public Certificado() {}

	/**
	 * Método que obtiene el archivo del certicado.
	 * 
	 * @return el certificado
	 * @see #certificado
	 */
	public byte[] getCertificado() {
		return certificado;
	}

	/**
	 * Método que asigna el archivo al certificado.
	 * 
	 * @param certificado
	 *            el certificado
	 * @see #certificado
	 */
	public void setCertificado(byte[] certificado) {
		this.certificado = certificado;
	}

	/**
	 * Método que obtiene la clave del certificado.
	 * 
	 * @return la clave
	 * @see #clave
	 */
	public byte[] getClave() {
		return clave;
	}

	/**
	 * Método que asigna la clave al certificado.
	 * 
	 * @param clave
	 *            la clave
	 * @see #clave
	 */
	public void setClave(byte[] clave) {
		this.clave = clave;
	}

	/**
	 * 
	 * @return
	 */
	public byte[] getPfx() {
		return pfx;
	}
	
	/**
	 * 
	 * @param pfx
	 */
	public void setPfx(byte[] pfx) {
		this.pfx = pfx;
	}
	
	/**
	 * Método que obtiene el emisor del certificado.
	 * 
	 * @return el emisor
	 * @see #emisor
	 */
	public Emisor getEmisor() {
		return emisor;
	}

	/**
	 * Método que asigna el emisor al certificado.
	 * 
	 * @param emisor
	 *            el emisor
	 * @see #emisor
	 */
	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}

	/**
	 * Método que obtiene el id del certificado.
	 * 
	 * @return el id
	 * @see #id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método que asigna el id al certificado.
	 * 
	 * @param id
	 *            el id
	 * @see #id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método que obtiene la fecha de inicio de vigencia del certificado.
	 * 
	 * @return la fecha de inicio
	 * @see #inicio
	 */
	public Date getInicio() {
		return inicio;
	}

	/**
	 * Método que asigna la fecha de inicio de vigencia al certificado.
	 * 
	 * @param inicio
	 *            la fecha de inicio
	 * @see #inicio
	 */
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	/**
	 * Método que obtiene la fecha de fin de vigencia del certificado.
	 * 
	 * @return la fecha de fin
	 * @see #fin
	 */
	public Date getFin() {
		return fin;
	}

	/**
	 * Método que asigna la fecha de fin de vigencia del certificado.
	 * 
	 * @param fin
	 *            la fecha de fin
	 * @see #fin
	 */
	public void setFin(Date fin) {
		this.fin = fin;
	}

	/**
	 * Método que obtiene el nombre del certificado.
	 * 
	 * @return el nombre
	 * @see #nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que asigna el nombre al certificado.
	 * 
	 * @param nombre
	 *            el nombre
	 * @see #nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método que obtiene el password del certificado.
	 * 
	 * @return el password
	 * @see #password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Método que asigna el password al certificado.
	 * 
	 * @param password
	 *            el password
	 * @see #password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Método que obtiene la serie del certificado.
	 * 
	 * @return la serie
	 * @see #serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Método que asigna la serie al certificado.
	 * 
	 * @param serie
	 *            la serie
	 * @see #serie
	 */
	public void setSerie(String serie) {
		this.serie = serie;
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
		Certificado other = (Certificado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
package com.entich.ezfact.facturacion.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Subselect;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


@Entity
@Table(name = "vcomprobantes")
@Subselect("SELECT * FROM vcomprobantes")
public class ComprobanteHelper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2769665922247981689L;
	
	@Id
	@Column(name = "NIdComprobante")
	private Long id;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@Column(name = "DFechaCreacion")
	private Date fechaCreacion;
	
	@Column(name = "VRFC")
	private String rfc;
	
	@Column(name = "NIdCliente")
	private Long idCliente;
	
	@Column(name = "VCliente")
	private String cliente;
	
	@Column(name = "BActivo")
	private Boolean estatus;
	
	@Column(name = "NIdCatalogo")
	private Long tipo;
	
	@Column(name = "VDescripcion")
	private String tipoDocumento;
	
	@Column(name = "VUUID")
	private String uuid;
	
	@Column(name = "NMontoSinIva")
	private BigDecimal montoSinIVA;
	
	@Column(name = "NIdEmisor")
	private Long emisor;
	
	public ComprobanteHelper() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getMontoSinIVA() {
		return montoSinIVA;
	}

	public void setMontoSinIVA(BigDecimal montoSinIVA) {
		this.montoSinIVA = montoSinIVA;
	}
	
	public Long getEmisor() {
		return emisor;
	}
	
	public void setEmisor(Long emisor) {
		this.emisor = emisor;
	}
	
	public Long getTipo() {
		return tipo;
	}
	
	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}
	
	public Long getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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
		ComprobanteHelper other = (ComprobanteHelper) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

package com.entich.ezfact.facturacion.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "tconceptoscomprobante")
public class Concepto {
	
	@Id
	@Column(name = "NIdConcepto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "{com.entich.ezfact.facturacion.model.Concepto.cantidad.NotNull}")
	@Column(name = "NCantidad")
	private Double cantidad;
	
	@NotNull(message = "{com.entich.ezfact.facturacion.model.Concepto.codigo.NotNull}")
	@Size(message = "{com.entich.ezfact.facturacion.model.Concepto.codigo.Size}", max = 25)
	@Column(name = "VCodigo", length = 25)
	private String codigo;
	
	@NotEmpty(message = "{com.entich.ezfact.facturacion.model.Concepto.descripcion.NotEmpty}")
	@Size(message = "{com.entich.ezfact.facturacion.model.Concepto.descripcion.Size}", max = 2048)
	@Column(name = "VDescripcion", length = 2048)
	private String descripcion;
	
	@NotNull(message = "{com.entich.ezfact.facturacion.model.Concepto.descuento.NotNull}")
	@Column(name = "NDescuento")
	private BigDecimal descuento;
	
	@NotNull(message = "{com.entich.ezfact.facturacion.model.Concepto.precioUnitario.NotNull}")
	@Column(name = "DPrecioUnitario")
	private BigDecimal precioUnitario;
	
	@NotNull(message = "{com.entich.ezfact.facturacion.model.Concepto.unidadDeMedida.NotNull}")
	@Size(message = "{com.entich.ezfact.facturacion.model.Concepto.unidadDeMedida.Size}", max = 20)
	@Column(name = "VUnidadMedida", length = 20)
	private String unidadDeMedida;
	
	@NotNull(message = "{com.entich.ezfact.facturacion.model.Concepto.claveProdServ.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdClaveProdServ", referencedColumnName = "NIdCatalogo")
    private ClaveProdServ claveProdServ;

    @NotNull(message = "{com.entich.ezfact.facturacion.model.Concepto.claveUnidad.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdClaveUnidad", referencedColumnName = "NIdCatalogo")
    private ClaveUnidad claveUnidad;
    
    @Min(value = 0L, message = "{com.entich.ezfact.facturacion.model.Concepto.iva.Min}")
    @Max(value = 1L, message = "{com.entich.ezfact.facturacion.model.Concepto.iva.Max}")
    @Column(name = "NIvaTrasladado")
    private Double ivaTrasladado;

    @Min(value = 0L, message = "{com.entich.ezfact.facturacion.model.Concepto.ieps.Min}")
    @Max(value = 3L, message = "{com.entich.ezfact.facturacion.model.Concepto.ieps.Max}")
    @Column(name = "NIepsTrasladado")
    private Double iepsTrasladado;
    
    @Min(value = 0L, message = "{com.entich.ezfact.facturacion.model.Concepto.iva.Min}")
    @Max(value = 1L, message = "{com.entich.ezfact.facturacion.model.Concepto.iva.Max}")
    @Column(name = "NIvaRetenido")
    private Double ivaRetenido;
    
    @Min(value = 0L, message = "{com.entich.ezfact.facturacion.model.Concepto.isr.Min}")
    @Max(value = 1L, message = "{com.entich.ezfact.facturacion.model.Concepto.isr.Max}")
    @Column(name = "NIsrRetenido")
    private Double isrRetenido;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getDescuento() {
		return this.descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getPrecioUnitario() {
		return this.precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getUnidadDeMedida() {
		return this.unidadDeMedida;
	}

	public void setUnidadDeMedida(String unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}

	public ClaveProdServ getClaveProdServ() {
		return claveProdServ;
	}

	public void setClaveProdServ(ClaveProdServ claveProdServ) {
		this.claveProdServ = claveProdServ;
	}

	public ClaveUnidad getClaveUnidad() {
		return claveUnidad;
	}

	public void setClaveUnidad(ClaveUnidad claveUnidad) {
		this.claveUnidad = claveUnidad;
	}

	public Double getIvaTrasladado() {
		return ivaTrasladado;
	}

	public void setIvaTrasladado(Double ivaTrasladado) {
		this.ivaTrasladado = ivaTrasladado;
	}

	public Double getIepsTrasladado() {
		return iepsTrasladado;
	}

	public void setIepsTrasladado(Double iepsTrasladado) {
		this.iepsTrasladado = iepsTrasladado;
	}

	public Double getIvaRetenido() {
		return ivaRetenido;
	}

	public void setIvaRetenido(Double ivaRetenido) {
		this.ivaRetenido = ivaRetenido;
	}

	public Double getIsrRetenido() {
		return isrRetenido;
	}

	public void setIsrRetenido(Double isrRetenido) {
		this.isrRetenido = isrRetenido;
	}

	public BigDecimal getImporte() {
		BigDecimal importe = precioUnitario.multiply(new BigDecimal(cantidad));
		//AHora el importe no debe considerar descuentos
        //importe = importe.subtract(importe.multiply(new BigDecimal(descuento > 1 ? descuento / 100 : descuento)));
		
		return importe;
	}
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Concepto)) {
			return false;
		}
		Concepto other = (Concepto) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}
}

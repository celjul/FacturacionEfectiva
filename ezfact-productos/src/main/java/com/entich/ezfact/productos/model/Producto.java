package com.entich.ezfact.productos.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.entich.ezfact.emisores.model.Emisor;

@Entity
@Table(name = "tproductos")
public class Producto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -793484339565653845L;

	@Id
	@Column(name = "NIdProducto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "{com.entich.ezfact.productos.model.Producto.codigo.NotEmpty}")
	@Size(message = "{com.entich.ezfact.productos.model.Producto.codigo.Size}")
	@Column(name = "VCodigo", length = 15, nullable = false)
	private String codigo;
	
	@Size(message = "{com.entich.ezfact.productos.model.Producto.nombre.Size}")
	@NotEmpty(message = "{com.entich.ezfact.productos.model.Producto.nombre.NotEmpty}")
	@Column(name = "VNombre", length = 500, nullable = false)
	private String nombre;
	
	@NotNull(message = "{com.entich.ezfact.productos.model.Producto.unidadDeMedida.NotNull}")
	@JoinColumn(name = "NIdUnidadMedida", nullable = false, referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private UnidadDeMedida unidadDeMedida;	
	
	@NotNull(message = "{com.entich.ezfact.productos.model.Producto.precio.NotNull}")
	@Min(message = "{com.entich.ezfact.productos.model.Producto.precio.Min}", value = 0)
	@Column(name = "DPrecio", nullable = false, precision = 14, scale = 2)
	private BigDecimal precio;
	
	@NotNull(message = "{com.entich.ezfact.productos.model.Producto.exentoIVA.NotNull}")
	@Column(name = "BExentoIVA", nullable = false, columnDefinition = "bit")
	private Boolean exentoIVA;
	
	@NotNull(message = "{com.entich.ezfact.productos.model.Producto.emisor.NotNull}")
	@JoinColumn(name = "NIdEmisor", nullable = false, referencedColumnName = "NIdEmisor")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Emisor emisor;
	
	public Producto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getExentoIVA() {
		return exentoIVA;
	}
	
	public void setExentoIVA(Boolean exentoIVA) {
		this.exentoIVA = exentoIVA;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Emisor getEmisor() {
		return emisor;
	}
	
	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public UnidadDeMedida getUnidadDeMedida() {
		return unidadDeMedida;
	}
	
	public void setUnidadDeMedida(UnidadDeMedida unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}
	
	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
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
		if (!(obj instanceof Producto)) {
			return false;
		}
		Producto other = (Producto) obj;
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

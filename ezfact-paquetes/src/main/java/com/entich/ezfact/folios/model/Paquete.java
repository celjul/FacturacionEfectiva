package com.entich.ezfact.folios.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tpaquetes")
public class Paquete implements Serializable{

	private static final long serialVersionUID = 2401938292936473243L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdPaquete", nullable = false, unique = true)
	private Long id;
	
	@Size(max = 64)
	@Column(name = "VNombre", nullable = false, length = 64)
	private String nombrePaquete;
	
	@Column(name = "NFacturas", nullable = false)
	private Integer facturas;
	
	@Column(name = "NPrecio", nullable = false)
	private BigDecimal precio;
	
	@ManyToOne
	@JoinColumn(name = "NIdTipoPaquete", referencedColumnName = "NIdCatalogo")
	private TipoPaquete tipo;
	
	public Paquete() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombrePaquete() {
		return nombrePaquete;
	}

	public void setNombrePaquete(String nombrePaquete) {
		this.nombrePaquete = nombrePaquete;
	}

	public Integer getFacturas() {
		return facturas;
	}

	public void setFacturas(Integer facturas) {
		this.facturas = facturas;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public TipoPaquete getTipo() {
		return tipo;
	}

	public void setTipo(TipoPaquete tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return String.format("{id : %s, nombrePaquete : %s, facturas : %s, precio : %s, tipo : %s}"
					, this.id, this.nombrePaquete, this.facturas, this.precio, this.tipo.getDescripcion());
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
		Paquete other = (Paquete) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

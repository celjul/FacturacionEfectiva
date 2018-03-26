package com.entich.ezfact.web.dto;

import java.io.Serializable;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 25-11-2017
 * @version 1.0
 *
 */
public class FacturaMasivaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int numero;
	
	private boolean exitosa;
	
	private long id;
	
	private String observaciones;

	public FacturaMasivaDto(boolean exitosa) {
		super();
		this.exitosa = exitosa;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isExitosa() {
		return exitosa;
	}

	public void setExitosa(boolean exitosa) {
		this.exitosa = exitosa;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numero;
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
		FacturaMasivaDto other = (FacturaMasivaDto) obj;
		if (numero != other.numero)
			return false;
		return true;
	}
}

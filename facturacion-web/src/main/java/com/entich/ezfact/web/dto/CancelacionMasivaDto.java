package com.entich.ezfact.web.dto;

import java.io.Serializable;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 18-12-2017
 * @version 1.0
 *
 */
public class CancelacionMasivaDto implements Serializable {
	
	private static final long serialVersionUID = -2338984047345725630L;
	
	private String uuid;
	
	private long id;
	
	private boolean exitosa;
	
	private String observaciones;

	public CancelacionMasivaDto(boolean exitosa) {
		super();
		this.exitosa = exitosa;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isExitosa() {
		return exitosa;
	}

	public void setExitosa(boolean exitosa) {
		this.exitosa = exitosa;
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
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		CancelacionMasivaDto other = (CancelacionMasivaDto) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}

package com.entich.ezfact.folios.model;

public class ContadorFoliosEmisor {
	private Integer disponibles;
	private Integer utilizados;
	private Integer diasRestantes;
	private String mensaje;
	
	public ContadorFoliosEmisor() {}
	
	public Integer getDisponibles() {
		return disponibles;
	}
	
	public void setDisponibles(Integer disponibles) {
		this.disponibles = disponibles;
	}
	
	public Integer getUtilizados() {
		return utilizados;
	}
	
	public void setUtilizados(Integer utilizados) {
		this.utilizados = utilizados;
	}

	public Integer getDiasRestantes() {
		return diasRestantes;
	}

	public void setDiasRestantes(Integer diasRestantes) {
		this.diasRestantes = diasRestantes;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((disponibles == null) ? 0 : disponibles.hashCode());
		result = prime * result
				+ ((utilizados == null) ? 0 : utilizados.hashCode());
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
		ContadorFoliosEmisor other = (ContadorFoliosEmisor) obj;
		if (disponibles == null) {
			if (other.disponibles != null)
				return false;
		} else if (!disponibles.equals(other.disponibles))
			return false;
		if (utilizados == null) {
			if (other.utilizados != null)
				return false;
		} else if (!utilizados.equals(other.utilizados))
			return false;
		return true;
	}
	
	
}

package com.entich.ezfact.folios.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.entich.ezfact.emisores.model.Emisor;

@Entity
@Table(name = "tpaquetesemisor")
public class PaqueteEmisor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4604212203582562957L;
	
	@Id
	@Column(name = "NIdPaqueteEmisor")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "NIdPaquete", referencedColumnName = "NIdPaqueteUsuario")
	private PaqueteUsuario paquete;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "NIdEmisor", referencedColumnName = "NIdEmisor")
	private Emisor emisor;
	
	/**
	 * Numero de facturas que el emisor tiene permitido utilizar del total del
	 * paquete contratado, en caso de ser nulo, significa que no existe
	 * restriccion y puede ocupar todos los folios del paquete.
	 */
	@Column(name = "NFoliosAutorizados", nullable = true)
	private Integer foliosAutorizados;
	
	public PaqueteEmisor() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PaqueteUsuario getPaquete() {
		return paquete;
	}

	public void setPaquete(PaqueteUsuario paquete) {
		this.paquete = paquete;
	}

	public Emisor getEmisor() {
		return emisor;
	}

	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}
	
	public Integer getFoliosAutorizados() {
		return foliosAutorizados;
	}
	
	public void setFoliosAutorizados(Integer foliosAutorizados) {
		this.foliosAutorizados = foliosAutorizados;
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
		PaqueteEmisor other = (PaqueteEmisor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

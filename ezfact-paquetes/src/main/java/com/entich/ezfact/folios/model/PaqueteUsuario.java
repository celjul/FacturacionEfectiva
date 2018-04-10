package com.entich.ezfact.folios.model;

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
import javax.validation.constraints.NotNull;

import com.entich.ezfact.usuarios.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "tpaquetesusuario")
public class PaqueteUsuario implements Serializable {

	private static final long serialVersionUID = 5974996910284404209L;

	@Id
	@Column(name = "NIdPaqueteUsuario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@JoinColumn(name = "NIdUsuario", nullable = true, referencedColumnName = "NIdUsuario")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Usuario usuario;
	
	@NotNull
	@JoinColumn(name = "NIdPaquete", nullable = false, referencedColumnName = "NIdPaquete")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Paquete paquete;
	
	@NotNull
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Mexico_City")
	@Column(columnDefinition = "date", name = "DAdquirido", nullable = false)
	private Date adquirido;
	
	@NotNull
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Mexico_City")
	@Column(columnDefinition = "date", name = "DVencimiento", nullable = false)
	private Date vencimiento;
	
	@NotNull
	@Column(columnDefinition = "bit", name = "BActivo", nullable = false)
	private Boolean activo;

	public PaqueteUsuario() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Paquete getPaquete() {
		return paquete;
	}

	public void setPaquete(Paquete paquete) {
		this.paquete = paquete;
	}

	public Date getAdquirido() {
		return adquirido;
	}

	public void setAdquirido(Date adquirido) {
		this.adquirido = adquirido;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
	
	public Boolean getActivo() {
		return activo;
	}
	
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
}

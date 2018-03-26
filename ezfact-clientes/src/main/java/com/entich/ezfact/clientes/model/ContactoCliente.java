package com.entich.ezfact.clientes.model;

import java.io.Serializable;

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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase para representar los Contactos de los Clientes
 * 
 * @author Pedro Josue Mendoza Islas
 */
@Entity
@Table(name = "tcontactoscliente")
@JsonIgnoreProperties({"cliente"})
public class ContactoCliente implements Serializable {

	private static final long serialVersionUID = -2056246332507414897L;

	@Id
	@Column(name = "NIdContactoCliente", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 255)
	@NotEmpty
	@Column(name = "VNombre", length = 255, nullable = false)
	private String nombre;

	@Size(max = 255)
	@NotEmpty
	@Column(name = "VApellidoPaterno", length = 255, nullable = false)
	private String apellidoPaterno;

	@Size(max = 255)
	@Column(name = "VApellidoMaterno", length = 255, nullable = true)
	private String apellidoMaterno;

	@Size(max = 255)
	@NotEmpty
	@Column(name = "VPuesto", length = 255, nullable = false)
	private String puesto;

	@Email
	@Size(max = 255)
	@NotEmpty
	@Column(name = "VEmail", length = 255, nullable = false)
	private String email;

	@NotNull
	@JoinColumn(name = "NIdCliente", nullable = false, referencedColumnName = "NIdCliente")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Cliente cliente;
	
	public ContactoCliente() {
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ContactoCliente other = (ContactoCliente) obj;
		if (this.id != other.id
				&& (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}
}

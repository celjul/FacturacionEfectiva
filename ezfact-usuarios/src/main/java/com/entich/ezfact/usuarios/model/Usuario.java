package com.entich.ezfact.usuarios.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.entich.ezfact.emisores.model.Emisor;

@Entity
@Table(name = "tusuarios")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2432098364909912011L;

	/**
	 * Identificador del usuario
	 */
	@Id
	@Column(name = "NIdUsuario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre de usuario para iniciar sesion.
	 */
	@NotNull(message = "{com.entich.ezfact.usuarios.model.Usuario.login.NotNull}")
	@Size(message = "{com.entich.ezfact.usuarios.model.Usuario.login.Size}", max = 128)
	@Column(name = "VLogin", length = 128, nullable = false, unique = true)
	private String login;

	/**
	 * Password del usuario para iniciar sesion.
	 */
	@NotNull(message = "{com.entich.ezfact.usuarios.model.Usuario.password.NotNull}")
	@Size(message = "{com.entich.ezfact.usuarios.model.Usuario.password.Size}", min = 8, max = 32)
	@Column(name = "VPassword", length = 32, nullable = false)
	private String password;

	/**
	 * Nombre del usuario
	 */
	@NotNull(message = "{com.entich.ezfact.usuarios.model.Usuario.nombre.NotNull}")
	@NotEmpty(message = "{com.entich.ezfact.usuarios.model.Usuario.nombre.NotEmpty}")
	@Size(message = "{com.entich.ezfact.usuarios.model.Usuario.nombre.Size}", max = 255)
	@Column(name = "VNombre", length = 255, nullable = false)
	private String nombre;

	/**
	 * Apellido paterno del usuario.
	 */
	@NotNull(message = "{com.entich.ezfact.usuarios.model.Usuario.apellidoPaterno.NotNull}")
	@NotEmpty(message = "{com.entich.ezfact.usuarios.model.Usuario.apellidoPaterno.NotEmpty}")
	@Size(message = "{com.entich.ezfact.usuarios.model.Usuario.apellidoPaterno.Size}", max = 255)
	@Column(name = "VApellidoPaterno", length = 255, nullable = false)
	private String apellidoPaterno;

	/**
	 * Apellido materno del usuario.
	 */
	@Size(message = "{com.entich.ezfact.usuarios.model.Usuario.apellidoMaterno.Size}", max = 255)
	@Column(name = "VApellidoMaterno", length = 255, nullable = true)
	private String apellidoMaterno;

	/**
	 * Bandera que indica si el usuario esta activo dentro del sistema.
	 */
	@NotNull(message = "{com.entich.ezfact.usuarios.model.Usuario.activo.NotNull}")
	@Column(name = "BActivo", nullable = false)
	private Boolean activo;

	@ManyToMany(fetch = FetchType.EAGER)
	@NotEmpty(message = "{com.entich.ezfact.usuarios.model.Usuario.roles.NotEmpty}")
	@JoinTable(name = "tusuariosroles", joinColumns = @JoinColumn(name = "NIdUsuario"), inverseJoinColumns = { @JoinColumn(name = "NIdRol") })
	private Set<Rol> roles;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tusuariosemisores", joinColumns = @JoinColumn(name = "NIdUsuario"), inverseJoinColumns = { @JoinColumn(name = "NIdEmisor") })
	private Set<Emisor> emisores;

	/**
	 * Teléfono del usuario para localizarlo.
	 */
	@Column(name = "VTelefono", length = 20, nullable = false)
	private String telefono;
	
	/**
	 * Código Promocional del asesor que esta dando de alta al usuario.
	 */
	@Column(name = "VCodigoPromocional", length = 20, nullable = true)
	private String codigo;

	/**
	 * Constructor por default de la clase.
	 */
	public Usuario() {
	}

	/**
	 * Devuelve el Id del usuario.
	 * 
	 * @return Id del usuario.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Asigna el Id a la instancia.
	 * 
	 * @param id
	 *            Identificador que se asignara a la instancia.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Devuelve el nombre el nombre de usuario o login.
	 * 
	 * @return Login o nombre de usuario.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Asigna el nombre de usuario a la instancia.
	 * 
	 * @param login
	 *            Login de usuario.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Devuelve el password del usuario.
	 * 
	 * @return Password del usuario.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Asigna el password de usuario a la instancia.
	 * 
	 * @param password
	 *            Password del usuario
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Devuelve el nombre del usuario.
	 * 
	 * @return Nombre de usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el nombre del usuario a la instancia.
	 * 
	 * @param nombre
	 *            Nombre de usuario que se asignara a la instnacia.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el apellido paterno del usuario.
	 * 
	 * @return Apellido paterno de la instancia.
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * Asigna el apellido paterno de la instancia.
	 * 
	 * @param apellidoPaterno
	 *            Apellido paterno del usuario
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * Devuelve el apellido materno de la instancia.
	 * 
	 * @return Apellido materno del usuario.
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * Asigna el apellido materno del usuario
	 * 
	 * @param apellidoMaterno
	 *            Apellido materno del usuario
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * Asigna la bandera para indicar que se trata de un usuario activo.
	 * 
	 * @return Estado del usuario, si el usuario esta aun habilitado en el
	 *         sistema el valor de esta bandera debe ser <code>true</code>, en
	 *         caso contratio <code>false</code>
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * Asigna el estatus del usuario.
	 * 
	 * @param activo
	 *            Estado del usuario del sistema
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Set<Emisor> getEmisores() {
		return emisores;
	}

	public void setEmisores(Set<Emisor> emisores) {
		this.emisores = emisores;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return String.format("{id : %s, nombre : %s, apellidoPaterno : %s}",
				this.id, this.nombre, this.apellidoPaterno);
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
		if (!(obj instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) obj;
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

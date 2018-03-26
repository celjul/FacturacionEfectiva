package com.entich.ezfact.clientes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.entich.ezfact.emisores.model.Emisor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Clase abstracta con los campos comunes entre Clientes de los tipos Persona
 * Fisica y Persona Moral
 *
 * @author Pedro Josue Mendoza Islas
 */
@Entity
@Table(name = "tclientes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorOptions(force = true)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,
        name = "VTipo")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY,
        property = "@class")
//@JsonIgnoreProperties({"emisor"})
public abstract class Cliente implements Serializable {

    private static final long serialVersionUID = 28321890965790293L;

    @Id
    @Column(name = "NIdCliente", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Size(message = "{com.entich.ezfact.clientes.model.Cliente.rfc.Size}",
            min = 12, max = 13)
    @Pattern(regexp = "[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?",
            message = "{com.entich.ezfact.clientes.model.Cliente.rfc.Pattern}")
    @NotEmpty(message = "{com.entich.ezfact.clientes.model.Cliente.rfc.NotEmpty}")
    @Column(name = "VRFC", length = 13, nullable = false)
    protected String rfc;

    @Size(message = "{com.entich.ezfact.clientes.model.Cliente.razonComercial.Size}",
            max = 255)
    @NotEmpty(message = "{com.entich.ezfact.clientes.model.Cliente.razonComercial.NotEmpty}")
    @Column(name = "VRazonComercial", length = 255, nullable = false)
    protected String razonComercial;

    @URL(message = "{com.entich.ezfact.clientes.model.Cliente.paginaWeb.URL}")
    @Size(message = "{com.entich.ezfact.clientes.model.Cliente.paginaWeb.Size}",
            max = 255)
    @Column(name = "VPaginaWeb", length = 255, nullable = true)
    protected String paginaWeb;

    @Size(message = "{com.entich.ezfact.clientes.model.Cliente.observaciones.Size}",
            max = 255)
    @Column(name = "VObservaciones", length = 255, nullable = true)
    protected String observaciones;

//    @Valid
//    @NotEmpty(message = "{com.entich.ezfact.clientes.model.Cliente.contactos.NotEmpty}")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "cliente", orphanRemoval = true)
    protected Collection<ContactoCliente> contactos;

    @JsonIgnoreProperties({"direcciones", "regimenes"})
    @NotNull(message = "{com.entich.ezfact.clientes.model.Cliente.emisor.NotNull}")
    @JoinColumn(name = "NIdEmisor", nullable = false,
            referencedColumnName = "NIdEmisor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    protected Emisor emisor;
    
    @NotNull(message = "{com.entich.ezfact.clientes.model.Cliente.usoCFDi.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdUsoCFDi", referencedColumnName = "NIdCatalogo")
    protected UsoCFDi usoCFDi;
    
    @NotNull(message = "{com.entich.ezfact.clientes.model.Cliente.pais.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdPais", referencedColumnName = "NIdCatalogo")
    protected PaisSAT pais;

    public Cliente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonComercial() {
        return razonComercial;
    }

    public void setRazonComercial(String razonComercial) {
        this.razonComercial = razonComercial;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Collection<ContactoCliente> getContactos() {
        return contactos;
    }

    public void setContactos(Collection<ContactoCliente> contactos) {
        this.contactos = contactos;
    }

    public Emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }

    public UsoCFDi getUsoCFDi() {
		return usoCFDi;
	}

	public void setUsoCFDi(UsoCFDi usoCFDi) {
		this.usoCFDi = usoCFDi;
	}

	public PaisSAT getPais() {
		return pais;
	}

	public void setPais(PaisSAT pais) {
		this.pais = pais;
	}

	/**
     * Método que devuelve el nombre completo del emisor.
     *
     * @return nombre del emisor
     */
    public String getNombreCompleto() {
        String nombre = null;
        if (this instanceof ClientePersonaFisica) {
            ClientePersonaFisica cliente = (ClientePersonaFisica) this;

            if (cliente.getApellidoPaterno() == null) {
                nombre = cliente.getNombre() + (" " + cliente.getApellidoPaterno() == null ? "" : " " + cliente.getApellidoPaterno());
            } else {
                nombre = cliente.getNombre() + " " + cliente.getApellidoPaterno()
                        + (cliente.getApellidoMaterno() == null ? "" : " " + cliente.getApellidoMaterno());
            }
        } else if (this instanceof ClientePersonaMoral) {
            ClientePersonaMoral cliente = (ClientePersonaMoral) this;
            nombre = cliente.getRazonSocial();
        }
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (this.id != other.id
                && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    /**
     * Agrega una <code>DireccionCliente</code> al Cliente Si la Collection es
     * nula crea una nueva
     *
     * @param contactoCliente
     */
    public void addContacto(ContactoCliente contactoCliente) {
        contactoCliente.setCliente(this);
        if (this.getContactos() == null) {
            this.contactos = new ArrayList<ContactoCliente>();
        }
        this.contactos.add(contactoCliente);
    }
}

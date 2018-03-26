package com.entich.commons.catalogo.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Abstraccion de una opcion de las existentes en alguno de los catalogos de la
 * aplicacion.
 *
 * @author Luis Angel Cardenas.
 */
@Entity
@Table(name = "tcatalogos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "VTipo", discriminatorType = DiscriminatorType.STRING)
public abstract class OpcionDeCatalogo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3917073742283448290L;

    /**
     * Identificador de la opcion del catalogo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdCatalogo", nullable = false, unique = true)
    private Long id;

    /**
     * Decripcion, nombre de la opcion del catalogo representada por la
     * instancia.
     */
    @Size(max = 256)
    @NotEmpty
    @Column(length = 256, name = "VDescripcion", nullable = false)
    private String descripcion;

    @Size(message = "{com.entich.ezfact.facturacion.model.Concepto.clavesat}", max = 10)
    @Column(name = "VClave", length = 10, nullable = true)
    private String clave;

    @Column(name = "BActivo")
    private Boolean activo;
    
    /**
     * Devuelve el identificador de la opcion del catalogo.
     *
     * @return Identificador del catalogo.
     */
    public Long getId() {
        return id;
    }

    /**
     * Asinga un numero identificador a la instancia.
     *
     * @param id Identificador que se asignara a la instancia.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve la descripcion o valor de la opcion de la opcion de catalogo que
     * se abstrae en la instancia.
     *
     * @return Descripcion, valor, nombre de la opcion de catalogo.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asigna un nombre o descripcion a la opcion de catalogo.
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return String.format("{id : %s, descripcion : %s}", this.id, this.descripcion);
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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
        if (!(obj instanceof OpcionDeCatalogo)) {
            return false;
        }
        OpcionDeCatalogo other = (OpcionDeCatalogo) obj;
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
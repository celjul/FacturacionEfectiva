package com.entich.ezfact.facturacion.model;

import com.entich.ezfact.emisores.model.Emisor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Pegasus on 17/05/2016.
 */
@Entity
@Table(name = "tseries")
public class Serie implements Serializable{

    private static final long serialVersionUID = 4297492387499743979L;

    @Id
    @Column(name = "NIdSerie", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VNombre", nullable = false)
    private String nombre;

    @Column(name = "NSiguienteFolio", nullable = false)
    private Integer siguienteFolio;

    @NotNull(message = "{com.entich.ezfact.facturacion.model.Comprobante.emisor.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdEmisor", referencedColumnName = "NIdEmisor")
    private Emisor emisor;

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

    public Integer getSiguienteFolio() {
        return siguienteFolio;
    }

    public void setSiguienteFolio(Integer siguienteFolio) {
        this.siguienteFolio = siguienteFolio;
    }

    public Emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }
}

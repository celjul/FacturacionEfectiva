package com.entich.ezfact.facturacion.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.emisores.model.Emisor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tcomprobantes")
@JsonIgnoreProperties(value = {"cfdi", "representacionImpresa", "acuseCancelacion"})
public class Comprobante {

    @Id
    @Column(name = "NIdComprobante")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{com.entich.ezfact.facturacion.model.Comprobante.cliente.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdCliente", referencedColumnName = "NIdCliente")
    private Cliente cliente;

    @NotNull(message = "{com.entich.ezfact.facturacion.model.Comprobante.tipo.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdTipoDocumento", referencedColumnName = "NIdCatalogo")
    private TipoDocumento tipo;

    @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
    @NotNull(message = "{com.entich.ezfact.facturacion.model.Comprobante.fechaCreacion.NotNull}")
    @Column(name = "DFechaCreacion", columnDefinition = "datetime")
    private Date fechaCreacion;

    @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
    @NotNull(message = "{com.entich.ezfact.facturacion.model.Comprobante.fechaEntrega.NotNull}")
    @Column(name = "DFechaEntrega", columnDefinition = "date")
    private Date fechaEntrega;

    @NotNull(message = "{com.entich.ezfact.facturacion.model.Comprobante.descuento.NotNull}")
    @Min(value = 0L, message = "{com.entich.ezfact.facturacion.model.Comprobante.descuento.Min}")
    @Column(name = "NDescuento")
    private BigDecimal descuento;

    @NotEmpty(message = "{com.entich.ezfact.facturacion.model.Comprobante.condicion.NotEmpty}")
    @Size(message = "{com.entich.ezfact.facturacion.model.Comprobante.condicion.Size}", max = 100)
    @Column(name = "VCondicionPago", length = 100)
    private String condicion;

    @NotNull(message = "{com.entich.ezfact.facturacion.model.Comprobante.metodo.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdMetodoPago", referencedColumnName = "NIdCatalogo")
    private MetodoPago metodo;
    
    @NotNull(message = "{com.entich.ezfact.facturacion.model.Comprobante.forma.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdFormaPago", referencedColumnName = "NIdCatalogo")
    private FormaPago forma;

    @Size(message = "{com.entich.ezfact.facturacion.model.Comprobante.numeroCuenta.NotEmpty}", max = 40)
    @Column(name = "VCuenta", length = 40)
    private String numeroCuenta;

    @Size(message = "{com.entich.ezfact.facturacion.model.Comprobante.leyendaComprobante.Size}", max = 500)
    @Column(name = "VLeyendaComprobante", length = 500)
    private String leyendaComprobante;

    @Size(message = "{com.entich.ezfact.facturacion.model.Comprobante.lugarDeExpedicion.Size}", max = 255)
    @Column(name = "VLugarExpedicion", length = 255)
    private String lugarDeExpedicion;

    @NotNull(message = "{com.entich.ezfact.facturacion.model.Comprobante.emisor.NotNull}")
    @ManyToOne
    @JoinColumn(name = "NIdEmisor", referencedColumnName = "NIdEmisor")
    private Emisor emisor;

    @Valid
    @NotEmpty(message = "{com.entich.ezfact.facturacion.model.Comprobante.conceptos.NotEmpty}")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "NIdComprobante", referencedColumnName = "NIdComprobante", nullable = false)
    private Collection<Concepto> conceptos;

    @Column(name = "FXml", columnDefinition = "blob")
    private byte[] cfdi;

    @Column(name = "FRepresentacionImpresa", columnDefinition = "blob")
    private byte[] representacionImpresa;

    @Column(name = "FAcuseCancelacion", columnDefinition = "blob")
    private byte[] acuseCancelacion;

    @Size(message = "{com.entich.ezfact.facturacion.model.Comprobante.uuid.Size}", max = 36)
    @Column(name = "VUUID", length = 36)
    private String uuid;

    @Column(name = "BActivo")
    private Boolean activo;

    @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
    @Column(name = "DFechaTimbrado", columnDefinition = "datetime", nullable = true)
    private Date fechaTimbrado;

    @ManyToOne
    @JoinColumn(name = "NIdSerie", referencedColumnName = "NIdSerie", nullable = true)
    private Serie serie;

    @Column(name = "NFolio", nullable = true)
    private Integer folio;
    
    @Column(name = "NFolioMasivo", nullable = true)
    private Integer folioMasivo;
    
    @Column(name = "VVentaUnica", nullable = true)
    private String idVentaUnica;
    
    @Column(name = "VUUIDpadre", nullable = true)
    private String VUUIDpadre;
    
    public String getVUUIDpadre() {
		return VUUIDpadre;
	}

	public void setVUUIDpadre(String vUUIDpadre) {
		VUUIDpadre = vUUIDpadre;
	}

	public Comprobante() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoDocumento getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoDocumento tipo) {
        this.tipo = tipo;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEntrega() {
        return this.fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public BigDecimal getDescuento() {
        return this.descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getCondicion() {
        return this.condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public MetodoPago getMetodo() {
        return this.metodo;
    }

    public void setMetodo(MetodoPago metodo) {
        this.metodo = metodo;
    }

    public String getNumeroCuenta() {
        return this.numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getLeyendaComprobante() {
        return this.leyendaComprobante;
    }

    public void setLeyendaComprobante(String leyendaComprobante) {
        this.leyendaComprobante = leyendaComprobante;
    }

    public String getLugarDeExpedicion() {
        return lugarDeExpedicion;
    }

    public Emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }

    public Collection<Concepto> getConceptos() {
        return this.conceptos;
    }

    public void setConceptos(Collection<Concepto> coneptos) {
        this.conceptos = coneptos;
    }

    public byte[] getCfdi() {
        return cfdi;
    }

    public void setCfdi(byte[] cfdi) {
        this.cfdi = cfdi;
    }

    public byte[] getRepresentacionImpresa() {
        return representacionImpresa;
    }

    public void setRepresentacionImpresa(byte[] representacionImpresa) {
        this.representacionImpresa = representacionImpresa;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setLugarDeExpedicion(String lugarDeExpedicion) {
        this.lugarDeExpedicion = lugarDeExpedicion;
    }

    public void setForma(FormaPago forma) {
        this.forma = forma;
    }

    public FormaPago getForma() {
        return forma;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public byte[] getAcuseCancelacion() {
        return acuseCancelacion;
    }

    public void setAcuseCancelacion(byte[] acuseCancelacion) {
        this.acuseCancelacion = acuseCancelacion;
    }

    public Date getFechaTimbrado() {
        return fechaTimbrado;
    }

    public Serie getSerie() {
        return serie;
    }

    public String getNombreSerie() {
        return serie.getNombre();
    }

    public String getDescripcionMetodoPago() {
        return metodo.getDescripcion();
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public void setFechaTimbrado(Date fechaTimbrado) {
        this.fechaTimbrado = fechaTimbrado;
    }
    
    public Integer getFolioMasivo() {
		return folioMasivo;
	}

	public void setFolioMasivo(Integer folioMasivo) {
		this.folioMasivo = folioMasivo;
	}

	public String getIdVentaUnica() {
		return idVentaUnica;
	}

	public void setIdVentaUnica(String idVentaUnica) {
		this.idVentaUnica = idVentaUnica;
	}

	public void addConcepto(Concepto concepto) {
    	if (this.conceptos == null) {
            this.conceptos = new ArrayList<Concepto>();
        }
        this.conceptos.add(concepto);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Comprobante)) {
            return false;
        }
        Comprobante other = (Comprobante) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}

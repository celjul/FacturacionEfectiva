package com.entich.ezfact.facturacion.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.commons.exceptions.service.ValidationException;
import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.clientes.model.PaisSAT;
import com.entich.ezfact.clientes.model.UsoCFDi;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Regimen;
import com.entich.ezfact.emisores.service.CertificadoService;
import com.entich.ezfact.emisores.service.EmisorService;
import com.entich.ezfact.facturacion.dao.ComprobanteDao;
import com.entich.ezfact.facturacion.factory.ComprobanteFactory;
import com.entich.ezfact.facturacion.model.ClaveUnidad;
import com.entich.ezfact.facturacion.model.Comprobante;
import com.entich.ezfact.facturacion.model.Concepto;
import com.entich.ezfact.facturacion.model.FormaPago;
import com.entich.ezfact.facturacion.model.MetodoPago;
import com.entich.ezfact.facturacion.model.Serie;
import com.entich.ezfact.facturacion.service.ComprobanteService;
import com.entich.ezfact.facturacion.service.QRService;
import com.entich.ezfact.facturacion.service.SerieService;
import com.mx.profact.ws.ArrayOfAnyType;
import com.mx.profact.ws.Timbrado;
import com.mx.profact.ws.TimbradoSoap;
import com.mysql.jdbc.log.Log;

import mx.gob.sat.cfd.x3.ComprobanteDocument;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.CfdiRelacionados;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Complemento;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Emisor;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Impuestos;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Impuestos.Retenciones;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Impuestos.Retenciones.Retencion;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Impuestos.Traslados;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Impuestos.Traslados.Traslado;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Receptor;
import mx.gob.sat.sitioInternet.cfd.catalogos.CClaveUnidad;
import mx.gob.sat.sitioInternet.cfd.catalogos.CFormaPago;
import mx.gob.sat.sitioInternet.cfd.catalogos.CImpuesto;
import mx.gob.sat.sitioInternet.cfd.catalogos.CMetodoPago;
import mx.gob.sat.sitioInternet.cfd.catalogos.CMoneda;
import mx.gob.sat.sitioInternet.cfd.catalogos.CPais;
import mx.gob.sat.sitioInternet.cfd.catalogos.CRegimenFiscal;
import mx.gob.sat.sitioInternet.cfd.catalogos.CTipoDeComprobante;
import mx.gob.sat.sitioInternet.cfd.catalogos.CTipoRelacion;
import mx.gob.sat.sitioInternet.cfd.catalogos.CTipoFactor;
import mx.gob.sat.sitioInternet.cfd.catalogos.CUsoCFDI;
import mx.gob.sat.timbreFiscalDigital.TimbreFiscalDigitalDocument;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;


@Service
public class ComprobanteServiceImpl implements ComprobanteService {
	private static final Logger LOG = LoggerFactory.getLogger(ComprobanteServiceImpl.class);

	@Autowired
	private SerieService serieService;

	@Autowired
	private EmisorService emisorService;

	@Autowired
	private ComprobanteDao comprobanteDao;

	@Autowired
	private Validator validator;

	@Autowired
	private QRService qrCodeService;

	@Autowired
	private CertificadoService certificadoService;

	@Value("${facturacion.produccion}")
	private Boolean produccion;

	@Value("${facturacion.user.name}")
	private String user;

	@Value("${facturacion.user.password}")
	private String password;

	@Value("${facturacion.path.plantillas}")
	private String pathPlantillas;

	@Value("${facturacion.path.plantillas.p0}")
	private String pathPlantilla0;

	@Value("${facturacion.path.plantillas.p1}")
	private String pathPlantilla1;

	@Value("${facturacion.path.plantillas.p2}")
	private String pathPlantilla2;

	@Value("${facturacion.path.plantillas.p3}")
	private String pathPlantilla3;

	@Value("${facturacion.path.plantillas.personalizada}")
	private String pathPlantillaPersonalizada;

	@Override
	public void create(Comprobante comprobante) {
		try {
			Assert.notNull(comprobante, "La remision no puede ser nula");
			validar(comprobante);
			
			comprobanteDao.create(comprobante);
		} catch (IllegalArgumentException ex) {
			String message = "Error al intentar crear la remision en la base de datos.";
			LOG.error(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	private void validar(Comprobante comprobante) {
		Set<ConstraintViolation<Comprobante>> errors = validator.validate(comprobante);

		if (CollectionUtils.isNotEmpty(errors)) {
			String message = "El comprobante presenta errores de validación.";
			LOG.warn(message + " " + errors);
			throw new ValidationException(message, errors);
		}
	}

	@Override
	public Comprobante read(long id) {
		LOG.debug("Recuperando una orden de venta de la base de datos.");

		return comprobanteDao.read(id);
	}

	@Override
	public void update(Comprobante remision) {
		try {
			LOG.debug("Iniciando el proceso de actualizacion de la orden de venta.");
			Assert.notNull(remision, "La remision no puede ser nula.");
			Assert.notNull(remision.getId(), "El identificador de la remision no puede ser nulo.");

			validar(remision);

			comprobanteDao.update(remision);
		} catch (IllegalArgumentException ex) {
			String message = "Error al intentar crear la remision en la base de datos.";
			LOG.error(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public void generarCFDi(long id) {
		Comprobante remision = read(id);
		Assert.notNull(remision, "No se pudo recuperar la orden.");

		Certificado certificado = certificadoService.read(remision.getEmisor());
		Assert.notNull(remision, "El comprobante no existe en la base de datos.");

		File xmlFile = generarDocumento(remision, certificado);
		TimbradoResponse response = timbrar(xmlFile, id);

		ComprobanteDocument document = parseFile(xmlFile);

		remision.setUuid(getUUID(document));
		remision.setActivo(true);
		remision.setFechaTimbrado(new Date());
		
		if (remision.getSerie() == null) {
			Collection<Serie> series = serieService.findAll(remision.getEmisor());
			if (series != null) {
				remision.setSerie(series.iterator().next());
			}
		}

		if (remision.getSerie() != null) {
			synchronized (this) {
				remision.setFolio(serieService.getSiguienteFolio(remision.getSerie()));
				serieService.actualizarSiguienteFolio(remision.getSerie());
			}
		}

		comprobanteDao.update(remision);

		File qrCode = new File(xmlFile.getParent(), "QR" + File.separator + xmlFile.getName().replace(".xml", ".png"));

		qrCodeService.guardarQRCode(response.getQrCode(), qrCode);
		File pdfFile = null;
		try {
			pdfFile = generarRepresentacionImpresa(xmlFile, qrCode, remision);
			remision.setCfdi(FileUtils.readFileToByteArray(xmlFile));
		} catch (IOException ex) {
			String message = "Error al convertir en bytes el documento XML.";
			LOG.error(message, ex);
		}

		if (pdfFile != null && pdfFile.exists()) {
			try {
				remision.setRepresentacionImpresa(FileUtils.readFileToByteArray(pdfFile));
			} catch (IOException ex) {
				String message = "Error al convertir en bytes el documento PDF.";
				LOG.error(message, ex);
			}
		}

		comprobanteDao.update(remision);
	}

	private File generarDocumento(Comprobante remision, Certificado certificado) {
		ComprobanteDocument document = createDocument(remision, certificado);

		validarFechas(certificado, document);

		// validarDocumento(document);

		File xmlFile = save(document, getPath(remision));
		return xmlFile;
	}

	private void validarFechas(Certificado certificado, ComprobanteDocument document) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(certificado.getFin());

		LOG.debug("Validando las fechas del certificado. " + document.getComprobante().getFecha().after(cal));
		if (document.getComprobante().getFecha().after(cal)) {
			throw new ServiceException("El certificado ha caducado y ya no es válido.");
		}
	}

	private ComprobanteDocument parseFile(File xmlFile) {
		try {
			return ComprobanteDocument.Factory.parse(xmlFile);
		} catch (XmlException ex) {
			String message = "Error al parsear el archivo XML";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		} catch (IOException ex) {
			String message = "Error al parsear el archivo XML";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	private String getCadenaQr(ComprobanteDocument document) {
		StringBuilder qrString = new StringBuilder();
		qrString.append("?re=");
		qrString.append(document.getComprobante().getEmisor().getRfc());
		qrString.append("&rr=");
		qrString.append(document.getComprobante().getReceptor().getRfc());
		qrString.append("&tt=");
		qrString.append(document.getComprobante().getTotal());
		qrString.append("&id=");
		qrString.append(getUUID(document));

		return qrString.toString();
	}

	/**
	 * @param document
	 * @return
	 */
	private String getUUID(ComprobanteDocument document) {
		String qrCode = null;
		try {
			Complemento complemento = getComplemento(document);
			TimbreFiscalDigitalDocument tfdDoc = TimbreFiscalDigitalDocument.Factory
					.parse(complemento.newInputStream());
			qrCode = tfdDoc.getTimbreFiscalDigital().getUUID();
		} catch (IOException ex) {
			String message = "Error al recuperar el UUID del comprobanteFiscal";
			LOG.info(message, ex);
			throw new ServiceException(message, ex);
		} catch (XmlException ex) {
			String message = "Error al recuperar el UUID del comprobanteFiscal";
			LOG.info(message, ex);
			throw new ServiceException(message, ex);
		}

		return qrCode;
	}

	@Override
	public File generarRepresentacionImpresa(com.entich.ezfact.emisores.model.Emisor emisor) {
		Comprobante comprobanteTest = getComprobante(emisor);
		File xmlFile = new File("cfdi" + File.separator + "test" + File.separator + "test.xml");

		if (!xmlFile.exists()) {
			throw new ServiceException("No existe el archivo para generar la Representacion impresa de prueba.");
		}

		ComprobanteDocument document = parseFile(xmlFile);
		File qrCode = new File(xmlFile.getParent(), "QR" + File.separator + xmlFile.getName().replace(".xml", ".png"));

		if (!qrCode.exists()) {
			qrCodeService.generarQRCode(getCadenaQr(document), qrCode);
		}

		return generarRepresentacionImpresa(xmlFile, qrCode, comprobanteTest);
	}

	private Comprobante getComprobante(com.entich.ezfact.emisores.model.Emisor emisor) {
		Comprobante comprobante = ComprobanteFactory.newInstance();
		comprobante.setEmisor(emisor);

		return comprobante;
	}

	@Override
	public File generarRepresentacionImpresa(File xmlFile, File qrCode, Comprobante comprobante) {
		JRDataSource xmlDataSource = null;
		// JasperReport js;
		JasperPrint jp;

		File pdfFile = null;

		// File plantilla = new File(comprobante.getEmisor().getPlantilla()
		// + File.separator + "factura0.jasper");
		String tipoPlantilla = getLogoUrl(comprobante.getEmisor().getPlantilla(), comprobante.getEmisor().getRfc());

		// if(comprobante.getEmisor().getPlantilla().equals("p0")){
		// tipoPlantilla = pathPlantilla0;
		// }else if (comprobante.getEmisor().getPlantilla().equals("p1")) {
		// tipoPlantilla = pathPlantilla1;
		// }else if (comprobante.getEmisor().getPlantilla().equals("p2")) {
		// tipoPlantilla = pathPlantilla2;
		// }else if (comprobante.getEmisor().getPlantilla().equals("p3")) {
		// tipoPlantilla = pathPlantilla3;
		// }

		File plantilla = new File(pathPlantillas + File.separator + tipoPlantilla + File.separator + "factura.jasper");

		try {
			pdfFile = new File(xmlFile.getParentFile(), xmlFile.getName().replace(".xml", ".pdf"));
			xmlDataSource = new JRXmlDataSource(xmlFile, "/");
			// js = JasperCompileManager
			// .compileReport(plantilla.getAbsolutePath());

			Map<String, Object> paramsPdf = new HashMap<String, Object>();

			paramsPdf.put("SUBREPORT_DIR", plantilla.getParent());
			paramsPdf.put("PATH_QR", qrCode);
			paramsPdf.put("CADENA_ORIGINA_TIMBRE", getCadenaOriginalTimbre(xmlFile));
			paramsPdf.put("COMPROBANTE", comprobante);
			paramsPdf.put("RUTA_LOGO", pathPlantillas + comprobante.getEmisor().getRfc() + File.separator + "logo.jpg");

			jp = JasperFillManager.fillReport(plantilla.getAbsolutePath(), paramsPdf, xmlDataSource);
			String hexVal = comprobante.getEmisor().getColorPlantilla();
			int R = Integer.parseInt(hexVal.substring(1, 3), 16);
			int G = Integer.parseInt(hexVal.substring(3, 5), 16);
			int B = Integer.parseInt(hexVal.substring(5, 7), 16);
			if (hexVal != null && !"".equals(hexVal)) {
				Color userColor = new Color(R, G, B);
				JRStyle[] styleList = jp.getStyles();
				for (int j = 0; j < styleList.length; j++) {
					System.out.println("style " + j + " is " + styleList[j].getName());
					if (styleList[j].getName().equals("background")) {
						System.out.println("found background");
						styleList[j].setBackcolor(userColor);
						jp.addStyle(styleList[j], true);
					}
				}
			}

			JasperExportManager.exportReportToPdfFile(jp, pdfFile.getAbsolutePath());
		} catch (Exception ex) {
			String message = "Error al generar la representacion impresa del comprobante";
			LOG.warn(message, ex);
		}

		return pdfFile;
	}

	private String getCadenaOriginalTimbre(File xmlFile) {

		StringWriter salida = new StringWriter();
		Result result = new StreamResult(salida);

		TransformerFactory tf = TransformerFactory.newInstance();
		tf.setURIResolver(new URIResolver() {

			@Override
			public Source resolve(String href, String base) throws TransformerException {
				return new StreamSource(getClass().getResourceAsStream(href));
			}
		});

		try {
			ComprobanteDocument document = ComprobanteDocument.Factory.parse(xmlFile);
			Complemento complemento = getComplemento(document);
			Source xmlSource = new StreamSource(complemento.newInputStream());
			Source xslSOurce = new StreamSource(
					this.getClass().getClassLoader().getResourceAsStream("xslt/cadenaoriginal_TFD_1_0.xslt"));
			Transformer transformator = tf.newTransformer(xslSOurce);
			transformator.transform(xmlSource, result);
		} catch (TransformerException ex) {
			String message = "Error al intentar generar la cadena original del timbre.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		} catch (XmlException ex) {
			String message = "Error al intentar generar la cadena original del timbre.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		} catch (IOException ex) {
			String message = "Error al intentar generar la cadena original del timbre.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		} finally {
			close(salida);
		}

		return salida != null ? salida.toString() : null;
	}

	private String getPath(Comprobante remision) {
		String path = "cfdi" + File.separator + remision.getEmisor().getRfc() + File.separator + getPathDate()
				+ File.separator + remision.getCliente().getRfc() + "_" + remision.getId() + ".xml";

		return path;
	}

	private String getPathDate() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd");
		return formater.format(getDate().getTime());
	}

	private ComprobanteDocument createDocument(Comprobante remision, Certificado certificado) {
		ComprobanteDocument document = ComprobanteDocument.Factory.newInstance();
		 
		ComprobanteDocument.Comprobante comprobante = document.addNewComprobante();
		
		
			if (remision.getTipo().getId() == 1) {
				comprobante.setTipoDeComprobante(CTipoDeComprobante.I);
			} else if(remision.getTipo().getId()==71694){
				comprobante.setTipoDeComprobante(CTipoDeComprobante.I);
				ComprobanteDocument.Comprobante.CfdiRelacionados cfdirelacionado = comprobante.addNewCfdiRelacionados();
				cfdirelacionado.addNewCfdiRelacionado().setUUID(remision.getVUUIDpadre());
				cfdirelacionado.setTipoRelacion(CTipoRelacion.X_07);
			}
			else{
				comprobante.setTipoDeComprobante(CTipoDeComprobante.E);
			}
			comprobante.setVersion("3.3");
			comprobante.setFecha(getDateFormat());
			comprobante.setFormaPago(getFormaPago(remision.getForma()));
			comprobante.setCondicionesDePago(remision.getCondicion());
			comprobante.setSubTotal(getSubtotal(remision.getConceptos()));
			BigDecimal desc = getDescuento(remision);
	        if (desc != null && desc.compareTo(BigDecimal.ZERO) > 0) {
	            comprobante.setDescuento(desc);
	        }
			comprobante.setMoneda(CMoneda.MXN);
			comprobante.setMetodoPago(getMetodoPago(remision.getMetodo()));
			comprobante.setLugarExpedicion(remision.getLugarDeExpedicion());
			comprobante.setEmisor(getEmisor(remision.getEmisor()));
			comprobante.setReceptor(getReceptor(remision.getCliente()));
			comprobante.setConceptos(getConceptos(remision.getConceptos()));
			comprobante.setImpuestos(getImpuestos(comprobante.getConceptos()));
			comprobante.setTotal(getTotal(comprobante));
			return document;
		}
		

	private BigDecimal getTotal(mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante comprobante) {

		return comprobante.getSubTotal()
				.subtract(comprobante.getDescuento() == null ? BigDecimal.ZERO : comprobante.getDescuento())
				.add(comprobante.getImpuestos().getTotalImpuestosTrasladados() == null ? BigDecimal.ZERO
						: comprobante.getImpuestos().getTotalImpuestosTrasladados())
				.subtract(comprobante.getImpuestos().getTotalImpuestosRetenidos() == null ? BigDecimal.ZERO
						: comprobante.getImpuestos().getTotalImpuestosRetenidos())
				.setScale(2, BigDecimal.ROUND_HALF_UP);

	}

	private TimbradoResponse timbrar(File xmlFile, long id) {

		Timbrado timbrado = new Timbrado();
		TimbradoSoap cfdi = timbrado.getTimbradoSoap();
		TimbradoResponse response = null;
		try {
			response = new TimbradoResponse(cfdi.timbraCFDI(user,
					new String(Base64.encode(FileUtils.readFileToByteArray(xmlFile))), String.valueOf(id)));
			
			if (response != null  || !response.UUID.equals("") || response.UUID != null) {
				switch (response.getCodigo()) {
				case "0":
					ComprobanteDocument document = ComprobanteDocument.Factory
							.parse(response.getComprobante().toString());
					save(document, xmlFile.getAbsolutePath());
					qrCodeService.guardarQRCode(response.getQrCode(), new File(xmlFile.getParent(),
							"QR" + File.separator + xmlFile.getName().replace(".xml", ".png")));
					break;
				default:
					throw new ServiceException(response.getMensaje());
				}
			}
		} catch (IOException ex) {
			LOG.error("Error al timbrar el comprobante.", ex);
		} catch (XmlException ex) {
			LOG.error("Error al guardar el archvo timbrado.", ex);
		}
		return response;
	}

	private File save(XmlObject documento, String path) {

		File xmlFile = new File(path);
		XmlOptions saveOptions = new XmlOptions();
		saveOptions.setSavePrettyPrint();
		saveOptions.setSavePrettyPrintIndent(2);
		saveOptions.setCharacterEncoding("UTF-8");
		Map<Object, Object> suggestedPrefixes = new HashMap<Object, Object>();
		suggestedPrefixes.put("http://www.sat.gob.mx/cfd/3", "cfdi");
		XmlCursor cursor = documento.newCursor();
		if (cursor.toFirstChild()) {
			cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", "xsi"),
					"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
		}
		saveOptions.setSaveSuggestedPrefixes(suggestedPrefixes);
		try {
			if (!xmlFile.getParentFile().exists()) {
				xmlFile.getParentFile().mkdirs();
			}
			documento.save(xmlFile, saveOptions);
		} catch (IOException ex) {
			LOG.warn(ex.getMessage());
		}
		LOG.debug(xmlFile.getAbsolutePath());
		return xmlFile;
	}

	private String getCadenaOrignal(ComprobanteDocument document) {

		Source xmlSource = new StreamSource(document.newInputStream());
		Source xslSOurce = getXslSource();
		StringWriter salida = new StringWriter();
		Result result = new StreamResult(salida);
		TransformerFactory tf = TransformerFactory.newInstance();
		tf.setURIResolver(new URIResolver() {
			@Override
			public Source resolve(String href, String base) throws TransformerException {
				return new StreamSource(getClass().getResourceAsStream(href));
			}
		});
		try {
			Transformer transformator = tf.newTransformer(xslSOurce);
			LOG.debug(transformator.toString());
			LOG.debug(xmlSource.toString());
			LOG.debug(result.toString());
			transformator.transform(xmlSource, result);
		} catch (TransformerException ex) {
			String message = "Error en el proceso de transformación. Error al intentar generar la cadena original del timbre.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		} finally {
			close(salida);
		}
		return salida.toString();
	}

	private void close(StringWriter salida) {
		Assert.notNull(salida);

		try {
			salida.flush();
			salida.close();
		} catch (IOException ex) {
			String message = "Error al cerrar el Stream de salida para la generacion de la cadena original.";
			LOG.warn(message);
		}
	}

	private Source getXslSource() {
		return new StreamSource(this.getClass().getClassLoader().getResourceAsStream("xslt/cadenaoriginal_3_3.xslt"));
	}

	private Impuestos getImpuestos(Conceptos conceptos) {
		Impuestos impuestos = null;
		
		BigDecimal totalTraslados = BigDecimal.ZERO;
		BigDecimal totalRetenciones = BigDecimal.ZERO;

		Integer conteoIvaTrasladado = 0;
		BigDecimal totalIvaTrasladado = BigDecimal.ZERO;
		Integer conteoIepsTrasladado = 0;
		BigDecimal totalIepsTrasladado = BigDecimal.ZERO;
		Integer conteoIvaRetenido = 0;
		BigDecimal totalIvaRetenido = BigDecimal.ZERO;
		Integer conteoIsrRetenido = 0;
		BigDecimal totalIsrRetenido = BigDecimal.ZERO;

		BigDecimal tasaIvaTrasladado = BigDecimal.ZERO;
		BigDecimal tasaIepsTrasladado = BigDecimal.ZERO;

		Traslados traslados = null;
		Retenciones retenciones = null;

		// TODO:Realizar logica de cuando los impuestos son con diferentes tasas
		for (mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto concepto : conceptos
				.getConceptoArray()) {

			if (concepto.getImpuestos() != null) {
				if (concepto.getImpuestos().getTraslados() != null
						&& concepto.getImpuestos().getTraslados().getTrasladoArray() != null) {

					for (mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado trasladoConcepto : concepto
							.getImpuestos().getTraslados().getTrasladoArray()) {
						if (trasladoConcepto.getImpuesto().equals(CImpuesto.X_002)) {
							conteoIvaTrasladado++;
							totalIvaTrasladado = totalIvaTrasladado.add(trasladoConcepto.getImporte());
							tasaIvaTrasladado = trasladoConcepto.getTasaOCuota();
						} else if (trasladoConcepto.getImpuesto().equals(CImpuesto.X_003)) {
							conteoIepsTrasladado++;
							totalIepsTrasladado = totalIepsTrasladado.add(trasladoConcepto.getImporte());
							tasaIepsTrasladado = trasladoConcepto.getTasaOCuota();
						}
						totalTraslados = totalTraslados.add(trasladoConcepto.getImporte());
					}
				}
				if (concepto.getImpuestos().getRetenciones() != null
						&& concepto.getImpuestos().getRetenciones().getRetencionArray() != null) {

					for (mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retencionConcepto : concepto
							.getImpuestos().getRetenciones().getRetencionArray()) {
						if (retencionConcepto.getImpuesto().equals(CImpuesto.X_002)) {
							conteoIvaRetenido++;
							totalIvaRetenido = totalIvaRetenido.add(retencionConcepto.getImporte());
						} else if (retencionConcepto.getImpuesto().equals(CImpuesto.X_001)) {
							conteoIsrRetenido++;
							totalIsrRetenido = totalIsrRetenido.add(retencionConcepto.getImporte());
						}
						totalRetenciones = totalRetenciones.add(retencionConcepto.getImporte());
					}
				}
			}
		}
		
		if (conteoIvaTrasladado > 0 || conteoIepsTrasladado > 0 || conteoIvaRetenido > 0 || conteoIsrRetenido > 0) {
			impuestos = Impuestos.Factory.newInstance();
		}
		
		if (conteoIvaTrasladado > 0 || conteoIepsTrasladado > 0) {
			traslados = impuestos.addNewTraslados();
			if (conteoIvaTrasladado > 0) {
				Traslado traslado = traslados.addNewTraslado();
				traslado.setImporte(totalIvaTrasladado);
				traslado.setImpuesto(CImpuesto.X_002);
				traslado.setTasaOCuota(tasaIvaTrasladado);
				traslado.setTipoFactor(CTipoFactor.TASA);
			}
			if (conteoIepsTrasladado > 0) {
				Traslado traslado = traslados.addNewTraslado();
				traslado.setImporte(totalIepsTrasladado);
				traslado.setImpuesto(CImpuesto.X_003);
				traslado.setTasaOCuota(tasaIepsTrasladado);
				traslado.setTipoFactor(CTipoFactor.TASA);
			}
		}

		if (conteoIvaRetenido > 0 || conteoIsrRetenido > 0) {
			retenciones = impuestos.addNewRetenciones();
			if (conteoIvaRetenido > 0) {
				Retencion retencion = retenciones.addNewRetencion();
				retencion.setImporte(totalIvaRetenido);
				retencion.setImpuesto(CImpuesto.X_002);
			}
			if (conteoIsrRetenido > 0) {
				Retencion retencion = retenciones.addNewRetencion();
				retencion.setImporte(totalIsrRetenido);
				retencion.setImpuesto(CImpuesto.X_001);
			}
		}

		if (traslados != null) {
			impuestos.setTotalImpuestosTrasladados(totalTraslados.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		if (retenciones != null) {
			impuestos.setTotalImpuestosRetenidos(totalRetenciones.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		return impuestos;
	}

	private Conceptos getConceptos(Collection<Concepto> conceptos) {

		Conceptos conceptosXml = Conceptos.Factory.newInstance();
		Assert.notNull(conceptos, "La lista de conceptos para el Comprobante CFDI no puede ser nula.");
		Assert.notEmpty(conceptos, "La lista de conceptos para el Comprobante CFDI no puede ser vacia.");
		for (Concepto concepto : conceptos) {
			Conceptos.Concepto conceptoXml = conceptosXml.addNewConcepto();
			conceptoXml.setCantidad(new BigDecimal(concepto.getCantidad().toString()));
			conceptoXml.setDescripcion(concepto.getDescripcion());

			conceptoXml.setImporte(concepto.getImporte().setScale(6, BigDecimal.ROUND_HALF_UP));
			if (concepto.getDescuento().compareTo(BigDecimal.ZERO) > 0) {
				conceptoXml.setDescuento(concepto.getDescuento().setScale(6, BigDecimal.ROUND_HALF_UP));
			}
			conceptoXml.setUnidad(concepto.getUnidadDeMedida());
			conceptoXml.setValorUnitario(concepto.getPrecioUnitario().setScale(6, BigDecimal.ROUND_HALF_UP));
			conceptoXml.setNoIdentificacion(concepto.getCodigo());
			conceptoXml.setClaveProdServ(concepto.getClaveProdServ().getClave());
			conceptoXml.setClaveUnidad(getClaveUnidad(concepto.getClaveUnidad()));

			mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto.Impuestos impuestos = conceptoXml
					.addNewImpuestos();

			// TRASLADOS
			boolean isIvaTraslado = concepto.getIvaTrasladado() != null && concepto.getIvaTrasladado() >= 0;
			boolean isIepsTraslado = concepto.getIepsTrasladado() != null && concepto.getIepsTrasladado() >= 0;
			if (isIvaTraslado || isIepsTraslado) {
				mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados = impuestos
						.addNewTraslados();
				if (isIvaTraslado) {
					mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado = traslados
							.addNewTraslado();
					traslado.setBase(conceptoXml.getImporte().subtract(
							conceptoXml.getDescuento() != null ? conceptoXml.getDescuento() : BigDecimal.ZERO));
					traslado.setImpuesto(CImpuesto.X_002);
					traslado.setTipoFactor(CTipoFactor.TASA);
					traslado.setTasaOCuota(
							new BigDecimal(concepto.getIvaTrasladado()).setScale(6, BigDecimal.ROUND_HALF_UP));
					traslado.setImporte(traslado.getBase().multiply(traslado.getTasaOCuota()).setScale(2,
							BigDecimal.ROUND_HALF_UP));
				}
				if (isIepsTraslado) {
					mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado = traslados
							.addNewTraslado();
					traslado.setBase(conceptoXml.getImporte().subtract(
							conceptoXml.getDescuento() != null ? conceptoXml.getDescuento() : BigDecimal.ZERO));
					traslado.setImpuesto(CImpuesto.X_003);
					traslado.setTipoFactor(CTipoFactor.TASA);
					traslado.setTasaOCuota(
							new BigDecimal(concepto.getIepsTrasladado()).setScale(6, BigDecimal.ROUND_HALF_UP));
					traslado.setImporte(traslado.getBase().multiply(traslado.getTasaOCuota()).setScale(2,
							BigDecimal.ROUND_HALF_UP));
				}
			}

			// RETENCIONES
			boolean isIsrRetencion = concepto.getIsrRetenido() != null && concepto.getIsrRetenido() > 0;
			boolean isIvaRetencion = concepto.getIvaRetenido() != null && concepto.getIvaRetenido() > 0;

			if (isIsrRetencion || isIvaRetencion) {
				mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto.Impuestos.Retenciones retenciones = impuestos
						.addNewRetenciones();

				if (isIsrRetencion) {
					mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retencion = retenciones
							.addNewRetencion();
					retencion.setBase(conceptoXml.getImporte().subtract(
							conceptoXml.getDescuento() != null ? conceptoXml.getDescuento() : BigDecimal.ZERO));
					retencion.setImpuesto(CImpuesto.X_001);
					retencion.setTipoFactor(CTipoFactor.TASA);
					retencion.setTasaOCuota(
							new BigDecimal(concepto.getIsrRetenido()).setScale(6, BigDecimal.ROUND_HALF_UP));
					retencion.setImporte(retencion.getBase().multiply(retencion.getTasaOCuota()).setScale(2,
							BigDecimal.ROUND_HALF_UP));
				}
				if (isIvaRetencion) {
					mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retencion = retenciones
							.addNewRetencion();
					retencion.setBase(conceptoXml.getImporte().subtract(
							conceptoXml.getDescuento() != null ? conceptoXml.getDescuento() : BigDecimal.ZERO));
					retencion.setImpuesto(CImpuesto.X_002);
					retencion.setTipoFactor(CTipoFactor.TASA);
					retencion.setTasaOCuota(
							new BigDecimal(concepto.getIvaRetenido()).setScale(6, BigDecimal.ROUND_HALF_UP));
					retencion.setImporte(retencion.getBase().multiply(retencion.getTasaOCuota()).setScale(2,
							BigDecimal.ROUND_HALF_UP));
				}
			}
		}
		return conceptosXml;
	}

	private Receptor getReceptor(Cliente cliente) {
		Receptor receptor = Receptor.Factory.newInstance();
		receptor.setRfc(cliente.getRfc());
		receptor.setNombre(cliente.getNombreCompleto());
		if (!getResidencia(cliente.getPais()).equals(CPais.MEX)) {
			receptor.setResidenciaFiscal(getResidencia(cliente.getPais()));
		}
		receptor.setUsoCFDI(getUsoCFDI(cliente.getUsoCFDi()));
		return receptor;
	}

	private Emisor getEmisor(com.entich.ezfact.emisores.model.Emisor emisor) {
		Assert.notNull(emisor, "El emisor del comprobante no puede ser nulo.");

		Emisor emisorXml = Emisor.Factory.newInstance();
		if (produccion) {
			emisorXml.setRfc(emisor.getRfc());
		} else {
			emisorXml.setRfc("AAA010101AAA");
		}
		emisorXml.setNombre(emisor.getNombreCompleto());
		emisorXml.setRegimenFiscal(getRegimenFiscal(emisor.getRegimen()));
		return emisorXml;
	}

	private BigDecimal getDescuento(Comprobante remision) {
		BigDecimal descuento = BigDecimal.ZERO;
		for (Concepto conc : remision.getConceptos()) {
			descuento = descuento.add(conc.getDescuento()).setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		return descuento;
	}

	private BigDecimal getSubtotal(Collection<Concepto> conceptos) {
		Assert.notNull(conceptos, "La lista de conceptos del comprobante CFDI no puede ser nula.");
		Assert.isTrue(conceptos.size() >= 1, "La lista de conceptos del comprobante CFDI no puede ser vacia.");
		BigDecimal subtotal = BigDecimal.ZERO;
		for (Concepto concepto : conceptos) {
			subtotal = subtotal.add(concepto.getImporte());
		}
		return subtotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	private Calendar getDate() {
		return GregorianCalendar.getInstance();
	}
	
	private Calendar getDateFormat() {
    	Calendar myDate = Calendar.getInstance();   // returns GregorianCalendar
    	Calendar now = (Calendar)myDate.clone();    // save current timestamp
    	myDate.clear(); // this clears the fields, including Calendar.ZONE_OFFSET
    	myDate.set(     //set all fields back from the saved copy
    	    now.get(Calendar.YEAR),
    	    now.get(Calendar.MONTH),
    	    now.get(Calendar.DAY_OF_MONTH),
    	    now.get(Calendar.HOUR_OF_DAY),
    	    now.get(Calendar.MINUTE),
    	    now.get(Calendar.SECOND)
    	);
    	return myDate;
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Comprobante> findAll(com.entich.ezfact.emisores.model.Emisor emisor) {

		Map params = new HashMap();
		params.put("emisor", emisor);
		return comprobanteDao.search(params.entrySet());
	}

	@Override
	public void cancelar(Comprobante comprobante) {
		Timbrado timbrado = new Timbrado();
		TimbradoSoap cfdi = timbrado.getTimbradoSoap();
		String rfc = null;
		if (produccion) {
			rfc = comprobante.getEmisor().getRfc();
		} else {
			rfc = "AAA010101AAA";
		}

		ArrayOfAnyType response = cfdi.cancelaCFDI(user, rfc, comprobante.getUuid());

		Integer status = Integer.valueOf((String) response.getAnyType().get(1));

		switch (status) {
		case 0:

			comprobante.setActivo(false);
			comprobante.setAcuseCancelacion(getAcuseCancelacion(comprobante));
			comprobanteDao.update(comprobante);
			break;

		default:
			String message = (String) response.getAnyType().get(2);

			throw new ServiceException(message);
		}

	}

	@SuppressWarnings("unused")
	private void printValues(ArrayOfAnyType response) {
		int i = 0;

		for (Object item : response.getAnyType()) {
			LOG.debug(String.format("param[%s] = %s", i, item));
		}

	}

	private byte[] getAcuseCancelacion(Comprobante comprobante) {
		JRDataSource xmlDataSource = null;
		JasperReport js;
		JasperPrint jp;
		byte[] xmlBytes = null;

		try {
			File xmlFile = File.createTempFile("xmlCancelado", ".xml");
			FileUtils.writeByteArrayToFile(xmlFile, comprobante.getCfdi());
			
			Map<String, Object> paramsPdf = new HashMap<String, Object>();
			paramsPdf.put("COMPROBANTE", comprobante);

			js = JasperCompileManager.compileReport(pathPlantillas + "respuestaCancelacion.jrxml");
			xmlDataSource = new JRXmlDataSource(xmlFile, "/");
			jp = JasperFillManager.fillReport(js, paramsPdf, xmlDataSource);
			xmlBytes = JasperExportManager.exportReportToPdf(jp);
		} catch (JRException ex) {
			String message = "Error al generar el acuse de recibo del comprobante.";
			LOG.info(message, ex);
		} catch (IOException ex) {
			String message = "Error al generar el acuse de recibo del comprobante.";
			LOG.info(message, ex);
		}

		return xmlBytes;
	}

	@Override
	public boolean validarPlantilla(Long id) {
		com.entich.ezfact.emisores.model.Emisor emisor = emisorService.read(id);

		String tipoPlantilla = getLogoUrl(emisor.getPlantilla(), emisor.getRfc());
		if (emisor.getPlantilla().equals("PlantillaPersonalizada")) {
			tipoPlantilla = tipoPlantilla + "_" + emisor.getRfc();
		}
		boolean bandera = new File(pathPlantillas + tipoPlantilla).exists();

		if (!bandera) {
			LOG.warn("No existe la ruta: " + emisor.getPlantilla());
		}

		return bandera;
	}

	@Override
	public String getCadenaOriginal(String file) {

		try {
			ComprobanteDocument document = ComprobanteDocument.Factory.parse(new File(file));
			return getCadenaOrignal(document);
		} catch (XmlException ex) {

		} catch (IOException ex) {

		}
		return null;
	}

	class TimbradoResponse {
		private String codigo;
		private String mensaje;
		private String comprobante;
		private String UUID;
		private byte[] qrCode;

		public TimbradoResponse(ArrayOfAnyType response) {
			this.codigo = (String) response.getAnyType().get(1);
			this.mensaje = (String) response.getAnyType().get(2);
			this.comprobante = (String) response.getAnyType().get(3);
			this.qrCode = (byte[]) response.getAnyType().get(4);
			this.UUID = (String) response.getAnyType().get(5);
		}

		public String getCodigo() {
			return codigo;
		}

		public String getMensaje() {
			return mensaje;
		}

		public String getComprobante() {
			return comprobante;
		}

		public String getUUID() {
			return UUID;
		}

		public byte[] getQrCode() {
			return qrCode;
		}

	}

	private String getLogoUrl(String plantilla, String rfc) {

		String tipoPlantilla = pathPlantilla0;
		if (plantilla.equals("p0")) {
			tipoPlantilla = pathPlantilla0;
		} else if (plantilla.equals("p1")) {
			tipoPlantilla = pathPlantilla1;
		} else if (plantilla.equals("p2")) {
			tipoPlantilla = pathPlantilla2;
		} else if (plantilla.equals("p3")) {
			tipoPlantilla = pathPlantilla3;
		} else if (plantilla.equals("pp")) {
			tipoPlantilla = pathPlantillaPersonalizada + "_" + rfc;
		} else {
			tipoPlantilla = plantilla;
		}
		return tipoPlantilla;
	}

	private Complemento getComplemento(ComprobanteDocument document) {
		Assert.notNull(document, "Factura nula");
		Assert.notNull(document.getComprobante(), "Factura sin comprobante");
		Assert.notEmpty(document.getComprobante().getComplementoArray(), "Factura sin complementos");
		Complemento complemento = document.getComprobante().getComplementoArray()[0];
		return complemento;
	}

	private CFormaPago.Enum getFormaPago(FormaPago formaPago) {
		if (formaPago == null) {
			return null;
		}
		return CFormaPago.Enum.forString(formaPago.getClave());
	}

	private CMetodoPago.Enum getMetodoPago(MetodoPago metodoPago) {
		if (metodoPago == null) {
			return null;
		}
		return CMetodoPago.Enum.forString(metodoPago.getClave());
	}

	private CClaveUnidad.Enum getClaveUnidad(ClaveUnidad claveUnidad) {
		if (claveUnidad == null) {
			return null;
		}
		return CClaveUnidad.Enum.forString(claveUnidad.getClave());
	}

	private CPais.Enum getResidencia(PaisSAT pais) {
		/*
		 * if (pais == null) { return CPais.MEX; }
		 */
		return CPais.Enum.forString(pais.getClave());
	}

	private CUsoCFDI.Enum getUsoCFDI(UsoCFDi usoCFDi) {
		if (usoCFDi == null) {
			return CUsoCFDI.G_03;
		}
		return CUsoCFDI.Enum.forString(usoCFDi.getClave());
	}

	private CRegimenFiscal.Enum getRegimenFiscal(Regimen regimen) {
		if (regimen == null) {
			return CRegimenFiscal.X_601;
		}
		return CRegimenFiscal.Enum.forString(regimen.getClave());
	}

	@Override
	public boolean folioMasivoDisponible(com.entich.ezfact.emisores.model.Emisor emisor, Integer folio) {
		return comprobanteDao.folioMasivoDisponible(emisor, folio);
	}

	@Override
	public Comprobante get(String uuid) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("uuid", uuid);
		Collection<Comprobante> comprobantes = comprobanteDao.search(map.entrySet());
		return !org.springframework.util.CollectionUtils.isEmpty(comprobantes) ? comprobantes.iterator().next() : null;
	}
}
package com.entich.ezfact.web.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.entich.commons.catalogo.factory.OpcionDeCatalogoFactory;
import com.entich.commons.catalogo.service.OpcionDeCatalogoService;
import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.clientes.model.ClientePersonaFisica;
import com.entich.ezfact.clientes.model.ClientePersonaMoral;
import com.entich.ezfact.clientes.model.PaisSAT;
import com.entich.ezfact.clientes.model.UsoCFDi;
import com.entich.ezfact.clientes.service.ClienteService;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.model.ClaveProdServ;
import com.entich.ezfact.facturacion.model.ClaveUnidad;
import com.entich.ezfact.facturacion.model.Comprobante;
import com.entich.ezfact.facturacion.model.Concepto;
import com.entich.ezfact.facturacion.model.FormaPago;
import com.entich.ezfact.facturacion.model.MetodoPago;
import com.entich.ezfact.facturacion.model.TipoDocumento;
import com.entich.ezfact.facturacion.service.ComprobanteService;
import com.entich.ezfact.web.dto.FacturaMasivaDto;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 25-11-2017
 * @version 1.0
 *
 */
@Controller
@RequestMapping("app/facturacion/masiva")
public class FacturacionMasivaController {

	private static final Logger LOG = LoggerFactory.getLogger(FacturacionMasivaController.class);

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private OpcionDeCatalogoService catalogoService;

	@Autowired
	private ComprobanteService comprobanteService;

	private final SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String init() {
		return "facturas/masiva/upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, Model model, HttpSession session, WebRequest request) {

		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		String id = request.getParameter("id_tipoPago");
		
		Map<Integer, Comprobante> comprobantes = new HashMap<>();
		Set<FacturaMasivaDto> facturas = new HashSet<>();
		
		CSVReader csv = null;
		InputStream is = null;
		BufferedReader br = null;
		
		try {
			
				is = new ByteArrayInputStream(file.getBytes());
				br = new BufferedReader(new InputStreamReader(is));
				csv = new CSVReader(br, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, 1);
				String[] line;
				while ((line = csv.readNext()) != null) {
					Integer folio = null;
					try {
						// Atributos del comprobante
						folio = Integer.valueOf(line[0].trim());
						String rfc = line[1].trim();
						String nombre = line[2].trim();
						String pais = line[3].trim();
						String usoCfdi = line[4].trim();
						String condiciones = line[5].trim();
						String formaPago = line[6].trim();
						if (formaPago.length() == 1 && !formaPago.startsWith("0")) {
							formaPago = "0" + formaPago;
						}
						String lugarExpedicion = line[7].trim();
						while (lugarExpedicion.length() < 5) {
							lugarExpedicion = "0" + lugarExpedicion;
						} 
						
						String metodoPago = line[8].trim();
						// Atributos del concepto
						String descripcion = line[9].trim();
						Double cantidad = Double.valueOf(line[10].trim());
						BigDecimal precio = new BigDecimal(line[11].trim());
						String claveProductoServicio = line[12].trim();
						String claveUnidad = line[13].trim();
						String codigo = line[14].trim();
						Double ivaTrasladado = !StringUtils.isEmpty(line[15].trim()) ? Double.valueOf(line[15].trim()) : null;
						Double iepsTrasladado = !StringUtils.isEmpty(line[16].trim()) ? Double.valueOf(line[16].trim()) : null;
						Double ivaRetenido = !StringUtils.isEmpty(line[17].trim()) ? Double.valueOf(line[17].trim()) : null;
						Double isrRetenido = !StringUtils.isEmpty(line[18].trim()) ? Double.valueOf(line[18].trim()) : null;
						BigDecimal descuento = !StringUtils.isEmpty(line[19].trim()) ? new BigDecimal(line[19].trim()) : BigDecimal.ZERO;
						String idVentaUnica = !StringUtils.isEmpty(line[20].trim()) ? line[20].trim() : null;
						String uuidpadre = !StringUtils.isEmpty(line[21].trim()) ? line[21].trim() : null;
						Comprobante comprobante = comprobantes.get(folio);
						if (comprobante == null) {
							comprobante = new Comprobante();
							comprobante.setFolioMasivo(folio);
							comprobante.setActivo(Boolean.TRUE);
	
							Cliente cliente = null;
							if (!rfc.equals("XAXX010101000") && !rfc.equals("XEXX010101000") ) {
								cliente = clienteService.readByRfc(rfc);
							}
							if (cliente == null) {
								if (rfc.length() == 12) {
									cliente = new ClientePersonaMoral();
									((ClientePersonaMoral) cliente).setRazonSocial(nombre);
								} else {
									cliente = new ClientePersonaFisica();
									String[] nombrePF = nombre.split(" ");
									((ClientePersonaFisica) cliente).setNombre(nombrePF[0]);
									if (nombrePF.length > 1) {
										StringBuilder apellidos = new StringBuilder();
										for (int i = 0; i < nombrePF.length; i++) {
											if (i == 0) {
												continue;
											}
											apellidos.append(nombrePF[i]).append(" ");
										}
										((ClientePersonaFisica) cliente).setApellidoPaterno(apellidos.toString());
									} else {
										((ClientePersonaFisica) cliente).setApellidoPaterno("-");
									}
								}
								cliente.setRfc(rfc);
								cliente.setEmisor(emisor);
								cliente.setPais((PaisSAT) catalogoService.get(PaisSAT.class, pais));
								cliente.setRazonComercial(nombre);
								cliente.setUsoCFDi((UsoCFDi) catalogoService.get(UsoCFDi.class, usoCfdi));
								clienteService.create(cliente);
							}
							comprobante.setCliente(cliente);
	
							comprobante.setCondicion(condiciones);
							comprobante.setDescuento(BigDecimal.ZERO);
							comprobante.setEmisor(emisor);
							comprobante.setFechaCreacion(new Date());
							comprobante.setFechaEntrega(new Date());
							comprobante.setForma((FormaPago) catalogoService.get(FormaPago.class, formaPago));
							comprobante.setLugarDeExpedicion(lugarExpedicion);
							comprobante.setMetodo((MetodoPago) catalogoService.get(MetodoPago.class, metodoPago));
							comprobante.setTipo((TipoDocumento) OpcionDeCatalogoFactory.newInstance(TipoDocumento.class, id));
							comprobante.setIdVentaUnica(idVentaUnica);
							if(id.equals("71694")){	
								comprobante.setVUUIDpadre(uuidpadre);}
						}
	
						Concepto concepto = new Concepto();
						concepto.setCantidad(cantidad);
						concepto.setClaveProdServ((ClaveProdServ) catalogoService.get(ClaveProdServ.class, claveProductoServicio));
						ClaveUnidad cu = (ClaveUnidad) catalogoService.get(ClaveUnidad.class, claveUnidad);
						concepto.setClaveUnidad(cu);
						concepto.setCodigo(codigo);
						concepto.setDescripcion(descripcion);
						concepto.setDescuento(descuento);
						comprobante.getDescuento().add(concepto.getDescuento());
						concepto.setIepsTrasladado(iepsTrasladado);
						concepto.setIsrRetenido(isrRetenido);
						concepto.setIvaRetenido(ivaRetenido);
						concepto.setIvaTrasladado(ivaTrasladado);
						concepto.setPrecioUnitario(precio);
						concepto.setUnidadDeMedida(cu.getDescripcion());
						
						comprobante.addConcepto(concepto);
	
						comprobantes.put(folio, comprobante);
					} catch (Exception e) {
						FacturaMasivaDto facturaDto = new FacturaMasivaDto(false);
						facturaDto.setNumero(folio);
						facturaDto.setObservaciones(e.getMessage());
						facturas.add(facturaDto);
						continue;
					}
				}
			
		} catch (IOException e) {
			LOG.warn("Error al subir factura masiva ", e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(csv);
		}

		for (Map.Entry<Integer, Comprobante> entry : comprobantes.entrySet()) {
			FacturaMasivaDto factura = new FacturaMasivaDto(true);
			Comprobante comprobante = null;
			try {
				comprobante = entry.getValue();
				if (comprobanteService.folioMasivoDisponible(emisor, entry.getKey())) {
					comprobanteService.create(comprobante);
					comprobanteService.generarCFDi(comprobante.getId());
				} else {
					factura.setExitosa(false);
					factura.setObservaciones("El folio " + entry.getKey() + " ya ha sido facturado.");
				}
			} catch (Exception e) {
				LOG.warn("Error en factura masiva ", e);
				factura.setExitosa(false);
				factura.setObservaciones(e.getMessage());
			}
			factura.setNumero(entry.getKey());
			if (factura.isExitosa()) {
				factura.setId(comprobante.getId());
			}
			facturas.add(factura);
		}

		model.addAttribute("facturas", facturas);
		return "facturas/masiva/result";
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ResponseEntity<byte[]> download(@RequestParam("ids") List<Long> ids) {
		ResponseEntity<byte[]> response = null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(baos);

			for (Long id : ids) {
				Comprobante comprobante = comprobanteService.read(id);
				byte[] xml = comprobante.getCfdi();
				byte[] pdf = comprobante.getRepresentacionImpresa();
				String name = getName(comprobante);

				ZipEntry entryXml = new ZipEntry(name + ".xml");
				entryXml.setSize(xml.length);
				zos.putNextEntry(entryXml);
				zos.write(xml);
				zos.closeEntry();

				ZipEntry entryPdf = new ZipEntry(name + ".pdf");
				entryPdf.setSize(pdf.length);
				zos.putNextEntry(entryPdf);
				zos.write(pdf);
				zos.closeEntry();
			}
		} catch (Exception e) {
			LOG.warn("Error en descarga de factura masiva ", e);
		} finally {
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(zos);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.setContentType(MediaType.parseMediaType("application/zip"));
		String name = "facturacion-masiva-efectiva.zip";
		headers.setContentDispositionFormData(name, name);
		response = new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);

		return response;
	}

	private String getName(Comprobante comprobante) {
		String name = null;
		if (comprobante.getFolioMasivo() != null) {
			name = comprobante.getFolioMasivo() + "_" + comprobante.getUuid();
		} else {
			name = comprobante.getCliente().getRfc() + "_"
					+ ((comprobante.getSerie() != null) ? comprobante.getSerie().getNombre() + comprobante.getFolio()
							: formater.format(comprobante.getFechaTimbrado()));
		}
		return name;
	}
}

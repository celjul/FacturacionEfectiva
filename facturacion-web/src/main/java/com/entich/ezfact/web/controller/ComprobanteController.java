package com.entich.ezfact.web.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.entich.ezfact.clientes.model.ClientePersonaFisica;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.entich.commons.catalogo.service.OpcionDeCatalogoService;
import com.entich.commons.exceptions.ApplicationException;
import com.entich.commons.exceptions.service.ServiceException;
import com.entich.commons.catalogo.factory.OpcionDeCatalogoFactory;
import com.entich.ezfact.clientes.factory.ClienteFactory;
import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.clientes.model.ClientePersonaMoral;
import com.entich.ezfact.clientes.model.ContactoCliente;
import com.entich.ezfact.clientes.service.ClienteService;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.service.EmisorService;
import com.entich.ezfact.facturacion.model.Comprobante;
import com.entich.ezfact.facturacion.model.ComprobanteHelper;
import com.entich.ezfact.facturacion.model.MetodoPago;
import com.entich.ezfact.facturacion.model.TipoDocumento;
import com.entich.ezfact.facturacion.service.ComprobanteHelperService;
import com.entich.ezfact.facturacion.service.ComprobanteService;
import com.entich.ezfact.facturacion.service.ConceptoService;
import com.entich.ezfact.model.Adjunto;
import com.entich.ezfact.services.EmailService;
import com.entich.ezfact.usuarios.factory.RolFactory;
import com.entich.ezfact.usuarios.model.Rol;
import com.entich.ezfact.usuarios.model.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.entich.ezfact.folios.service.PaqueteUsuarioService;

@Controller
@RequestMapping("app/comprobantes")
public class ComprobanteController extends AbstractController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ComprobanteController.class);

	private SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private OpcionDeCatalogoService catalogoService;

	@Autowired
	private ComprobanteService remisionService;

	@Autowired
	private ComprobanteHelperService comprobanteService;
	
	@Autowired
	private EmisorService emisorService;

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailingService;
	
	@Autowired
	private ConceptoService conceptoService;
	
	@Autowired
	private OpcionDeCatalogoService opcionDeCatalogoService;
	
	@Autowired
	private PaqueteUsuarioService paqueteUsuarioService;
	
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "new" })
	public String newComprobante(Model modelo, HttpSession session) {
		initCatalogos(modelo);
		modelo.addAttribute("fechaActual", new Date());
		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		modelo.addAttribute("plantilla",emisor.getPlantilla());//traer el logo del emisor
		
		return "facturas/registro_venta";
	}

	private void initCatalogos(Model model) {
		
		addToModel(model, "tiposDeDocumento", catalogoService.getCatalogo(TipoDocumento.class));
		addToModel(model, "metodosPago", catalogoService.getCatalogo(MetodoPago.class));
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "{comprobante}" })
	public String readComprobante(Model model, @PathVariable Long comprobante) {
		initCatalogos(model);
		
		addToModel(model, "comprobante", remisionService.read(comprobante));
		return "facturas/editar_venta";
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/list" })
	public String listaComprobantesView() {
		return "facturas/lista";
	}
	
	@RequestMapping(method = { RequestMethod.DELETE }, value = { "{remision}/partida/{partida}" })
	public @ResponseBody Boolean deleteConcepto(@PathVariable Long remision, @PathVariable Long partida) {
		LOGGER.debug("Eliminando el concepto de la remisión.");
		conceptoService.delete(partida);
		return true;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/" })
	private @ResponseBody Collection<ComprobanteHelper>listaComprobantess(WebRequest request, Model model, HttpSession session, 
			@RequestParam(required = false, value="fi") String fechaInicial,
			@RequestParam(required = false, value="ff") String fechaFinal,
			@RequestParam(required = false, value="mi") BigDecimal montoMin,
			@RequestParam(required = false, value="mf") BigDecimal montoMax,
			@RequestParam(required = false, value="t") Long tipoDocumento,
			@RequestParam(required = false, value="c") Long idCliente,
			@RequestParam(required = false, value="nc") String nombreCliente,
			@RequestParam(required = false, value="rfc") String rfc,
			@RequestParam(required = false, value="e") Boolean estatus,
			@RequestParam(required = false, value="lblclave") String idquery
			) {
		
		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		Cliente cliente = null;
		TipoDocumento tipo = null;
		Date fInicio = null;
		Date fFin = null;
		
		String idclave = request.getParameter("lblclave");
		
		if (fechaInicial == null && fechaFinal == null && tipoDocumento == null
				&& idCliente == null && StringUtils.isBlank(rfc) && StringUtils.isBlank(nombreCliente)
				&& estatus == null && montoMin == null && montoMax == null) {
			return comprobanteService.findOnly50(emisor,0);
		} else {
			if (idCliente != null || rfc != null || nombreCliente != null) {
				cliente = ClienteFactory.newInstance(ClientePersonaFisica.class);
				cliente.setId(idCliente);
				cliente.setRfc(rfc);
			}
			
			if (tipoDocumento != null) {
				tipo = OpcionDeCatalogoFactory.newInstance(TipoDocumento.class, tipoDocumento);
			}
			
			if (fechaFinal != null && fechaInicial != null) {
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				try {
					fInicio = format.parse(fechaInicial);
					
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTime(format.parse(fechaFinal));
					calendar.set(Calendar.HOUR_OF_DAY, 23);
					calendar.set(Calendar.MINUTE, 59);
					calendar.set(Calendar.SECOND, 59);
					
					fFin = calendar.getTime();
				} catch (ParseException ex) {
					LOGGER.warn("Problemas al intentar convertir la fecha.", ex);
				}
			}
			return comprobanteService.find(emisor, fInicio, fFin, montoMin, 
					montoMax, cliente, tipo, estatus, nombreCliente);
		}
	}

	private String listaComprobantes(Model model, HttpSession session, 
			@RequestParam(required = false) String fi,
			@RequestParam(required = false) String ff,
			@RequestParam(required = false) Long t,
			@RequestParam(required = false) Long c
			) {
		
		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		Cliente cliente = null;
		TipoDocumento tipo = null;
		Date fInicio = null;
		Date fFin = null;
		
		addToModel(model, "tiposDocumento", opcionDeCatalogoService.getCatalogo(TipoDocumento.class));
		if (fi == null && ff == null && t == null && c == null) {
			model.addAttribute("comprobantes", comprobanteService.find(emisor));
		} else {
			if (c != null) {
				cliente = ClienteFactory.newInstance(ClientePersonaMoral.class, c);
			}
			
			if (t != null) {
				tipo = OpcionDeCatalogoFactory.newInstance(TipoDocumento.class, t);
			}
			
			if (ff != null && fi != null) {
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				try {
					fInicio = format.parse(fi);
					
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTime(format.parse(ff));
					calendar.set(Calendar.HOUR_OF_DAY, 23);
					calendar.set(Calendar.MINUTE, 59);
					calendar.set(Calendar.SECOND, 59);
					
					fFin = calendar.getTime();
				} catch (ParseException ex) {
					LOGGER.warn("Problemas al intentar conertir la fecha.", ex);
				}
			}
			model.addAttribute("comprobantes", comprobanteService.find(emisor, fInicio, fFin, cliente, tipo, null));
		}
		return "facturas/lista";
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = { "/" })
	public @ResponseBody
	Comprobante saveRemision(HttpSession session,
			@RequestBody Comprobante remision) {
		LOGGER.debug("Recuperacion de una remision para generar la facturacion.");

		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		remision.setEmisor(emisor);
		//remision.setDescuento(remision.getDescuento());
//		remision.setCliente(clienteService.read(remision.getCliente().getId()));
		
		if (remision.getId() == null) {
			remision.setFechaCreacion(new Date());
			remisionService.create(remision);
		} else {
			remisionService.update(remision);
		}

		return remision;
	}

	@RequestMapping(method = { RequestMethod.POST }, value = { "timbrar" })
	public String timbrarComprobante(Model modelo, HttpSession session,
			@RequestParam Long id) {
		try {
			Emisor emisor = (Emisor) session.getAttribute("emisorSession");
			/*ContadorFoliosEmisor contador = paqueteUsuarioService.getValidacionFolios(emisor);*/
			/*ContadorFoliosEmisor contador = paqueteUsuarioService.getget;*/
			
			Usuario usuario = (Usuario) session.getAttribute("user");
			paqueteUsuarioService.read(usuario);
			Rol webmaster = RolFactory.newInstance(1L);
			String timbresDisponibles = paqueteUsuarioService.getContadorFolios(emisor).getMensaje();

			if (usuario.getRoles().contains(webmaster)) {
				remisionService.generarCFDi(id);
				return listaComprobantes(modelo, session, null, null, null, null);
			} else if(timbresDisponibles.equals("")) {
				remisionService.generarCFDi(id);
				return listaComprobantes(modelo, session, null, null, null, null);
			} else {
				throw new ServiceException(timbresDisponibles);
			}



			/*else if (contador != null && contador.getUtilizados() < contador.getDisponibles()) {
				
				remisionService.generarCFDi(id);
				
				return listaComprobantes(modelo, session, null, null, null, null);
			} else {
				throw new ServiceException("El emisor seleccionado no cuenta con folios para poder timbrar, pongase en contacto con su proveedor.");
			}*/
			

		} catch (NumberFormatException ex) {
			String message = String
					.format("El Id enviado [%s] no puede ser convertido a un Id valido.",
							id);
			LOGGER.warn(message, ex);
			throw new ServiceException(message, ex);
		} catch (ApplicationException ex) {
			modelo.addAttribute("ex", ex);
			
			return ERROR_PAGE;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}/file/{format}")
	public ResponseEntity<byte[]> getPDF(@PathVariable Long id,
			@PathVariable String format) {
		Comprobante comprobante = remisionService.read(id);

		HttpHeaders headers = new HttpHeaders();

		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = null;
		
		String filename = getName(comprobante);
				
		switch (format) {
		case "xml":
			headers.setContentType(MediaType.parseMediaType("text/xml"));
			filename += ".xml";
			headers.setContentDispositionFormData(filename, filename);
			response = new ResponseEntity<byte[]>(comprobante.getCfdi(),
					headers, HttpStatus.OK);
			
			break;
		case "cancelacion":
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			filename += "Cancelacion.pdf";
			headers.setContentDispositionFormData(filename, filename);
			response = new ResponseEntity<byte[]>(
					comprobante.getAcuseCancelacion(), headers,
					HttpStatus.OK);
			break;

		default:
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			filename += ".pdf";
			headers.setContentDispositionFormData(filename, filename);
			response = new ResponseEntity<byte[]>(
					comprobante.getRepresentacionImpresa(), headers,
					HttpStatus.OK);
			break;
		}
		

		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "sendMail")
	public String enviarEmail(@RequestParam Long id, @RequestParam String mensaje, @RequestParam String correos, @RequestParam String nombres) {
		final Map<String, Object> model = new HashMap<String, Object>();
		final String[] emailArray = correos.split(";");
		model.put("title", "T&uacute; Factura Electr&oacute;nica.");
		model.put("name", "Env&iacute;o Comprobante Fiscal Digital <br> ESTIMADO (A): " + nombres);
		model.put("message", "Siempre pensando en tu comodidad, te damos la bienvenida a nuestro servicio de facturaci&oacute;n electr&oacute;nica. <br>" +
				"http://facturacionefectiva.com <br> La manera m&aacute;s r&aacute;pida y f&aacute;cil de facturar.");
		model.put("customMessage", mensaje);
		final Comprobante comprobante = remisionService.read(id);
		model.put("coloremisor",comprobante.getEmisor().getColorPlantilla());
		model.put("logotipo",comprobante.getEmisor().getPlantilla());//traer el logo del emisor
		
//		for (final ContactoCliente contacto : comprobante.getCliente().getContactos()) {
//			final Adjunto pdf = new Adjunto(comprobante.getCliente().getRfc() + "_" + comprobante.getId() + ".pdf", new ByteArrayResource(comprobante.getRepresentacionImpresa()));
//			final Adjunto xml = new Adjunto(comprobante.getCliente().getRfc() + "_" + comprobante.getId() + ".xml", new ByteArrayResource(comprobante.getCfdi()));
//
//			Thread hilo = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					emailingService.sendEmail(contacto.getEmail(), model, "Comprobante Fiscal Digital", "envioComprobante.vm", pdf, xml);
//				}
//			});
//			hilo.start();
//		}>
		
		final String emails = correos;
		final Adjunto pdf = new Adjunto(comprobante.getCliente().getRfc() + "_" + comprobante.getId() + ".pdf", new ByteArrayResource(comprobante.getRepresentacionImpresa()));
		final Adjunto xml = new Adjunto(comprobante.getCliente().getRfc() + "_" + comprobante.getId() + ".xml", new ByteArrayResource(comprobante.getCfdi()));
		

		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				emailingService.sendEmail(emailArray, model, comprobante.getEmisor().getNombreCompleto() + " - Factura" , "envioComprobante.vm", pdf, xml);
			}
		});
		hilo.start();

		return "facturas/lista";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "cancel")
	public String cancelarComprobante(@RequestParam Long id) {
		Comprobante comprobante = remisionService.read(id);
		
		remisionService.cancelar(comprobante);
		return "facturas/lista";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "test" })
	public ResponseEntity<byte[]> testRepresentacion(HttpSession session) {
		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		
		HttpHeaders headers = new HttpHeaders();

		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = null;
		
		String filename = "RepresentacionImpresaTest.pdf";
		
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setContentDispositionFormData(filename, filename);
		try {
		response = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(remisionService.generarRepresentacionImpresa(emisor)),
				headers, HttpStatus.OK);
		} catch (IOException ex) {
			LOGGER.warn("No se pudo generar la representacion impresa de pruebas.", ex);
		}
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
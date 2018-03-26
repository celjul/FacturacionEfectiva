package com.entich.ezfact.web.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.emisores.factory.CertificadoFactory;
import com.entich.ezfact.emisores.factory.EmisorFactory;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.model.EmisorFisica;
import com.entich.ezfact.emisores.service.CertificadoService;
import com.entich.ezfact.emisores.service.EmisorService;

/**
 * Controller para el módulo de certificados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 12/12/2013
 */
@Controller
@RequestMapping("app/certificados")
public class CertificadoController extends AbstractController {

	private static final Logger LOG = LoggerFactory
			.getLogger(CertificadoController.class);

	@Autowired
	@Qualifier("certificadoServiceProfact") 
	private CertificadoService certificadoService;

	@Autowired
	private EmisorService emisorService;

	@RequestMapping(method = RequestMethod.GET, value = "new")
	public String newCertificado(ModelMap model) {
		LOG.info("Iniciando la pantalla para registrar un certificado");
		model.put("emisores", emisorService.getAll());
		return "certificado/registro_certificado";
	}

	@RequestMapping(method = RequestMethod.POST, value = "create")
	public String createCertificado(ModelMap model, HttpSession session,
			@RequestParam("txt-nombreCSD") String nombre,
			@RequestParam("file-certificado") MultipartFile fileCer,
			@RequestParam("file-clave") MultipartFile fileKey,
			@RequestParam("txt-password") String password) {
		LOG.debug("Creando un certificado.");

		try {
			Certificado certificado = CertificadoFactory.newInstance();
			certificado.setCertificado(fileCer.getBytes());
			certificado.setClave(fileKey.getBytes());

			Emisor emisor = (Emisor) session.getAttribute("emisorSession");
			certificado.setEmisor(emisor);
			certificado.setNombre(nombre);
			certificado.setPassword(password);
			certificadoService.create(certificado);

			model.put("type", true);
			model.put("message",
					"Se ha registrado el certificado correctamente");
		} catch (IOException ex) {
			String message = "Ocurrió un error al crear el certificado";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
		model.put("emisores", emisorService.getAll());
		return "certificado/registro_certificado";
	}

	@ExceptionHandler(ServiceException.class)
	public ModelAndView handleCustomException(ServiceException ex) {
		ModelAndView model = new ModelAndView(
				"/certificado/registro_certificado");
		model.addObject("type", false);
		model.addObject("message", ex.getMessage());
		return model;
	}

	@RequestMapping(method = RequestMethod.GET, value = "list")
	public String listAllEmisores(ModelMap model) {
		LOG.info("Devolviendo la vista para mostrar la lista de certificados.");
		return "certificado/lista_certificado";
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody boolean listAllEmisores(@PathVariable Long id) {
		LOG.debug("Eliminando el certificado solicitado.");

		certificadoService.delete(CertificadoFactory.newInstance(id));

		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public @ResponseBody Collection<Certificado> getCertificados(
			HttpSession session) {
		LOG.debug("Cargando los certificados de un emisor");
		Map map = new HashMap();
		map.put("emisor", (Emisor) session.getAttribute("emisorSession"));

		return certificadoService.search(map);
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/{emisor}/caducidad" })
	public @ResponseBody Integer validarCertificado(@PathVariable Long emisor) {
		LOG.debug(String
				.format("Validando la existencia de la plantilla del emisor %s",
						emisor));
		Emisor em = EmisorFactory.newInstance(EmisorFisica.class, emisor);
		Certificado certificado = certificadoService.read(em);

		if (certificado != null) {
			return validarCertificado(certificado);
		} else {
			return null;
		}
	}

	private int validarCertificado(Certificado certificado) {
		long dif = certificado.getFin().getTime() - new Date().getTime();
		final int MILIS_PER_DAY = 1000 * 60 * 60 * 24;
		int dias = (int) (dif / MILIS_PER_DAY);
		if (dias == 0 && ((dif % MILIS_PER_DAY) > 1000)) {
			dias = 1;
		}

		return dias;
	}

}
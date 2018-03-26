package com.entich.ezfact.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.clientes.service.ClienteService;
import com.entich.ezfact.emisores.factory.CertificadoFactory;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.service.CertificadoService;
import com.entich.ezfact.emisores.service.EmisorService;
import com.entich.ezfact.productos.model.Producto;
import com.entich.ezfact.services.impl.ConfigurationServiceImp;
import com.entich.ezfact.usuarios.factory.RolFactory;
import com.entich.ezfact.usuarios.model.Rol;
import com.entich.ezfact.usuarios.model.Usuario;
import com.entich.ezfact.usuarios.service.UsuarioService;
import com.entich.ezfact.web.dto.FileMetaDataCertificado;
import com.entich.ezfact.web.dto.FileMetaDataClave;

@Controller
@RequestMapping("app/wizard")
public class WizardController extends AbstractController {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(WizardController.class);
	
	@Autowired
	private CertificadoService certificadoService;

	@Autowired
	private EmisorService emisorService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ConfigurationServiceImp configurationServiceImpl;
	
	public static final String LOGO = "logoEmisor";
	
	@RequestMapping(method= RequestMethod.GET, value = "/inicio")
	public String wizard(HttpSession session, Model model) {
		
		File convFile = new File(configurationServiceImpl.getPathLogos() + configurationServiceImpl.getPathPlantilla0() + File.separator + "logo.jpg");
		byte[] array;
		try {
			array = Files.readAllBytes(convFile.toPath());
		} catch (IOException e) {
			String message = "No se pudo leer la imagen del servidor.";
			LOG.warn(message, e);
			throw new ServiceException(message, e);
		}	
		addToModel(model, "logo", array);
		
		session.setAttribute(LOGO, array);
		
		return "wizard/wizard";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "selected/{idEmisor}" })
	public @ResponseBody Emisor selected(HttpSession session, Model model,
			@PathVariable Long idEmisor) throws ParseException {
		LOG.debug("Cargando los comprobantes emitidos por un emisor");
		
		Emisor emisor = emisorService.read(idEmisor);
		
		Usuario usuario = (Usuario) session.getAttribute("user");
		Rol webmaster = RolFactory.newInstance(1L);
		if (usuario.getRoles().contains(webmaster) || usuario.getEmisores().contains(emisor)) {
			session.setAttribute("emisorSession", emisor);
		} else {
			model.addAttribute("ex", new ServiceException("El usuario no tiene acceso al emisor solicitado."));
			return null;
		}
		
		
		return emisor;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "create/certificado")
	public @ResponseBody Certificado crearCertificado(@RequestBody Certificado certificado, HttpSession session) {
		LOG.debug("Guardando datos del certificado.");
		
		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		certificado.setEmisor(emisor);
		certificadoService.create(certificado);
		
		return certificado;
	}
	
//	esto es para cargar un certificado
	
	public static final String CERTIFICADO = "certificado";

	@RequestMapping(method = RequestMethod.POST, value = "subir/certificado")
	public @ResponseBody FileMetaDataCertificado processUploadCertificado(
			@RequestParam MultipartFile file, HttpServletRequest request,
			HttpSession session) {

		//session.removeAttribute(CERTIFICADO);

		FileMetaDataCertificado fileMeta = new FileMetaDataCertificado();
		fileMeta.setName(file.getOriginalFilename());
		fileMeta.setSize(file.getSize() / 1024 + " Kb");
		fileMeta.setType(file.getContentType());

		try {
			fileMeta.setBytes(file.getBytes());
		} catch (IOException ex) {
			String message = "Error al intentar subir el archivo al servidor.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
		session.setAttribute(CERTIFICADO, fileMeta);
		return fileMeta;
	}
	
	
	public static final String CLAVE = "clave";
	
	@RequestMapping(method = RequestMethod.POST, value = "subir/clave")
	public @ResponseBody FileMetaDataClave processUploadClave(
			@RequestParam MultipartFile file, HttpServletRequest request,
			HttpSession session) {

		session.removeAttribute(CLAVE);

		FileMetaDataClave fileMeta = new FileMetaDataClave();
		fileMeta.setName(file.getOriginalFilename());
		fileMeta.setSize(file.getSize() / 1024 + " Kb");
		fileMeta.setType(file.getContentType());

		try {
			fileMeta.setBytes(file.getBytes());
		} catch (IOException ex) {
			String message = "Error al intentar subir el archivo al servidor.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
		session.setAttribute(CLAVE, fileMeta);
		return fileMeta;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "guardar/certificado")
	public @ResponseBody Certificado guardar(@RequestBody Certificado certificado, HttpSession session) {
		
			if (session.getAttribute(CERTIFICADO) != null) {
				FileMetaDataCertificado file = (FileMetaDataCertificado) session
						.getAttribute(CERTIFICADO);
				certificado.setCertificado(file.getBytes());
			}
			
			if (session.getAttribute(CLAVE) != null) {
				FileMetaDataClave file = (FileMetaDataClave) session
						.getAttribute(CLAVE);
				certificado.setClave(file.getBytes());
			}
			
			Emisor emisor = (Emisor) session.getAttribute("emisorSession");
			certificado.setEmisor(emisor);
			if (certificado.getId() == null) {
				certificadoService.create(certificado);
			}
		
		return certificado;
	}
}

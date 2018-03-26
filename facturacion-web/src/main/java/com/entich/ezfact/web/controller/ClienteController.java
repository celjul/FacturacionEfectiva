package com.entich.ezfact.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

import com.entich.commons.direcciones.factory.DireccionFactory;
import com.entich.commons.direcciones.service.PaisService;
import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.clientes.factory.ContactoClienteFactory;
import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.clientes.model.DireccionCliente;
import com.entich.ezfact.clientes.service.ClienteService;
import com.entich.ezfact.clientes.service.ContactoClienteService;
import com.entich.ezfact.clientes.service.DireccionClienteService;
import com.entich.ezfact.emisores.model.Emisor;

/**
 * Controller para el módulo de clientes.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 20/12/2013
 */
@Controller
@RequestMapping("app/clientes")
public class ClienteController extends AbstractController {

	private static final Logger LOG = LoggerFactory
			.getLogger(ClienteController.class);

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PaisService paisService;
	
	@Autowired
	private DireccionClienteService direccionClienteService;
	
	@Autowired
	private ContactoClienteService contactoClienteService;

	@RequestMapping(method = RequestMethod.GET, value = "new")
	public String newCliente(HttpSession session, Model model) {
		return "cliente/registro_cliente";
	}

//	@RequestMapping(method = RequestMethod.POST, value = "fisica")
//	public @ResponseBody
//	Cliente createClienteFisico(HttpSession session,
//			@RequestBody ClientePersonaFisica cliente) {
//		LOG.info("Creando un cliente de tipo persona fisica");
//		cliente.setEmisor(getEmisor(session));
//		clienteService.create(cliente);
//		return cliente;
//	}

	@RequestMapping(method = RequestMethod.POST, value = "/")
	public @ResponseBody
	Cliente createClienteMoral(HttpSession session,
			@RequestBody Cliente cliente) {
		LOG.info("Creando un cliente de tipo persona moral");
		cliente.setEmisor(getEmisor(session));
		clienteService.create(cliente);
		return cliente;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "{id}" })
	public String readCliente(HttpSession session, Model model,
			@PathVariable Long id) {
		LOG.info("Cargando un cliente");
		
		addToModel(model, "cliente", clienteService.read(id));
		return "cliente/edicion_cliente";
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "leer/{id}" })
	public @ResponseBody Cliente readClienteById(HttpSession session, Model model,
							  @PathVariable Long id) {
		LOG.info("Cargando un cliente por Id");
		return clienteService.read(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/")
	public @ResponseBody
	Cliente updateClienteMoral(HttpSession session,
			@RequestBody Cliente cliente) {
		LOG.info("Actualizando un cliente de tipo persona moral");
		cliente.setEmisor(getEmisor(session));
		clienteService.update(cliente);
		return cliente;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "list")
	public String listAllClientes(HttpSession session, ModelMap model) {
		LOG.info("Cargando todos los clientes");
		/*
		
		*/
		return "cliente/lista_cliente";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public @ResponseBody Collection<Cliente> getClientes(HttpSession session) {
		LOG.info("Recuperando la lista de clientes.");
		Map map = new HashMap();
		map.put("emisor", getEmisor(session));
		return clienteService.search(map);
	}

	@RequestMapping(method = { RequestMethod.DELETE }, value = { "{cliente}/contactos/{contacto}" })
	public @ResponseBody String eliminarContacto(@PathVariable Long cliente, @PathVariable Long contacto) {
		LOG.info("Eliminando un contacto del cliente.");
		contactoClienteService.delete(ContactoClienteFactory.newInstanceId(contacto));
		return "";
	}
	
	@RequestMapping(method = { RequestMethod.DELETE }, value = { "{cliente}/direcciones/{direccion}" })
	public @ResponseBody String eliminarDireccion(@PathVariable Long cliente, @PathVariable Long direccion) {
		LOG.info("Eliminando una direccion del cliente.");
		DireccionCliente dirObj = DireccionFactory.newInstance(DireccionCliente.class, direccion);
		
		direccionClienteService.delete(dirObj);
		return "";
	}
	
	private Emisor getEmisor(HttpSession session) {
		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		if (emisor == null) {
			throw new ServiceException("No se ha seleccionado un emisor");
		}
		return emisor;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "find" })
	public @ResponseBody Collection<Cliente> listaClientes(HttpSession session,
			@RequestParam String nombre) {
		Emisor emisor = getEmisor(session);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("razonSocial", "%" + nombre + "%");
		params.put("nombre", "%" + nombre + "%");
		params.put("apellidoPaterno", "%" + nombre + "%");
		params.put("apellidoMaterno", "%" + nombre + "%");
		params.put("emisor", emisor);

		Collection<Cliente> clientes = clienteService.find(params);

		return clientes;
	}
}

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.productos.model.Producto;
import com.entich.ezfact.productos.service.ProductoService;

@Controller
@RequestMapping("app/productos")
public class ProductoController extends AbstractController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@RequestMapping(method= RequestMethod.GET, value = "new")
	public String nuevoProducto() {
		LOGGER.debug("Generando la pantalla de alta de productos.");
		return "productos/nuevo";
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/")
	public @ResponseBody Producto crearProducto(@RequestBody Producto producto, HttpSession session) {
		LOGGER.debug("Guardando datos del producto.");
		
		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		producto.setEmisor(emisor);
		
		if (producto.getId() == null) {
			productoService.create(producto);
		} else {
			productoService.update(producto);
		}
		
		return producto;
	}

	@RequestMapping(method= RequestMethod.GET, value = "/")
	public @ResponseBody Collection<Producto> getProductos(HttpSession session) {
		LOGGER.debug("Generando la pantalla de alta de productos.");
		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		return productoService.getAll(emisor);
	}
	
	@RequestMapping(method= RequestMethod.GET, value = "list")
	public String getProductos() {
		LOGGER.debug("Mostrando la pantalla de la lista de productos.");
		return "productos/lista";
	}
	
	@RequestMapping(method= RequestMethod.GET, value = "{producto}")
	public String  getProducto(@PathVariable Long producto, Model modelo) {
		LOGGER.debug("Generando pantalla para la edicion de productos.");
		addToModel(modelo, "producto", productoService.read(producto));
		return "productos/editar";
	}
	
	@RequestMapping(method= RequestMethod.GET, value = "find")
	public @ResponseBody Collection<Producto>  findProductos(HttpSession session, 
			@RequestParam(required = false, value = "c") String codigo, 
			@RequestParam(required = false, value = "n") String nombre) {
		LOGGER.debug("Buscando productos por codigo o nombre del producto.");
		Emisor emisor = (Emisor) session.getAttribute("emisorSession");
		
		return productoService.find(emisor, codigo, nombre);
	}
	
	/*
	 * @RequestMapping(method = { RequestMethod.GET }, value = { "find" })
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
	 */
}

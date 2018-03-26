package com.entich.ezfact.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entich.commons.direcciones.model.Colonia;
import com.entich.commons.direcciones.model.Estado;
import com.entich.commons.direcciones.model.Municipio;
import com.entich.commons.direcciones.model.Pais;
import com.entich.commons.direcciones.service.ColoniaService;
import com.entich.commons.direcciones.service.EstadoService;
import com.entich.commons.direcciones.service.MunicipioService;
import com.entich.commons.direcciones.service.PaisService;

/**
 * Controller para el módulo de direcciones.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
@Controller
@RequestMapping("app/direcciones")
public class DireccionController extends AbstractController {

	private static final Logger LOG = LoggerFactory
			.getLogger(DireccionController.class);

	@Autowired
	private PaisService paisService;

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private MunicipioService municipioService;

	@Autowired
	private ColoniaService coloniaService;

	@RequestMapping(method = RequestMethod.GET, value = "paises")
	public @ResponseBody
	Collection<Pais> searchPaises() {
		LOG.debug("Obteniendo todos los paises");
		return paisService.findAll();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "paises/{pais}/estados")
	public @ResponseBody
	Collection<Estado> searchEstados(@PathVariable Long pais) {
		LOG.debug("Obteniendo los estados del país con el id: " + pais);
		Map map = new HashMap();
		map.put("pais.id", pais);
		return estadoService.search(map);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "estados/{estado}/municipios")
	public @ResponseBody
	Collection<Municipio> searchMunicipios(@PathVariable Long estado) {
		LOG.debug("Obteniendo los municipios del estado con el id: "
				+ estado);
		Map map = new HashMap();
		map.put("estado.id", estado);
		return municipioService.search(map);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "municipios/{municipio}/colonias")
	public @ResponseBody
	Collection<Colonia> searchColonias(@PathVariable Long municipio, @RequestParam String nombre) {
		LOG.debug("Obteniendo las colonias del municipio con el id: "
				+ municipio);
		Map map = new HashMap();
		map.put("municipio.id", municipio);
		map.put("nombre", nombre);
		return coloniaService.search(map);
	}
}

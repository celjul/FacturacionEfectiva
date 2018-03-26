package com.entich.ezfact.web.controller;

import javax.servlet.http.HttpSession;

import com.entich.ezfact.emisores.service.EmisorService;
import com.entich.ezfact.web.dto.FoliosEmisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.folios.model.ContadorFoliosEmisor;
import com.entich.ezfact.folios.model.PaqueteUsuario;
import com.entich.ezfact.folios.service.PaqueteUsuarioService;
import com.entich.ezfact.usuarios.model.Usuario;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("app/folios")
public class FolioController extends AbstractController {

	@Autowired
	private PaqueteUsuarioService paqueteUsuarioService;

	@Autowired
	private EmisorService emisorService;

	@RequestMapping(method = { RequestMethod.GET }, value = { "/" })
	public @ResponseBody ContadorFoliosEmisor validarDisponibles(
			HttpSession session) {
		ContadorFoliosEmisor contador = null;

		if (isWebmaster(session)) {
			contador = getFoliosWebmaster();
		} else {
			Emisor emisor = (Emisor) session.getAttribute("emisorSession");
			Usuario usuario = (Usuario) session.getAttribute("user");
			if(emisor != null && usuario != null) {
				contador = paqueteUsuarioService.getContadorFolios(emisor);
			}
		}

		return contador;
	}

	private ContadorFoliosEmisor getFoliosWebmaster() {
		ContadorFoliosEmisor contador;
		contador = new ContadorFoliosEmisor();
		contador.setDisponibles(1000);
		contador.setUtilizados(0);
		return contador;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/verificacion" })
	public @ResponseBody ContadorFoliosEmisor verificarFolios(
			HttpSession session) {
		ContadorFoliosEmisor contador = null;
		if (isWebmaster(session)) {
			contador = getFoliosWebmaster();
		} else {
			/*Emisor emisor = (Emisor) session.getAttribute("emisorSession");

			contador = paqueteEmisorService.getValidacionFolios(emisor);*/
			Emisor emisor = (Emisor) session.getAttribute("emisorSession");
			Usuario usuario = (Usuario) session.getAttribute("user");
			if(emisor != null && usuario != null) {
				contador = paqueteUsuarioService.getContadorFolios(emisor);
			}
		}

		return contador;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/paquete" })
	public @ResponseBody PaqueteUsuario getPaqueteUsuario(HttpSession session) {
		Usuario user = (Usuario) session.getAttribute("user");
		PaqueteUsuario paqueteUsuario = null;
		if (isWebmaster(session)) {

		} else {
			paqueteUsuario = paqueteUsuarioService.read(user);
		}

		return paqueteUsuario;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/emisores" })
	public String listaEmisores(HttpSession session, Model model) {

		return "paquetes/folios";
	}

	@RequestMapping(method = { RequestMethod.POST }, value = { "/actualizar" })
	public @ResponseBody Collection<Emisor> actualizar(@RequestBody Collection<FoliosEmisor> foliosEmisores, HttpSession session, Model model) {

		Collection<Emisor> emisores = new ArrayList<>();

		for(FoliosEmisor foliosEmisor : foliosEmisores) {
			Emisor emisor = emisorService.read(foliosEmisor.getId());
			emisor.setFacturas(foliosEmisor.getFacturas());
			emisorService.update(emisor);
			emisores.add(emisor);
		}
		return emisores;
	}
}

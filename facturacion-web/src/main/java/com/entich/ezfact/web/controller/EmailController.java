package com.entich.ezfact.web.controller;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.service.EmisorService;
import com.entich.ezfact.folios.model.ContadorFoliosEmisor;
import com.entich.ezfact.folios.model.PaqueteUsuario;
import com.entich.ezfact.folios.service.PaqueteUsuarioService;
import com.entich.ezfact.usuarios.model.Usuario;
import com.entich.ezfact.web.dto.FoliosEmisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("app/emails")
public class EmailController extends AbstractController {

	@Autowired
	private PaqueteUsuarioService paqueteUsuarioService;

	@Autowired
	private EmisorService emisorService;



	@RequestMapping(method = { RequestMethod.GET }, value = { "/emisores" })
	public String listaEmisores(HttpSession session, Model model) {

		return "emisor/emails";
	}


	/*@RequestMapping(method = { RequestMethod.GET }, value = { "/paquete" })
	public @ResponseBody PaqueteUsuario getPaqueteUsuario(HttpSession session) {
		Usuario user = (Usuario) session.getAttribute("user");
		PaqueteUsuario paqueteUsuario = null;
		if (isWebmaster(session)) {

		} else {
			paqueteUsuario = paqueteUsuarioService.read(user);
		}

		return paqueteUsuario;
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
	}*/
}

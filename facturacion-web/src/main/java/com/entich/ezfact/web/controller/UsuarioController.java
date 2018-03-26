package com.entich.ezfact.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.usuarios.factory.UsuarioFactory;
import com.entich.ezfact.usuarios.model.Usuario;
import com.entich.ezfact.usuarios.service.UsuarioService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("app/usuarios")
public class UsuarioController extends AbstractController {
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(method= RequestMethod.GET, value = "new")
	public String newUser() {
		return "usuario/registro_usuario";
	}

	@RequestMapping(method= RequestMethod.GET, value = "profile")
	public String perfilUsuario() {
		return "usuario/perfil";
	}
	
	@RequestMapping(method= RequestMethod.POST, value = "/")
	public @ResponseBody Usuario createUser(@RequestBody Usuario usuario) {
		LOGGER.debug("Recupere al usuario: " + (usuario == null ? "[null]" :
			usuario.getNombre() + " " + usuario.getApellidoPaterno()));
		usuarioService.create(usuario);
		return usuario;
	}

	@RequestMapping(method= RequestMethod.GET, value = "list")
	public String getViewLista() {
		return "usuario/lista";
	}
	
	@RequestMapping(method= RequestMethod.GET, value = "/")
	public @ResponseBody Collection<Usuario> getUsers(HttpSession session) {
		LOGGER.debug("Recuperando la lista de usuarios");
		
		return usuarioService.findAll((Emisor)session.getAttribute("emisorSession"));
	}

	@RequestMapping(method= RequestMethod.GET, value = "{usuario}")
	public @ResponseBody Usuario getUser(@PathVariable Long usuario) {
		LOGGER.debug("Recuperando la informacion del usuario");
		
		return usuarioService.read(usuario);
	}

	@RequestMapping(method= RequestMethod.PATCH, value = "/{id}/status")
	public @ResponseBody Usuario updateStatus(@PathVariable Long id) {
		LOGGER.debug("Actualizando el estatus del usuario");
		Usuario usuario = UsuarioFactory.newInstance(id);
		usuarioService.changeStatus(usuario);
		
		return usuario;
	}

	@RequestMapping(method= RequestMethod.PUT, value = "/{id}")
	public @ResponseBody Usuario updateUser(@PathVariable Long id,
			@RequestBody Usuario usuario) {
		LOGGER.debug("Actualizando el perfil de usuario");
		usuarioService.update(usuario);
		
		return usuario;
	}
}

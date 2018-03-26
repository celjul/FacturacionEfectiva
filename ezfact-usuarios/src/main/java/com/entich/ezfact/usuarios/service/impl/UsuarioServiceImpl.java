package com.entich.ezfact.usuarios.service.impl;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.commons.exceptions.service.ValidationException;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.usuarios.dao.UsuarioDao;
import com.entich.ezfact.usuarios.model.Usuario;
import com.entich.ezfact.usuarios.service.UsuarioService;
import com.entich.utils.PasswordUtils;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private static final Logger LOG = 
			LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Override
	public void create(Usuario usuario) {
		try {
			Assert.notNull(usuario, "El usuario no puede ser nulo.");			
			usuario.setActivo(true);
			
			validar(usuario);
			usuario.setPassword(cifrarPassword(usuario.getPassword()));
			usuarioDao.create(usuario);
		} catch (IllegalArgumentException ex) {
			LOG.error(ex.getMessage());
			throw new ServiceException(ex.getMessage());
		}
	}

	private void validar(Usuario usuario) {
		Set<ConstraintViolation<Usuario>> errors = validator.validate(usuario);
		if (CollectionUtils.isNotEmpty(errors)) {
			String message = "El usuario presenta errores de validacion.";
			LOG.error(message + " " + errors);
			throw new ValidationException(message, errors);
		}
	}

	@Override
	public void update(Usuario usuario) {
		Usuario usuarioDB = usuarioDao.read(usuario.getId());

		usuarioDB.setNombre(usuario.getNombre());
		usuarioDB.setApellidoPaterno(usuario.getApellidoPaterno());
		usuarioDB.setApellidoMaterno(usuario.getApellidoMaterno());

		if (StringUtils.isNotBlank(usuario.getLogin())) {
			String passwordNuevo = cifrarPassword(usuario.getPassword());
			
			validarPasswordAntiguo(cifrarPassword(usuario.getLogin()), 
					usuarioDB.getPassword());

			usuarioDB.setPassword(passwordNuevo);
		}
		
		validar(usuarioDB);
		usuarioDao.update(usuarioDB);
	}

	private void validarPasswordAntiguo(String passwordNuevo, String password) {
		if (!passwordNuevo.equals(password)) {
			throw new ServiceException("El password anterior es incorrecto.");
		}
	}

	@Override
	public void changeStatus(Usuario usuario) {
		LOG.debug("Actualizando el estatus del usuario.");
		try {
			usuario = usuarioDao.read(usuario.getId());
			
			usuario.setActivo((usuario.getActivo() != null) ? !usuario.getActivo() : true);
			validar(usuario);
			usuarioDao.update(usuario);
		} catch(IllegalArgumentException ex) {
			String message = "El usuario no puede ser nulo.";
			LOG.error(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public Usuario validate(String login, String password) {
		LOG.debug("Iniciando el proceso de validacion de usuarios.");
		try {
			Assert.notNull(login, "El nombre de usuario no puede ser nulo.");
			Assert.notNull(password, "El password no puede ser nulo.");
			
			return usuarioDao.validate(login, cifrarPassword(password));
		} catch (IllegalArgumentException ex) {
			String message = "Error en los valores de los parametros de validacion de usuario.";
			LOG.error(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public Collection<Usuario> findAll() {
		LOG.debug("Recuperando la lista de usuarios de la aplicación.");

		return usuarioDao.findAll();
	}
	
	private String cifrarPassword(String password) {
		return PasswordUtils.md5Digest(password);
	}

	@Override
	public Usuario getByUsername(String username) {
		LOG.debug("Iniciando el proceso de validacion de usuarios.");
		try {
			Assert.notNull(username, "El nombre de usuario no puede ser nulo.");
			
			return usuarioDao.validate(username);
		} catch (IllegalArgumentException ex) {
			String message = "Error en los valores de los parametros de validacion de usuario.";
			LOG.error(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public Usuario read(Long id) {
		LOG.debug("Recuperando la información del usuario.");

		try {
			Assert.notNull(id, "El id de usuario no puede ser nulo.");
			
			return usuarioDao.read(id);
		} catch (IllegalArgumentException ex) {
			String message = "Error en los valores de los parametros de validacion de usuario.";
			LOG.error(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public Collection<Usuario> findAll(Emisor emisor) {
		return usuarioDao.findAll(emisor);
	}
	
	@Override
	public void asignarEmisor(Usuario usuario) {
		Usuario usuarioDB = usuarioDao.read(usuario.getId());

		usuarioDB.setEmisores(usuario.getEmisores());

		validar(usuarioDB);
		usuarioDao.update(usuarioDB);
	}
}

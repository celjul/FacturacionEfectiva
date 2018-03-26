package com.entich.ezfact.web.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.entich.commons.exceptions.ApplicationException;
import com.entich.commons.exceptions.service.ValidationException;
import com.entich.ezfact.usuarios.factory.RolFactory;
import com.entich.ezfact.usuarios.model.Rol;
import com.entich.ezfact.usuarios.model.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
	
	protected static final String ERROR_PAGE = "error/exception";
	
	@Autowired
	protected ObjectMapper mapper;
	
	protected void addToModel(Model model, String name, Object value) {
		try {
			model.addAttribute(name, mapper.writeValueAsString(value));
		} catch (JsonProcessingException e) {
			LOGGER.warn("Error al convertir el objeto a formato JSON.");
		}
		
	}
	
	protected boolean isWebmaster(HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("user");
		
		Rol webmaster = RolFactory.newInstance(1L);
		
		return usuario != null ? usuario.getRoles().contains(webmaster):false;
	}
	/*
	public ModelAndView handleAllExceptions(Exception ex) {
		return new JsonError(ex).asModelAndView();
	}
	*/
}

package com.entich.ezfact.folios.service;

import java.util.Collection;
import java.util.Map;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.folios.model.ContadorFoliosEmisor;
import com.entich.ezfact.folios.model.PaqueteUsuario;
import com.entich.ezfact.folios.model.Paquete;
import com.entich.ezfact.usuarios.model.Usuario;

public interface PaqueteUsuarioService {
	
	void create(PaqueteUsuario paqueteusuario);
	
	void update(PaqueteUsuario paqueteusuario);
	
	Collection<PaqueteUsuario> getAll();

	PaqueteUsuario read(Long id);
	
	//PaqueteUsuario read(Paquete paquete);
	
	PaqueteUsuario read(Usuario usuario);
	
	@SuppressWarnings("rawtypes")
	Collection<PaqueteUsuario> search(Map map);

	ContadorFoliosEmisor getContadorFolios(Emisor emisor);

	/*String validarDisponibilidadTimbres(Emisor emisor);*/
}

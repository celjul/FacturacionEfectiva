package com.entich.ezfact.folios.dao;

import com.entich.commons.dao.GenericDao;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.folios.model.Paquete;
import com.entich.ezfact.folios.model.PaqueteEmisor;
import com.entich.ezfact.folios.model.PaqueteUsuario;
import com.entich.ezfact.usuarios.model.Usuario;

public interface PaqueteUsuarioDao extends GenericDao<PaqueteUsuario, Long> {

	PaqueteUsuario read(Paquete paquete);
	
	PaqueteUsuario read(Usuario usuario);

	PaqueteUsuario getPaqueteActivo(Usuario usuario);

	Integer getFoliosUtilizados(PaqueteUsuario paquete, Emisor... emisor);
}

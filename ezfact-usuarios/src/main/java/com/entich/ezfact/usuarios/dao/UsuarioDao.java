package com.entich.ezfact.usuarios.dao;

import java.text.ParseException;
import java.util.Collection;

import com.entich.commons.dao.GenericDao;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.usuarios.model.Usuario;

/**
 * 
 * @author Luis Angel Cardenas
 *
 */
public interface UsuarioDao extends GenericDao<Usuario, Long> {
	/**
	 * Valida que el login y el password proporcionados coincidan con algun 
	 * usuario del reposiorio de datos. Si existe una combinacion de passwor y 
	 * login en el repositorio devuelve una instancia con la informacion de 
	 * este usuario. En caso de no encontrar algun usuario 
	 * con esta informacion, devuelve un <code>null</code>
	 * 
	 * @param login Login del usuario que intenta validarse.
	 * @param password Password del usuario que intenta validarse.
	 * @return Usuario propietario de la combinacion de login y password
	 */
	Usuario validate(String login, String password);

	Usuario validate(String username);

	Collection<Usuario> findAll(Emisor emisor);
}

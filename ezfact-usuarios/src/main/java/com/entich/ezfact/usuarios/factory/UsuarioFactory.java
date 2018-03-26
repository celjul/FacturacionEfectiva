package com.entich.ezfact.usuarios.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.ApplicationException;
import com.entich.commons.exceptions.factory.FactoryException;
import com.entich.ezfact.usuarios.model.Usuario;

/**
 * Factoria de instancias de tipo Usuario 
 * @author Luis Angel Cardenas
 * @version 1.0.0
 * @see Usuario
 */
public class UsuarioFactory {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UsuarioFactory.class);

	/**
	 * Devuelve una instancia vacia. Una instancia vacia significa que ninguno
	 * de sus atributos ha sido inicializado.
	 * 
	 * @return Instancia vacia.
	 */
	public static Usuario newInstance() {
		return new Usuario();
	}

	/**
	 * Devuelve una instancia con el id especificado. Los atributos de esta
	 * instancia no ha sido inicializados, solo el id tiene valor.
	 * 
	 * @param id
	 *            Identificador que se signa a la instancia.
	 * @return Instancia con el Id especificado.
	 */
	public static Usuario newInstance(long id) {
		Usuario usuario = newInstance();
		usuario.setId(id);

		return usuario;
	}

	/**
	 * Devuelve una instancia con el id especificado. Los atributos de esta
	 * instancia no ha sido inicializados, solo el id tiene valor.
	 * 
	 * <p>
	 * Si el valor de la cadena es nulo o no se puede convertir en un Long
	 * valido se lanza una excepcion del tipo <code>ApplicationException</code>.
	 * </p>
	 * 
	 * @param id
	 *            Cadena que se intentara asignar como id de la instancia.
	 * @return Instancia con el Id especificado.
	 * @throws ApplicationException
	 */
	public static Usuario newInstance(String id) {
		try {
			Assert.notNull(id);
			return newInstance(Long.parseLong(id));
		} catch (NumberFormatException ex) {
			String message = String.format(
					"No es posible crear la instancia con id de valor [%s]",
					id);
			LOGGER.warn(message, ex);
			throw new FactoryException(message, ex);
		} catch (IllegalArgumentException ex) {
			String message = "La cadena con el Id de la instancia no puede ser nula";
			throw new FactoryException(message, ex);
		}
	}
}

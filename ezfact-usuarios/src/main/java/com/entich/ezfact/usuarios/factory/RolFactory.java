package com.entich.ezfact.usuarios.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.ApplicationException;
import com.entich.commons.exceptions.factory.FactoryException;
import com.entich.ezfact.usuarios.model.Rol;

/**
 * Factoria de instancias de tipo Puesto.
 * 
 * @author Luis Angel Cardenas
 * @version 1.0.0
 * @see Rol
 *
 */
public class RolFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(RolFactory.class);
	
	/**
	 * Devuelve una instancia vacia. Una instancia vacia significa que ninguno
	 * de sus atributos ha sido inicializado.
	 * 
	 * @return Instancia vacia.
	 */
	public static Rol newInstance() {
		return new Rol();
	}
	
	/**
	 * Devuelve una instancia con el id especificado. Los atributos de esta
	 * instancia no ha sido inicializados, solo el id tiene valor.
	 * 
	 * @param id
	 *            Identificador que se signa a la instancia.
	 * @return Instancia con el Id especificado.
	 */
	public static Rol newInstance(long id) {
		Rol puesto = newInstance();
		puesto.setId(id);
		
		return puesto;
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
	public static Rol newInstance(String id) {
		try {
			Assert.notNull(id, "La cadena no puede ser nula");
			
			return newInstance(Long.parseLong(id));
		} catch (NumberFormatException ex) {
			String message = String.format(
					"No es posible crear la instancia con id de valor [%s]",
					id);
			LOGGER.warn(message, ex);
			throw new FactoryException(message, ex);
		} catch (IllegalArgumentException ex) {
			String message = "El parametro del Id no puede ser nulo.";
			LOGGER.warn(message, ex);
			throw new FactoryException(message, ex);
		} 
	}
}

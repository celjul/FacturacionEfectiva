package com.entich.commons.dao;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <p>
 * Intefaz que define el comportamiento genérico para un objeto de acceso a
 * datos.
 * </p>
 * <p>
 * Las operaciones que se deben implementar son:
 * <ul>
 * <li>Create</li>
 * <li>Read</li>
 * <li>Update</li>
 * <li>Delete</li>
 * <li>FindAll</li>
 * <li>Search</li>
 * </ul>
 * </p>
 * 
 * @author Angel Cárdenas
 * 
 * @param <T>
 *            Tipo de entidad que se manipula en el objeto que implementa la
 *            interfaz.
 * @param <PK>
 *            Objeto que funciona como clave primaria de los objetos del modelo
 *            de datos.
 */
public interface GenericDao<T, PK> {

	/**
	 * Persiste la instancia en el repositorio de datos de la aplicacion. La
	 * instancia que se intenta persistir no puede ser nula, en caso de serlo se
	 * debe lanzar una excepcion.
	 * 
	 * @param obj
	 *            Objeto que será persistido en el repositorio de datos.
	 */
	void create(T obj);

	/**
	 * Recupera una instancia persistente del repositorio de datos, la búsqueda
	 * se hace a traves de la llave primaria especificada. La llave de búsqueda
	 * no puede ser nula, en caso contrario de debe lanzar una excepcion.
	 * 
	 * <p>
	 * Por default, si el objeto no es hallado en el repositorio, se debe
	 * devolver un valor <code>null</code>
	 * </p>
	 * 
	 * @param id
	 *            Lave, clave primaria del objeto que se intenta recuperar del
	 *            repositario de datos.
	 * @return Objeto del repositorio que al que pertenece el Id enviado como
	 *         parametro.
	 */
	T read(PK id);

	/**
	 * Actualiza la informacion en el repositorio de la instancia que coincida
	 * con el identificador. Si no existe una instancia con el identificador en
	 * el reositorio se lanza una excepcion.
	 * 
	 * @param obj
	 *            Objeto que sera actualizado en el repositorio de datos.
	 */
	void update(T obj);

	/**
	 * Elimina la instancia persistente del repositorio de datos.
	 * 
	 * @param obj
	 *            Instancia que se desea eliminar del repositorio.
	 */
	void delete(T obj);

	/**
	 * Devuelve todas las instancias del tipo <code>T</code> que existen en el
	 * repositorio de datos.
	 * 
	 * @return Coleccion de objetos peristentes contenidos en el repositorio de
	 *         datos.
	 */
	Collection<T> findAll();

	/**
	 * M�todo que devuelve las instancias del tipo <code>T</code> que existen en
	 * el repositorio de datos, que cumplan con los parámetros especificados en
	 * la lista del tipo <code>Set</code> de datos.
	 * 
	 * @param set
	 *            colección de parámetros de búsqueda sin elementos repetidos
	 * @return Collección de objetos persistentes que cumplan con los parámetros
	 *         especificados.
	 */
	Collection<T> search(Set<Entry<String, Object>> set);
}
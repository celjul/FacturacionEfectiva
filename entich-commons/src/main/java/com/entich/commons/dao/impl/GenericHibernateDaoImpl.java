package com.entich.commons.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entich.commons.dao.GenericDao;
import com.entich.commons.exceptions.dao.DataAccessException;

/**
 * Implementación genérica que usa Hibernate para la implementación de las
 * operaciones básicas de un objeto de acceso a datos.
 * 
 * @author Luis Ángel Cárdenas
 * 
 * @param <T>
 *            Tipo de objeto del modelo que administra la clase.
 * @param <PK>
 *            Tipo de dato del objeto que es empleado como identificador de la
 *            clase de modelo.
 * 
 * @see GenericDao
 */
@Repository
public abstract class GenericHibernateDaoImpl<T, PK extends Serializable>
		implements GenericDao<T, PK> {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(GenericHibernateDaoImpl.class);

	/**
	 * Proporciona la conexión a la base de datos.
	 */
	@Autowired
	protected SessionFactory sessionFactory;

	/**
	 * Tipo de instancia que se administra en el objeto que hereda la
	 * funcionalidad.
	 */
	private Class<T> type = null;

	@Transactional
	@Override
	public void create(T obj) {
		try {
			LOGGER.debug("Iniciando la creación de un objeto");
			this.sessionFactory.getCurrentSession().save(obj);
			this.sessionFactory.getCurrentSession().flush();
			LOGGER.debug("El objeto se creo correctamente.");
		} catch (HibernateException ex) {
			LOGGER.error("Error en la creación del objeto.", ex);
			throw new DataAccessException("Error al insertar el objeto en la base de datos.", ex);
		} catch (ConstraintViolationException ex) {
			LOGGER.error("Error en la creación del objeto", ex);
			throw new DataAccessException("Se esta violando una restricción "
					+ "en la base de datos.", ex);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Error en la creación del objeto", ex);
			throw new DataAccessException(
					"Existen argumentos no válidos al intentar guardar el objeto.",
					ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public T read(PK id) {
		try {
			LOGGER.debug("Iniciando la recuperación de una instancia persistente.");
			return (T) this.sessionFactory.getCurrentSession().get(getType(),
					id);
		} catch (IllegalArgumentException ex) {
			String message = "Error al recuperar el objeto desde la base de datos.";
			LOGGER.error(message, ex);
			throw new DataAccessException(message, ex);
		}
	}

	@Override
	@Transactional
	public void update(T obj) {
		try {
			LOGGER.debug("Iniciando la actualizacion de información de una "
					+ "instancia persistente.");
			this.sessionFactory.getCurrentSession().update(obj);
			this.sessionFactory.getCurrentSession().flush();
		} catch (HibernateOptimisticLockingFailureException ex) {
			LOGGER.error("Error en la actualización del objeto", ex);
			throw new DataAccessException("No se actualizó ningún objeto", ex);
		} catch (HibernateException ex) {
			LOGGER.error("Error en la actualización del objeto", ex);
			throw new DataAccessException("Error de acceso a datos", ex);
		} catch (ConstraintViolationException ex) {
			LOGGER.error("Error en la actualización del objeto", ex);
			throw new DataAccessException("Se esta violando una restricción "
					+ "en la base de datos.", ex);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Error en la actualización del objeto", ex);
			throw new DataAccessException(
					"Existen argumentos no válidos al intentar guardar el objeto.",
					ex);
		}
	}

	@Override
	@Transactional
	public void delete(T obj) {
		try {
			LOGGER.debug("Iniciando la eliminación de una instancia persistente.");
			this.sessionFactory.getCurrentSession().delete(obj);
			this.sessionFactory.getCurrentSession().flush();
		} catch (HibernateOptimisticLockingFailureException ex) {
			LOGGER.error("Error en la eliminación del objeto", ex);
			throw new DataAccessException("No se eliminó ningún objeto", ex);
		} catch (HibernateException ex) {
			LOGGER.error("Error en la eliminacion del objeto", ex);
			throw new DataAccessException("Error de acceso a datos", ex);
		} catch (ConstraintViolationException ex) {
			LOGGER.error("Error en la eliminacion del objeto", ex);
			throw new DataAccessException("Se esta violando una restricción "
					+ "en la base de datos.", ex);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Error en la eliminacion del objeto", ex);
			throw new DataAccessException(
					"Existen argumentos no válidos al intentar guardar el objeto.",
					ex);
		}
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> findAll() {
		try {
			LOGGER.debug("Iniciando la recuperación de las instancias persistentes.");

			Query query = sessionFactory.getCurrentSession().createQuery(
					String.format("from %s obj", getType().getSimpleName()));
			return query.list();
		} catch (HibernateException ex) {
			String mensaje = String.format("Error al recuperar los "
					+ "objetos de tipo {%s} en la base de datos", getType()
					.getSimpleName());
			LOGGER.error(mensaje, ex);
			throw new DataAccessException(mensaje, ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<T> search(Set<Entry<String, Object>> set) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(getType());
			for (Map.Entry<String, Object> entry : set) {
				if (entry.getValue() instanceof String) {
					criteria.add(Restrictions.like((String) entry.getKey(),
							"%" + entry.getValue() + "%").ignoreCase());
				} else {
					criteria.add(Restrictions.eq((String) entry.getKey(),
							entry.getValue()));
				}
			}
			return criteria.list();
		} catch (HibernateException ex) {
			String message = String.format(
					"Error al recuperar los objetos del tipo {%s} desde la base "
							+ "de datos.", getType().getSimpleName());
			LOGGER.error(message, ex);
			throw new DataAccessException(message, ex);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Class<T> getType() {
		if (type == null) {
			Class clazz = getClass();

			while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
				clazz = clazz.getSuperclass();
			}

			type = (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass())
					.getActualTypeArguments()[0];
		}

		return type;
	}
}

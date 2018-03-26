package com.entich.ezfact.usuarios.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.exolab.castor.types.DateTime;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.exceptions.dao.DataAccessException;
import com.entich.ezfact.emisores.dao.EmisorDao;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.usuarios.service.UsuarioService;
import com.entich.ezfact.usuarios.dao.UsuarioDao;
import com.entich.ezfact.usuarios.model.Rol;
import com.entich.ezfact.usuarios.model.Usuario;
import com.entich.ezfact.facturacion.dao.ComprobanteDao;
import com.entich.ezfact.facturacion.model.Comprobante;
import com.entich.ezfact.facturacion.service.ComprobanteService;

/**
 * Clase con la responsabilidad unica de administrar las operaciones de acceso a
 * datos de las instancias del tipo Usuario
 * 
 * @author Luis Angel Cardenas
 *
 */
@Repository("usuarioDao")
public class UsuarioDaoImpl extends GenericHibernateDaoImpl<Usuario, Long>
		implements UsuarioDao {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UsuarioDaoImpl.class);

	@Autowired
	private EmisorDao emisorDao;

	@Autowired
	private ComprobanteService comprobanteService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Transactional
	@Override
	public void create(Usuario obj) {
		try {
			LOGGER.debug("Iniciando la creación de un objeto");

			Set<Rol> roles = obj.getRoles();
			obj.setRoles(new HashSet<Rol>());

			LOGGER.debug(roles.toString());
			for (Rol rol : roles) {
				obj.getRoles().add(
						(Rol) this.sessionFactory.getCurrentSession().get(
								Rol.class, rol.getId()));
			}

			this.sessionFactory.getCurrentSession().save(obj);
			this.sessionFactory.getCurrentSession().flush();
			LOGGER.debug("El objeto se creo correctamente.");
		} catch (HibernateException ex) {
			LOGGER.error("Error en la creación del objeto.", ex);
			throw new DataAccessException(
					"Error al insertar el objeto en la base de datos.", ex);
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

	@Transactional(readOnly = true)
	@Override
	public Usuario validate(String login, String password) {
		LOGGER.debug("Iniciando el proceso de validacion del usuario en la "
				+ "base de datos.");
		try {
			return (Usuario) this.sessionFactory
					.getCurrentSession()
					.createQuery(
							String.format(
									"from %s obj where obj.password like "
											+ ":password and obj.login like :login",
									getType().getSimpleName()))
					.setParameter("password", password)
					.setParameter("login", login).uniqueResult();
		} catch (HibernateException ex) {
			String message = "Error al intentar validar al usuario en la "
					+ "base de datos.";
			LOGGER.error(message, ex);
			throw new DataAccessException(message, ex);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Usuario validate(String username) {
		LOGGER.debug("Iniciando el proceso de validacion del usuario en la "
				+ "base de datos.");
		try {
			Usuario usuario = (Usuario) this.sessionFactory
					.getCurrentSession()
					.createQuery(
							String.format(
									"from %s obj where obj.login like :login",
									getType().getSimpleName()))
					.setParameter("login", username).uniqueResult();

			if (usuario != null) {
				usuario.getEmisores().size();
				usuario.getRoles().size();
			}

			return usuario;
		} catch (HibernateException ex) {
			String message = "Error al intentar validar al usuario en la "
					+ "base de datos.";
			LOGGER.error(message, ex);
			throw new DataAccessException(message, ex);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Usuario> findAll() {
		Collection<Usuario> usuarios = super.findAll();
		for (Usuario usu : usuarios) {
			usu.getEmisores().size();
		}
		return usuarios;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly= true)
	@Override
	public Collection<Usuario> findAll(Emisor emisor) {
		try{
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
			criteria.createAlias("emisores", "e");
			criteria.add(Restrictions.eq("e.id", emisor.getId()));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Collection<Usuario> usuarios = criteria.list();
			for (Usuario usuario : usuarios) {
				usuario.getEmisores().size();
			}
			return usuarios;
		} catch (HibernateException ex) {
			String message = "Error al intentar obtener una lista de usuarios "
					+ "base de datos.";
			LOGGER.error(message, ex);
			throw new DataAccessException(message, ex);
		}
		
	}
	
}
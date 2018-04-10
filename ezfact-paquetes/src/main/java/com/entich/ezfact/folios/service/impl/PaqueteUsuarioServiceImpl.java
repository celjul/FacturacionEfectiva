package com.entich.ezfact.folios.service.impl;

import java.util.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.service.EmisorService;
import com.entich.ezfact.folios.model.ContadorFoliosEmisor;
import com.entich.ezfact.usuarios.service.UsuarioService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.commons.exceptions.service.ValidationException;
import com.entich.ezfact.folios.dao.PaqueteUsuarioDao;
import com.entich.ezfact.folios.model.PaqueteUsuario;
import com.entich.ezfact.folios.service.PaqueteUsuarioService;
import com.entich.ezfact.usuarios.model.Usuario;

@Service
public class PaqueteUsuarioServiceImpl implements PaqueteUsuarioService{
	private static final Logger LOG = 
			LoggerFactory.getLogger(PaqueteUsuarioServiceImpl.class);

	static final int NUM_DIAS_ALERTA = 5;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private PaqueteUsuarioDao paqueteUsuarioDao;

	@Autowired
	private EmisorService emisorService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Transactional
	@Override
	public void create(PaqueteUsuario paqueteUsuario) {
		LOG.debug("Creando un paqueteUsuario.");
		try {
			Assert.notNull(paqueteUsuario, "El paqueteUsuario es nulo.");
			validar(paqueteUsuario);
			Assert.notNull(paqueteUsuario.getPaquete().getId(), "El id del paquete es nulo.");
			
			paqueteUsuarioDao.create(paqueteUsuario);
		} catch (IllegalArgumentException ex) {
			LOG.warn(ex.getMessage());
			throw new ServiceException(ex.getMessage());
		}
	}
	
	private void validar(PaqueteUsuario paqueteUsuario) {
		Set<ConstraintViolation<PaqueteUsuario>> violations = validator
				.validate(paqueteUsuario);
		
		if (CollectionUtils.isNotEmpty(violations)) {
			String message = "El paqueteUsuario tiene errores de validación.";
			LOG.warn(message + " " + violations);
			throw new ValidationException(message, violations);
		}
	}
	
	@Transactional
	@Override
	public void update(PaqueteUsuario paqueteUsuario) {
		LOG.debug("Actualizando un PaqueteUsuario.");
		try {
			Assert.notNull(paqueteUsuario, "El PaqueteUsuario es nulo.");
			Assert.notNull(paqueteUsuario.getId(), "El id del PaqueteUsuario es nulo.");
			validar(paqueteUsuario);
			Assert.notNull(paqueteUsuario.getPaquete().getId(), "El id del emisor es nulo.");
			
			paqueteUsuarioDao.update(paqueteUsuario);
		} catch (IllegalArgumentException ex) {
			LOG.warn(ex.getMessage());
			throw new ServiceException(ex.getMessage());
		}
	}
	
	@Override
	public Collection<PaqueteUsuario> getAll() {
		LOG.debug("Obteniendo los paqueteUsuario existentes.");
		Collection<PaqueteUsuario> paqueteUsuarios = null;
		paqueteUsuarios = paqueteUsuarioDao.findAll();
		if (paqueteUsuarios.isEmpty()) {
			LOG.info("No se encontraron certificados.");
		}
		return paqueteUsuarios;
	}

	@Override
	public PaqueteUsuario read(Long id) {
		LOG.debug("Obteniendo un paqueteUsuario por id.");
		try {
			Assert.notNull(id);
			return paqueteUsuarioDao.read(id);
		} catch (IllegalArgumentException ex) {
			String message = "El id es nulo.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}
	
//	@Override
//	public PaqueteUsuario read(Paquete paquete) {
//		LOG.debug("Obteniendo un certificado por id.");
//		try {
//			Assert.notNull(paquete);
//			return paqueteUsuarioDao.read(paquete);
//		} catch (IllegalArgumentException ex) {
//			String message = "El identificador es nulo.";
//			LOG.warn(message, ex);
//			throw new ServiceException(message, ex);
//		}
//	}
	
	@Override
	public PaqueteUsuario read(Usuario usuario) {
		LOG.debug("Obteniendo un paquteUsuario por usuario.");
		try {
			Assert.notNull(usuario);
			return paqueteUsuarioDao.read(usuario);
		} catch (IllegalArgumentException ex) {
			String message = "El identificador es nulo.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<PaqueteUsuario> search(Map map) {
		LOG.debug("Buscando PaqueteUsuario");
		Collection<PaqueteUsuario> paqueteUsuario = null;
		if (map != null && !map.isEmpty()) {
			paqueteUsuario = paqueteUsuarioDao.search(map.entrySet());
		} else {
			LOG.info("No se encontraron PaqueteUsuario.");
		}
		if (paqueteUsuario.isEmpty()) {
			LOG.info("No se encontraron PaqueteUsuario.");
		}
		return paqueteUsuario;
	}

	/*public ContadorFoliosEmisor getContadorFolios(PaqueteUsuario paqueteUsuario) {

	}*/

	/*@Override
	public String validarDisponibilidadTimbres(Emisor emisor) {

		String valido = "";

		if(emisor != null) {
			Collection<Usuario> usuarios = usuarioService.findAll(emisor);
			PaqueteUsuario paquete = null;
			Usuario usuario = null;

			for (Usuario usuarioTemp : usuarios) {
				paquete = paqueteUsuarioDao.getPaqueteActivo(usuarioTemp);
				usuario = usuarioTemp;
				if (paquete != null) {
					break;
				}
			}

			if (paquete != null && usuario != null) {

				Calendar hoy = GregorianCalendar.getInstance();

				Calendar fecAdquirido = Calendar.getInstance();
				fecAdquirido.setTime(paquete.getVencimiento());
				fecAdquirido.set(Calendar.HOUR_OF_DAY, 0);

				Calendar fecVencimiento = Calendar.getInstance();
				fecVencimiento.setTime(paquete.getVencimiento());
				fecVencimiento.set(Calendar.HOUR_OF_DAY, 24);

				if (hoy.getTime().after(fecAdquirido.getTime()) && hoy.getTime().before(fecVencimiento.getTime())) {
					if (emisorService.getFoliosUtilizados(paquete.getAdquirido(), paquete.getVencimiento(),
							usuario.getEmisores().toArray(new Emisor[usuario.getEmisores().size()])) <= paquete.getPaquete().getFacturas()) {
						if (emisor.getFacturas() == 0 ||
								emisorService.getFoliosUtilizados(paquete.getAdquirido(), paquete.getVencimiento(), emisor) <= emisor.getFacturas()) {
							valido = "";
						}

					} else {
						valido = "Se han agotado los folios de su paquete contratado, pongase en contacto con su administrador de folios";
					}
				} else {
					valido = "Su paquete ha expirado, adquiera uno nuevo para seguir facturando";
				}
			}
		}
		return valido;
	}*/

	@Override
	public ContadorFoliosEmisor getContadorFolios(Emisor emisor) {

		ContadorFoliosEmisor contador = new ContadorFoliosEmisor();

		if(emisor != null) {
			Collection<Usuario> usuarios = usuarioService.findAll(emisor);

			PaqueteUsuario paquete = null;
			Usuario usuario = null;

			for (Usuario usuarioTemp : usuarios) {
				paquete = paqueteUsuarioDao.getPaqueteActivo(usuarioTemp);
				usuario = usuarioTemp;
				if (paquete != null) {
					break;
				}
			}

			if (paquete != null && usuario != null) {

			/*		paqueteEmisorDao.getFoliosUtilizados(
							paquete, emisor));
			*/

				Calendar hoy = GregorianCalendar.getInstance();

				Calendar fecAdquirido = Calendar.getInstance();
				fecAdquirido.setTime(paquete.getAdquirido());
				fecAdquirido.set(Calendar.HOUR_OF_DAY, 0);

				Calendar fecVencimiento = Calendar.getInstance();
				fecVencimiento.setTime(paquete.getVencimiento());
				fecVencimiento.set(Calendar.HOUR_OF_DAY, 24);

				int disponibles = (emisor.getFacturas() != null && paquete.getPaquete().getFacturas() > emisor.getFacturas()) ?
						emisor.getFacturas() : paquete.getPaquete().getFacturas();
				contador.setDisponibles(disponibles);

				Emisor[] emisores = usuario.getEmisores().toArray(new Emisor[usuario.getEmisores().size()]);
				contador.setUtilizados(emisorService.getFoliosUtilizados(paquete.getAdquirido(), paquete.getVencimiento(), emisores));

				long dias = fecVencimiento.getTime().getTime()
						- hoy.getTime().getTime();
				dias /= ((long) 86400000);
				if (dias <= NUM_DIAS_ALERTA) {
					contador.setDiasRestantes((int) dias);
				}

				/*if (hoy.after(fecVencimiento.getTime())) {
					LOG.debug("El paquete ha vencido.");
				} else {
					dias /= ((long) 86400000);
					if (dias <= NUM_DIAS_ALERTA) {
						contador.setDiasRestantes((int) dias);
					}
				}*/

				if (hoy.getTime().after(fecAdquirido.getTime()) && hoy.getTime().before(fecVencimiento.getTime())) {
					if (emisorService.getFoliosUtilizados(paquete.getAdquirido(), paquete.getVencimiento(),
							usuario.getEmisores().toArray(new Emisor[usuario.getEmisores().size()])) < paquete.getPaquete().getFacturas()) {
						if (emisor.getFacturas() == null ||
								contador.getUtilizados() < emisor.getFacturas()) {
							contador.setMensaje("");
						} else {
							contador.setMensaje("Su emisor ha alcanzado el límite máximo de folios designados, pongase encontácto con su administrador de emisores.");
						}
					} else {
						contador.setMensaje("Se han agotado los folios de su paquete contratado, pongase en contacto con su administrador de folios. Atención a clientes al (55) 12041299.");
					}
				} else {
					contador.setMensaje("Su paquete ha expirado, adquiera uno nuevo para seguir facturando. Atención a clientes al (55) 12041299.");
				}





			} else {
				contador.setDisponibles(0);
				contador.setDiasRestantes(0);
				contador.setUtilizados(0);
				contador.setMensaje("No tiene folios para poder generar su CDFI, por favor comuniquese con el proveedor.");
			}
		}
		return contador;
	}

	/*@Override
	public ContadorFoliosEmisor getValidacionFolios(Emisor emisor) {
		PaqueteEmisor paquete = paqueteEmisorDao.getPaqueteActivo(emisor);

		if (paquete != null) {
			ContadorFoliosEmisor contador = new ContadorFoliosEmisor();
			contador.setDisponibles(emisor.getFacturas());

			Emisor[] emisores = paqueteEmisorDao.getEmisores(paquete).toArray(
					new Emisor[0]);

			contador.setUtilizados(paqueteEmisorDao.getFoliosUtilizados(
					paquete.getPaquete(), emisores));

			return contador;
		}

		return null;
	}*/

}

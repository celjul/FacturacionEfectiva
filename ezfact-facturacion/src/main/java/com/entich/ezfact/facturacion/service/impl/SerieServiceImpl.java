package com.entich.ezfact.facturacion.service.impl;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.commons.exceptions.service.ValidationException;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.dao.SerieDao;
import com.entich.ezfact.facturacion.model.Serie;
import com.entich.ezfact.facturacion.service.SerieService;
import org.apache.commons.collections.CollectionUtils;
import org.castor.core.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Pegasus on 17/05/2016.
 */
@Service
public class SerieServiceImpl implements SerieService {

    private static final Logger LOG = LoggerFactory.getLogger(SerieServiceImpl.class);

    @Autowired
    private SerieDao serieDao;

    @Autowired
    private Validator validator;

    @Override
    public void create(Serie serie) {
        try {
            Assert.notNull(serie, "La serie no puede ser nula.");
            validar(serie);
            serieDao.create(serie);
        } catch (IllegalArgumentException ex) {
            String message = "Error al intentar crear la serie en la base de datos.";
            LOG.error(message, ex);
            throw new ServiceException(message, ex);
        }
    }

    @Override
    public Serie read(Long id) {
        LOG.debug("Recuperando una serie a partir de un ID de la base de datos.");
        return serieDao.read(id);
    }

    @Override
    public void update(Serie serie) {
        try {
            LOG.debug("Iniciando el proceso de actualización de una serie.");
            Assert.notNull(serie, "La serie no puede ser nula.");
            Assert.notNull(serie.getId(), "El identificardor de la serie no puede ser nulo.");
            validar(serie);
            serieDao.update(serie);
        } catch (IllegalArgumentException ex) {
            String message = "Error al intentar crear la serie en la base de datos.";
            LOG.error(message, ex);
            throw new ServiceException(message, ex);
        }

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Collection<Serie> findAll(Emisor emisor) {
        Map params = new HashMap();
        params.put("emisor", emisor);
        return serieDao.search(params.entrySet());
    }

    @Override
    public Integer getSiguienteFolio(Serie serie) {
        try {
            LOG.debug("Iniciando el proceso de actualización de una serie.");
            Assert.notNull(serie, "La serie no puede ser nula.");
            Assert.notNull(serie.getId(), "El identificardor de la serie no puede ser nulo.");
            validar(serie);
            return read(serie.getId()).getSiguienteFolio();
        } catch (IllegalArgumentException ex) {
            String message = "Error al intentar recuperar el siguiente folio de la serie en la base de datos.";
            LOG.error(message, ex);
            throw new ServiceException(message, ex);
        }
    }

    @Override
    public void actualizarSiguienteFolio(Serie serie) {
        try {
            LOG.debug("Iniciando el proceso de actualización de una serie.");
            Assert.notNull(serie, "La serie no puede ser nula.");
            Assert.notNull(serie.getId(), "El identificardor de la serie no puede ser nulo.");
            validar(serie);
            serie = read(serie.getId());
            serie.setSiguienteFolio(serie.getSiguienteFolio() + 1);
            update(serie);
        } catch (IllegalArgumentException ex) {
            String message = "Error al intentar actualizar el siguiente folio de la serie en la base de datos.";
            LOG.error(message, ex);
            throw new ServiceException(message, ex);
        }
    }

    private void validar(Serie serie) {
        Set<ConstraintViolation<Serie>> errors = validator.validate(serie);
        if (CollectionUtils.isNotEmpty(errors)) {
            String message = "La serie presenta errores de validación.";
            LOG.warn(message + " " + errors);
            throw new ValidationException(message, errors);
        }
    }
}

package com.entich.ezfact.facturacion.dao.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.entich.ezfact.clientes.factory.ClienteFactory;
import com.entich.ezfact.clientes.model.ClientePersonaFisica;
import com.entich.ezfact.clientes.model.ClientePersonaMoral;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.exceptions.dao.DataAccessException;
import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.dao.ComprobanteHelperDao;
import com.entich.ezfact.facturacion.model.ComprobanteHelper;
import com.entich.ezfact.facturacion.model.TipoDocumento;

@Repository
public class ComprobanteHelperDaoImpl extends GenericHibernateDaoImpl<ComprobanteHelper, Long>
        implements ComprobanteHelperDao {
    private static final Logger LOG = LoggerFactory.getLogger(ComprobanteHelperDaoImpl.class);

    @Override
    public void create(ComprobanteHelper obj) {
        throw new DataAccessException();
    }

    @Override
    public void update(ComprobanteHelper obj) {
        throw new DataAccessException();
    }

    @Override
    public void delete(ComprobanteHelper obj) {
        throw new DataAccessException();
    }

    @Override
    public ComprobanteHelper read(Long id) {
        throw new DataAccessException();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Collection<ComprobanteHelper> find(Emisor emisor, Date inicio, Date fin,
                                              BigDecimal montoMin, BigDecimal montoMax, Cliente cliente,
                                              TipoDocumento tipo, Boolean estatus, String nombreCliente) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(getType());
            criteria.add(Restrictions.eq("emisor", emisor.getId()));

            if (inicio != null && fin != null) {
                criteria.add(Restrictions.between("fechaCreacion", inicio, fin));
            } else {
                LOG.warn("No se aplico el filtro, ya que faltó alguno de los paramatros de fecha");
            }

            if (montoMin != null && montoMax != null) {
                criteria.add(Restrictions.between("montoConIVA", montoMin, montoMax));
            } else {
                LOG.warn("No se aplico el filtro, ya que faltó alguno de los paramatros de montos");
            }

            if (cliente != null && cliente.getId() != null) {
                criteria.add(Restrictions.eq("idCliente", cliente.getId()));
            }

            if (cliente != null && StringUtils.isNotBlank(cliente.getRfc())) {
                criteria.add(Restrictions.like("rfc", cliente.getRfc()));
            }

            if (nombreCliente != null) {
                cliente = ClienteFactory.newInstance(ClientePersonaFisica.class);
                ((ClientePersonaFisica) cliente).setNombre(nombreCliente);
                criteria.add(Restrictions.like("cliente", "%"+ ((ClientePersonaFisica) cliente).getNombre() + "%"));
            }
            
            if (tipo != null && tipo.getId() != null) {
                criteria.add(Restrictions.eq("tipo", tipo.getId()));
            }

            if (estatus != null) {
                criteria.add(Restrictions.eq("estatus", estatus));
            }

            criteria.addOrder(Order.desc("fechaCreacion"));
            criteria.addOrder(Order.desc("id"));

            return criteria.list();
        } catch (HibernateException ex) {
            String message = String.format(
                    "Error al recuperar los objetos del tipo {%s} desde la base "
                            + "de datos.", getType().getSimpleName());
            LOG.error(message, ex);
            throw new DataAccessException(message, ex);
        }
    }
}

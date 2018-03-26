package com.entich.ezfact.facturacion.dao.impl;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.ezfact.facturacion.dao.SerieDao;
import com.entich.ezfact.facturacion.model.Serie;
import org.springframework.stereotype.Repository;

/**
 * Created by Pegasus on 17/05/2016.
 */
@Repository
public class SerieDaoImpl extends GenericHibernateDaoImpl<Serie, Long> implements SerieDao {
}

package com.entich.ezfact.facturacion.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.ezfact.facturacion.dao.ConceptoDao;
import com.entich.ezfact.facturacion.model.Concepto;

@Repository
public class ConceptoDaoImpl extends GenericHibernateDaoImpl<Concepto, Long>
		implements ConceptoDao {
}

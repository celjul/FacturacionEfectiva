package com.entich.ezfact.facturacion.dao;

import com.entich.commons.dao.GenericDao;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.model.Comprobante;

public interface ComprobanteDao extends GenericDao<Comprobante, Long> {

	boolean folioMasivoDisponible(Emisor emisor, Integer folio);
}

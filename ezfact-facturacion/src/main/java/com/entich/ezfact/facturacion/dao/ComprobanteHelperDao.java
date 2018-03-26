package com.entich.ezfact.facturacion.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.entich.commons.dao.GenericDao;
import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.model.ComprobanteHelper;
import com.entich.ezfact.facturacion.model.TipoDocumento;

public interface ComprobanteHelperDao extends GenericDao<ComprobanteHelper, Long> {

	Collection<ComprobanteHelper> find(Emisor emisor, Date inicio, Date fin, 
			BigDecimal montoMin, BigDecimal montoMax, Cliente cliente,
			TipoDocumento tipo, Boolean estatus, String nombreCliente);
}

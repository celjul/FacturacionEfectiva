package com.entich.ezfact.facturacion.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.clientes.model.ClientePersonaMoral;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.model.ComprobanteHelper;
import com.entich.ezfact.facturacion.model.TipoDocumento;

public interface ComprobanteHelperService {
	
	Collection<ComprobanteHelper> findAll(Emisor emisor);
	
	Collection<ComprobanteHelper> find(Emisor emisor);
	
	Collection<ComprobanteHelper> find(Emisor emisor, Date inicio, Date fin);
	
	Collection<ComprobanteHelper> find(Emisor emisor, Date inicio, Date fin, 
			Cliente cliente);
	
	Collection<ComprobanteHelper> find(Emisor emisor, Date inicio, Date fin, 
			TipoDocumento tipo);
	
	Collection<ComprobanteHelper> find(Emisor emisor, Date inicio, Date fin, 
			Cliente cliente, TipoDocumento tipo, Boolean estatus);
	
	Collection<ComprobanteHelper> find(Emisor emisor, Date inicio, Date fin, 
			BigDecimal montoMin, BigDecimal montoMax, Cliente cliente,
			TipoDocumento tipo, Boolean estatus, String nombreCliente);
}

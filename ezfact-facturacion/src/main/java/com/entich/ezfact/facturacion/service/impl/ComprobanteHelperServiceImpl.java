package com.entich.ezfact.facturacion.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.dao.ComprobanteHelperDao;
import com.entich.ezfact.facturacion.model.ComprobanteHelper;
import com.entich.ezfact.facturacion.model.TipoDocumento;
import com.entich.ezfact.facturacion.service.ComprobanteHelperService;

@Service("comprobanteHelper")
public class ComprobanteHelperServiceImpl implements ComprobanteHelperService {

	@Autowired
	private ComprobanteHelperDao comprobanteDao;
	
	@Override
	public Collection<ComprobanteHelper> find(
			Emisor emisor, Date inicio,
			Date fin) {
		return comprobanteDao.find(emisor, inicio, fin, null, null, null, null,
				null, null);
	}

	@Override
	public Collection<ComprobanteHelper> find(
			Emisor emisor, Date inicio,
			Date fin, Cliente cliente) {
		return comprobanteDao.find(emisor, inicio, fin, null, null, cliente,
				null, null, null);
	}

	@Override
	public Collection<ComprobanteHelper> find(
			Emisor emisor, Date inicio,
			Date fin, TipoDocumento tipo) {
		return comprobanteDao.find(emisor, inicio, fin, null, null, null, tipo,
				null, null);
	}

	@Override
	public Collection<ComprobanteHelper> find(
			Emisor emisor, Date inicio,
			Date fin, Cliente cliente, TipoDocumento tipo, Boolean estatus) {
		return comprobanteDao.find(emisor, inicio, fin, null, null, cliente, 
				tipo, estatus, null);
	}

	@Override
	public Collection<ComprobanteHelper> find(
			Emisor emisor, Date inicio,
			Date fin, BigDecimal montoMin, BigDecimal montoMax, Cliente cliente,
			TipoDocumento tipo, Boolean estatus, String nombreCliente) {
		return comprobanteDao.find(emisor, inicio, fin, montoMin, montoMax,
				cliente, tipo, estatus, nombreCliente);
	}
	
	@Override
	public Collection<ComprobanteHelper> find(Emisor emisor) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.MONTH, -3);
		
		return comprobanteDao.find(emisor, calendar.getTime(), new Date(),
				null, null, null, null, null, null);
	}

	@Override
	public Collection<ComprobanteHelper> findAll(Emisor emisor) {
		return comprobanteDao.find(emisor, null, null, null, null, null, null, null, null);
	}
}

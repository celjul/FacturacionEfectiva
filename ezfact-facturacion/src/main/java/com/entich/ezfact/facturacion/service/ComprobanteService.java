package com.entich.ezfact.facturacion.service;

import java.io.File;
import java.util.Collection;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.model.Comprobante;

public interface ComprobanteService {
	void create(Comprobante remision);
	
	Comprobante read(long id);
	
	void update(Comprobante remision);
	
	void generarCFDi(long id);
	
	File generarRepresentacionImpresa(File xmlFile, File qrCode, Comprobante comprobante);
	File generarRepresentacionImpresa(Emisor emisor);
	
	String getCadenaOriginal(String file);
	
	Collection<Comprobante> findAll(Emisor emisor);
	
	void cancelar(Comprobante comprobante);
	
	boolean validarPlantilla(Long id);
	
	boolean folioMasivoDisponible(Emisor emisor, Integer folio);
	
	Comprobante get(String uuid);
}

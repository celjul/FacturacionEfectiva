package com.entich.commons.catalogo.service;

import java.util.Collection;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;

public interface OpcionDeCatalogoService {
	OpcionDeCatalogo read(long id);
	
	Collection<? extends OpcionDeCatalogo> 
		getCatalogo(Class<? extends OpcionDeCatalogo> tipo);
	
	OpcionDeCatalogo get(Class<? extends OpcionDeCatalogo> tipo, String clave);
	
	Collection<? extends OpcionDeCatalogo> search(Class<? extends OpcionDeCatalogo> tipo, String key);
}

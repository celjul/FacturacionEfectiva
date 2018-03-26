package com.entich.ezfact.facturacion.service;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.model.Serie;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Pegasus on 17/05/2016.
 */
@Service
public interface SerieService {

    void create(Serie serie);

    Serie read(Long id);

    void update(Serie serie);

    Collection<Serie> findAll(Emisor emisor);

    Integer getSiguienteFolio(Serie serie);

    void actualizarSiguienteFolio(Serie serie);
}

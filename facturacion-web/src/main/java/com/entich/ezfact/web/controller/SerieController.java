package com.entich.ezfact.web.controller;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.model.Serie;
import com.entich.ezfact.facturacion.service.SerieService;
import com.entich.ezfact.usuarios.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * Created by Pegasus on 17/05/2016.
 */
@Controller
@RequestMapping("app/series")
public class SerieController extends  AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SerieController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SerieService serieService;

    /*Método para recuperar la pantalla para registrar una serie.*/
    @RequestMapping(method = RequestMethod.GET, value = "new")
    public String nuevaSerie() {
        LOG.debug("Generando la pantalla de alta de series.");
        return "series/nuevaSerie";
    }

    /*Método para crear una serie.*/
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/")
    public @ResponseBody Serie crearSerie(@RequestBody Serie serie, HttpSession session) {
        LOG.debug("Guardando los datos de la nueva serie.");
        Emisor emisor = (Emisor) session.getAttribute("emisorSession");
        serie.setEmisor(emisor);
        if (serie.getId() == null) {
            serieService.create(serie);
        } else {
            serieService.update(serie);
        }
        return serie;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public
    @ResponseBody
    Collection<Serie> getSeries(HttpSession session) {
        LOG.debug("Generando la pantalla de alta de series.");
        Emisor emisor = (Emisor) session.getAttribute("emisorSession");
        return serieService.findAll(emisor);
    }

    @RequestMapping(method = RequestMethod.GET, value = "list")
    public String getSeries() {
        LOG.debug("Mostrando la pantalla de la lista de series.");
        return "series/lista_serie";
    }

    @RequestMapping(method = {RequestMethod.GET}, value = {"/{id}"})
    public String readSerie(Model model,
                            @PathVariable Long id, HttpSession session) {
        LOG.debug("Cargando una serie por ID.");
        Serie serie = serieService.read(id);
        addToModel(model, "serie", serie);
        return "series/edicion_serie";
    }
}

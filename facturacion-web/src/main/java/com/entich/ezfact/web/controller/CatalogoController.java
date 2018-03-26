package com.entich.ezfact.web.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;
import com.entich.commons.catalogo.service.OpcionDeCatalogoService;
import com.entich.ezfact.clientes.model.PaisSAT;
import com.entich.ezfact.clientes.model.UsoCFDi;
import com.entich.ezfact.emisores.model.Regimen;
import com.entich.ezfact.facturacion.model.ClaveProdServ;
import com.entich.ezfact.facturacion.model.ClaveUnidad;
import com.entich.ezfact.facturacion.model.FormaPago;
import com.entich.ezfact.facturacion.model.MetodoPago;
import com.entich.ezfact.facturacion.model.TipoDocumento;
import com.entich.ezfact.productos.model.UnidadDeMedida;
import com.entich.ezfact.usuarios.factory.RolFactory;
import com.entich.ezfact.usuarios.model.Rol;
import com.entich.ezfact.usuarios.model.Usuario;
import com.entich.ezfact.usuarios.service.RolService;

/**
 * Controller para el módulo de catálogos.
 * 
 * @author Luis Ángel Cárdenas Ortiz
 * @version 1.0
 * @created 17/07/2015
 */
@Controller
@RequestMapping("app/catalogos")
public class CatalogoController extends AbstractController {

	private static final Logger LOG = LoggerFactory
			.getLogger(CatalogoController.class);

	@Autowired
	private OpcionDeCatalogoService opcionDeCatalogoService;
	
	@Autowired
	private RolService rolService;

	@RequestMapping(method = RequestMethod.GET, value = "/{catalogo}")
	public @ResponseBody Collection<? extends OpcionDeCatalogo> getCatalogo(@PathVariable String catalogo) {
		LOG.debug("Recuperando la lista de opciones del catálogo:" + catalogo);
		
		switch (catalogo) {
		case "tipoDocumentos":
			return opcionDeCatalogoService.getCatalogo(TipoDocumento.class);

		case "metodoPago":
			return opcionDeCatalogoService.getCatalogo(MetodoPago.class);
			
		case "unidadMedida":
			return opcionDeCatalogoService.getCatalogo(UnidadDeMedida.class);
		
		 case "paises":
             return opcionDeCatalogoService.getCatalogo(PaisSAT.class);

         case "usosCfdi":
             return opcionDeCatalogoService.getCatalogo(UsoCFDi.class);
             
         case "regimenes":
        	 return opcionDeCatalogoService.getCatalogo(Regimen.class);
        	 
         case "formasPagos":
             return opcionDeCatalogoService.getCatalogo(FormaPago.class);

         case "clavesUnidad":
             return opcionDeCatalogoService.getCatalogo(ClaveUnidad.class);

         case "clavesProdServs":
             return opcionDeCatalogoService.getCatalogo(ClaveProdServ.class);
			
		default:
			return null;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "filtro/{catalogo}")
    public
    @ResponseBody
    Collection<? extends OpcionDeCatalogo> getCatalogo(@PathVariable String catalogo, @RequestParam String key) {

        LOG.debug("Recuperando la lista de opciones del catálogo:" + catalogo);

        switch (catalogo) {
            case "tipoDocumentos":
                return opcionDeCatalogoService.search(TipoDocumento.class, key);

            case "metodoPago":
                return opcionDeCatalogoService.search(MetodoPago.class, key);

            case "unidadMedida":
                return opcionDeCatalogoService.search(UnidadDeMedida.class, key);

            case "paises":
                return opcionDeCatalogoService.search(PaisSAT.class, key);

            case "usosCfdi":
                return opcionDeCatalogoService.search(UsoCFDi.class, key);

            case "regimenes":
                return opcionDeCatalogoService.search(Regimen.class, key);

            case "formasPagos":
                return opcionDeCatalogoService.search(FormaPago.class, key);

            case "clavesUnidad":
                return opcionDeCatalogoService.search(ClaveUnidad.class, key);

            case "clavesProdServs":
                return opcionDeCatalogoService.search(ClaveProdServ.class, key);

            default:
                return null;
        }
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/roles")
	public @ResponseBody Collection<Rol> getRoles(HttpSession session) {
		final Rol webmaster = RolFactory.newInstance(1L);
//		final Rol propietario = RolFactory.newInstance(2L);
		Usuario usuario = (Usuario) session.getAttribute("user");
		Collection<Rol> roles = rolService.getRoles();
		
		if (! usuario.getRoles().contains(webmaster)) {
			roles.remove(webmaster);
		}
		
		return roles;
	}
}
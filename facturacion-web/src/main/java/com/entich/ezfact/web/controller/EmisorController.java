package com.entich.ezfact.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.commons.lang.StringUtils;
import org.hibernate.type.ByteArrayBlobType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entich.commons.catalogo.service.OpcionDeCatalogoService;
import com.entich.commons.direcciones.factory.DireccionFactory;
import com.entich.commons.direcciones.service.PaisService;
import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.emisores.model.DireccionEmisor;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.service.DireccionEmisorService;
import com.entich.ezfact.emisores.service.EmisorService;
import com.entich.ezfact.facturacion.service.ComprobanteService;
import com.entich.ezfact.folios.model.PaqueteUsuario;
import com.entich.ezfact.services.impl.ConfigurationServiceImp;
import com.entich.ezfact.usuarios.factory.RolFactory;
import com.entich.ezfact.usuarios.model.Rol;
import com.entich.ezfact.usuarios.model.Usuario;
import com.entich.ezfact.folios.service.PaqueteUsuarioService;
import com.entich.ezfact.usuarios.service.UsuarioService;
import com.sun.media.sound.EmergencySoundbank;

/**
 * Controller para el módulo de emisores.
 *
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 02/12/2013
 */
@Controller
@RequestMapping("app/emisores")
public class EmisorController extends AbstractController {

    private static final Logger LOG = LoggerFactory
            .getLogger(EmisorController.class);

    @Autowired
    private EmisorService emisorService;

    @Autowired
    private ComprobanteService comprobanteService;

    @Autowired
    private DireccionEmisorService direccionService;

    @Autowired
    private PaisService paisService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private OpcionDeCatalogoService opcionDeCatalogoService;

    @Autowired
    PaqueteUsuarioService paqueteUsuarioService;

    @Autowired
    private ConfigurationServiceImp configurationServiceImpl;

    public static final String LOGO = "logoEmisor";

    @RequestMapping(method = RequestMethod.GET, value = "new")
    public String newEmisor(HttpSession session, Model model) {
        LOG.debug("Cargando la pantalla para registrar un emisor");

        File convFile = new File(configurationServiceImpl.getPathLogos() + configurationServiceImpl.getPathPlantilla0() + File.separator + "logo.jpg");
        byte[] array;
        try {
            array = Files.readAllBytes(convFile.toPath());
        } catch (IOException e) {
            String message = "No se pudo leer la imagen del servidor.";
            LOG.warn(message, e);
            throw new ServiceException(message, e);
        }
        addToModel(model, "logo", array);

        return "emisor/registro_emisor";
    }

    @RequestMapping(method = RequestMethod.POST, value = "subirimagen")
    public
    @ResponseBody
    Emisor cargarLogo(@RequestParam MultipartFile file, HttpSession session) {

        session.removeAttribute(LOGO);

        try {
            session.setAttribute(LOGO, file.getBytes());
        } catch (IOException e) {
            String message = "No se pudo convertir a bytes la imagen.";
            LOG.warn(message, e);
            throw new ServiceException(message, e);
        }
        return null;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/")
    public
    @ResponseBody
    Emisor crearEmisor(HttpSession session,
                       @RequestBody Emisor emisor) {
        LOG.debug(String.format("Guardando la informacion de un emisor de tipo %s.",
                emisor.getClass().getSimpleName()));
        Usuario usuario = (Usuario) session.getAttribute("user");

        File convDirsDefault = new File(configurationServiceImpl.getPathPlantillas() + configurationServiceImpl.getPathPlantillaDefault());
        File convDirsDestino = new File(configurationServiceImpl.getPathLogos() + emisor.getRfc());
        File convFile = new File(convDirsDestino.getPath() + File.separator + "logo.jpg");

        if (emisor.getPlantilla() == null ||
                StringUtils.isBlank(emisor.getPlantilla()) ||
                emisor.getPlantilla().equals("personalizada")) {
            emisor.setPlantilla(convDirsDestino.getAbsolutePath());
        }

        if (emisor.getId() == null) {
            emisorService.create(emisor);
            usuario.getEmisores().add(emisor);
            usuarioService.asignarEmisor(usuario);
        } else {
            emisorService.update(emisor);
        }
        session.setAttribute("user", usuarioService.getByUsername(usuario.getLogin()));
//		MultipartFile multi = (MultipartFile)session.getAttribute(LOGO);
        byte[] imgby = (byte[]) session.getAttribute(LOGO);

        try {
            if (!convFile.getParentFile().exists()) {
                convFile.getParentFile().mkdirs();
            }
            if (emisor.getPlantilla() == null ||
                    StringUtils.isBlank(emisor.getPlantilla()) ||
                    emisor.getPlantilla().equals("personalizada")) {
                Files.copy(convDirsDefault.toPath(), convDirsDestino.toPath());
            }

//			 FileCopyUtils.copy(multi.getBytes(), convFile);
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(imgby);
            fos.close();

        } catch (IllegalStateException e) {
            String message = "No se pudo subir la imagen al servidor.";
            LOG.warn(message, e);
            throw new ServiceException(message, e);
        } catch (IOException e) {
            String message = "No se pudo subir la imagen al servidor.";
            LOG.warn(message, e);
            throw new ServiceException(message, e);
        }

        return emisor;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public
    @ResponseBody
    Collection<Emisor> getEmisores(HttpSession session) {
        LOG.debug("Recuperando la lista de emisores en el controlador.");

        Usuario usuario = (Usuario) session.getAttribute("user");
        Rol webmaster = RolFactory.newInstance(1L);

        if (usuario.getRoles().contains(webmaster)) {
            return emisorService.getAll();
        } else {
            return usuarioService.getByUsername(usuario.getLogin()).getEmisores();
            /*return usuario.getEmisores();*/
        }
    }

    @RequestMapping(method = {RequestMethod.GET}, value = {"/{id}"})
    public String readEmisor(Model model,
                             @PathVariable Long id, HttpSession session) {
        LOG.debug("Cargando un emisor");
        Emisor emisor = emisorService.read(id);

        addToModel(model, "emisor", emisor);

        /*************************************************************************************************/

        File convFile = new File(configurationServiceImpl.getPathLogos() + emisor.getRfc() + File.separator + "logo.jpg");
        byte[] array;
        try {
            array = Files.readAllBytes(convFile.toPath());
        } catch (IOException e) {
            String message = "No se pudo leer la imagen del servidor.";
            LOG.warn(message, e);
            throw new ServiceException(message, e);
        }
        addToModel(model, "logo", array);
        session.setAttribute(LOGO, array);
        return "emisor/edicion_emisor";
    }


    @RequestMapping(method = RequestMethod.GET, value = "list")
    public String listAllEmisores(Model model) {
        LOG.debug("Cargando todos los emisores");
//		model.addAttribute("emisores", emisorService.getAll());
        return "emisor/lista_emisor";
    }

    @RequestMapping(value = "select", method = RequestMethod.GET)
    public String select(Model model, HttpSession session) {
        LOG.debug("Pantalla para seleccionar el emisor con el cual se conectara el usuario");
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserDetails userDetails = null;

        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }

        Usuario usuario = usuarioService.getByUsername(userDetails
                .getUsername());
        session.setAttribute("user", usuario);


        return "emisor/select";
    }

    @RequestMapping(method = {RequestMethod.GET}, value = {"selected"})
    public String selected(HttpSession session, Model model,
                           @RequestParam(value = "emisor") Long id) throws ParseException {
        LOG.debug("Cargando los comprobantes emitidos por un emisor");

        Emisor emisor = emisorService.read(id);

        Usuario usuario = (Usuario) session.getAttribute("user");
        Rol webmaster = RolFactory.newInstance(1L);
        if (usuario.getRoles().contains(webmaster) || usuario.getEmisores().contains(emisor)) {
            session.setAttribute("emisorSession", emisor);
        } else {
            model.addAttribute("ex", new ServiceException("El usuario no tiene acceso al emisor solicitado."));
            return ERROR_PAGE;
        }

        return "facturas/lista";
    }

    @RequestMapping(method = {RequestMethod.DELETE}, value = {"/{emisor}/direcciones/{direccion}"})
    public
    @ResponseBody
    String eliminarDireccion(@PathVariable Long emisor, @PathVariable Long direccion) {
        LOG.debug(String.format("Eliminar la direccion %s del emisor %s", direccion, emisor));
        DireccionEmisor dir = DireccionFactory.newInstance(DireccionEmisor.class, direccion);

        direccionService.eliminar(dir);
        return "";
    }

    @RequestMapping(method = {RequestMethod.GET}, value = {"/{emisor}/plantilla"})
    public
    @ResponseBody
    boolean validarPlantilla(@PathVariable Long emisor) {
        LOG.debug(String.format("Validando la existencia de la plantilla del emisor %s", emisor));
        return comprobanteService.validarPlantilla(emisor);
    }

}
package com.entich.ezfact.web.controller;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.folios.factory.PaqueteUsuarioFactory;
import com.entich.ezfact.folios.model.Paquete;
import com.entich.ezfact.folios.model.PaqueteUsuario;
import com.entich.ezfact.folios.service.PaqueteService;
import com.entich.ezfact.folios.service.PaqueteUsuarioService;
import com.entich.ezfact.model.UsuarioAutoregistroDto;
import com.entich.ezfact.services.EmailService;
import com.entich.ezfact.services.impl.ConfigurationServiceImp;
import com.entich.ezfact.usuarios.factory.UsuarioFactory;
import com.entich.ezfact.usuarios.model.Usuario;
import com.entich.ezfact.usuarios.service.RolService;
import com.entich.ezfact.usuarios.service.UsuarioService;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class LoginController extends AbstractController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmailService emailingService;

	@Autowired
	private PaqueteUsuarioService paqueteUsuarioService;

	@Autowired
	private PaqueteService paqueteService;

	@Autowired
	private ConfigurationServiceImp configurationServiceImp;

	@Autowired
	private RolService rolService;


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		LOGGER.debug("Pantalla de login");
		return "login/login";
	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "true");
		return "login/login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {

		return "login/login";
	}

	@RequestMapping(method = RequestMethod.GET, value = "newAccount")
	public String newAccount(ModelMap model) {
		model.addAttribute("roles", rolService.getRoles());
		return "login/new_account";
	}

	//
	// /**********************************************************************************************************/
	// @RequestMapping(method = RequestMethod.POST, value = "sendMail")
	// public String enviarEmail(@RequestParam Long id) {
	// Map<String, Object> model = new HashMap<String, Object>();
	// model.put("title", "Bienvenido a Facturación Efectiva");
	// model.put("name", "Hola!");
	// model.put("message",
	// "Te damos las gracias por contratar un paquete con nosotros.");
	//
	// Usuario usuario = usuarioService.read(id);
	//
	// emailingService.sendEmail(usuario.getLogin(), model,
	// "Bienvenido a Facturación Efectiva", "mensajeBienvenida.vm");
	//
	// return "login/login";
	// }
	// /**********************************************************************************************************/
	//

	@RequestMapping(method = RequestMethod.POST, value = "autoregistro")
	public @ResponseBody Usuario createUser(
			@RequestBody final UsuarioAutoregistroDto usuario,
			HttpServletRequest request) {
		LOGGER.debug("Recupere al usuario: "
				+ (usuario == null ? "[null]" : usuario.getNombre() + " "
						+ usuario.getApellidoPaterno()));
		// model.addAttribute("roles", rolService.getRoles());

		String remoteAddr = request.getRemoteAddr();
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey(configurationServiceImp.getClaveSecreta());

		String challenge = usuario.getChallengeField();
		String uresponse = usuario.getResponseField();
		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr,
				challenge, uresponse);

		if (reCaptchaResponse.isValid()) {
			System.out.println("Answer was entered correctly!");
			Usuario user = UsuarioFactory.newInstance();
			user.setApellidoMaterno(usuario.getApellidoMaterno());
			user.setApellidoPaterno(usuario.getApellidoPaterno());
			user.setEmisores(usuario.getEmisores());
			user.setLogin(usuario.getLogin());
			user.setNombre(usuario.getNombre());
			user.setPassword(usuario.getPassword());
			user.setRoles(usuario.getRoles());
			user.setTelefono(usuario.getTelefono());
			user.setCodigo(usuario.getCodigo());
			System.out.println(user);
			usuarioService.create(user);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, 7);
			Date date = new Date();
			PaqueteUsuario paqueteUsuario = PaqueteUsuarioFactory.newInstance();
			paqueteUsuario.setAdquirido(date);
			date = cal.getTime();
			paqueteUsuario.setVencimiento(date);
			paqueteUsuario.setUsuario(user);
			paqueteUsuario.setActivo(true);
			Collection<Paquete> paquetes = paqueteService.getPaquetes();
			for (Paquete paquete : paquetes) {
				if (paquete.getNombrePaquete().compareTo("Demo") == 0) {
					paqueteUsuario.setPaquete(paquete);
				}
			}
			paqueteUsuarioService.create(paqueteUsuario);
			final Map<String, Object> model = new HashMap<String, Object>();
			model.put("title", "Bienvenido a Facturación Efectiva, ");
			model.put("persona", usuario.getNombre());
			model.put("name", "Hola!");
			model.put(
					"message",
					"Te damos la cordial bienvenida al servicio de Facturación Efectiva; "
							+ "la manera más fácil y rápida de facturar. Disfruta tu periodo de prueba gratis.");
			model.put("usuario", "Tu usuario: " + usuario.getLogin());
			model.put("contrasena",
					"Tu contraseña: " + usuario.getPassword());
			model.put(
					"messageFooter",
					"Aviso de Confidencialidad: Este correo electrónico está destinado únicamente "
							+ "a la persona o entidad a quien va dirigido, puede contener información legal confidencial "
							+ "y privilegiada incluida los ficheros adjuntos. Si Ud. no es el destinatario a quien "
							+ "se desea enviar este mensaje, se advierte que cualquier divulgación, distribución, "
							+ "copia o acción relacionada con el contenido de esta comunicación, sin la autorización del remitente "
							+ "está totalmente prohibida. Si recibe este mensaje por error, favor de notificarlo al remitente "
							+ "de inmediato y desecharlo de su correo… Por su atención Gracias. ");

			// Usuario usuario = usuarioService.read(id);
			Thread hilo = new Thread(new Runnable() {
				@Override
				public void run() {
					emailingService.sendEmail(usuario.getLogin(), model,
							"Bienvenido a Facturación Efectiva",
							"mensajeBienvenida.vm");
				}
			});
			hilo.start();

			return user;
		} else {
			throw new ServiceException(
					"Los valores escritos no corresponde a los valores de la imagen");
		}

	}
}

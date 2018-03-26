package com.entich.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;

/**
 * Utileria que permite la digestion de cadenas.
 * 
 * @author Luis Angel Cardenas
 * 
 *
 */
public class PasswordUtils {
	private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);
	
	public static String md5Digest(String password) {
			log.debug("Convirtiendo la cadena: " + password);
			
			try {
				Assert.notNull(password, "El password no puede ser nulo.");
				
				byte[] defaultBytes = password.getBytes("UTF8");
				MessageDigest algorithm = MessageDigest.getInstance("MD5");
				algorithm.reset();
				algorithm.update(defaultBytes);
				byte messageDigest[] = algorithm.digest();
	
				StringBuilder hexString = new StringBuilder();
				for (int i = 0; i < messageDigest.length; i++) {
					int byteValue = 0xFF & messageDigest[i];
					String hexStringAux = byteValue < 16 ? "0"
							+ Integer.toHexString(byteValue) : Integer
							.toHexString(byteValue);
	
					hexString.append(hexStringAux);
				}
				String md5 = hexString.toString();
				log.debug("Se obtuvo la cadena: " + md5);
				return md5;
			} catch (NoSuchAlgorithmException ex) {
				log.error("No se pudo encriptar la cadena debido a errores "
						+ "en el algoritmo seleccionado.", ex);
				throw new ServiceException("Error al generar la digestion MD5", ex);
			} catch (UnsupportedEncodingException ex) {
				log.error("No se pudo encriptar la cadena debido a errores "
						+ "en el encoding de la palabra a encriptar.", ex);
				throw new ServiceException("Codificacion de caracteres no valida", 
						ex);
			} catch (IllegalArgumentException ex) {
				String mesage = "Argumentos no permitidos en la invocacion del metodo."; 
				log.error(mesage, ex);
				throw new ServiceException(mesage, ex);
			}
		}
}


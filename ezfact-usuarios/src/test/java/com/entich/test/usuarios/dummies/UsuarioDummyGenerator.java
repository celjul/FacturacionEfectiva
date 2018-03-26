package com.entich.test.usuarios.dummies;

import com.entich.ezfact.usuarios.factory.UsuarioFactory;
import com.entich.ezfact.usuarios.model.Rol;
import com.entich.ezfact.usuarios.model.Usuario;

/**
 * Utileria que crea instancias del tipo Usuario usadas en las pruebas.
 * 
 * @author Luis Angel Cardenas
 *
 */
public class UsuarioDummyGenerator {
	private static int INDEX = 0;
	
	public static Usuario createUsuario(Usuario superior, Rol puesto) {
		Usuario usuario = UsuarioFactory.newInstance();
		usuario.setActivo(true);
		usuario.setApellidoMaterno("Apemat" + INDEX);
		usuario.setApellidoPaterno("Apepat" + INDEX);
		usuario.setLogin("userlogin" + INDEX);
		usuario.setPassword("unpassword");
		usuario.setNombre("Nombre" + INDEX);
		
		INDEX++;
		return usuario;
	}
}
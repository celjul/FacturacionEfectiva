package com.entich.emisores.test.dummies;

import com.entich.commons.direcciones.factory.DireccionFactory;
import com.entich.ezfact.emisores.factory.EmisorFactory;
import com.entich.ezfact.emisores.model.DireccionEmisor;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.model.EmisorFisica;
import com.entich.ezfact.emisores.model.EmisorMoral;

/**
 * Clase que crea instancias dummy para las pruebas unitarias de un emisor.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
public class EmisorDummyGenerator {

	// private static int index = 0;

	public static Emisor getEmisorFisica() {
		EmisorFisica emisor = EmisorFactory.newInstance(EmisorFisica.class);
		emisor.setApellidoMaterno("Santos");
		emisor.setApellidoPaterno("Cruz");
		emisor.addDireccion(getDireccion());
		emisor.setNombre("Pablo");
		emisor.setRfc("CUSO8909197X8");
		return emisor;
	}

	public static Emisor getEmisorMoral() {
		EmisorMoral emisor = EmisorFactory.newInstance(EmisorMoral.class);
		emisor.addDireccion(getDireccion());
		emisor.setRazonSocial("Porto Mx SA de CV");
		emisor.setRfc("PMX000615HD5");
		return emisor;
	}

	public static DireccionEmisor getDireccion() {
		DireccionEmisor direccion = DireccionFactory
				.newInstance(DireccionEmisor.class);
		direccion.setCalle("Norte 81");
		//direccion.setColonia(ColoniaFactory.newInstance(3L));
		direccion.setNoExterior("408");
		direccion.setNoInterior("Planta baja");
		direccion.setReferencias("No confundir la calle con Norte 81-A");
		return direccion;
	}

	public static String insertChars(String string, int chars) {
		StringBuilder builder = new StringBuilder(string);
		for (int i = string.length(); i <= chars; i++) {
			builder.append("x");
		}
		return builder.toString();
	}
}
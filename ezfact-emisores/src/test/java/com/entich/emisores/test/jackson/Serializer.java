package com.entich.emisores.test.jackson;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.ezfact.emisores.model.Emisor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(Serializer.class);
	
	ObjectMapper mapper = new ObjectMapper();
	
	public void serialize() {
		
	}
	
	@Test
	public void deserialize() throws JsonParseException, JsonMappingException, IOException {
		String emisor = "{\"direcciones\":[{\"calle\":\"Bruselas\",\"colonia\":{\"codigoPostal\":\"43245\",\"id\":1,\"nombre\":\"Morelos\"},\"noExterior\":\"6\",\"noInterior\":\"202\",\"referencias\":\"Frente al museo de cera\",\"@class\":\"com.entich.ezfact.emisores.model.DireccionEmisor\"}],\"rfc\":\"TDI140522133\",\"nombre\":\"\",\"razonSocial\":\"Tejón Digital S. A.de C. V.\",\"regimenes\":[\"Régimen de personas morales\"],\"@class\":\"com.entich.ezfact.emisores.model.EmisorMoral\"}";
		
		Emisor objeto = mapper.readValue(emisor, Emisor.class);
		LOGGER.info(objeto.toString());
	}
}

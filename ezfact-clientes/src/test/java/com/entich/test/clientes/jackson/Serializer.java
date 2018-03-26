package com.entich.test.clientes.jackson;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.ezfact.clientes.model.Cliente;
import com.entich.test.clientes.dummy.ClienteDummyGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(Serializer.class);
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void serialize() throws JsonProcessingException {
		Cliente cliente = ClienteDummyGenerator.createClientePersonaFisicaCompleto();
		
		
		LOGGER.info(mapper.writeValueAsString(cliente));
	}
	
	@Test
	public void deserialize() throws JsonParseException, JsonMappingException, IOException {
		String cliente = "{\"@class\":\"com.entich.ezfact.clientes.model.ClientePersonaFisica\",\"id\":null,\"rfc\":\"MEIP8606304G6\",\"razonComercial\":\"Aquiles va la Razon Comercial1\",\"paginaWeb\":\"Aquiles va la Pagina Web1\",\"observaciones\":\"Aquiles van las Observaciones1\",\"direcciones\":[{\"type\":\"DireccionCliente\",\"calle\":\"Aquiles va la calle1\",\"colonia\":{\"codigoPostal\":null,\"id\":1,\"nombre\":null},\"id\":null,\"noExterior\":\"Aquiles va el numero exterior1\",\"noInterior\":null,\"referencias\":null,\"localidad\":\"Aquiles va la localidad1\"}],\"contactos\":[{\"id\":null,\"nombre\":\"Aquiles va el nombre1\",\"apellidoPaterno\":\"Aquiles va el apellido paterno1\",\"apellidoMaterno\":null,\"puesto\":\"Aquiles va el puesto1\",\"email\":\"aquiles.va@el.email1\"}],\"nombre\":\"Aquiles va el nombre1\",\"apellidoPaterno\":\"Aquiles va el apellido paterno1\",\"apellidoMaterno\":\"Aquiles va el apellido materno1\"}";
		
		Cliente objeto = mapper.readValue(cliente.getBytes(), Cliente.class);
		
		LOGGER.info(objeto.toString());
	}
}

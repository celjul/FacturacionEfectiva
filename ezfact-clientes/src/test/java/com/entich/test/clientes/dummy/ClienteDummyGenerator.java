package com.entich.test.clientes.dummy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.ezfact.clientes.factory.ClienteFactory;
import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.clientes.model.ClientePersonaFisica;
import com.entich.ezfact.clientes.model.ClientePersonaMoral;

/**
 * Utileria que crea instancias de
 * <code>Cliente</code> para facilitar la creacion de pruebas. *
 *
 * @author Pedro Josue Mendoza Islas
 */
public class ClienteDummyGenerator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteDummyGenerator.class);

    private static int INDEX_DUMMY = 1;
    private static final String RFC_DUMMY = "MEIP8606304G6";
    private static final String RAZON_COMERCIAL_DUMMY = "Aquiles va la Razon Comercial";
    private static final String PAGINA_WEB_DUMMY = "Aquiles va la Pagina Web";
    private static final String OBSERVACIONES_DUMMY = "Aquiles van las Observaciones";
    
    /*
     * Persona Fisica
     */
    private static final String NOMBRE_DUMMY = "Aquiles va el nombre";
    private static final String APELLIDO_PATERNO_DUMMY = "Aquiles va el apellido paterno";
    private static final String APELLIDO_MATERNO_DUMMY = "Aquiles va el apellido materno";
    
    /*
     * Persona Moral
     */
    private static final String RAZON_SOCIAL_DUMMY = "Aquiles va la razon social";

    /**
     * Crea una instancia de <code>ClientePersonaFisica</code> con los atributos
     * minimos 
     * @return cliente
     */
    public static Cliente createClientePersonaFisicaMinimo() {
    	LOGGER.info("Alta de cliente.");
        ClientePersonaFisica cliente = ClienteFactory.newInstance(ClientePersonaFisica.class);
        cliente.setNombre(NOMBRE_DUMMY + INDEX_DUMMY);
        cliente.setApellidoPaterno(APELLIDO_PATERNO_DUMMY + INDEX_DUMMY);
        cliente.setRfc(RFC_DUMMY);
        cliente.setRazonComercial(RAZON_COMERCIAL_DUMMY + INDEX_DUMMY);        
        cliente.addContacto(ContactoClienteDummyGenerator.createContactoClienteMinimo());

        INDEX_DUMMY ++;
        return cliente;
    }
    
    /**
     * Crea una instancia de <code>ClientePersonaFisica</code> con todos los atributos
     * 
     * @return cliente
     */
    public static Cliente createClientePersonaFisicaCompleto() {
        ClientePersonaFisica cliente = ClienteFactory.newInstance(ClientePersonaFisica.class);
        cliente.setNombre(NOMBRE_DUMMY + INDEX_DUMMY);
        cliente.setApellidoPaterno(APELLIDO_PATERNO_DUMMY + INDEX_DUMMY);
        cliente.setApellidoMaterno(APELLIDO_MATERNO_DUMMY + INDEX_DUMMY);
        cliente.setRfc(RFC_DUMMY);
        cliente.setRazonComercial(RAZON_COMERCIAL_DUMMY + INDEX_DUMMY);
        cliente.setPaginaWeb(PAGINA_WEB_DUMMY + INDEX_DUMMY);
        cliente.setObservaciones(OBSERVACIONES_DUMMY + INDEX_DUMMY);
        cliente.addContacto(ContactoClienteDummyGenerator.createContactoClienteMinimo());

        INDEX_DUMMY ++;
        return cliente;
    }   
    /**
     * Crea una instancia de <code>ClientePersonaFisica</code> con los atributos
     * minimos 
     * @return cliente
     */
    public static Cliente createClientePersonaMoralMinimo() {
        ClientePersonaMoral cliente = ClienteFactory.newInstance(ClientePersonaMoral.class);
        cliente.setRazonSocial(RAZON_SOCIAL_DUMMY + INDEX_DUMMY);        
        cliente.setRfc(RFC_DUMMY);
        cliente.setRazonComercial(RAZON_COMERCIAL_DUMMY + INDEX_DUMMY);        
        cliente.addContacto(ContactoClienteDummyGenerator.createContactoClienteMinimo());

        INDEX_DUMMY ++;
        return cliente;
    }
    
    /**
     * Crea una instancia de <code>ClientePersonaMoral</code> con todos los atributos
     * 
     * @return cliente
     */
    public static Cliente createClientePersonaMoralCompleto() {
        ClientePersonaMoral cliente = ClienteFactory.newInstance(ClientePersonaMoral.class);
        cliente.setRazonSocial(RAZON_SOCIAL_DUMMY + INDEX_DUMMY);
        cliente.setRfc(RFC_DUMMY);
        cliente.setRazonComercial(RAZON_COMERCIAL_DUMMY + INDEX_DUMMY);
        cliente.setPaginaWeb(PAGINA_WEB_DUMMY + INDEX_DUMMY);
        cliente.setObservaciones(OBSERVACIONES_DUMMY + INDEX_DUMMY);
        cliente.addContacto(ContactoClienteDummyGenerator.createContactoClienteMinimo());
        
        INDEX_DUMMY ++;
        return cliente;
    }
}
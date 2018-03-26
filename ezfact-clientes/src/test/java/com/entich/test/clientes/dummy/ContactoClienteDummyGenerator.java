package com.entich.test.clientes.dummy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.ezfact.clientes.factory.ContactoClienteFactory;
import com.entich.ezfact.clientes.model.ContactoCliente;

/**
 * Utileria que crea instancias de
 * <code>Cliente</code> para facilitar la creacion de pruebas. *
 *
 * @author Pedro Josue Mendoza Islas
 */
public class ContactoClienteDummyGenerator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactoClienteDummyGenerator.class);

    private static int INDEX_DUMMY = 1;
    private static final String NOMBRE_DUMMY = "Aquiles va el nombre";
    private static final String APELLIDO_PATERNO_DUMMY = "Aquiles va el apellido paterno";
    private static final String PUESTO_DUMMY = "Aquiles va el puesto";
    private static final String EMAIL_DUMMY = "aquiles.va@el.email";
    
    /**
     * Devuelve una instancia de <code>ContactoCliente</code> con los campos minimos necesarios
     * @return contactoCliente
     */   
    public static ContactoCliente createContactoClienteMinimo() {
    	LOGGER.info("Creando contacto");
    	
        ContactoCliente a = ContactoClienteFactory.newInstance();
        a.setNombre(NOMBRE_DUMMY + INDEX_DUMMY);
        a.setApellidoPaterno(APELLIDO_PATERNO_DUMMY + INDEX_DUMMY);
        a.setEmail(EMAIL_DUMMY + INDEX_DUMMY);
        a.setPuesto(PUESTO_DUMMY + INDEX_DUMMY);

        INDEX_DUMMY ++;
        return a;
    }
}
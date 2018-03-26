package com.entich.test.clientes.dummy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.commons.direcciones.factory.ColoniaFactory;
import com.entich.commons.direcciones.factory.DireccionFactory;
import com.entich.ezfact.clientes.model.DireccionCliente;

/**
 * Utileria que crea instancias de
 * <code>Cliente</code> para facilitar la creacion de pruebas. *
 *
 * @author Pedro Josue Mendoza Islas
 */
public class DireccionClienteDummyGenerator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DireccionClienteDummyGenerator.class);

    private static Integer INDEX_DUMMY = 1;
    private static final String CALLE_DUMMY = "Aquiles va la calle";
    private static final String NUMERO_EXTERIOR_DUMMY = "Aquiles va el numero exterior";
    private static final String LOCALIDAD_DUMMY = "Aquiles va la localidad";

    /**
     * Devuelve una instancia de <code>DireccionCliente</code> con los campos minimos requeridos
     * @return direccionCliente
     */
    public static DireccionCliente createDireccionClienteMinima() {
    	LOGGER.info("Creando direccion.");
        DireccionCliente direccionCliente = DireccionFactory.newInstance(DireccionCliente.class);
        direccionCliente.setCalle(CALLE_DUMMY + INDEX_DUMMY);
        direccionCliente.setNoExterior(NUMERO_EXTERIOR_DUMMY + INDEX_DUMMY);
        //direccionCliente.setColonia(ColoniaFactory.newInstance(1L));
//        direccionCliente.setCodigoPostal(CODIGO_POSTAL_DUMMY + INDEX_DUMMY);
        direccionCliente.setLocalidad(LOCALIDAD_DUMMY + INDEX_DUMMY);

        INDEX_DUMMY ++;
        return direccionCliente;
    }
}
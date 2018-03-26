package com.entich.ezfact.clientes.factory;

import com.entich.ezfact.clientes.model.ContactoCliente;

/**
 * Factoria de instancias de la clase <code>ContactoCliente</code>
 * 
 * @author Pedro Josue Mendoza Islas
 */
public class ContactoClienteFactory {

	/**
	 * Crea una nueva instancia de <code>ContactoCliente</code> utilizando el
	 * constructor por defecto
	 * 
	 * @return ContactoCliente
	 */
	public static ContactoCliente newInstance() {
		return new ContactoCliente();
	}

	/**
	 * Crea una nueva instancia de <code>ContactoCliente</code> utilizando el
	 * constructor por defecto y establece el valor del atributo id
	 * 
	 * @return contactoCliente
	 */
	public static ContactoCliente newInstanceId(long id) {
		ContactoCliente contactoCliente = newInstance();
		contactoCliente.setId(id);

		return contactoCliente;
	}
}
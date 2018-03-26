package com.entich.test.clientes.service;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.clientes.dao.ClienteDao;
import com.entich.ezfact.clientes.factory.ClienteFactory;
import com.entich.ezfact.clientes.model.ClientePersonaFisica;
import com.entich.ezfact.clientes.model.ClientePersonaMoral;
import com.entich.ezfact.clientes.service.ClienteService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"/service-test-clientes.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceTest.class);
    @ReplaceWithMock
    @Autowired
    private ClienteDao mockClienteDao;
    @ReplaceWithMock
    @Autowired
    private Validator mockValidator;
    @InjectMocks
    @Autowired
    private ClienteService clienteService;

    @Test
    public void _001_testValidarServiceNoNulo() {
        logger.info("Prueba para validar que el servicio no sea nulo");
        Assert.assertNotNull(clienteService);
    }

    @Test(expected = ServiceException.class)
    public void _002_testCrearClientePersonaFisicaNulo() {
        clearMocks();
        logger.info("Prueba para validar la creacion de un cliente nulo.");

        clienteService.create(null);
    }

    @Test(expected = ServiceException.class)
    public void _003_testCrearClientePersonaFisicaInvalida() {
        clearMocks();
        logger.info("Prueba para validar la creacion de un cliente del tipo Persona Fisic con errores de validacion");
        ClientePersonaFisica cliente = ClienteFactory.newInstance(ClientePersonaFisica.class);
        Mockito.when(mockValidator.validate(cliente)).thenReturn(getValidationErrorsPersonaFisica(1));
        clienteService.create(cliente);
    }

    @Test(expected = ServiceException.class)
    public void _004_testCrearClientePersonaMoralInvalida() {
        clearMocks();
        logger.info("Prueba para validar la creacion de un cliente del tipo Persona Moral con errores de validacion");
        ClientePersonaMoral cliente = ClienteFactory.newInstance(ClientePersonaMoral.class);
        Mockito.when(mockValidator.validate(cliente)).thenReturn(getValidationErrorsPersonaMoral(1));
        clienteService.create(cliente);
    }

//	@Test(expected = ServiceException.class)
//	public void testCrearUsuarioErroresValidacion() {
//		clearMocks();
//		LOG.info("Test para crear un usuario con errores de validacion.");
//		Usuario usuario = UsuarioFactory.newInstance();
//		
//		Mockito.when(mockValidator.validate(usuario)).thenReturn(getValidationErrors(1));
//		
//		clienteService.create(usuario);
//		Mockito.verify(mockValidator, Mockito.only()).validate(usuario);
//		Mockito.verify(mockClienteDao, Mockito.never()).create(usuario);
//	}
//
//	@Test
//	public void testCrearUsuarioSinErroresValidacion() {
//		clearMocks();
//		LOG.info("Test para crear un usuario sin errores de validacion.");
//		Usuario usuario = UsuarioDummyGenerator.createUsuario(null, null);
//		
//		Mockito.when(mockValidator.validate(usuario)).thenReturn(getValidationErrors(0));
//		
//		clienteService.create(usuario);
//		Mockito.verify(mockValidator, Mockito.atLeastOnce()).validate(usuario);
//		Mockito.verify(mockClienteDao, Mockito.only()).create(usuario);
//	}
//	
    @SuppressWarnings("unchecked")
    private Set<ConstraintViolation<ClientePersonaFisica>> getValidationErrorsPersonaFisica(int sizeErrors) {
        Set<ConstraintViolation<ClientePersonaFisica>> errors = new HashSet<ConstraintViolation<ClientePersonaFisica>>();
        for (int i = 0; i < sizeErrors; i++) {
            errors.add(Mockito.mock(ConstraintViolation.class));
        }

        return errors;
    }

    @SuppressWarnings("unchecked")
    private Set<ConstraintViolation<ClientePersonaMoral>> getValidationErrorsPersonaMoral(int sizeErrors) {
        Set<ConstraintViolation<ClientePersonaMoral>> errors = new HashSet<ConstraintViolation<ClientePersonaMoral>>();
        for (int i = 0; i < sizeErrors; i++) {
            errors.add(Mockito.mock(ConstraintViolation.class));
        }

        return errors;
    }

    private void clearMocks() {
        Mockito.reset(mockClienteDao, mockValidator);
    }
}

package com.entich.ezfact.test.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entich.ezfact.services.EmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:/applicationContext.xml" })
public class EmailServiceTest {
	@Autowired
	private EmailService mailingService;
	
	@Test
	public void testEmailig() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "Comprobante Fiscal Digital");
		model.put("name", "Envío de CFDi");
		model.put("message", "El presente correo electronico fue enviado de manera automatica por el sistema de facturación electrónica.");
		model.put("customMessage", "Este mensaje es capturado por el usuario.");
		
		mailingService.sendEmail("luis.cardeno@gmail.com", model, "Comprobante Fiscal Digital", "envioComprobante.vm");
	}
}

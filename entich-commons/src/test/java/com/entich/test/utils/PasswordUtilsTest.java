package com.entich.test.utils;


import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.commons.exceptions.ApplicationException;
import com.entich.utils.PasswordUtils;

public class PasswordUtilsTest {
	private static final Logger log = LoggerFactory.getLogger(PasswordUtilsTest.class);
	
	@Test
	public void emptyString() {
		log.debug("Validando al digestion de una cadena vacia.");
		Assert.assertEquals("d41d8cd98f00b204e9800998ecf8427e", PasswordUtils.md5Digest(""));
	}
	
	@Test(expected = ApplicationException.class)
	public void nullString() {
		log.debug("Validando al digestion de una cadena nula.");
		Assert.assertEquals("d41d8cd98f00b204e9800998ecf8427e", PasswordUtils.md5Digest(null));
	}
	
	@Test
	public void digestionCadenaHola() {
		log.debug("Validando al digestion de la cadena 'hola'.");
		Assert.assertEquals("4d186321c1a7f0f354b297e8914ab240", PasswordUtils.md5Digest("hola"));
	}
	
	@Test
	public void digestionCadenaHola2() {
		log.debug("Validando al digestion de la cadena 'Hola'.");
		Assert.assertEquals("f688ae26e9cfa3ba6235477831d5122e", PasswordUtils.md5Digest("Hola"));
	}
}
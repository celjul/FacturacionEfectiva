package com.entich.emisores.test.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entich.ezfact.emisores.dao.CertificadoDao;
import com.entich.ezfact.emisores.dao.EmisorDao;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Emisor;

/**
 * Pruebas unitarias para el acceso a datos de los certificados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 29/11/2013
 */
@ContextConfiguration(value = "/mysql/persistence-prod.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class CertificadoUpdateProduction {

	private static final Logger LOG = LoggerFactory
			.getLogger(CertificadoUpdateProduction.class);

	@Autowired
	private CertificadoDao certificadoDao;

	@Autowired
	private EmisorDao emisorDao;

	@Test
	public void testFile() {
		Certificado certificado = certificadoDao.read(1L);

		try {
			FileOutputStream fosCer = new FileOutputStream("certificado.cer");
			fosCer.write(certificado.getCertificado());

			FileOutputStream fosKey = new FileOutputStream("certificado.key");
			fosKey.write(certificado.getClave());

			fosKey.close();
			fosCer.close();
		} catch (IOException ex) {

		}
	}

	@Test
	public void getFiles() {
		Collection<Emisor> emisores = emisorDao.findAll();
		try {
			for (Emisor emisor : emisores) {
				Certificado certificado = certificadoDao.read(emisor);
				if (certificado != null && certificado.getCertificado() != null
						&& certificado.getClave() != null) {
					String path = "certificados" + File.separator
							+ emisor.getRfc() + File.separator;
					File pathCer = new File(path + "certificado.cer");
					File pathKey = new File(path + "certificado.key");

					if (!pathCer.getParentFile().exists()) {
						pathCer.getParentFile().mkdirs();
					}

					FileOutputStream fosCer = new FileOutputStream(pathCer);
					fosCer.write(certificado.getCertificado());

					FileOutputStream fosKey = new FileOutputStream(pathKey);
					fosKey.write(certificado.getClave());

					fosKey.close();
					fosCer.close();
				}
			}

		} catch (IOException ex) {

		}
	}

	@Test
	public void getFilesEmisores() {
		long[] ids = { 55 };

		try {
			for (long id : ids) {
				Emisor emisor = emisorDao.read(id);

				Certificado certificado = certificadoDao.read(emisor);
				if (certificado != null && certificado.getCertificado() != null
						&& certificado.getClave() != null) {
					String path = "certificados" + File.separator
							+ emisor.getRfc() + File.separator;
					File pathCer = new File(path + "certificado.cer");
					File pathKey = new File(path + "certificado.key");

					if (!pathCer.getParentFile().exists()) {
						pathCer.getParentFile().mkdirs();
					}

					FileOutputStream fosCer = new FileOutputStream(pathCer);
					fosCer.write(certificado.getCertificado());

					FileOutputStream fosKey = new FileOutputStream(pathKey);
					fosKey.write(certificado.getClave());

					fosKey.close();
					fosCer.close();
				}
			}
		} catch (IOException ex) {

		}
	}

	@Test
	public void updateCer() {
		String[] rfcs = new String[] { "PMC130213SF8" };

		long[] ids = { 55 };
		int i = 0;
		for (String rfc : rfcs) {
			String path = "certificados" + File.separator + rfc
					+ File.separator;
			File pathPfx = new File(path + "certificado.pfx");
			LOG.debug("Ruta del PFX: " + pathPfx.getAbsolutePath());

			Certificado certificado = certificadoDao.read(ids[i++]);
			try {
				certificado.setPfx(FileUtils.readFileToByteArray(pathPfx));
			} catch (IOException ex) {
				LOG.debug("Error al actualizar el archivo.", ex);
			}

			certificadoDao.update(certificado);
			LOG.debug("Termino de actualizar el certificado.");
		}
	}
}
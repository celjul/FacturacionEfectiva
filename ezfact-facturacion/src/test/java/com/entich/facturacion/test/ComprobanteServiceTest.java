package com.entich.facturacion.test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.impl.util.Base64;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entich.ezfact.emisores.factory.EmisorFactory;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.model.EmisorMoral;
import com.entich.ezfact.facturacion.model.Comprobante;
import com.entich.ezfact.facturacion.service.ComprobanteService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/persistence-facturacion.xml",
		"/service-facturacion.xml", "/datasource.xml" })
// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComprobanteServiceTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ComprobanteServiceTest.class);

	@Autowired
	private ComprobanteService remisionService;

	//
	// @Test
	// public void createComprobante() {
	// remisionService.generarCFDi(4);
	// }
	//
	// // @Ignore
	@Test
	public void generarRepresentacionImpresa() {
		String root = "C:\\Users\\BST MEXICO02\\Desktop\\Nueva carpeta\\";
		String file = "PSP9609113S8_44";
		Long id = 44L;
		File xmlFile = new File(String.format("%s%s.xml", root, file));
		File qrCode = new File(String.format("%s\\QR\\%s.png", root, file));
		File pdfFile = remisionService.generarRepresentacionImpresa(xmlFile,
				qrCode, remisionService.read(id));
		Assert.assertNotNull(pdfFile);
	}

	@Test
	public void update() throws IOException {
		long id = 11226;
		Comprobante comprobante = remisionService.read(id);
		File pdf = new File(
				"D:\\EntiCH\\ezfact\\facturas\\SSP0605301E8\\GAPJ730923QY4_11226.pdf");
		byte[] bytes = FileUtils.readFileToByteArray(pdf);
		comprobante.setRepresentacionImpresa(bytes);

		remisionService.update(comprobante);
	}

	@Test
	public void compile() {
		File root = new File(
				"D:\\EntiCH\\ezfact\\pos-entich\\ezfact-facturacion\\plantillas");

		for (File file : root.listFiles()) {
			if (!file.getName().equals("CAOL860818R04")) {
				try {
					for (File plantilla : FileUtils.listFiles(file,
							new String[] { "jrxml" }, false)) {
						LOGGER.debug("Procesando el archivo: "
								+ plantilla.getAbsolutePath());
						JasperCompileManager.compileReportToFile(plantilla
								.getAbsolutePath());
						LOGGER.debug("Creando el archivo: "
								+ JasperCompileManager
										.compileReportToFile(plantilla
												.getAbsolutePath()));
					}
				} catch (JRException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void updatePlantillas() {
		File root = new File(
				"D:\\EntiCH\\ezfact\\pos-entich\\ezfact-facturacion\\plantillas");

		for (File file : root.listFiles()) {
			if (!file.getName().equals("CAOL860818R04")) {
				try {
					for (File plantilla : FileUtils.listFiles(file,
							new String[] { "jrxml" }, false)) {
						LOGGER.debug("Procesando el archivo: "
								+ plantilla.getAbsolutePath());
						String content = FileUtils.readFileToString(plantilla);
						content = content.replaceAll(
								"com\\.portomx\\.translate\\.currency",
								"com\\.lankorlab\\.traslate\\.currency");
						content = content.replaceAll(
								"com\\.entich\\.facturacion\\.model",
								"com\\.entich\\.ezfact\\.facturacion");

						FileUtils.write(plantilla, content);
						LOGGER.debug("Creando el archivo: "
								+ JasperCompileManager
										.compileReportToFile(plantilla
												.getAbsolutePath()));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void testCadenaOriginal() {
		String path = "D:\\EntiCH\\ezfact\\pos-entich\\facturacion-web\\target\\";

		path += "TEOT850712G33_2.xml";
		LOGGER.info(remisionService.getCadenaOriginal(path));
		LOGGER.info(new String(
				Base64.decode("fHwzLjJ8MjAxNS0wOC0yOVQxMjo0Mzo0OS41ODktMDY6MDB8aW5ncmVzb3xFbiB1bmEgc29sYSBleGhpYmljafNufElubWVkaWF0b3wxLjAwfFBlc28gTWV4aWNhbm98MS4xNnxObyBJZGVudGlmaWNhZG98Tel4aWNvIEQuIEYufG5vIGFwbGljYXxDQU9MODYwODE4UjU2fEx1Y+1hIENhc3RybyBPcnRlZ2F8SnXhcmV6fDkzNXxDZW50cm98VHVsYSBkZSBBbGxlbmRlfEhpZGFsZ298Tel4aWNvfDQyODAwfFBlcnNvbmEgZu1zaWNhfFhBWFgwMTAxMDEwMDB8UHVibGljbyBnZW5lcmFsfE5vIGFwbGljYXxObyBhcGxpY2F8Tm8gYXBsaWNhfE1vcmVsb3N8Q3VhdWh06W1vY3xEaXN0cml0byBGZWRlcmFsfE3peGljb3wwNjIwMHwxLjB8UGllemF8MDAwMDAwMDAxfEFydGljdWxvIGRlIHBydWViYXwxLjAwfDEuMDB8SVZBfDE2LjAwfDAuMTZ8MC4xNnx8"
						.getBytes())));
	}
	// @Ignore
	// @Test
	// public void findAll() {
	// Emisor emisor = EmisorFactory.newInstance(EmisorMoral.class, 1L);
	// Collection<Comprobante> comprobantes = remisionService.findAll(emisor);
	// Assert.assertNotEquals(0, comprobantes.size());
	// }
}

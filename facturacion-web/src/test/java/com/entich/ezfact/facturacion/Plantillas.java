package com.entich.ezfact.facturacion;

import java.io.File;
import java.io.IOException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.ezfact.facturacion.model.Comprobante;


public class Plantillas {
	private static final Logger LOGGER = LoggerFactory.getLogger(Plantillas.class);
	
	@Test
		public void compilePlantillas() {
			File root = new File("F:\\BST\\pos-entich\\facturacion-web\\plantillas");
			
			for (File file : root.listFiles()) {
				if (!file.getName().equals("CAOL860818R04") && file.isDirectory()) {
					try {
						for (File plantilla : FileUtils.listFiles(file, new String[] {"jrxml"}, false)) {
							LOGGER.debug("Procesando el archivo: " + plantilla.getAbsolutePath());
							
	//						String content = FileUtils.readFileToString(plantilla);
	//						content = content.replaceAll("isBlankWhenNull=\\\"false\\\"", "isBlankWhenNull=\\\"true\\\"");
	//						content = content.replaceAll("com\\.portomx\\.translate\\.currency", "com\\.lankorlab\\.traslate\\.currency");
	//						content = content.replaceAll("com\\.entich\\.facturacion\\.model", "com\\.entich\\.ezfact\\.facturacion\\.model");
	//						
	//						content = content.replaceAll("<textField isBlankWhenNull=\\\"true\\\">", "<textField isStretchWithOverflow=\\\"true\\\" isBlankWhenNull=\\\"true\\\">");
	//						
	//						FileUtils.write(plantilla, content);
							
							LOGGER.debug("Creando el archivo: " + JasperCompileManager.compileReportToFile(plantilla.getAbsolutePath()));
						}
				} catch (JRException e) {
						e.printStackTrace();
					}
				}
			}
		}
	
	@Test
	public void updatePlantillas() {
		File root = new File("D:\\EntiCH\\ezfact\\pos-entich\\ezfact-facturacion\\plantillas");
		for (File file : root.listFiles()) {
			if (!file.getName().equals("CAOL860818R04") && file.isDirectory()) {
				try {
					for (File plantilla : FileUtils.listFiles(file, new String[] {"jrxml"}, false)) {
						LOGGER.debug("Procesando el archivo: " + plantilla.getAbsolutePath());
						String content = FileUtils.readFileToString(plantilla);
						content = content.replaceAll("isBlankWhenNull=\\\"false\\\"", "isBlankWhenNull=\\\"true\\\"");
						content = content.replaceAll("com\\.portomx\\.translate\\.currency", "com\\.lankorlab\\.traslate\\.currency");
						content = content.replaceAll("com\\.entich\\.facturacion\\.model", "com\\.entich\\.ezfact\\.facturacion\\.model");
						
						content = content.replaceAll("<textField isBlankWhenNull=\\\"true\\\">", "<textField isStretchWithOverflow=\\\"true\\\" isBlankWhenNull=\\\"true\\\">");
						
						FileUtils.write(plantilla, content);
						LOGGER.debug("Creando el archivo: " + JasperCompileManager.compileReportToFile(plantilla.getAbsolutePath()));
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
	public void updateUna() {
		File file = new File("C:\\Users\\LuisAngel\\JaspersoftWorkspace\\MyReports\\Tejon");
		
		try {
			for (File plantilla : FileUtils.listFiles(file, new String[] {"jrxml"}, false)) {
//				String content = FileUtils.readFileToString(plantilla);
//				content = content.replaceAll("isBlankWhenNull=\\\"false\\\"", "isBlankWhenNull=\\\"true\\\"");
//				content = content.replaceAll("com\\.portomx\\.translate\\.currency", "com\\.lankorlab\\.traslate\\.currency");
//				content = content.replaceAll("com\\.entich\\.facturacion\\.model", "com\\.entich\\.ezfact\\.facturacion\\.model");
//				
//				content = content.replaceAll("<textField isBlankWhenNull=\\\"true\\\">", "<textField isStretchWithOverflow=\\\"true\\\" isBlankWhenNull=\\\"true\\\">");
//				
//				FileUtils.write(plantilla, content);
				LOGGER.debug("Creando el archivo: " + JasperCompileManager.compileReportToFile(plantilla.getAbsolutePath()));
			}
//		} catch (IOException e) {
//			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}

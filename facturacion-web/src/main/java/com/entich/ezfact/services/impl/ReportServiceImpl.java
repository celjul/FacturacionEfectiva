package com.entich.ezfact.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entich.ezfact.services.ReporteService;

@Service("reporteService")
public class ReportServiceImpl implements ReporteService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private DataSource dataSource;

	@Override
	public byte[] getReporte(JasperReport jd, String format,
			Map<String, Object> parametros) {
		LOGGER.debug("Iniciando el proceso para llenar el reporte.");
		JasperPrint jp;
		Connection con;
		byte[] report = null;
		try {
			LOGGER.debug("Recuperando conexion");
			con = dataSource.getConnection();
			jp = JasperFillManager.fillReport(jd, parametros, con);
			
			switch (format.toLowerCase()) {
			case PDF:
				LOGGER.debug("Generando reporte en formato PDF.");
				report = JasperExportManager.exportReportToPdf(jp);
				break;

			case XLS:
				LOGGER.debug("Generando reporte en formato XLS.");
				JRXlsxExporter xlsxExporter = new JRXlsxExporter();
				List<JasperPrint> jpList = new ArrayList<JasperPrint>();
				jpList.add(jp);
				
				
				xlsxExporter.setExporterInput(SimpleExporterInput.getInstance(jpList));
				File temp = File.createTempFile("tempReport", "xlsx");
				
				xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(temp));
				
				SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
				
				config.setAutoFitPageHeight(true);
				config.setDetectCellType(true);
				config.setFontSizeFixEnabled(true);
				config.setWhitePageBackground(false);
				config.setRemoveEmptySpaceBetweenColumns(true);
				config.setRemoveEmptySpaceBetweenRows(true);
				
				xlsxExporter.setConfiguration(config);
				
				xlsxExporter.exportReport();
				
				report = FileUtils.readFileToByteArray(temp);
				temp.delete();
				
				break;
			}
		} catch (JRException ex) {
			LOGGER.warn("Error para generar el reporte.", ex);
		} catch (SQLException ex) {
			LOGGER.warn("Error al recuperar una conexion para el reporte.", ex);
		} catch (IOException ex) {
			LOGGER.warn("Error de entrada-salida.", ex);
		} finally {
			
		}
		
		return report;
	}

}

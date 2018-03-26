package com.entich.ezfact.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entich.ezfact.services.ReporteService;

@Controller
@RequestMapping("app/reportes/")
public class ReporteController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReporteController.class);
	
	@Autowired
	private ReporteService reporteService;
	
	@RequestMapping(method = RequestMethod.GET, value = "{reporte}/format/{format}")
	public ResponseEntity<byte[]> getPDF(@PathVariable String reporte, 
			@PathVariable String format, HttpServletRequest request) {
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = null;
		
		InputStream stream = null;
		String filename = reporte;
		try {
			stream = getClass().getResourceAsStream("/reportes/" + reporte + ".jasper");
			JasperReport jd = ( JasperReport) JRLoader.loadObject(stream);
			
			headers.setExpires(0);
			headers.setPragma("no-cache");
			
			switch (format) {
			case ReporteService.PDF:
				headers.setContentType(MediaType.parseMediaType("application/pdf"));
				filename += ".pdf";
				headers.setContentDispositionFormData(filename, filename);
				break;
			
			case ReporteService.XLS:
				headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
				filename += ".xlsx";
				headers.setContentDispositionFormData(filename, filename);
				break;

			default:
				headers.setContentType(MediaType.parseMediaType("application/pdf"));
				filename += ".pdf";
				headers.setContentDispositionFormData(filename, filename);
				break;
			}
			
			response = new ResponseEntity<byte[]>(reporteService.getReporte(jd, format, getParametros(jd, request)),
					headers, HttpStatus.OK);
		} catch (JRException ex) {
			LOGGER.warn("Error al generar el reporte.", ex);
		} finally {
			close(stream);
		}
		
		return response;
	}
	
	private void close(InputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException ex) {
				LOGGER.warn("Error al cerrar el Stream del reporte", ex);
			}
		}
	}
	
	private Map<String, Object> getParametros(JasperReport jd,
			HttpServletRequest request) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		try {
			
			for(JRParameter param : jd.getParameters()) {
				LOGGER.debug("Recuperando el parametro " + param.getName());
				if (request.getParameterMap().containsKey(param.getName())) {
					if (param.getValueClass().isAssignableFrom(Integer.class)) {
						LOGGER.warn(String.format("Tratando de convertir en int el valor: %s",
								request.getParameter(param.getName())));
						parametros.put(param.getName(), Integer.valueOf(request.getParameter(param.getName()), 10));
					} else if (param.getValueClass().isAssignableFrom(Long.class)) {
						LOGGER.warn(String.format("Tratando de convertir en long el valor: %s",
								request.getParameter(param.getName())));
						parametros.put(param.getName(), Long.valueOf(request.getParameter(param.getName()), 10));
					} else if (param.getValueClass().isAssignableFrom(Date.class)) {
						LOGGER.warn(String.format("Tratando de convertir en date el valor: %s",
								request.getParameter(param.getName())));
						parametros.put(param.getName(), getDate(request.getParameter(param.getName()), "dd-MM-yyyy"));
					} else if (param.getValueClass().isAssignableFrom(BigDecimal.class)) {
						LOGGER.warn(String.format("Tratando de convertir en BigDecimal el valor: %s",
								request.getParameter(param.getName())));
						parametros.put(param.getName(), new BigDecimal(request.getParameter(param.getName())));
					}
						
				}
			}
		} catch (Throwable ex) {
			LOGGER.warn("No se qe chingados paso.", ex);
		}
		
		return parametros;
	}
	
	private Date getDate(String parameter, String string) {
		SimpleDateFormat format = new SimpleDateFormat(string);
		
		try {
			return format.parse(parameter);			
		} catch (ParseException ex) {
			LOGGER.warn("No se puede convertir la fecha con el formato especificado.", ex);
		}
		
		return null;
	}

}

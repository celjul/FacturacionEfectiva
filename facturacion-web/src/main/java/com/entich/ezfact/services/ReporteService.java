package com.entich.ezfact.services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JasperReport;

public interface ReporteService {
	public static final String PDF = "pdf";
	public static final String XLS = "xls";
	public static final String DEFAULT = PDF;
	
//	byte[] getReporte(String reporte, String format, HttpServletRequest request);

	byte[] getReporte(JasperReport jd, String format,
			Map<String, Object> parametros);
}

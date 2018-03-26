package com.entich.ezfact.web.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.entich.ezfact.facturacion.model.Comprobante;
import com.entich.ezfact.facturacion.service.ComprobanteService;
import com.entich.ezfact.web.dto.CancelacionMasivaDto;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 18-12-2017
 * @version 1.0
 *
 */
@Controller
@RequestMapping("app/cancelacion/masiva")
public class CancelacionMasivaController {

	private static final Logger LOG = LoggerFactory.getLogger(CancelacionMasivaController.class);
	private static final String PATTERN = "[a-f0-9A-F]{8}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{12}";

	@Autowired
	private ComprobanteService comprobanteService;
	
	private final SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String init() {
		return "cancelaciones/masiva/upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, Model model, HttpSession session) {
		
		Set<String> uuids = new HashSet<>();
		Set<CancelacionMasivaDto> facturasCanceladas = new HashSet<>();

		CSVReader csv = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = new ByteArrayInputStream(file.getBytes());
			br = new BufferedReader(new InputStreamReader(is));
			csv = new CSVReader(br, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, 1);
			String[] line;
			while ((line = csv.readNext()) != null) {
				try {
					String uuid = line[0].trim();
					uuids.add(uuid);
				} catch (Exception e) {
					continue;
				}
			}
		} catch (IOException e) {
			LOG.warn("Error al subir la cancelación masiva ", e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(csv);
		}

		for (String uuid : uuids) {
			CancelacionMasivaDto cancelacion = new CancelacionMasivaDto(true);
			try {
				cancelacion.setUuid(uuid);
				if (!uuid.matches(PATTERN)) {
					cancelacion.setExitosa(false);
					cancelacion.setObservaciones("El UUID no es válido.");
					facturasCanceladas.add(cancelacion);
					continue;
				}
				Comprobante comprobante = comprobanteService.get(uuid);
				if (comprobante == null) {
					cancelacion.setExitosa(false);
					cancelacion.setObservaciones("El UUID no existe en la base de datos.");
					facturasCanceladas.add(cancelacion);
					continue;
				}
				cancelacion.setId(comprobante.getId());
				comprobanteService.cancelar(comprobante);
			} catch (Exception e) {
				LOG.warn("Error en factura masiva ", e);
				cancelacion.setExitosa(false);
				cancelacion.setObservaciones(e.getMessage());
			}
			facturasCanceladas.add(cancelacion);
		}

		model.addAttribute("cancelaciones", facturasCanceladas);
		return "cancelaciones/masiva/result";
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ResponseEntity<byte[]> download(@RequestParam("ids") List<Long> ids) {
		ResponseEntity<byte[]> response = null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(baos);

			for (Long id : ids) {
				Comprobante comprobante = comprobanteService.read(id);
				byte[] pdf = comprobante.getAcuseCancelacion();
				String name = getName(comprobante);

				ZipEntry entryPdf = new ZipEntry(name + ".pdf");
				entryPdf.setSize(pdf.length);
				zos.putNextEntry(entryPdf);
				zos.write(pdf);
				zos.closeEntry();
			}
		} catch (Exception e) {
			LOG.warn("Error en descarga de cancelación masiva ", e);
		} finally {
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(zos);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.setContentType(MediaType.parseMediaType("application/zip"));
		String name = "cancelacion-masiva-efectiva.zip";
		headers.setContentDispositionFormData(name, name);
		response = new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);

		return response;
	}
	
	

	private String getName(Comprobante comprobante) {
		String name = null;
		if (comprobante.getFolioMasivo() != null) {
			name = comprobante.getFolioMasivo() + "_" + comprobante.getUuid();
		} else {
			name = comprobante.getCliente().getRfc() + "_"
					+ ((comprobante.getSerie() != null) ? comprobante.getSerie().getNombre() + comprobante.getFolio()
							: formater.format(comprobante.getFechaTimbrado()));
		}
		return name;
	}
}

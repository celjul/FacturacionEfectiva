package com.entich.ezfact.facturacion.service;

import java.io.File;

/**
 * @author Luis Ángel Cárdenas  luis.cardeno@gmail.com
 * @version 1.0
 *
 */
public interface QRService {
	/**
	 *
	 * @param text
	 * @param path
	 * @return
	 */
	public void generarQRCode(String text, File path);

	public void guardarQRCode(byte[] qrCode, File path);
}

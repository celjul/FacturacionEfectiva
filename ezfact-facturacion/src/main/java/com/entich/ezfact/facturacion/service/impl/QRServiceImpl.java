/**
 *
 */
package com.entich.ezfact.facturacion.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.facturacion.service.QRService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * @author Luis Ángel Cárdenas luis.cardeno@gmail.com
 * @version 1.0 20/12/2012
 *
 */
@Service
public class QRServiceImpl implements QRService {
    private static final Logger LOG = LoggerFactory.getLogger(QRServiceImpl.class);

    private static final int WIDTH = 256;
    private static final int HEIGHT = WIDTH;

    /**
     * @param text
     * @param path
     * @return
     */
    public void generarQRCode(String text, File path) {
        BitMatrix matriz = null;
        Writer writer = new QRCodeWriter();
        try {
            String datos = new String(text.getBytes(), "UTF-8");
            Map<EncodeHintType, Object> parametros = new HashMap<EncodeHintType, Object>();
            parametros.put(EncodeHintType.MARGIN, 1);

            matriz = writer.encode(datos, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, parametros);

            if (!path.exists()) {
                path.mkdirs();
            }

            MatrixToImageWriter.writeToFile(matriz, "png", path);
        } catch (UnsupportedEncodingException ex) {
            String message = "Error al obtener el QRCode del comprobante fiscal.";
            LOG.info(message, ex);
            throw new ServiceException(message, ex);
        } catch (WriterException ex) {
            String message = "Error al obtener el QRCode del comprobante fiscal.";
            LOG.info(message, ex);
            throw new ServiceException(message, ex);
        } catch (IOException ex) {
            String message = "Error al obtener el QRCode del comprobante fiscal.";
            LOG.info(message, ex);
            throw new ServiceException(message, ex);
        }
    }

    @Override
    public void guardarQRCode(byte[] qrCode, File path) {
        // BufferedImage img = ImageIO.read(new ByteArrayInputStream((byte[])
        // array));
        OutputStream out = null;
        try {
            if(!path.getParentFile().exists()) {
                path.getParentFile().mkdirs();
            }
            if(!path.exists()) {
                path.createNewFile();
            }

            out = new BufferedOutputStream(new FileOutputStream(path));
            out.write( qrCode);

        } catch (IOException ex) {
            LOG.error("Se ha producido un error en la creación de la imagen", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    LOG.error("Se ha producido un error cerrar el flujo", ex);
                }
            }

        }
    }
}

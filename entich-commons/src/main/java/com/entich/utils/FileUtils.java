/**
 * 
 */
package com.entich.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.commons.exceptions.service.ServiceException;



/**
 * 
 * @author Luis Ángel Cárdenas 
 * @version 1.0 30/10/2012
 *
 */
public class FileUtils {
	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
	
	/**
	 * 
	 * @param source
	 * @param target
	 */
	public static void moveFile(File source, File target) {
		moveFile(source, target, false);
	}
	
	/**
	 * 
	 * @param source
	 * @param target
	 * @param remove 
	 */
	public static void moveFile(File source, File target, boolean remove) {
		FileInputStream fileInput = null;
		BufferedInputStream bufferedInput = null;
		
		FileOutputStream fileOutput = null;
		BufferedOutputStream bufferedOutput = null;
		try {
			fileInput = new FileInputStream(source);
			bufferedInput = new BufferedInputStream(fileInput);
			
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
			}
			
			fileOutput = new FileOutputStream (target);
			bufferedOutput = new BufferedOutputStream(fileOutput);
			
			byte [] array = new byte[1000];
			int leidos = bufferedInput.read(array);
			
			while (leidos > 0) {
				bufferedOutput.write(array,0,leidos);
				leidos = bufferedInput.read(array);
			}
			
			if (remove) {
				source.delete();
			}
		} catch (FileNotFoundException ex) {
			String message ="Error al mover los archivos."; 
			log.warn(message, ex);
			throw new ServiceException(message, ex);
		} catch (IOException ex) {
			String message ="Error al mover los archivos."; 
			log.warn(message, ex);
			throw new ServiceException(message, ex);
		} finally {
			close(bufferedInput);
			close(fileInput);
			close(bufferedOutput);
			close(fileOutput);
		}
	}

	/**
	 * @param fileOutput
	 */
	private static void close(FileOutputStream fileOutput) {
		try {
			fileOutput.flush();
			fileOutput.close();
		} catch (FileNotFoundException ex) {
			log.warn("Error al intentar cerrar el flujo de salida.", ex);
		} catch (IOException ex) {
			log.warn("Error al intentar cerrar el flujo de salida.", ex);
		}
	}

	/**
	 * @param fileInput
	 */
	private static void close(FileInputStream fileInput) {
		try {
			fileInput.close();
		} catch (FileNotFoundException ex) {
			log.warn("Error al intentar cerrar el flujo de salida.", ex);
		} catch (IOException ex) {
			log.warn("Error al intentar cerrar el flujo de salida.", ex);
		}
	}

	/**
	 * @param bufferedOutput
	 */
	private static void close(BufferedOutputStream bufferedOutput) {
		try {
			bufferedOutput.flush();
			bufferedOutput.close();
		} catch (FileNotFoundException ex) {
			log.warn("Error al intentar cerrar el flujo de salida.", ex);
		} catch (IOException ex) {
			log.warn("Error al intentar cerrar el flujo de salida.", ex);
		}
	}

	/**
	 * @param bufferedInput
	 */
	private static void close(BufferedInputStream bufferedInput) {
		try {
			bufferedInput.close();
		} catch (FileNotFoundException ex) {
			log.warn("Error al intentar cerrar el flujo de entrada.", ex);
		} catch (IOException ex) {
			log.warn("Error al intentar cerrar el flujo de entrada.", ex);
		}
	}

	/**
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static void zipFile(File source, File target) {
		FileOutputStream streamOutput = null;
        ZipOutputStream zipOutput = null;
        ZipEntry zipEntry = null;
        InputStream inputStream = null;
        try {
        	streamOutput = new FileOutputStream(target);
            
            zipOutput = new ZipOutputStream(streamOutput);
            inputStream = new FileInputStream(source);

            zipEntry = new ZipEntry(source.getName());
            zipOutput.putNextEntry(zipEntry);

            int len = 0;
            byte[] b = new byte[512];
            while ((len = inputStream.read(b)) != -1) {
                zipOutput.write(b, 0, len);
            }
            
        } catch (FileNotFoundException ex) {
        	String message = "No se pudo comprimir el archivo.";
        	log.info(message, ex);
            throw new ServiceException(message, ex);
        } catch (IOException ex) {
        	String message = "No se pudo comprimir el archivo.";
        	log.info(message, ex);
        } finally {
        	close(zipOutput);
            close(streamOutput);
            close(inputStream);
        }
	}

	
	/**
	 * 
	 * @param fileZip
	 * @param xmlFile
	 * @return
	 */
	public static File unzipFile(File fileZip, File xmlFile) {
		final int BUFFER = 1024;
//		File xmlFile = null;
		BufferedOutputStream dest = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        ZipInputStream zis = null;
        @SuppressWarnings("unused")
		ZipEntry entry = null;
        
        try {
        	fis = new FileInputStream(fileZip);
            zis = new ZipInputStream(new BufferedInputStream(fis));
            while ((entry = zis.getNextEntry()) != null) {
                byte data[] = new byte[BUFFER];
                
                if (!xmlFile.getParentFile().exists()) {
                	xmlFile.getParentFile();
                }
                
                if (!xmlFile.exists()) {
                	xmlFile.createNewFile();
                }
                
                fos = new FileOutputStream(xmlFile);
                dest = new BufferedOutputStream(fos, BUFFER);
                int count;
                while ((count = zis.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, count);
                }
                
            }
        } catch (FileNotFoundException ex) {
        	String message = "Error al descomprimir el archivo.";
        	log.warn(message, ex);
        	throw new ServiceException(message, ex);
        } catch (IOException ex) {
        	String message = "Error al descomprimir el archivo.";
        	log.warn(message, ex);
        	throw new ServiceException(message, ex);
        } finally {
        	close(dest);
        	close(fis);
        	close(fos);
        	close(zis);
        }
        
		return xmlFile;
	}
	
	/**
	 * @param inputStream
	 */
	private static void close(InputStream inputStream) {
		try {
			inputStream.close();
		} catch (FileNotFoundException ex) {
			String message = "Error al cerrar los flujos de salida del zip.";
			log.warn(message, ex);
			throw new ServiceException(message, ex);
		} catch (IOException ex) {
			String message = "Error al cerrar los flujos de salida del zip.";
			log.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	/**
	 * @param zipOutput
	 */
	private static void close(ZipOutputStream zipOutput) {
		try {
			zipOutput.flush();
			zipOutput.closeEntry();
            zipOutput.close();
		} catch (FileNotFoundException ex) {
			String message = "Error al cerrar los flujos de salida del zip.";
			log.warn(message, ex);
			throw new ServiceException(message, ex);
		} catch (IOException ex) {
			String message = "Error al cerrar los flujos de salida del zip.";
			log.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	/**
	 * @param file
	 * @param bytes
	 */
	public static void save(File file, byte[] bytes) {
		FileOutputStream fileBytes = null;
		
		try {
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
            }
			file.createNewFile();
			
			fileBytes = new FileOutputStream(file);
			fileBytes.write(bytes);
		} catch (FileNotFoundException ex) {
			String message = "Error al guardar el fujo de bytes.";
			log.warn(message);
			throw new ServiceException(message, ex);
		} catch (IOException ex) {
			String message = "Error al guardar el fujo de bytes.";
			log.warn(message);
			throw new ServiceException(message, ex);
		} finally {
			close(fileBytes);
		}
	}
}

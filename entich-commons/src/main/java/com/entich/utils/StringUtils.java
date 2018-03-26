package com.entich.utils;

/**
 * Utileria para trabajar con cadenas.
 * 
 * Usar los metodos de la libreria de cadenas de commons-util
 * @author Luis Ángel Cárdenas
 * @version 1.0.0
 * 
 */
@Deprecated
public class StringUtils {
	/**
	 * Evalua si la cadena pasada como parametro es nula o vacia.
	 * 
	 * @param string
	 *            Cadena que sera evaluanda.
	 * @return <code>true</code> si la cadena es <code>null</code>,
	 *         <code>false</code> en caso contrario.
	 *         {@link #isReallyEmptyOrNull(String)}
	 */
	@Deprecated
	public static final boolean isEmptyOrNull(String string) {
		return string == null ? true : string.isEmpty();
	}

	/**
	 * Evalua si la cadena pasada como parametro es realmente nula o vacia. Una
	 * cadena es realmente vacia si despues de ejecutar el metodo
	 * <code>trim</code> el resultado es "".
	 * 
	 * @param string
	 *            Cadena que sera evaluanda.
	 * @return <code>true</code> si la cadena es <code>null</code>,
	 *         <code>false</code> en caso contrario.
	 * 
	 *         {@link #isEmptyOrNull(String)} {@link String#trim()}
	 *         {@link String#isEmpty()}
	 */
	@Deprecated
	public static final boolean isReallyEmptyOrNull(String string) {
		return string == null ? true : string.trim().isEmpty();
	}

	/**
	 * Método que parsea una cadena en hexadecimal. 
	 * 
	 * @param hex
	 *            cadena evaluada en hexadecimal
	 * @return cadena en {@link String}
	 */
	@Deprecated
	public static String parseHexToString(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < hex.length() - 1; i += 2) {
			String output = hex.substring(i, (i + 2));
			int decimal = Integer.parseInt(output, 16);
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}
}

package com.entich.ezfact.model;

import org.springframework.core.io.InputStreamSource;

public class Adjunto {
	private String nombre;
	private InputStreamSource source;
	
	public Adjunto(String nombre, InputStreamSource source) {
		this.nombre = nombre;
		this.source = source;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public InputStreamSource getSource() {
		return source;
	}
	
	public void setSource(InputStreamSource source) {
		this.source = source;
	}
}

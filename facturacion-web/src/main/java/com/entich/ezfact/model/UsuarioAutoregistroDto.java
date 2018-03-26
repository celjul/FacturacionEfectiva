package com.entich.ezfact.model;

import java.io.Serializable;

import com.entich.ezfact.usuarios.model.Usuario;

public class UsuarioAutoregistroDto extends Usuario implements Serializable {

	private static final long serialVersionUID = 4394874315207407888L;
	
	private String challengeField;
	
	private String responseField;

	public String getChallengeField() {
		return challengeField;
	}

	public void setChallengeField(String challengeField) {
		this.challengeField = challengeField;
	}

	public String getResponseField() {
		return responseField;
	}

	public void setResponseField(String responseField) {
		this.responseField = responseField;
	}
	
}

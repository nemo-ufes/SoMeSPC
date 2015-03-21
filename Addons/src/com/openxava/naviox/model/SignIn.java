package com.openxava.naviox.model;

import javax.persistence.*;
import org.openxava.annotations.*;

/**
 * 
 * @author Javier Paniza 
 */

public class SignIn {
	
	
	@Column(length=30) @LabelFormat(LabelFormatType.SMALL)
	private String usuario;

	@Column(length=30) @Stereotype("PASSWORD")
	@LabelFormat(LabelFormatType.SMALL)
	private String senha;
	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String password) {
		this.senha = password;
	}

	public void setUsuario(String user) {
		this.usuario = user;
	}

	public String getUsuario() {
		return usuario;
	}
	
}

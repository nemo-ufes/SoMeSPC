package org.somespc.integracao.taiga.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tarefa {

	@XmlElement(name = "subject")
	private String nome;

	@XmlElement(name = "is_closed")
	private boolean fechada;

	@XmlElement(name = "is_iocaine")
	private boolean iocaine;

	@XmlElement(name = "owner")
	private int idDono;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isFechada() {
		return fechada;
	}

	public void setFechada(boolean fechada) {
		this.fechada = fechada;
	}

	public boolean isIocaine() {
		return iocaine;
	}

	public void setIocaine(boolean iocaine) {
		this.iocaine = iocaine;
	}

	public int getIdDono() {
		return idDono;
	}

	public void setIdDono(int idDono) {
		this.idDono = idDono;
	}

}

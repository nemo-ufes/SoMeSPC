package org.somespc.integracao.taiga.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Estoria {

	@XmlElement(name = "subject")
	private String titulo;

	@XmlElement(name = "description")
	private String descricao;
	
	@XmlElement(name = "is_closed")
	private boolean fechada;
	
	@XmlElement(name = "owner")
	private int idDono;

	@XmlElement(name = "total_points")
	private Double totalPontos;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isFechada() {
		return fechada;
	}

	public void setFechada(boolean fechada) {
		this.fechada = fechada;
	}

	public int getIdDono() {
		return idDono;
	}

	public void setIdDono(int idDono) {
		this.idDono = idDono;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getTotalPontos() {
		return totalPontos;
	}

	public void setTotalPontos(Double totalPontos) {
		this.totalPontos = totalPontos;
	}
}

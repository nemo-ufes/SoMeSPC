package org.medcep.webservices.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanoDTO {

	@XmlElement(name = "nome_Projeto")
	private String nomeProjeto;
	
	@XmlElement(name = "nome_Periodicidade")
	private String nomePeriodicidade;
	
	@XmlElement(name = "nome_Medidas")
	private List<String> nomesMedidas;

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public String getNomePeriodicidade() {
		return nomePeriodicidade;
	}

	public void setNomePeriodicidade(String nomePeriodicidade) {
		this.nomePeriodicidade = nomePeriodicidade;
	}

	public List<String> getNomesMedidas() {
		return nomesMedidas;
	}

	public void setNomesMedidas(List<String> nomesMedidas) {
		this.nomesMedidas = nomesMedidas;
	}
	
	
}

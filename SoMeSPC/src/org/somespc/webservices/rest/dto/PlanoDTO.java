package org.somespc.webservices.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanoDTO {

	@XmlElement(name = "taiga_Login")
	private TaigaLoginDTO taigaLogin;

	@XmlElement(name = "projetos_Taiga")
	private List<String> projetosTaiga;

	@XmlElement(name = "sonar_Login")
	private SonarLoginDTO sonarLogin;

	@XmlElement(name = "projetos_Sonar")
	private List<String> projetosSonar;

	@XmlElement(name = "nome_Periodicidade")
	private String nomePeriodicidade;

	@XmlElement(name = "nome_Itens")
	private List<ItemPlanoDeMedicaoDTO> itensPlanoDeMedicao;

	public TaigaLoginDTO getTaigaLogin() {
		return taigaLogin;
	}

	public void setTaigaLogin(TaigaLoginDTO taigaLogin) {
		this.taigaLogin = taigaLogin;
	}

	public List<String> getProjetosTaiga() {
		return projetosTaiga;
	}

	public void setProjetosTaiga(List<String> projetosTaiga) {
		this.projetosTaiga = projetosTaiga;
	}

	public SonarLoginDTO getSonarLogin() {
		return sonarLogin;
	}

	public void setSonarLogin(SonarLoginDTO sonarLogin) {
		this.sonarLogin = sonarLogin;
	}

	public List<String> getProjetosSonar() {
		return projetosSonar;
	}

	public void setProjetosSonar(List<String> projetosSonar) {
		this.projetosSonar = projetosSonar;
	}

	public String getNomePeriodicidade() {
		return nomePeriodicidade;
	}

	public void setNomePeriodicidade(String nomePeriodicidade) {
		this.nomePeriodicidade = nomePeriodicidade;
	}

	public List<ItemPlanoDeMedicaoDTO> getItensPlanoDeMedicao() {
		return itensPlanoDeMedicao;
	}

	public void setItensPlanoDeMedicao(List<ItemPlanoDeMedicaoDTO> itensPlanoDeMedicao) {
		this.itensPlanoDeMedicao = itensPlanoDeMedicao;
	}

}

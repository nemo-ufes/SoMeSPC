package org.somespc.integracao.taiga.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Mede dados do projeto Taiga.
 * 
 * Obtido em http://<taiga-url>/api/projects/<nome-projeto>/stats
 * 
 * @author Vinicius
 *
 */
@XmlRootElement
public class EstadoProjeto {

	@XmlElement(name = "speed")
	private float velocidade;

	@XmlElement(name = "closed_points")
	private float pontosFechados;

	@XmlElement(name = "total_milestones")
	private int totalMilestones;

	@XmlElement(name = "total_points")
	private float totalPontos;

	public float getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(float velocidade) {
		this.velocidade = velocidade;
	}

	public float getPontosFechados() {
		return pontosFechados;
	}

	public void setPontosFechados(float pontosFechados) {
		this.pontosFechados = pontosFechados;
	}

	public float getTotalPontos() {
		return totalPontos;
	}

	public void setTotalPontos(float totalPontos) {
		this.totalPontos = totalPontos;
	}

	public int getTotalMilestones() {
		return totalMilestones;
	}

	public void setTotalMilestones(int totalMilestones) {
		this.totalMilestones = totalMilestones;
	}

}

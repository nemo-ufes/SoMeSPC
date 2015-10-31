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
	private double velocidade;

	@XmlElement(name = "closed_points")
	private double pontosFechados;

	@XmlElement(name = "total_milestones")
	private int totalMilestones;

	@XmlElement(name = "total_points")
	private double totalPontos;

	public double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

	public double getPontosFechados() {
		return pontosFechados;
	}

	public void setPontosFechados(double pontosFechados) {
		this.pontosFechados = pontosFechados;
	}

	public double getTotalPontos() {
		return totalPontos;
	}

	public void setTotalPontos(double totalPontos) {
		this.totalPontos = totalPontos;
	}

	public int getTotalMilestones() {
		return totalMilestones;
	}

	public void setTotalMilestones(int totalMilestones) {
		this.totalMilestones = totalMilestones;
	}

}

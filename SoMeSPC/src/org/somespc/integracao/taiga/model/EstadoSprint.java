package org.somespc.integracao.taiga.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class Dia {

	@XmlElement(name = "day")
	private String dia;
	@XmlElement(name = "name")
	private String nome;

	@XmlElement(name = "open_points")
	private String pontosAbertos;

	@XmlElement(name = "optimal_points")
	private String pontosOtimos;

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPontosAbertos() {
		return pontosAbertos;
	}

	public void setPontosAbertos(String pontosAbertos) {
		this.pontosAbertos = pontosAbertos;
	}

	public String getPontosOtimos() {
		return pontosOtimos;
	}

	public void setPontosOtimos(String pontosOtimos) {
		this.pontosOtimos = pontosOtimos;
	}

}

@XmlRootElement
public class EstadoSprint {
	@XmlElement(name = "name")
	private String nome;

	@XmlElement(name = "total_userstories")
	private int totalEstorias;

	@XmlElement(name = "completed_userstories")
	private int estoriasCompletadas;

	@XmlElement(name = "total_tasks")
	private int totalTarefas;

	@XmlElement(name = "completed_tasks")
	private int tarefasCompletadas;

	@XmlElement(name = "completed_points")
	private List<Double> pontosCompletados;

	@XmlElement(name = "total_points")
	private Map<String, Double> totalPontos;

	@XmlElement(name = "iocaine_doses")
	private int dosesIocaine;

	@XmlElement(name = "is_closed")
	private boolean concluida;

	@XmlElement(name = "days")
	private List<Dia> dias;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Double> getPontosCompletados() {
		return pontosCompletados;
	}

	public void setPontosCompletados(List<Double> pontosCompletados) {
		this.pontosCompletados = pontosCompletados;
	}

	public int getTotalEstorias() {
		return totalEstorias;
	}

	public void setTotalEstorias(int totalEstorias) {
		this.totalEstorias = totalEstorias;
	}

	public int getEstoriasCompletadas() {
		return estoriasCompletadas;
	}

	public void setEstoriasCompletadas(int estoriasCompletadas) {
		this.estoriasCompletadas = estoriasCompletadas;
	}

	public int getTotalTarefas() {
		return totalTarefas;
	}

	public void setTotalTarefas(int totalTarefas) {
		this.totalTarefas = totalTarefas;
	}

	public int getTarefasCompletadas() {
		return tarefasCompletadas;
	}

	public void setTarefasCompletadas(int tarefasCompletadas) {
		this.tarefasCompletadas = tarefasCompletadas;
	}

	public int getDosesIocaine() {
		return dosesIocaine;
	}

	public void setDosesIocaine(int dosesIocaine) {
		this.dosesIocaine = dosesIocaine;
	}

	public Map<String, Double> getTotalPontos() {
		return totalPontos;
	}

	public void setTotalPontos(Map<String, Double> totalPontos) {
		this.totalPontos = totalPontos;
	}

	public boolean isConcluida() {
		return concluida;
	}

	public void setConcluida(boolean concluida) {
		this.concluida = concluida;
	}

	public List<Dia> getDias() {
		return dias;
	}

	public void setDias(List<Dia> dias) {
		this.dias = dias;
	}

}

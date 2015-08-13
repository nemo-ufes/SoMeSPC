package org.somespc.integracao.taiga.model;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class EstadoSprint
{
    @XmlElement(name = "name")
    private String nome;

    @XmlElement(name = "completed_points")
    private List<Integer> pontosCompletados;

    @XmlElement(name = "total_userstories")
    private int totalEstorias;

    @XmlElement(name = "completed_userstories")
    private int estoriasCompletadas;

    @XmlElement(name = "total_tasks")
    private int totalTarefas;

    @XmlElement(name = "completed_tasks")
    private int tarefasCompletadas;

    @XmlElement(name = "iocaine_doses")
    private int dosesIocaine;

    @XmlElement(name = "total_points")
    private Map<String, Integer> totalPontos;

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public List<Integer> getPontosCompletados()
    {
	return pontosCompletados;
    }

    public void setPontosCompletados(List<Integer> pontosCompletados)
    {
	this.pontosCompletados = pontosCompletados;
    }

    public int getTotalEstorias()
    {
	return totalEstorias;
    }

    public void setTotalEstorias(int totalEstorias)
    {
	this.totalEstorias = totalEstorias;
    }

    public int getEstoriasCompletadas()
    {
	return estoriasCompletadas;
    }

    public void setEstoriasCompletadas(int estoriasCompletadas)
    {
	this.estoriasCompletadas = estoriasCompletadas;
    }

    public int getTotalTarefas()
    {
	return totalTarefas;
    }

    public void setTotalTarefas(int totalTarefas)
    {
	this.totalTarefas = totalTarefas;
    }

    public int getTarefasCompletadas()
    {
	return tarefasCompletadas;
    }

    public void setTarefasCompletadas(int tarefasCompletadas)
    {
	this.tarefasCompletadas = tarefasCompletadas;
    }

    public int getDosesIocaine()
    {
	return dosesIocaine;
    }

    public void setDosesIocaine(int dosesIocaine)
    {
	this.dosesIocaine = dosesIocaine;
    }

    public Map<String, Integer> getTotalPontos()
    {
	return totalPontos;
    }

    public void setTotalPontos(Map<String, Integer> totalPontos)
    {
	this.totalPontos = totalPontos;
    }

}

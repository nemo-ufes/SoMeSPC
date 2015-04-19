package org.medcep.integracao.taiga.model;

import java.util.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.*;

import org.medcep.integracao.util.*;

@XmlRootElement
public class Sprint
{
    @XmlElement
    private int id;

    @XmlElement(name = "name")
    private String nome;

    @XmlElement(name = "slug")
    private String apelido;

    @XmlElement(name = "order")
    private int ordem;

    @XmlElement(name = "project")
    private int idProjeto;

    @XmlElement(name = "estimated_start")
    @XmlJavaTypeAdapter(TaigaSimpleDateAdapter.class)
    private Date dataInicio;

    @XmlElement(name = "estimated_finish")
    @XmlJavaTypeAdapter(TaigaSimpleDateAdapter.class)
    private Date dataFim;

    @XmlElement(name = "closed_points")
    private float pontosFechados;

    @XmlElement(name = "closed")
    private boolean fechada;

    @XmlElement(name = "total_points")
    private float totalPontos;

    @XmlElement(name = "client_increment_points")
    private float pontosIncrementadosPeloCliente;

    @XmlElement(name = "team_increment_points")
    private float pontosIncrementadosPelaEquipe;

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public String getApelido()
    {
	return apelido;
    }

    public void setApelido(String apelido)
    {
	this.apelido = apelido;
    }

    public int getOrdem()
    {
	return ordem;
    }

    public void setOrdem(int ordem)
    {
	this.ordem = ordem;
    }

    public int getIdProjeto()
    {
	return idProjeto;
    }

    public void setIdProjeto(int idProjeto)
    {
	this.idProjeto = idProjeto;
    }

    public Date getDataInicio()
    {
	return dataInicio;
    }

    public void setDataInicio(Date dataInicio)
    {
	this.dataInicio = dataInicio;
    }

    public Date getDataFim()
    {
	return dataFim;
    }

    public void setDataFim(Date dataFim)
    {
	this.dataFim = dataFim;
    }

    public float getPontosFechados()
    {
	return pontosFechados;
    }

    public void setPontosFechados(float pontosFechados)
    {
	this.pontosFechados = pontosFechados;
    }

    public boolean isFechada()
    {
	return fechada;
    }

    public void setFechada(boolean fechada)
    {
	this.fechada = fechada;
    }

    public float getTotalPontos()
    {
	return totalPontos;
    }

    public void setTotalPontos(float totalPontos)
    {
	this.totalPontos = totalPontos;
    }

    public float getPontosIncrementadosPeloCliente()
    {
	return pontosIncrementadosPeloCliente;
    }

    public void setPontosIncrementadosPeloCliente(float pontosIncrementadosPeloCliente)
    {
	this.pontosIncrementadosPeloCliente = pontosIncrementadosPeloCliente;
    }

    public float getPontosIncrementadosPelaEquipe()
    {
	return pontosIncrementadosPelaEquipe;
    }

    public void setPontosIncrementadosPelaEquipe(float pontosIncrementadosPelaEquipe)
    {
	this.pontosIncrementadosPelaEquipe = pontosIncrementadosPelaEquipe;
    }

}

package org.medcep.integracao.taiga.model;

import java.util.*;

import javax.xml.bind.annotation.*;

/**
 * Mede dados do projeto Taiga.
 * 
 * Obtido em http://<taiga-url>/api/projects/<nome-projeto>/stats
 * 
 * @author Vinicius
 *
 */
@XmlRootElement
public class EstadoProjeto
{
    @XmlElement(name="name")
    private String nomeProjeto;
    
    @XmlElement(name = "assigned_points")
    private float pontosAlocados;

    @XmlElement(name = "closed_points_per_role")
    private Map<String, Float> pontosFechadosPorPapel;

    @XmlElement(name = "speed")
    private float velocidade;

    @XmlElement(name = "defined_points")
    private float pontosDefinidos;

    @XmlElement(name = "closed_points")
    private float pontosFechados;

    @XmlElement(name = "defined_points_per_role")
    private Map<String, Float> pontosDefinidosPorPapel;

    @XmlElement(name = "total_milestones")
    private float totalMarcos;

    @XmlElement(name = "assigned_points_per_role")
    private Map<String, Float> pontosAlocadosPorPapel;

    @XmlElement(name = "total_points")
    private float totalPontos;

    public float getPontosAlocados()
    {
	return pontosAlocados;
    }

    public void setPontosAlocados(float pontosAlocados)
    {
	this.pontosAlocados = pontosAlocados;
    }

    public float getVelocidade()
    {
	return velocidade;
    }

    public void setVelocidade(float velocidade)
    {
	this.velocidade = velocidade;
    }

    public float getPontosDefinidos()
    {
	return pontosDefinidos;
    }

    public void setPontosDefinidos(float pontosDefinidos)
    {
	this.pontosDefinidos = pontosDefinidos;
    }

    public float getPontosFechados()
    {
	return pontosFechados;
    }

    public void setPontosFechados(float pontosFechados)
    {
	this.pontosFechados = pontosFechados;
    }

    public float getTotalMarcos()
    {
	return totalMarcos;
    }

    public void setTotalMarcos(float totalMarcos)
    {
	this.totalMarcos = totalMarcos;
    }

    public float getTotalPontos()
    {
	return totalPontos;
    }

    public void setTotalPontos(float totalPontos)
    {
	this.totalPontos = totalPontos;
    }

    public Map<String, Float> getPontosFechadosPorPapel()
    {
	return pontosFechadosPorPapel;
    }

    public void setPontosFechadosPorPapel(Map<String, Float> pontosFechadosPorPapel)
    {
	this.pontosFechadosPorPapel = pontosFechadosPorPapel;
    }

    public Map<String, Float> getPontosDefinidosPorPapel()
    {
	return pontosDefinidosPorPapel;
    }

    public void setPontosDefinidosPorPapel(Map<String, Float> pontosDefinidosPorPapel)
    {
	this.pontosDefinidosPorPapel = pontosDefinidosPorPapel;
    }

    public Map<String, Float> getPontosAlocadosPorPapel()
    {
	return pontosAlocadosPorPapel;
    }

    public void setPontosAlocadosPorPapel(Map<String, Float> pontosAlocadosPorPapel)
    {
	this.pontosAlocadosPorPapel = pontosAlocadosPorPapel;
    }

    public String getNomeProjeto()
    {
	return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto)
    {
	this.nomeProjeto = nomeProjeto;
    }

}

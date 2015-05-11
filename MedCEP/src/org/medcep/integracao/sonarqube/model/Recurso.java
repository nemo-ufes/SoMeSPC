package org.medcep.integracao.sonarqube.model;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Recurso
{
    @XmlElement(name = "id")
    private Integer id;

    @XmlElement(name = "key")
    private String chave;

    @XmlElement(name = "name")
    private String nome;

    @XmlElement(name = "scope")
    private String escopo;

    @XmlElement(name = "qualifier")
    private String qualificador;

    @XmlElement(name = "lname")
    private String nomeLabel;

    @XmlElement(name = "msr")
    private List<Medida> medidas;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public String getChave()
    {
	return chave;
    }

    public void setChave(String chave)
    {
	this.chave = chave;
    }

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public String getEscopo()
    {
	return escopo;
    }

    public void setEscopo(String escopo)
    {
	this.escopo = escopo;
    }

    public String getQualificador()
    {
	return qualificador;
    }

    public void setQualificador(String qualificador)
    {
	this.qualificador = qualificador;
    }

    public String getNomeLabel()
    {
	return nomeLabel;
    }

    public void setNomeLabel(String nomeLabel)
    {
	this.nomeLabel = nomeLabel;
    }

    public List<Medida> getMedidas()
    {
	return medidas;
    }

    public void setMedidas(List<Medida> medidas)
    {
	this.medidas = medidas;
    }

}

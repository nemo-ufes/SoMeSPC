package org.medcep.webservices.rest.dto;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ObjetivoMedicaoDTO
{
    @XmlElement(name = "nome")
    private String nome;

    @XmlElement(name = "objetivos_estrategicos")
    private List<String> objetivosEstrategicos;

    @XmlElement(name = "objetivos_software")
    private List<String> objetivosSoftware;

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public List<String> getObjetivosEstrategicos()
    {
	return objetivosEstrategicos;
    }

    public void setObjetivosEstrategicos(List<String> objetivosEstrategicos)
    {
	this.objetivosEstrategicos = objetivosEstrategicos;
    }

    public List<String> getObjetivosSoftware()
    {
	return objetivosSoftware;
    }

    public void setObjetivosSoftware(List<String> objetivosSoftware)
    {
	this.objetivosSoftware = objetivosSoftware;
    }

}

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
    private List<ObjetivoEstrategicoDTO> objetivosEstrategicos;

    @XmlElement(name = "objetivos_software")
    private List<ObjetivoSoftwareDTO> objetivosSoftware;

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public List<ObjetivoEstrategicoDTO> getObjetivosEstrategicos()
    {
	return objetivosEstrategicos;
    }

    public void setObjetivosEstrategicos(List<ObjetivoEstrategicoDTO> objetivosEstrategicos)
    {
	this.objetivosEstrategicos = objetivosEstrategicos;
    }

    public List<ObjetivoSoftwareDTO> getObjetivosSoftware()
    {
	return objetivosSoftware;
    }

    public void setObjetivosSoftware(List<ObjetivoSoftwareDTO> objetivosSoftware)
    {
	this.objetivosSoftware = objetivosSoftware;
    }

}

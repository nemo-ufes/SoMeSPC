package org.medcep.webservices.rest.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PeriodicidadeDTO
{

    @XmlElement(name = "nome")
    private String nome;

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

}

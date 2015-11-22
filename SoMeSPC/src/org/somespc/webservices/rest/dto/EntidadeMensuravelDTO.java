package org.somespc.webservices.rest.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EntidadeMensuravelDTO
{
    private Integer id;

    @XmlElement(name = "nome")
    private String nome;
    
    @XmlElement(name ="nomeTipo")
    private String nomeTipo;

    public String getNomeTipo() {
		return nomeTipo;
	}

	public void setNomeTipo(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}

	public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
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
}

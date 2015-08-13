package org.somespc.webservices.rest.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OcorrenciaDTO
{
    private Integer id;

    @XmlElement(name = "nome_ocorrencia")
    private String nomeOcorrencia;

    @XmlElement(name = "nome_processo_ocorrido")
    private String nomeProcessoOcorrido;

    @XmlElement(name = "nome_atividade_ocorrida")
    private String nomeAtividadeOcorrida;

    public String getNomeOcorrencia()
    {
	return nomeOcorrencia;
    }

    public void setNomeOcorrencia(String nomeOcorrencia)
    {
	this.nomeOcorrencia = nomeOcorrencia;
    }

    public String getNomeProcessoOcorrido()
    {
	return nomeProcessoOcorrido;
    }

    public void setNomeProcessoOcorrido(String nomeProcessoOcorrido)
    {
	this.nomeProcessoOcorrido = nomeProcessoOcorrido;
    }

    public String getNomeAtividadeOcorrida()
    {
	return nomeAtividadeOcorrida;
    }

    public void setNomeAtividadeOcorrida(String nomeAtividadeOcorrida)
    {
	this.nomeAtividadeOcorrida = nomeAtividadeOcorrida;
    }

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

}

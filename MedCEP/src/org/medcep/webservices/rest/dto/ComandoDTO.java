package org.medcep.webservices.rest.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ComandoDTO
{
    @XmlElement(name="nome_agendamento")
    private String nomeAgendamento;
    
    @XmlElement(name="grupo_agendamento")
    private String grupoAgendamento;
    
    @XmlElement(name="comando")
    private String comando;

    public String getNomeAgendamento()
    {
	return nomeAgendamento;
    }

    public void setNomeAgendamento(String nomeAgendamento)
    {
	this.nomeAgendamento = nomeAgendamento;
    }

    public String getGrupoAgendamento()
    {
	return grupoAgendamento;
    }

    public void setGrupoAgendamento(String grupoAgendamento)
    {
	this.grupoAgendamento = grupoAgendamento;
    }

    public String getComando()
    {
	return comando;
    }

    public void setComando(String comando)
    {
	this.comando = comando;
    }

}

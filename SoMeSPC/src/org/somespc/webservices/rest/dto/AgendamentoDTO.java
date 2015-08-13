package org.somespc.webservices.rest.dto;

import java.math.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AgendamentoDTO
{
    @XmlElement(name = "nome_agendador")
    private String nomeAgendador;

    @XmlElement(name = "nome_agendamento")
    private String nomeAgendamento;

    @XmlElement(name = "grupo_agendamento")
    private String grupoAgendamento;

    @XmlElement(name = "nome_job")
    private String nomeJob;

    @XmlElement(name = "grupo_job")
    private String grupoJob;

    @XmlElement(name = "descricao")
    private String descricao;

    @XmlElement(name = "proxima_execucao")
    private BigInteger proximaExecucao;

    @XmlElement(name = "execucao_anterior")
    private BigInteger execucaoAnterior;

    @XmlElement(name = "prioridade")
    private Integer prioridade;

    @XmlElement(name = "estado_agendamento")
    private String estadoAgendamento;

    @XmlElement(name = "tipo_agendamento")
    private String tipoAgendamento;

    @XmlElement(name = "inicio_agendamento")
    private BigInteger inicioAgendamento;

    @XmlElement(name = "fim_agendamento")
    private BigInteger fimAgendamento;

    @XmlElement(name = "calendario")
    private String calendario;

    @XmlElement(name = "instrucao_erro")
    private Short instrucaoErro;

    public String getNomeAgendador()
    {
	return nomeAgendador;
    }

    public void setNomeAgendador(String nomeAgendador)
    {
	this.nomeAgendador = nomeAgendador;
    }

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

    public String getNomeJob()
    {
	return nomeJob;
    }

    public void setNomeJob(String nomeJob)
    {
	this.nomeJob = nomeJob;
    }

    public String getGrupoJob()
    {
	return grupoJob;
    }

    public void setGrupoJob(String grupoJob)
    {
	this.grupoJob = grupoJob;
    }

    public String getDescricao()
    {
	return descricao;
    }

    public void setDescricao(String descricao)
    {
	this.descricao = descricao;
    }

    public BigInteger getProximaExecucao()
    {
	return proximaExecucao;
    }

    public void setProximaExecucao(BigInteger proximaExecucao)
    {
	this.proximaExecucao = proximaExecucao;
    }

    public BigInteger getExecucaoAnterior()
    {
	return execucaoAnterior;
    }

    public void setExecucaoAnterior(BigInteger execucaoAnterior)
    {
	this.execucaoAnterior = execucaoAnterior;
    }

    public Integer getPrioridade()
    {
	return prioridade;
    }

    public void setPrioridade(Integer prioridade)
    {
	this.prioridade = prioridade;
    }

    public String getEstadoAgendamento()
    {
	return estadoAgendamento;
    }

    public void setEstadoAgendamento(String estadoAgendamento)
    {
	this.estadoAgendamento = estadoAgendamento;
    }

    public String getTipoAgendamento()
    {
	return tipoAgendamento;
    }

    public void setTipoAgendamento(String tipoAgendamento)
    {
	this.tipoAgendamento = tipoAgendamento;
    }

    public BigInteger getInicioAgendamento()
    {
	return inicioAgendamento;
    }

    public void setInicioAgendamento(BigInteger inicioAgendamento)
    {
	this.inicioAgendamento = inicioAgendamento;
    }

    public BigInteger getFimAgendamento()
    {
	return fimAgendamento;
    }

    public void setFimAgendamento(BigInteger fimAgendamento)
    {
	this.fimAgendamento = fimAgendamento;
    }

    public String getCalendario()
    {
	return calendario;
    }

    public void setCalendario(String calendario)
    {
	this.calendario = calendario;
    }

    public Short getInstrucaoErro()
    {
	return instrucaoErro;
    }

    public void setInstrucaoErro(Short instrucaoErro)
    {
	this.instrucaoErro = instrucaoErro;
    }

}

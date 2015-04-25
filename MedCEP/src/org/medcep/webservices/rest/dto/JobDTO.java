package org.medcep.webservices.rest.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JobDTO
{
    @XmlElement(name = "nome_agendador")
    private String nomeAgendador;

    @XmlElement(name = "nome_job")
    private String nomeJob;

    @XmlElement(name = "grupo_job")
    private String grupoJob;

    @XmlElement(name = "descricao")
    private String descricao;

    @XmlElement(name = "classe_job")
    private String classeJob;

    @XmlElement(name = "is_duravel")
    private Boolean isDuravel;

    @XmlElement(name = "is_nao_concorrente")
    private Boolean isNaoConcorrente;

    @XmlElement(name = "is_atualizacao_dados")
    private Boolean isAtualizacaoDados;

    @XmlElement(name = "requer_recuperacao")
    private Boolean requerRecuperacao;

    public String getNomeAgendador()
    {
	return nomeAgendador;
    }

    public void setNomeAgendador(String nomeAgendador)
    {
	this.nomeAgendador = nomeAgendador;
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

    public String getClasseJob()
    {
	return classeJob;
    }

    public void setClasseJob(String classeJob)
    {
	this.classeJob = classeJob;
    }

    public Boolean getIsDuravel()
    {
	return isDuravel;
    }

    public void setIsDuravel(Boolean isDuravel)
    {
	this.isDuravel = isDuravel;
    }

    public Boolean getIsNaoConcorrente()
    {
	return isNaoConcorrente;
    }

    public void setIsNaoConcorrente(Boolean isNaoConcorrente)
    {
	this.isNaoConcorrente = isNaoConcorrente;
    }

    public Boolean getIsAtualizacaoDados()
    {
	return isAtualizacaoDados;
    }

    public void setIsAtualizacaoDados(Boolean isAtualizacaoDados)
    {
	this.isAtualizacaoDados = isAtualizacaoDados;
    }

    public Boolean getRequerRecuperacao()
    {
	return requerRecuperacao;
    }

    public void setRequerRecuperacao(Boolean requerRecuperacao)
    {
	this.requerRecuperacao = requerRecuperacao;
    }

}

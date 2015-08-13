package org.somespc.integracao.taiga.model;

import java.util.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.*;

import org.somespc.integracao.util.*;

@XmlRootElement
public class Estoria
{

    private int Id;

    @XmlElement(name = "description")
    private String descricao;

    @XmlElement(name = "created_date")
    @XmlJavaTypeAdapter(TaigaDateAdapter.class)
    private Date dataCriacao;

    @XmlElement(name = "finish_date")
    @XmlJavaTypeAdapter(TaigaDateAdapter.class)
    private Date dataFim;

    @XmlElement(name = "is_archived")
    private boolean arquivada;

    @XmlElement(name = "is_blocked")
    private boolean bloqueada;

    @XmlElement(name = "is_closed")
    private boolean fechada;

    @XmlElement(name = "milestone")
    private int sprintId;

    @XmlElement(name = "milestone_name")
    private String nomeSprint;

    @XmlElement(name = "milestone_slug")
    private String apelidoSprint;

    @XmlElement(name = "owner")
    private int idDono;

    @XmlElement(name = "points")
    private Map<String, Float> pontos;

    @XmlElement(name = "project")
    private int idProjeto;

    @XmlElement(name = "subject")
    private String titulo;

    @XmlElement(name = "team_requirement")
    private boolean requisitoDoTime;

    @XmlElement(name = "total_points")
    private Float totalPontos;

    public int getId()
    {
	return Id;
    }

    public void setId(int id)
    {
	Id = id;
    }

    public String getDescricao()
    {
	return descricao;
    }

    public void setDescricao(String descricao)
    {
	this.descricao = descricao;
    }

    public Date getDataCriacao()
    {
	return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao)
    {
	this.dataCriacao = dataCriacao;
    }

    public Date getDataFim()
    {
	return dataFim;
    }

    public void setDataFim(Date dataFim)
    {
	this.dataFim = dataFim;
    }

    public boolean isArquivada()
    {
	return arquivada;
    }

    public void setArquivada(boolean arquivada)
    {
	this.arquivada = arquivada;
    }

    public boolean isBloqueada()
    {
	return bloqueada;
    }

    public void setBloqueada(boolean bloqueada)
    {
	this.bloqueada = bloqueada;
    }

    public boolean isFechada()
    {
	return fechada;
    }

    public void setFechada(boolean fechada)
    {
	this.fechada = fechada;
    }

    public int getSprintId()
    {
	return sprintId;
    }

    public void setSprintId(int sprintId)
    {
	this.sprintId = sprintId;
    }

    public String getNomeSprint()
    {
	return nomeSprint;
    }

    public void setNomeSprint(String nomeSprint)
    {
	this.nomeSprint = nomeSprint;
    }

    public String getApelidoSprint()
    {
	return apelidoSprint;
    }

    public void setApelidoSprint(String apelidoSprint)
    {
	this.apelidoSprint = apelidoSprint;
    }

    public int getIdDono()
    {
	return idDono;
    }

    public void setIdDono(int idDono)
    {
	this.idDono = idDono;
    }

    public Map<String, Float> getPontos()
    {
	return pontos;
    }

    public void setPontos(Map<String, Float> pontos)
    {
	this.pontos = pontos;
    }

    public int getIdProjeto()
    {
	return idProjeto;
    }

    public void setIdProjeto(int idProjeto)
    {
	this.idProjeto = idProjeto;
    }

    public String getTitulo()
    {
	return titulo;
    }

    public void setTitulo(String titulo)
    {
	this.titulo = titulo;
    }

    public boolean isRequisitoDoTime()
    {
	return requisitoDoTime;
    }

    public void setRequisitoDoTime(boolean requisitoDoTime)
    {
	this.requisitoDoTime = requisitoDoTime;
    }

    public Float getTotalPontos()
    {
	return totalPontos;
    }

    public void setTotalPontos(Float totalPontos)
    {
	this.totalPontos = totalPontos;
    }
}

package org.medcep.webservices.rest.dto;

import java.util.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.*;

import org.medcep.webservices.rest.util.*;

//Exemplo de JSON
//{
//    "nome": "Definição Operacional criada via REST",
//    "data": "19/04/2015",
//    "descricao":"Definição Operacional criada via REST",
//    "nome_medida":"Doses de Iocaine",
//    "momento_medicao":"Sprint",
//    "periodicidade_medicao":"Diária",
//    "papel_responsavel_medicao":"Quality",
//    "procedimento_medicao":"Procedimento de coleta diária para TAIGA-IOC",
//    "momento_analise":"Sprint",
//    "periodicidade_analise":"Diária",
//    "papel_responsavel_analise":"Quality",
//    "procedimento_analise":"Procedimento de teste de analise"
//}	

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DefinicaoOperacionalMedidaDTO
{
    @XmlElement(name="id")
    private Integer id;    
    
    @XmlElement(name="nome")
    private String nome;
    
    @XmlElement(name="data")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date data;
    
    @XmlElement(name="descricao")
    private String descricao;
    
    @XmlElement(name="nome_medida")
    private String nomeMedida;
    
    @XmlElement(name="momento_medicao")
    private String momentoMedicao;
    
    @XmlElement(name="periodicidade_medicao")
    private String periodicidadeMedicao;
    
    @XmlElement(name="papel_responsavel_medicao")
    private String papelResponsavelMedicao;
    
    @XmlElement(name="procedimento_medicao")
    private String nomeProcedimentoMedicao;
    
    @XmlElement(name="momento_analise")
    private String momentoAnalise;
    
    @XmlElement(name="periodicidade_analise")
    private String periodicidadeAnalise;
    
    @XmlElement(name="papel_responsavel_analise")
    private String papelResponsavelAnalise;
    
    @XmlElement(name="procedimento_analise")
    private String nomeProcedimentoAnalise;

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

    public Date getData()
    {
	return data;
    }

    public void setData(Date data)
    {
	this.data = data;
    }

    public String getDescricao()
    {
	return descricao;
    }

    public void setDescricao(String descricao)
    {
	this.descricao = descricao;
    }

    public String getNomeMedida()
    {
	return nomeMedida;
    }

    public void setNomeMedida(String nomeMedida)
    {
	this.nomeMedida = nomeMedida;
    }

    public String getMomentoMedicao()
    {
	return momentoMedicao;
    }

    public void setMomentoMedicao(String momentoMedicao)
    {
	this.momentoMedicao = momentoMedicao;
    }

    public String getPeriodicidadeMedicao()
    {
	return periodicidadeMedicao;
    }

    public void setPeriodicidadeMedicao(String periodicidadeMedicao)
    {
	this.periodicidadeMedicao = periodicidadeMedicao;
    }

    public String getPapelResponsavelMedicao()
    {
	return papelResponsavelMedicao;
    }

    public void setPapelResponsavelMedicao(String papelResponsavelMedicao)
    {
	this.papelResponsavelMedicao = papelResponsavelMedicao;
    }

    public String getNomeProcedimentoMedicao()
    {
	return nomeProcedimentoMedicao;
    }

    public void setNomeProcedimentoMedicao(String nomeProcedimentoMedicao)
    {
	this.nomeProcedimentoMedicao = nomeProcedimentoMedicao;
    }

    public String getMomentoAnalise()
    {
	return momentoAnalise;
    }

    public void setMomentoAnalise(String momentoAnalise)
    {
	this.momentoAnalise = momentoAnalise;
    }

    public String getPeriodicidadeAnalise()
    {
	return periodicidadeAnalise;
    }

    public void setPeriodicidadeAnalise(String periodicidadeAnalise)
    {
	this.periodicidadeAnalise = periodicidadeAnalise;
    }

    public String getPapelResponsavelAnalise()
    {
	return papelResponsavelAnalise;
    }

    public void setPapelResponsavelAnalise(String papelResponsavelAnalise)
    {
	this.papelResponsavelAnalise = papelResponsavelAnalise;
    }

    public String getNomeProcedimentoAnalise()
    {
	return nomeProcedimentoAnalise;
    }

    public void setNomeProcedimentoAnalise(String nomeProcedimentoAnalise)
    {
	this.nomeProcedimentoAnalise = nomeProcedimentoAnalise;
    }

}

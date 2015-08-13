package org.somespc.integracao.taiga.model;

import java.util.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.*;

import org.somespc.integracao.util.*;

/**
 * Projeto do Taiga.
 * 
 * @author Vinicius
 *
 */
@XmlRootElement
public class Projeto
{
    private int id;

    @XmlElement(name = "name")
    private String nome;

    @XmlElement(name = "slug")
    private String apelido;

    @XmlElement(name = "description")
    private String descricao;

    @XmlElement(name = "memberships")
    private List<Membro> membros;

    @XmlElement(name = "created_date")
    @XmlJavaTypeAdapter(TaigaDateAdapter.class)
    private Date dataCriacao;

    public int getId()
    {
	return id;
    }

    public void setId(int id)
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

    public String getDescricao()
    {
	return descricao;
    }

    public void setDescricao(String descricao)
    {
	this.descricao = descricao;
    }

    public List<Membro> getMembros()
    {
	return membros;
    }

    public void setMembros(List<Membro> membros)
    {
	this.membros = membros;
    }

    public Date getDataCriacao()
    {
	return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao)
    {
	this.dataCriacao = dataCriacao;
    }

    public String getApelido()
    {
	return apelido;
    }

    public void setApelido(String apelido)
    {
	this.apelido = apelido;
    }

}

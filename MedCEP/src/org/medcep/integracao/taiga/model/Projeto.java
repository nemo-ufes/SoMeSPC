package org.medcep.integracao.taiga.model;

import java.util.*;

import javax.xml.bind.annotation.*;

/**
 * Projeto do Taiga.
 * @author Vinicius
 *
 */
@XmlRootElement
public class Projeto
{
    private int id;
    @XmlElement(name="name")
    private String nome;
    @XmlElement(name="description")
    private String descricao;
    @XmlElement(name="memberships")
    private List<Membro> membros;
    
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
    
    
}

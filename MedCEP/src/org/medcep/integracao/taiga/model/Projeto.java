package org.medcep.integracao.taiga.model;

import java.util.*;

/**
 * Projeto do Taiga.
 * @author Vinicius
 *
 */
public class Projeto
{
    private int id;
    private String nome;
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
    public List<Membro> getMembros()
    {
        return membros;
    }
    public void setMembros(List<Membro> membros)
    {
        this.membros = membros;
    }
    
    
}

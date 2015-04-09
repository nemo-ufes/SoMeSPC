package org.medcep.integracao.taiga.model;

/**
 * Papel do Taiga.
 * @author Vinicius
 *
 */
public class Membro
{
    private int id;
    private String nome;
    private String papel;
    
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
    public String getPapel()
    {
        return papel;
    }
    public void setPapel(String papel)
    {
        this.papel = papel;
    }
    
    
}

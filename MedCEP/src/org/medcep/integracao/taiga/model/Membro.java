package org.medcep.integracao.taiga.model;

import javax.xml.bind.annotation.*;

/**
 * Papel do Taiga.
 * @author Vinicius
 *
 */
@XmlRootElement
public class Membro
{
    private int id;
    @XmlElement(name="full_name")
    private String nome;
    @XmlElement(name="role_name")
    private String papel;
    @XmlElement(name="user")
    private int idUsuario;
    
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
    public int getIdUsuario()
    {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario)
    {
        this.idUsuario = idUsuario;
    }
    
    
}

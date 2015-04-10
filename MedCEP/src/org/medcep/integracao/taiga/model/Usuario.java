package org.medcep.integracao.taiga.model;

import javax.xml.bind.annotation.*;

/**
 * Usuario do Taiga
 * @author Vinicius
 *
 */
@XmlRootElement
public class Usuario
{
    private int id;
    @XmlElement(name="full_name_display")
    private String nome;
    @XmlElement(name="username")
    private String login;
    private String email;
    
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
    public String getLogin()
    {
        return login;
    }
    public void setLogin(String login)
    {
        this.login = login;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
}

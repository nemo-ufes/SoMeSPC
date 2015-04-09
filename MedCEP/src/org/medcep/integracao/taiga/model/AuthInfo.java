package org.medcep.integracao.taiga.model;

import javax.xml.bind.annotation.*;

/**
 * Classe para mapear as informações de autenticação com o Taiga.
 * 
 * @author Vinicius
 *
 */
@XmlRootElement
public class AuthInfo
{
    //TODO: Avaliar implementação do tipo Github.
    private String type = "normal";
    private String username;
    private String password;

    public AuthInfo()
    {

    }

    public AuthInfo(String username, String password)
    {
	this.username = username;
	this.password = password;
    }

    public String getType()
    {
	return type;
    }

    public String getUsername()
    {
	return username;
    }

    public void setUsername(String username)
    {
	this.username = username;
    }

    public String getPassword()
    {
	return password;
    }

    public void setPassword(String password)
    {
	this.password = password;
    }

}
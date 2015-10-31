package org.somespc.webservices.rest.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TaigaLoginDTO
{
    @XmlElement(name = "url")
    private String url;
    
    @XmlElement(name = "usuario")
    private String usuario;
    
    @XmlElement(name = "senha")
    private String senha;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public String getUsuario()
    {
	return usuario;
    }

    public void setUsuario(String usuario)
    {
	this.usuario = usuario;
    }

    public String getSenha()
    {
	return senha;
    }

    public void setSenha(String senha)
    {
	this.senha = senha;
    }

}

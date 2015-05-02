package org.medcep.webservices.rest.dto;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanoDTO
{

    @XmlElement(name = "taiga_Login")
    private TaigaLoginDTO taigaLogin;

    @XmlElement(name = "apelido_Projeto")
    private String apelidoProjeto;

    @XmlElement(name = "nome_Periodicidade")
    private String nomePeriodicidade;

    @XmlElement(name = "nome_Medidas")
    private List<String> nomesMedidas;

    public String getApelidoProjeto()
    {
	return apelidoProjeto;
    }

    public void setApelidoProjeto(String apelidoProjeto)
    {
	this.apelidoProjeto = apelidoProjeto;
    }

    public String getNomePeriodicidade()
    {
	return nomePeriodicidade;
    }

    public void setNomePeriodicidade(String nomePeriodicidade)
    {
	this.nomePeriodicidade = nomePeriodicidade;
    }

    public List<String> getNomesMedidas()
    {
	return nomesMedidas;
    }

    public void setNomesMedidas(List<String> nomesMedidas)
    {
	this.nomesMedidas = nomesMedidas;
    }

    public TaigaLoginDTO getTaigaLogin()
    {
	return taigaLogin;
    }

    public void setTaigaLogin(TaigaLoginDTO taigaLogin)
    {
	this.taigaLogin = taigaLogin;
    }

}

package org.somespc.webservices.rest.dto;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanoDTO
{

    @XmlElement(name = "taiga_Login")
    private TaigaLoginDTO taigaLogin;

    @XmlElement(name = "apelido_Projetos")
    private List<String> apelidosProjetos;

    @XmlElement(name = "nome_Periodicidade")
    private String nomePeriodicidade;
    
    @XmlElement(name = "nome_Itens")
    private List<ItemPlanoDeMedicaoDTO> itensPlanoDeMedicao;

	public List<ItemPlanoDeMedicaoDTO> getItensPlanoDeMedicao() {
		return itensPlanoDeMedicao;
	}

	public void setItensPlanoDeMedicao(List<ItemPlanoDeMedicaoDTO> itensPlanoDeMedicao) {
		this.itensPlanoDeMedicao = itensPlanoDeMedicao;
	}

	public List<String> getApelidosProjetos()
    {
	return apelidosProjetos;
    }

    public void setApelidosProjetos(List<String> apelidosProjetos)
    {
	this.apelidosProjetos = apelidosProjetos;
    }

    public String getNomePeriodicidade()
    {
	return nomePeriodicidade;
    }

    public void setNomePeriodicidade(String nomePeriodicidade)
    {
	this.nomePeriodicidade = nomePeriodicidade;
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

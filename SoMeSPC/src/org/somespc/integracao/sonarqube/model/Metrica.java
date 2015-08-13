package org.somespc.integracao.sonarqube.model;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Metrica implements Comparable<Metrica>
{
    @XmlElement(name = "key")
    private String chave;

    @XmlElement(name = "name")
    private String nome;

    @XmlElement(name = "description")
    private String descricao;

    @XmlElement(name = "domain")
    private String dominio;

    @XmlElement(name = "qualitative")
    private Boolean qualitativa;

    @XmlElement(name = "user_managed")
    private Boolean gerenciadaPorUsuario;

    @XmlElement(name = "direction")
    private Integer direcao;

    @XmlElement(name = "val_type")
    private String tipoDeValor;

    @XmlElement(name = "hidden")
    private Boolean oculta;

    public String getChave()
    {
	return chave;
    }

    public void setChave(String chave)
    {
	this.chave = chave;
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

    public String getDominio()
    {
	return dominio;
    }

    public void setDominio(String dominio)
    {
	this.dominio = dominio;
    }

    public Boolean getQualitativa()
    {
	return qualitativa;
    }

    public void setQualitativa(Boolean qualitativa)
    {
	this.qualitativa = qualitativa;
    }

    public Boolean getGerenciadaPorUsuario()
    {
	return gerenciadaPorUsuario;
    }

    public void setGerenciadaPorUsuario(Boolean gerenciadaPorUsuario)
    {
	this.gerenciadaPorUsuario = gerenciadaPorUsuario;
    }

    public Integer getDirecao()
    {
	return direcao;
    }

    public void setDirecao(Integer direcao)
    {
	this.direcao = direcao;
    }

    public String getTipoDeValor()
    {
	return tipoDeValor;
    }

    public void setTipoDeValor(String tipoDeValor)
    {
	this.tipoDeValor = tipoDeValor;
    }

    public Boolean getOculta()
    {
	return oculta;
    }

    public void setOculta(Boolean oculta)
    {
	this.oculta = oculta;
    }

    @Override
    public int compareTo(Metrica arg0)
    {
	return nome.compareTo(arg0.getNome());
    }

}

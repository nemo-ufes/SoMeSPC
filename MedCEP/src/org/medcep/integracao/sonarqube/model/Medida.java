package org.medcep.integracao.sonarqube.model;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Medida
{
    @XmlElement(name = "key")
    private String chave;

    @XmlElement(name = "val")
    private String valor;

    @XmlElement(name = "frmt_val")
    private String valorFormatado;

    @XmlElement(name = "data")
    private String dados;

    public String getChave()
    {
        return chave;
    }

    public void setChave(String chave)
    {
        this.chave = chave;
    }

    public String getValor()
    {
        return valor;
    }

    public void setValor(String valor)
    {
        this.valor = valor;
    }

    public String getValorFormatado()
    {
        return valorFormatado;
    }

    public void setValorFormatado(String valorFormatado)
    {
        this.valorFormatado = valorFormatado;
    }

    public String getDados()
    {
        return dados;
    }

    public void setDados(String dados)
    {
        this.dados = dados;
    }

}

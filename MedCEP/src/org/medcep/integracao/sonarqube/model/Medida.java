/*
 * MedCEP - A powerful tool for measure
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl.html>.
 */
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

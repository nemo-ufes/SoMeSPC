/*
 * SoMeSPC - powerful tool for measurement
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
package org.somespc.model.entidades_e_medidas;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

/**
 * Números inteiros positivos; baixo, medio, alto;
 */
@Entity
@Views({ @View(members = "valor; numerico"),
	@View(name = "Simple", members = "nome") })
@Tabs({ @Tab(properties = "valor, numerico", defaultOrder = "${valor} asc") })
public class ValorDeEscala
{

    @Id
    @TableGenerator(name="TABLE_GENERATOR", table="ID_TABLE", pkColumnName="ID_TABLE_NAME", pkColumnValue="VALOR_ESCALA_ID", valueColumnName="ID_TABLE_VALUE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator="TABLE_GENERATOR")
      @Hidden
    private Integer id;

    @Column(length = 255, unique = true)
    @Required
    private String valor;

    private boolean numerico;

    @ManyToMany
    @JoinTable(name = "escala_valorDeEscala", joinColumns = { @JoinColumn(name = "valorDeEscala_id") }, inverseJoinColumns = { @JoinColumn(name = "escala_id") })
    private Collection<Escala> escala;

    public String getValor()
    {
	return valor;
    }

    public void setValor(String valor)
    {
	this.valor = valor;
    }

    public boolean isNumerico()
    {
	return numerico;
    }

    public void setNumerico(boolean numerico)
    {
	this.numerico = numerico;
    }

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public Collection<Escala> getEscala()
    {
	return escala;
    }

    public void setEscala(Collection<Escala> escala)
    {
	this.escala = escala;
    }

}

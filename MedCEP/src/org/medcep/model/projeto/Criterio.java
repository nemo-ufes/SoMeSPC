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
package org.medcep.model.projeto;

import javax.persistence.*;

import org.medcep.model.medicao.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(name = "Simple", members = "nome")
})
public class Criterio
{

    @Id
    @SequenceGenerator(name="pk_sequence",sequenceName="seq_criterio", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
    @Hidden
    private Integer id;

    @Column(length = 500, unique = true)
    @Required
    private String nome;

    @ManyToOne
    @DescriptionsList(descriptionProperties = "nome")
    @Required
    private Escala escala;

    @ManyToOne
    @DescriptionsList(descriptionProperties = "nome")
    private UnidadeDeMedida unidadeDeMedida;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
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

    public Escala getEscala()
    {
	return escala;
    }

    public void setEscala(Escala escala)
    {
	this.escala = escala;
    }

    public UnidadeDeMedida getUnidadeDeMedida()
    {
	return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida)
    {
	this.unidadeDeMedida = unidadeDeMedida;
    }

}

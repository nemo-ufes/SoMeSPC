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

package org.medcep.model.medicao;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

/**
 * Medição realizada após alteração na legislação que rege o
 * domínio tratado pelo sistema, o que contribuiu para o
 * elevado número de alterações registradas.
 */
@Entity
@Views({
	@View(members = "descricao"),
	@View(name = "Simple", members = "descricao")
})
@Tabs({
	@Tab(properties = "descricao", defaultOrder = "${descricao} desc")
})
public class ContextoDeMedicao
{

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Hidden
    private Integer id;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @Stereotype("TEXT_AREA")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @OneToMany(mappedBy = "contextoDeMedicao")
    private Collection<Medicao> medicao;

    public Collection<Medicao> getMedicao()
    {
	return medicao;
    }

    public void setMedicao(Collection<Medicao> medicao)
    {
	this.medicao = medicao;
    }

    public String getDescricao()
    {
	return descricao;
    }

    public void setDescricao(String descricao)
    {
	this.descricao = descricao;
    }

}

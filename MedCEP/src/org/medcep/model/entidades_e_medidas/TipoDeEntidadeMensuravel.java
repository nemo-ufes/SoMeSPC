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
package org.medcep.model.entidades_e_medidas;

import java.util.*;

import javax.persistence.*;

import org.medcep.validators.*;
import org.openxava.annotations.*;
import org.openxava.util.*;

@Entity
@Views({
	@View(members = "nome; descricao; elementoMensuravel"),
	@View(name = "Simple", members = "nome"),
	@View(name = "elementosMensuraveis", members = "nome; elementoMensuravel"),
	@View(name = "SimpleNoFrame", members = "nome"),
})
@Tabs({
	@Tab(properties = "nome", defaultOrder = "${nome} asc")
})
@RemoveValidator(value=NaoRemoverValidator.class,
properties=@PropertyValue(name="nome")
)
public class TipoDeEntidadeMensuravel
{

    @Id
    @TableGenerator(name="TABLE_GENERATOR", table="ID_TABLE", pkColumnName="ID_TABLE_NAME", pkColumnValue="TIPO_ENT_MENSURAVEL_ID", valueColumnName="ID_TABLE_VALUE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator="TABLE_GENERATOR")
    @Hidden
    private Integer id;

    @Column(length = 255, unique = true)
    @Required
    private String nome;

    @Stereotype("TEXT_AREA")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @OneToMany(mappedBy = "tipoDeEntidadeMensuravel")
    private Collection<EntidadeMensuravel> entidadeMensuravel;

    @ManyToMany
    @JoinTable(
	    name = "elementoMensuravel_tipoDeEntidadeMensuravel"
	    , joinColumns = {
		    @JoinColumn(name = "tipoDeEntidadeMensuravel_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "elementoMensuravel_id")
	    })
    private Collection<ElementoMensuravel> elementoMensuravel;

    @ManyToMany
    @JoinTable(
	    name = "medida_tipoDeEntidadeMensuravel"
	    , joinColumns = {
		    @JoinColumn(name = "tipoDeEntidadeMensuravel_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "medida_id")
	    })
    private Collection<Medida> medida;

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

    public String getDescricao()
    {
	return descricao;
    }

    public void setDescricao(String descricao)
    {
	this.descricao = descricao;
    }

    public Collection<EntidadeMensuravel> getEntidadeMensuravel()
    {
	return entidadeMensuravel;
    }

    public void setEntidadeMensuravel(
	    Collection<EntidadeMensuravel> entidadeMensuravel)
    {
	this.entidadeMensuravel = entidadeMensuravel;
    }

    public Collection<ElementoMensuravel> getElementoMensuravel()
    {
	return elementoMensuravel;
    }

    public void setElementoMensuravel(
	    Collection<ElementoMensuravel> elementoMensuravel)
    {
	this.elementoMensuravel = elementoMensuravel;
    }

    public Collection<Medida> getMedida()
    {
	return medida;
    }

    public void setMedida(Collection<Medida> medida)
    {
	this.medida = medida;
    }
    
    @PreDelete
    public void preDelete() throws Exception
    {
	if (this.nome.equals("Artefato"))
	    throw new SystemException("Esse tipo de entidade não pode ser excluído.");
	
    }
    
}

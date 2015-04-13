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

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members = "nome; tipoDeEntidadeMensuravel; descricao; elementoMensuravel"),
	@View(name = "Simple", members = "nome"),
	@View(name = "SimpleNoFrame", members = "nome"),
})
//@Tab(properties="nome")
//@Tab(properties="nome", baseCondition="TYPE(e) = EntidadeMensuravel", defaultOrder="${nome} asc")
@Tab(properties = "nome, tipoDeEntidadeMensuravel.nome", defaultOrder = "${nome} asc")
public class EntidadeMensuravel
{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Hidden
    private Integer id;

    @Column(length = 500, unique = true)
    @Required
    private String nome;

    @Stereotype("TEXT_AREA")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @Required
    @DescriptionsList(descriptionProperties = "nome")
    protected TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;

    public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel()
    {
	return tipoDeEntidadeMensuravel;
    }

    public void setTipoDeEntidadeMensuravel(
	    TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel)
    {
	this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
    }

    @ManyToMany
    @JoinTable(
	    name = "elementoMensuravel_entidadeMensuravel"
	    , joinColumns = {
		    @JoinColumn(name = "entidadeMensuravel_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "elementoMensuravel_id")
	    })
    /*
     * @Condition(
     * "${id} = ${this.tipoDeEntidadeMensuravel.id}"
     * )
     */
    @NewAction("EntidadeMensuravel.AddElementoMensuravel")
    @ListProperties("nome")
    protected Collection<ElementoMensuravel> elementoMensuravel;

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

    public Collection<ElementoMensuravel> getElementoMensuravel()
    {
	return elementoMensuravel;
    }

    public void setElementoMensuravel(
	    Collection<ElementoMensuravel> elementoMensuravel)
    {
	this.elementoMensuravel = elementoMensuravel;
    }

    @OneToMany(mappedBy = "entidadeMensuravel")
    private Collection<Medicao> medicao;

    public Collection<Medicao> getMedicao()
    {
	return medicao;
    }

    public void setMedicao(Collection<Medicao> medicao)
    {
	this.medicao = medicao;
    }

    /*
     * public String getTipo(){
     * return getTipoDeEntidadeMensuravel().getNome();
     * }
     */
    /*
     * private Collection<AnaliseDeMedicao> analiseDeMedicao;
     */

    @PreCreate
    @PreUpdate
    public void ajustaElementosMensuraveis()
    {
	if (elementoMensuravel == null)
	    elementoMensuravel = new ArrayList<ElementoMensuravel>();

	if (tipoDeEntidadeMensuravel != null && tipoDeEntidadeMensuravel.getElementoMensuravel() != null)
	{
	    boolean add;
	    for (ElementoMensuravel elemTipo : tipoDeEntidadeMensuravel.getElementoMensuravel())
	    {
		add = true;
		for (ElementoMensuravel elem : elementoMensuravel)
		{
		    if (elem.getNome().compareTo(elemTipo.getNome()) == 0)
		    {
			add = false;
			break;
		    }
		}
		if (add)
		    elementoMensuravel.add(elemTipo);
	    }//elemTipo
	}
    }//ajusta

}

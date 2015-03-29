/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca                          

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/lgpl.html>.    
 */
package org.medcep.model.projeto;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.medcep.actions.*;
import org.medcep.model.medicao.*;
import org.medcep.model.organizacao.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="projeto; criterio; valorMedido; "),
	@View(name="Projeto", members="criterio; valorMedido;")
})/*
@Tabs({
	@Tab(properties="nome, mnemonico, indicador", defaultOrder="${nome} asc")
})
@EntityValidator(
		value=MedidaValidator.class, 
		properties={
			@PropertyValue(name="tipoMedida"),
			@PropertyValue(name="elementoMensuravel")
		}
)*/
public class CriterioDeProjeto {

	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne
	@ReferenceView("Simple")
	private Projeto projeto;
	
	@ManyToOne
	@ReferenceView("Simple")
	@Required
	private Criterio criterio;
	
    @OneToOne(cascade=CascadeType.ALL)
	//@ManyToOne(cascade=CascadeType.REMOVE)
    //@PrimaryKeyJoinColumn
    @NoSearch
    //@NoFrame
    @OnChange(OnChangePropertyDoNothingValorMedido.class)
    //@OnChangeSearch(OnChangeSectionCEPAction.class)
    @Required
	private ValorMedido valorMedido;

	public ValorMedido getValorMedido() {
		return valorMedido;
	}

	public void setValorMedido(ValorMedido valorMedido) {
		this.valorMedido = valorMedido;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
	public Criterio getCriterio() {
		return criterio;
	}

	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}

	//Copiado de Medição
	@PreCreate
	//@PreUpdate
	public void ajusteValorMedido() 
	{
		ValorMedido valorMedidoAux = valorMedido;
		if(getCriterio().getEscala().isNumerico())
    	{
    		valorMedido = new ValorNumerico();
    		((ValorNumerico)valorMedido).setValorMedido(valorMedidoAux.getValorMedido());
    	}
		else
    	{
    		valorMedido = new ValorAlfanumerico();
    		((ValorAlfanumerico)valorMedido).setValorMedido(valorMedidoAux.getValorMedido()); 		
    	}
		//TODO: e se alterar a medida plano medição depois que a medição for criada?	
	}
}

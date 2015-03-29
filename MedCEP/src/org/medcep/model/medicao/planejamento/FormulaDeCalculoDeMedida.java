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

package org.medcep.model.medicao.planejamento;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

/**
 * Taxa de Alteração de requisitos = Requisitos alterado / Requisitos Homologados
 */
@Entity
@View(name="Simple", members="nome, formula")
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class FormulaDeCalculoDeMedida {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
    @Column(length=500, unique=true) @Required 
    private String nome;
    
    @Required
	private String formula;

    //o ideal seria ao remover a formula fosse removido a referencia de medida sem excluir ela, mas ja tentei varias coisas e nd
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.REFRESH)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.ALL)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.DETACH)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.MERGE)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.PERSIST)
    //@OneToOne(mappedBy = "calculadaPor", orphanRemoval=true)
    //@OneToOne(mappedBy = "calculadaPor", optional=true)
    @OneToOne(mappedBy = "calculadaPor")
    @Required
    @ReferenceView("Simple")
	private Medida calcula;
    
	/*private Collection<ProcedimentoDeMedicao> procedimentoDeMedicao;*/
	
    @ManyToMany 
    @JoinTable(
	      name="formulaDeCalculoDeMedida_usa_medida"
	      , joinColumns={
	    		  @JoinColumn(name="formulaDeCalculoDeMedida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      )
    @ListProperties("nome, mnemonico")
	private Collection<Medida> usaMedidas;
	 
    @ManyToMany 
    @JoinTable(
	      name="formulaDeCalculoDeMedida_usa_formulaDeCalculoDeMedida"
	      , joinColumns={
	    		  @JoinColumn(name="formulaDeCalculoDeMedida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="formulaDeCalculoDeMedida_id2")
	       }
	      )
	private Collection<FormulaDeCalculoDeMedida> usaFormulaDeCalculoDeMedida;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Medida getCalcula() {
		return calcula;
	}

	public void setCalcula(Medida calcula) {
		this.calcula = calcula;
	}

	public Collection<FormulaDeCalculoDeMedida> getUsaFormulaDeCalculoDeMedida() {
		return usaFormulaDeCalculoDeMedida;
	}

	public void setUsaFormulaDeCalculoDeMedida(
			Collection<FormulaDeCalculoDeMedida> usaFormulaDeCalculoDeMedida) {
		this.usaFormulaDeCalculoDeMedida = usaFormulaDeCalculoDeMedida;
	}

	public Collection<Medida> getUsaMedidas() {
		return usaMedidas;
	}

	public void setUsaMedidas(Collection<Medida> usaMedidas) {
		this.usaMedidas = usaMedidas;
	}
	
}
 

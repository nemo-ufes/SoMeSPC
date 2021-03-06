/*
 * SoMeSPC - powerful tool for measurement
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique N�spoli Castro, Vin�cius Soares Fonseca
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
package org.somespc.model.objetivos;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.somespc.model.organizacao_de_software.Objetivo;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome;"
		+ "objetivoDeSoftware;"
		+ "objetivoEstrategico"
	),
	@View(name = "Simple", members = "nome"),
})
@Tabs({
	@Tab(properties = "nome", defaultOrder = "${nome} asc")
})
public class ObjetivoDeMedicao extends Objetivo
{

    @ManyToMany
    @NewAction("ObjetivoDeMedicao.createObjetivoEstrategico")
    @JoinTable(
	    name = "ObjetivoDeMedicao_BaseadoEm_ObjetivoDeSoftware"
	    , joinColumns = {
		    @JoinColumn(name = "ObjetivoDeMedicao")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "ObjetivoDeSoftware")
	    })
    @ListProperties("nome")
    private Collection<ObjetivoDeSoftware> objetivoDeSoftware;

    @ManyToMany
    @Size(min=1)
    @JoinTable(
	    name = "ObjetivoDeMedicao_BaseadoEm_ObjetivoEstrategico"
	    , joinColumns = {
		    @JoinColumn(name = "ObjetivoDeMedicao")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "ObjetivoEstrategico")
	    })
    @ListProperties("nome")
    private Collection<ObjetivoEstrategico> objetivoEstrategico;

    public Collection<ObjetivoDeSoftware> getObjetivoDeSoftware()
    {
	return objetivoDeSoftware;
    }

    public void setObjetivoDeSoftware(
	    Collection<ObjetivoDeSoftware> objetivoDeSoftware)
    {
	this.objetivoDeSoftware = objetivoDeSoftware;
    }

    public Collection<ObjetivoEstrategico> getObjetivoEstrategico()
    {
	return objetivoEstrategico;
    }

    public void setObjetivoEstrategico(
	    Collection<ObjetivoEstrategico> objetivoEstrategico)
    {
	this.objetivoEstrategico = objetivoEstrategico;
    }
    
    
	/*Caso um ObjetivoDeSoftware seja adicionado, todos os objetos do tipo ObjetivoEstrategico
	assossiados aquele ObjetivoDeSoftware s�o incluidos na lista.*/
	public void ajustaObjetivos() {
		System.out.println("Entrei fun��o ajusta");
    	if (objetivoDeSoftware != null){
			System.out.println("Entrou @PostCreate");
			if(objetivoEstrategico == null){
				objetivoEstrategico = new ArrayList<ObjetivoEstrategico>();
			}
			
			for (ObjetivoDeSoftware obj_software : objetivoDeSoftware) {
				System.out.println("primeiro for");
				for (ObjetivoEstrategico obj_estrategico : obj_software.getObjetivoEstrategico()) {
					System.out.println("segundo for");
					System.out.println(obj_estrategico.getNome());
					objetivoEstrategico.add(obj_estrategico);
					System.out.println(obj_estrategico.getNome());
				}
			}
		}
	}

}

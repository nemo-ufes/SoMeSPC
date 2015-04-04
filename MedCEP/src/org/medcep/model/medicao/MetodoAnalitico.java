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
package org.medcep.model.medicao;

import javax.persistence.*;

import org.medcep.model.processo.*;
import org.openxava.annotations.*;

/**
 * Histograms and bar charts
 */
@Entity
@Views({
	@View(members="nome, ehMetodoCEP; descricao"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome, ehMetodoCEP", defaultOrder="${nome} asc")
})
public class MetodoAnalitico extends Procedimento {
    
	private boolean ehMetodoCEP;

	public boolean isEhMetodoCEP() {
		return ehMetodoCEP;
	}

	public void setEhMetodoCEP(boolean ehMetodoCEP) {
		this.ehMetodoCEP = ehMetodoCEP;
	}
	 
	/*private Collection<ProcedimentoDeAnaliseDeMedicao> procedimentoDeAnaliseDeMedicao;*/
	 
	//private Collection<AnaliseDeMedicao> analiseDeMedicao;
	 
	
}
 

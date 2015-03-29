package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;

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
 

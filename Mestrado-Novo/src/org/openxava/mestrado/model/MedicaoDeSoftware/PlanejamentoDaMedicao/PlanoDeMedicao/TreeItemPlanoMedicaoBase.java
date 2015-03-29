package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members="nome;"
			//+ " path;"
			//+ " treeOrder;"
	),
	@View(name="Simple", members="nome;")
})
@Tab(properties="nome", defaultOrder="${nome} asc")
public class TreeItemPlanoMedicaoBase {

	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}

package org.openxava.mestrado.model.MedicaoDeSoftware.Medicao;

import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;

/**
 * Medição realizada após alteração na legislação que rege o
 * domínio tratado pelo sistema, o que contribuiu para o
 * elevado número de alterações registradas.
 */
@Entity
@Views({
	@View(members="descricao"),
	@View(name="Simple", members="descricao")
	})
@Tabs({
	@Tab(properties="descricao", defaultOrder="${descricao} desc")
})
public class ContextoDeMedicao {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Stereotype("TEXT_AREA")
	@Column(columnDefinition="TEXT")
	private String descricao;

	@OneToMany(mappedBy="contextoDeMedicao")
	private Collection<Medicao> medicao;

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	 
	
}
 

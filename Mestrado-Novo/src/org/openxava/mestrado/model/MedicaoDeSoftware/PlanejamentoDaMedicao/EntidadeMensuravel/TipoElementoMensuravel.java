package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;

@Entity
@Views({
	@View(members="nome"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class TipoElementoMensuravel {
    
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
	
    @OneToMany(mappedBy="tipoElementoMensuravel")
	private Collection<ElementoMensuravel> elementoMensuravel;


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<ElementoMensuravel> getElementoMensuravel() {
		return elementoMensuravel;
	}

	public void setElementoMensuravel(
			Collection<ElementoMensuravel> elementoMensuravel) {
		this.elementoMensuravel = elementoMensuravel;
	}
    
    
}
 

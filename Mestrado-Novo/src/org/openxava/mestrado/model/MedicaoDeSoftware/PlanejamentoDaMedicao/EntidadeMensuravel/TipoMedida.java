package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;

@Entity
@Views({
	@View(members="nome"),
	@View(name="Simple", members="nome"),
	})
public class TipoMedida {
	 
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
	
    @OneToMany(mappedBy="tipoMedida")
	private Collection<Medida> medida;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Medida> getMedida() {
		return medida;
	}

	public void setMedida(Collection<Medida> medida) {
		this.medida = medida;
	}
 
    
}
 

package org.openxava.mestrado.model.ProcessoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;

@Entity
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; descricao; elementoMensuravel;"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class TipoDeArtefato extends EntidadeMensuravel {
 
    /*
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    */
	
	@OneToMany(mappedBy="tipoDeArtefato")
	private Collection<Artefato> artefato;
	
	public Collection<Artefato> getArtefato() {
		return artefato;
	}

	public void setArtefato(Collection<Artefato> artefato) {
		this.artefato = artefato;
	}
		
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Tipo de Artefato")
			 }
	)
	private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;
	
	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}

	//removido por questões de navegabilidade
	//requerido
	//private Collection<AtividadePadrao> atividadePadrao;
	 
	//removido por questões de navegabilidade
	//produzido
	//private Collection<AtividadePadrao> atividadePadrao;
	 
	
}
 

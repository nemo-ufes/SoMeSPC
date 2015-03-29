package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; descricao; elementoMensuravel"),
	@View(name="Simple", members="nome")
	})
//@Tab(properties="nome")
//@Tab(properties="nome", baseCondition="TYPE(e) = EntidadeMensuravel", defaultOrder="${nome} asc")
@Tab(properties="nome", defaultOrder="${nome} asc")
public class EntidadeMensuravel {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;   
    
    @Column(length=500, unique=true) @Required
	private String nome;
	
	@Stereotype("TEXT_AREA") 
	private String descricao;
	
	@ManyToOne 
	@Required
	@DescriptionsList(descriptionProperties="nome") 
	protected TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;

	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}	 
	
    @ManyToMany
    @JoinTable(
	      name="elementoMensuravel_entidadeMensuravel"
	      , joinColumns={
	    		  @JoinColumn(name="entidadeMensuravel_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="elementoMensuravel_id")
	       }
	      )
/*    @Condition(
      		 "${id} = ${this.tipoDeEntidadeMensuravel.id}"
      		)*/
    //@NewAction("EntidadeMensuravel.AddElementoMensuravel")
    @ListProperties("nome")
	private Collection<ElementoMensuravel> elementoMensuravel;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Collection<ElementoMensuravel> getElementoMensuravel() {
		return elementoMensuravel;
	}

	public void setElementoMensuravel(
			Collection<ElementoMensuravel> elementoMensuravel) {
		this.elementoMensuravel = elementoMensuravel;
	}

	@OneToMany(mappedBy="entidadeMensuravel")
	private Collection<Medicao> medicao;

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}
	

	/*		 
	private Collection<AnaliseDeMedicao> analiseDeMedicao;*/
	 
}
 

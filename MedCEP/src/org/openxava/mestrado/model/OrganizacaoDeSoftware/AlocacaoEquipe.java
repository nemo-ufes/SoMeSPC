package org.openxava.mestrado.model.OrganizacaoDeSoftware;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="inicio, fim; recursoHumano; papelRecursoHumano; equipe"),
	@View(name="Simple", members="nome")
})
@Tab(properties="recursoHumano.nome, equipe.nome, papelRecursoHumano.nome, inicio, fim")
public class AlocacaoEquipe {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
	private Date inicio;
	 
	private Date fim;
	
	@ManyToOne 
	@Required
	@ReferenceView("Simple")
	private Equipe equipe;
	 
	@ManyToOne 
	@Required
	@ReferenceView("Simple")
	private RecursoHumano recursoHumano;
	 
	@ManyToOne 
	@Required
	@ReferenceView("Simple")
	private PapelRecursoHumano papelRecursoHumano;

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public RecursoHumano getRecursoHumano() {
		return recursoHumano;
	}

	public void setRecursoHumano(RecursoHumano recursoHumano) {
		this.recursoHumano = recursoHumano;
	}

	public PapelRecursoHumano getPapelRecursoHumano() {
		return papelRecursoHumano;
	}

	public void setPapelRecursoHumano(PapelRecursoHumano papelRecursoHumano) {
		this.papelRecursoHumano = papelRecursoHumano;
	}
	 
}
 

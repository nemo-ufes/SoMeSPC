package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;

/**
 * Escala que caracteriza o grau de experiência de um Analista de
 * Sistemas considerando-se as características de um projeto.
 */
@Entity
@Views({
	@View(members="nome; tipoEscala; descricao; valorDeEscala"),
	@View(name="Simple", members="nome"),
	})
@Tabs({
	@Tab(properties="nome, tipoEscala.nome", defaultOrder="${nome} asc")
})
public class Escala {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
    @Column(length=500, unique=true) 
    @Required 
    private String nome;
    
	@Stereotype("TEXT_AREA")
	private String descricao;
	 
	@ManyToOne
	@DescriptionsList(descriptionProperties="nome") 
	@Required 
	@NoCreate 
	@NoModify
	private TipoEscala tipoEscala;
	
	@OneToMany(mappedBy="escala")
	private Collection<Medida> medida;
	 
	@OneToMany(mappedBy="escala")
	private Collection<ValorDeEscala> valorDeEscala;

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

	public TipoEscala getTipoEscala() {
		return tipoEscala;
	}

	public void setTipoEscala(TipoEscala tipoEscala) {
		this.tipoEscala = tipoEscala;
	}

	public Collection<Medida> getMedida() {
		return medida;
	}

	public void setMedida(Collection<Medida> medida) {
		this.medida = medida;
	}

	public Collection<ValorDeEscala> getValorDeEscala() {
		return valorDeEscala;
	}

	public void setValorDeEscala(Collection<ValorDeEscala> valorDeEscala) {
		this.valorDeEscala = valorDeEscala;
	}
	 
	public boolean isNumerico()
	{
		if( tipoEscala != null
			&& tipoEscala.getNome().compareTo("Absoluta") == 0 
			|| tipoEscala.getNome().compareTo("Taxa") == 0)
		{
			return true;
		}
		else
		{
			return false;			
		}
	}
}
 

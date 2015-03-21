package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;



/**
 * Números inteiros positivos; baixo, medio, alto;
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members="valor; numerico"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="valor, numerico", defaultOrder="${valor} desc")
})
public class ValorDeEscala {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
	
	@Column(length=500, unique=true) @Required 
	private String valor;
	
	private boolean numerico;
	
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isNumerico() {
		return numerico;
	}

	public void setNumerico(boolean numerico) {
		this.numerico = numerico;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
	@ManyToOne
	private Escala escala;

	public Escala getEscala() {
		return escala;
	}

	public void setEscala(Escala escala) {
		this.escala = escala;
	}

}
 

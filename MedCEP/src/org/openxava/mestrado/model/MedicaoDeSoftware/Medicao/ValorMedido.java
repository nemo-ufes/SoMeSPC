package org.openxava.mestrado.model.MedicaoDeSoftware.Medicao;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@View(members="valorMedido")
public class ValorMedido {

	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    //@Stereotype("NO_CHANGE")
	protected String valorMedido;

	public String getValorMedido() {
		return valorMedido;
	}

	public void setValorMedido(String valorMedido) {
		this.valorMedido = valorMedido;
	}
		
/*	@OneToMany(mappedBy="valorMedido")
	private Collection<Medicao> medicao;
	
	public Collection<Medicao> getMedicao() {
		return medicao;
	}
	
	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}*/

	//private Medicao medicao;
	 
	//private ValorDeEscala valorDeEscala;
	 
    
}
 

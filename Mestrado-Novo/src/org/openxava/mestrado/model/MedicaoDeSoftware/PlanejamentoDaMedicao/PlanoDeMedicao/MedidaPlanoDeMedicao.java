/*package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.hibernate.validator.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.validators.*;

@Entity
@Views({
	@View (members="planoDeMedicao; medida; definicaoOperacionalDeMedida;"),
	@View(name="PlanoMedicao", members="medida; definicaoOperacionalDeMedida"),
	@View(name="Simple", members="medida.nome"),
	//@View(name="Medicao", members="medida.nome; medida; Definição Operacional [ definicaoOperacionalDeMedida.nome ]")
	//@View(name="Medicao", members="medida.nome")
})
@Tabs({
	@Tab( properties = "medida.nome, planoDeMedicao.nome, definicaoOperacionalDeMedida.nome" )	
})
@EntityValidator(value=MedidaPlanoMedicaoValidator.class, 
	properties={
		@PropertyValue(name="medida"), 
		@PropertyValue(name="definicaoOperacionalDeMedida"), 
	}
)

public class MedidaPlanoDeMedicao {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	@ReferenceViews({
		@ReferenceView(forViews="DEFAULT", value="Simple"),
		//@ReferenceView(forViews="Medicao", value="ForMedicao"),
	})
	@ReferenceView("Simple")
	private Medida medida;
	
	@ManyToOne
	@ReferenceView("Simple")
	private PlanoDeMedicao planoDeMedicao;
	 
	@ManyToOne
	@ReferenceView("Simple")
	private DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida;

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public PlanoDeMedicao getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(PlanoDeMedicao planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}

	public DefinicaoOperacionalDeMedida getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida) {
		if(definicaoOperacionalDeMedida.getMedida().getid() != getMedida().getid())
			throw new InvalidStateException(
				new InvalidValue[] {
						new InvalidValue(
								"a",
								getClass(), "a",
								true, this)
					}
				);
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}
	
	@OneToMany(mappedBy="medidaPlanoDeMedicao")
	private Collection<Medicao> medicao;

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}	
	 
	private Collection<AnaliseDeMedicao> analiseDeMedição;
	 
	
}
 
*/
package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.hibernate.validator.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.validators.*;

@Entity
@Views({
	@View(members="medida; definicaoOperacionalDeMedida;"),
	@View(name="PlanoMedicao", members="medida; definicaoOperacionalDeMedida"),
	@View(name="Simple", members="medida.nome")
})
@Tab( properties = "medida.nome, planoDeMedicao.nome, definicaoOperacionalDeMedida.nome" )
@EntityValidator(value=MedidaPlanoMedicaoValidator.class, 
	properties={
		@PropertyValue(name="medida"), 
		@PropertyValue(name="definicaoOperacionalDeMedida"), 
	}
)
public class MedidaPlanoDeMedicao {
 
	@Id @GeneratedValue(generator="system-uuid") 
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	@NoCreate
	@NoModify
	@ReferenceView("Simple")
	@SearchAction("MedidaPlanoDeMedicao.searchMedidaOfPlanoMedicao")
	private Medida medida;
	
	@ManyToOne
	@ReferenceView("Simple")
	private PlanoDeMedicao planoDeMedicao;
	 
	@ManyToOne
	@NoCreate
	@NoModify
	@ReferenceView("Simple")
	private DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida;

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public PlanoDeMedicao getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(PlanoDeMedicao planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}

	public DefinicaoOperacionalDeMedida getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida) {
/*		if(definicaoOperacionalDeMedida.getMedida().getid() != getMedida().getid())
			throw new InvalidStateException(
				new InvalidValue[] {
						new InvalidValue(
								"a",
								getClass(), "a",
								true, this)
					}
				);*/
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}
	
/*	@OneToMany(mappedBy="medidaPlanoDeMedicao")
	private Collection<Medicao> medicao;

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}*/	
/*	 
	private Collection<AnaliseDeMedicao> analiseDeMedição;*/
	 
	
}
 

package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@Views({
	@View (members="Plano da Organização [nome, date; versao; descricao; recursoHumano]; Objetivos { objetivoEstrategico; objetivoDeSoftware; objetivoDeMedicao; necessidadeDeInformacao; } MedidasDoPlano { medidaPlanoDeMedicao }"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc")	
})
public class PlanoDeMedicaoDaOrganizacao extends PlanoDeMedicao {
 
/*	@OneToMany(mappedBy="planoDeMedicaoDaOrganizacao")
    private Collection<PlanoDeMedicaoDoProjeto> planoDeMedicaoDoProjeto;

	public Collection<PlanoDeMedicaoDoProjeto> getPlanoDeMedicaoDoProjeto() {
		return planoDeMedicaoDoProjeto;
	}

	public void setPlanoDeMedicaoDoProjeto(
			Collection<PlanoDeMedicaoDoProjeto> planoDeMedicaoDoProjeto) {
		this.planoDeMedicaoDoProjeto = planoDeMedicaoDoProjeto;
	}*/	
	
}
 

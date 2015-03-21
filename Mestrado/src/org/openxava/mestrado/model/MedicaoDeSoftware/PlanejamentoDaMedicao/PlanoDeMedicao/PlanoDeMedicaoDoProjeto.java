package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao;

import java.util.*;
import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;

@Entity
@Views({
	@View (members="nome, versao, data; planoDeMedicaoDaOrganizacao; projeto; descricao; recursoHumano; objetivos { objetivoEstrategico; objetivoDeSoftware; objetivoDeMedicao; necessidadeDeInformacao; } MedidasDoPlano { medidaPlanoDeMedicao }"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc")	
})
public class PlanoDeMedicaoDoProjeto extends PlanoDeMedicao {
 
	@ManyToOne
	@ReferenceView("Simple")
	private PlanoDeMedicaoDaOrganizacao planoDeMedicaoDaOrganizacao;
	
	@ManyToOne
	@ReferenceView("Simple")
	private Projeto projeto;

	public PlanoDeMedicaoDaOrganizacao getPlanoDeMedicaoDaOrganizacao() {
		return planoDeMedicaoDaOrganizacao;
	}

	public void setPlanoDeMedicaoDaOrganizacao(
			PlanoDeMedicaoDaOrganizacao planoDeMedicaoDaOrganizacao) {
		this.planoDeMedicaoDaOrganizacao = planoDeMedicaoDaOrganizacao;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	
	
}
 

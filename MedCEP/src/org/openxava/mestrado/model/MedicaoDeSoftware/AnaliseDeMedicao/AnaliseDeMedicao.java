/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca                          

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/lgpl.html>.    
 */

package org.openxava.mestrado.model.MedicaoDeSoftware.AnaliseDeMedicao;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;

@Entity
@Views({
	@View (members="data;"
			+ "planoDeMedicao;"
			+ "medidaPlanoDeMedicao;"
			+ "entidadeMensuravel;"
			+ "executorDaAnaliseDeMedicao;"
			+ "momentoRealDaAnaliseDeMedicao;"
			+ "resultado;"
			+ "medicao;"
			//+ "Medições para Análise {medicao;},"
			//+ "Detalhes da Análise {"
			//+ "executorDaAnaliseDeMedicao;"
			//+ "momentoRealDaAnaliseDeMedicao;}"
	),
	@View (name="CEP",
			members="data; resultado;"
			//+ "Medicoes {medicao;},"
			//+ "Resultado { resultado;}"
	),
	@View(name="Simple", members="resultado")
})
@Tabs({
	@Tab(properties="data, " +
					"medidaPlanoDeMedicao.medida.nome, " +
					//"medidaPlanoDeMedicao.medida.entidadeMedida.nome, " +
					//"executorDaAnaliseDeMedicao.nome, " +
					//"momentoRealDaAnaliseDeMedicao.nome" +
					"projeto", defaultOrder="${data} desc")	
})
public class AnaliseDeMedicao {

	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; 
	
	@Stereotype("TEXT_AREA")
	@Column(columnDefinition="TEXT")
	private String resultado;

	public String getId() {
		return id;		
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	private Date data;

	@NoCreate
	@NoModify
	//@NoFrame
	@ManyToOne
	@ReferenceView("Simple")
	//@Required
	private RecursoHumano executorDaAnaliseDeMedicao;
	
	@NoCreate
	@NoModify
	//@NoFrame
	@ManyToOne
	@ReferenceView("Simple")
	//@Required
	private AtividadeDeProjeto momentoRealDaAnaliseDeMedicao;
	
/*	@ManyToOne
	@ReferenceView("Simple")
	//@Required
	@SearchAction("Medicao.searchDefinicaoOperacional")
	private DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida;*/

	@ManyToOne
	@ReferenceView(value="Simple", forViews="CEP")
	@DescriptionsList(descriptionProperties="nome", notForViews="CEP")
	@NoCreate
	private PlanoDeMedicao planoDeMedicao;

	public PlanoDeMedicao getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(PlanoDeMedicao planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}
	
	@ManyToOne
	@ReferenceView(value="Simple", forViews="CEP")
	@DescriptionsList(
			descriptionProperties="medida.nome, medida.mnemonico"
			,depends="planoDeMedicao"
			,condition="${planoDeMedicao.id} = ?"
			,order="${medida.nome} asc", notForViews="CEP"
			)
	@NoCreate
	//@Required
	//ChooseReferenceAction se tentar com a outra visao
	private MedidaPlanoDeMedicao medidaPlanoDeMedicao;
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public RecursoHumano getExecutorDaAnaliseDeMedicao() {
		return executorDaAnaliseDeMedicao;
	}

	public void setExecutorDaAnaliseDeMedicao(
			RecursoHumano executorDaAnaliseDeMedicao) {
		this.executorDaAnaliseDeMedicao = executorDaAnaliseDeMedicao;
	}

	public AtividadeDeProjeto getMomentoRealDaAnaliseDeMedicao() {
		return momentoRealDaAnaliseDeMedicao;
	}

	public void setMomentoRealDaAnaliseDeMedicao(
			AtividadeDeProjeto momentoRealDaAnaliseDeMedicao) {
		this.momentoRealDaAnaliseDeMedicao = momentoRealDaAnaliseDeMedicao;
	}

/*	public DefinicaoOperacionalDeMedida getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(
			DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida) {
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}*/

	@ManyToOne
	@ReferenceView("Simple")
	//@Required
	private EntidadeMensuravel entidadeMensuravel;
	
	public EntidadeMensuravel getEntidadeMensuravel() {
		return entidadeMensuravel;
	}

	public void setEntidadeMensuravel(EntidadeMensuravel entidadeMensuravel) {
		this.entidadeMensuravel = entidadeMensuravel;
	}
	
	@ManyToMany
    @JoinTable(
	      name="analiseDeMedicao_medicao"
	      , joinColumns={
	    		  @JoinColumn(name="analiseDeMedicao_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medicao_id")
	       }
	      )
	@ListProperties("medidaPlanoDeMedicao.medida.nome, projeto.nome, data, valorMedido.valorMedido")
	@NewAction("AnaliseDeMedicao.addMedicao")
	//@ListAction("AnaliseDeMedicao.generateExcel")//TODO: 
	private Collection<Medicao> medicao;

	public MedidaPlanoDeMedicao getMedidaPlanoDeMedicao() {
		return medidaPlanoDeMedicao;
	}

	public void setMedidaPlanoDeMedicao(MedidaPlanoDeMedicao medidaPlanoDeMedicao) {
		this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
	}

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	/*
	public Collection<Medicao> getSortMedicao() {
		ArrayList<Medicao> lstMedicao = new ArrayList<Medicao>(getMedicao());
		
		Collections.sort(lstMedicao);
		
		return lstMedicao;
	}*/
	
	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}
	
	public String getProjeto()
	{
		if(medidaPlanoDeMedicao != null 
			&& medidaPlanoDeMedicao.getPlanoDeMedicao() != null)
		{
			if(medidaPlanoDeMedicao.getPlanoDeMedicao() instanceof PlanoDeMedicaoDoProjeto)
			{
				if(((PlanoDeMedicaoDoProjeto)medidaPlanoDeMedicao.getPlanoDeMedicao()).getProjeto() != null)
				{
					return ((PlanoDeMedicaoDoProjeto)medidaPlanoDeMedicao.getPlanoDeMedicao()).getProjeto().getNome();
				}
			}	
			
		}
		return ""; 		
	}
	
	//private BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso;
	
	//@ManyToOne
	//@ReferenceView("Simple")
	//private Conclusao conclusaoAtribuida;
	
/*	public Conclusao getConclusaoAtribuida() {
		return conclusaoAtribuida;
	}

	public void setConclusaoAtribuida(Conclusao conclusaoAtribuida) {
		this.conclusaoAtribuida = conclusaoAtribuida;
	}*/
	
	//private ProcedimentoDeAnaliseDeMedicao procedimentoDeAnaliseDeMedicao;
	 
	//private Collection<MetodoAnalitico> metodoAnalitico;

	//private Collection<CriterioDeDecisao> criterioDeDecisao;
	
}

 

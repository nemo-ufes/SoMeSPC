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
/*	@View (members="data;"
			+ "entidadeMensuravel;"
			+ "resultado;"
			//+ " Medida [ medidaPlanoDeMedicao.medida.nome]; "
			//+ "Definição Operacional [ medidaPlanoDeMedicao.definicaoOperacionalDeMedida.nome ];"
			+ "Planejamento {planoDeMedicao;"
			+ "medidaPlanoDeMedicao;}"
			+ "Execucao { executorDaAnaliseDeMedicao,"
			+ "momentoRealDaAnaliseDeMedicao;"
			+ "definicaoOperacionalDeMedida; }"
			+ "Medicoes {medicao;};"
	),*/
	@View (members="data;"
			+ "planoDeMedicao;"
			+ "medidaPlanoDeMedicao;"
			+ "resultado;"
			//+ " Medida [ medidaPlanoDeMedicao.medida.nome]; "
			//+ "Definição Operacional [ medidaPlanoDeMedicao.definicaoOperacionalDeMedida.nome ];"
			+ "Execucao {"
			+ "executorDaAnaliseDeMedicao;"
			+ "momentoRealDaAnaliseDeMedicao; }"
			+ "Medicoes {medicao;};"
	),
	@View (name="CEP",
			members="data;"
			+ "planoDeMedicao;"
			+ "medidaPlanoDeMedicao;"
			+ "resultado;"
			//+ " Medida [ medidaPlanoDeMedicao.medida.nome]; "
			//+ "Definição Operacional [ medidaPlanoDeMedicao.definicaoOperacionalDeMedida.nome ];"
			+ "Execucao {"
			+ "executorDaAnaliseDeMedicao;"
			+ "momentoRealDaAnaliseDeMedicao; }"
			+ "Medicoes {medicao;};"
	),
	@View(name="Simple", members="resultado")
})
@Tabs({
	@Tab(properties="data, entidadeMensuravel.nome, executorDaAnaliseDeMedicao.nome, momentoRealDaAnaliseDeMedicao.nome", defaultOrder="${data} desc")	
})
public class AnaliseDeMedicao {

	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; 
	
	@Stereotype("MEMO") 
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

	@ManyToOne
	@ReferenceView("Simple")
	//@Required
	private RecursoHumano executorDaAnaliseDeMedicao;
	
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
	//@ReferenceView("Simple")
	@DescriptionsList(descriptionProperties="nome", notForViews="CEP")
	@ReferenceView(value="Simple", forViews="CEP")
	private PlanoDeMedicao planoDeMedicao;

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

	public PlanoDeMedicao getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(PlanoDeMedicao planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}
	
	@ManyToOne
	//@ReferenceView("Simple")//lincar com o de cima com depende e DescriptionList
	@DescriptionsList(
			descriptionProperties="medida.nome, medida.mnemonico"
			,depends="planoDeMedicao"
			,condition="${planoDeMedicao.id} = ?"
			,order="${medida.nome} asc", notForViews="CEP"
			)
	@ReferenceView(value="Simple", forViews="CEP")
	@NoCreate
	//@Required
	//ChooseReferenceAction se tentar com a outra visao
	private MedidaPlanoDeMedicao medidaPlanoDeMedicao;
	
	@ManyToOne
	@ReferenceView("Simple")
	//@Required
	private EntidadeMensuravel entidadeMensuravel;
	
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
	@ListProperties("medidaPlanoDeMedicao.medida.nome, data, valorMedido.valorMedido")
	private Collection<Medicao> medicao;

	public MedidaPlanoDeMedicao getMedidaPlanoDeMedicao() {
		return medidaPlanoDeMedicao;
	}

	public void setMedidaPlanoDeMedicao(MedidaPlanoDeMedicao medidaPlanoDeMedicao) {
		this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
	}

	public EntidadeMensuravel getEntidadeMensuravel() {
		return entidadeMensuravel;
	}

	public void setEntidadeMensuravel(EntidadeMensuravel entidadeMensuravel) {
		this.entidadeMensuravel = entidadeMensuravel;
	}

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
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




/*package org.openxava.mestrado.model.MedicaoDeSoftware.AnaliseDeMedicao;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;

@Entity
@Views({
	@View (members="data;"
			+ "entidadeMensuravel;"
			+ "resultado;"
			//+ " Medida [ medidaPlanoDeMedicao.medida.nome]; "
			//+ "Definição Operacional [ medidaPlanoDeMedicao.definicaoOperacionalDeMedida.nome ];"
			+ "Planejamento {planoDeMedicao;"
			+ "medidaPlanoDeMedicao;}"
			+ "Execucao { executorDaAnaliseDeMedicao,"
			+ "momentoRealDaAnaliseDeMedicao;"
			+ "definicaoOperacionalDeMedida; }"
			+ "Medicoes {medicao;};"
	),
	@View(name="Simple", members="resultado")
})
@Tabs({
	@Tab(properties="data, entidadeMensuravel.nome, executorDaAnaliseDeMedicao.nome, momentoRealDaAnaliseDeMedicao.nome")	
})
public class AnaliseDeMedicao {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; 
	
	@Stereotype("TEXT_AREA") 
	private String resultado;
	 
	private Date data;

	@ManyToOne
	@ReferenceView("Simple")
	//@Required
	private RecursoHumano executorDaAnaliseDeMedicao;
	
	@ManyToOne
	@ReferenceView("Simple")
	//@Required
	private AtividadeDeProjeto momentoRealDaAnaliseDeMedicao;
	
	@ManyToOne
	@ReferenceView("Simple")
	//@Required
	private DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida;

	@ManyToOne
	//@ReferenceView("Simple")
	@DescriptionsList(descriptionProperties="nome")
	private PlanoDeMedicao planoDeMedicao;
	
	@ManyToOne
	//@ReferenceView("Simple")//lincar com o de cima com depende e DescriptionList
	@DescriptionsList(
			descriptionProperties="medida.nome"
			,depends="planoDeMedicao"
			,condition="${planoDeMedicao.id} = ?"
			,order="${medida.nome} desc"
			)
	//@Required
	//ChooseReferenceAction se tentar com a outra visao
	private MedidaPlanoDeMedicao medidaPlanoDeMedicao;
	
	@ManyToOne
	@ReferenceView("Simple")
	//@Required
	private EntidadeMensuravel entidadeMensuravel;
	
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
	//@ListProperties("")
	private Collection<Medicao> medicao;
	
	//private BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso;
	
	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

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

	public DefinicaoOperacionalDeMedida getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(
			DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida) {
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}

	public PlanoDeMedicao getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(PlanoDeMedicao planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}

	public MedidaPlanoDeMedicao getMedidaPlanoDeMedicao() {
		return medidaPlanoDeMedicao;
	}

	public void setMedidaPlanoDeMedicao(MedidaPlanoDeMedicao medidaPlanoDeMedicao) {
		this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
	}

	public EntidadeMensuravel getEntidadeMensuravel() {
		return entidadeMensuravel;
	}

	public void setEntidadeMensuravel(EntidadeMensuravel entidadeMensuravel) {
		this.entidadeMensuravel = entidadeMensuravel;
	}

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}

	//@ManyToOne
	//@ReferenceView("Simple")
	//private Conclusao conclusaoAtribuida;
	
	public Conclusao getConclusaoAtribuida() {
		return conclusaoAtribuida;
	}

	public void setConclusaoAtribuida(Conclusao conclusaoAtribuida) {
		this.conclusaoAtribuida = conclusaoAtribuida;
	}
	
	//private ProcedimentoDeAnaliseDeMedicao procedimentoDeAnaliseDeMedicao;
	 
	//private Collection<MetodoAnalitico> metodoAnalitico;

	//private Collection<CriterioDeDecisao> criterioDeDecisao;
	 
	
	
	
}*/
 

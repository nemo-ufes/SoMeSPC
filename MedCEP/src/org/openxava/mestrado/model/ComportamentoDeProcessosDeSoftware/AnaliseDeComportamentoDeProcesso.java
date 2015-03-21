package org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware;

import javax.persistence.*;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.AnaliseDeMedicao.*;

//@Entity
@Views({
	@View (members="Analise De Medicao { analiseDeMedicao }," +
			"Registrar Baseline { baselineDeDesempenhoDeProcesso }," +
			"Determinar a Capacidade { capacidadeDeProcesso }"),
	//@View(name="PlanoMedicao", members="medida; definicaoOperacionalDeMedida"),
	//@View(name="Simple", members="medida.nome"),
	//@View(name="Medicao", members="medida.nome; medida; Definição Operacional [ definicaoOperacionalDeMedida.nome ]")
	//@View(name="Medicao", members="medida.nome")
})
//@Tab( properties = "medida.nome, planoDeMedicao.nome, definicaoOperacionalDeMedida.nome" )
public class AnaliseDeComportamentoDeProcesso {

	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@OneToOne
	//@Embedded
	@ReferenceView("CEP")
	@NoFrame
	private AnaliseDeMedicao analiseDeMedicao;
	
	public AnaliseDeMedicao getAnaliseDeMedicao() {
		return analiseDeMedicao;
	}

	public void setAnaliseDeMedicao(AnaliseDeMedicao analiseDeMedicao) {
		this.analiseDeMedicao = analiseDeMedicao;
	}
	
	
	@OneToOne
	@NoFrame
	//TODO: NewAction de ao add setar o processo padrão
	private BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso;

	public BaselineDeDesempenhoDeProcesso getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}
	
	
	@OneToOne
	@NoFrame
	private CapacidadeDeProcesso capacidadeDeProcesso;

	public CapacidadeDeProcesso getCapacidadeDeProcesso() {
		return capacidadeDeProcesso;
	}

	public void setCapacidadeDeProcesso(CapacidadeDeProcesso capacidadeDeProcesso) {
		this.capacidadeDeProcesso = capacidadeDeProcesso;
	}
	
}

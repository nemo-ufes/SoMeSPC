 package org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware;

 import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;

//TODO: caso o autorelacionamento "precede" fosse tratado seria melhor não usar o Embeddable
@Embeddable
@Views({
	@View(members="limiteInferior, limiteSuperior"),
})
public class LimiteDeControle {
 
/*	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}*/
 
	//@Required
	private float limiteInferior;
	
	
	private float limiteCentral;
	
	//@Required
	private float limiteSuperior;
	 
	//private BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso;
	
	/*
	@OneToOne
	//@PrimaryKeyJoinColumn
	private LimiteDeControle precedeLimiteDeControle;
	 */
	 
	//private DesempenhoDeProcessoEspecificado desempenhoDeProcessoEspecificado;

	public float getLimiteInferior() {
		return limiteInferior;
	}

	public void setLimiteInferior(float limiteInferior) {
		this.limiteInferior = limiteInferior;
	}

	public float getLimiteCentral() {
		return limiteCentral;
	}

	public void setLimiteCentral(float limiteCentral) {
		this.limiteCentral = limiteCentral;
	}

	public float getLimiteSuperior() {
		return limiteSuperior;
	}

	public void setLimiteSuperior(float limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}
/*
	public BaselineDeDesempenhoDeProcesso getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}*/

	/*
	public LimiteDeControle getPrecedeLimiteDeControle() {
		return precedeLimiteDeControle;
	}

	public void setPrecedeLimiteDeControle(LimiteDeControle precedeLimiteDeControle) {
		this.precedeLimiteDeControle = precedeLimiteDeControle;
	}
	*/
/*
	public DesempenhoDeProcessoEspecificado getDesempenhoDeProcessoEspecificado() {
		return desempenhoDeProcessoEspecificado;
	}

	public void setDesempenhoDeProcessoEspecificado(
			DesempenhoDeProcessoEspecificado desempenhoDeProcessoEspecificado) {
		this.desempenhoDeProcessoEspecificado = desempenhoDeProcessoEspecificado;
	}
	 */
	
	
}
 

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

package org.medcep.model.processo.comportamento;

 import javax.persistence.*;

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
 

package org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware;

import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;

/**
 * Primeira baseline de desempenho estabelecida para o
 * processo de Gerência de Requisitos, tendo sido o processo
 * executado em 6 projetos pequenos, cujas equipes foram
 * compostas pelos mesmos recursos humanos, sob
 * condições usuais tendo sido desconsiderados dois pontos
 * fora dos limites de controle, por caracterizarem situações
 * de ocorrência excepcional.
 */
@Entity
public class ContextoDeBaselineDeDesempenhoDeProcesso {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;   
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Stereotype("TEXT_AREA")
	private String descricao;
	 
/*	@OneToMany(mappedBy="contextoDeBaselineDeDesempenhoDeProcesso")
	private Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso;*/

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
/*
	public Collection<BaselineDeDesempenhoDeProcesso> getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}
*/	 
	
	
}
 

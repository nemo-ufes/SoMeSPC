package org.openxava.mestrado.model.CaracterizacaoDeProjetos;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.hibernate.validator.*;
import org.openxava.mestrado.actions.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;
import org.openxava.mestrado.validators.*;

@Entity
@Views({
	@View(members="projeto; criterio; valorMedido; "),
	@View(name="Projeto", members="criterio; valorMedido;")
})/*
@Tabs({
	@Tab(properties="nome, mnemonico, indicador", defaultOrder="${nome} asc")
})
@EntityValidator(
		value=MedidaValidator.class, 
		properties={
			@PropertyValue(name="tipoMedida"),
			@PropertyValue(name="elementoMensuravel")
		}
)*/
public class CriterioDeProjeto {

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
	@ReferenceView("Simple")
	private Projeto projeto;
	
	@ManyToOne
	@ReferenceView("Simple")
	@Required
	private Criterio criterio;
	
    @OneToOne(cascade=CascadeType.ALL)
	//@ManyToOne(cascade=CascadeType.REMOVE)
    //@PrimaryKeyJoinColumn
    @NoSearch
    //@NoFrame
    @OnChange(OnChangePropertyDoNothingValorMedido.class)
    //@OnChangeSearch(OnChangeSectionCEPAction.class)
    @Required
	private ValorMedido valorMedido;

	public ValorMedido getValorMedido() {
		return valorMedido;
	}

	public void setValorMedido(ValorMedido valorMedido) {
		this.valorMedido = valorMedido;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
	public Criterio getCriterio() {
		return criterio;
	}

	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}

	//Copiado de Medição
	@PreCreate
	//@PreUpdate
	public void ajusteValorMedido() 
	{
		ValorMedido valorMedidoAux = valorMedido;
		if(getCriterio().getEscala().isNumerico())
    	{
    		valorMedido = new ValorNumerico();
    		((ValorNumerico)valorMedido).setValorMedido(valorMedidoAux.getValorMedido());
    	}
		else
    	{
    		valorMedido = new ValorAlfanumerico();
    		((ValorAlfanumerico)valorMedido).setValorMedido(valorMedidoAux.getValorMedido()); 		
    	}
		//TODO: e se alterar a medida plano medição depois que a medição for criada?	
	}
}

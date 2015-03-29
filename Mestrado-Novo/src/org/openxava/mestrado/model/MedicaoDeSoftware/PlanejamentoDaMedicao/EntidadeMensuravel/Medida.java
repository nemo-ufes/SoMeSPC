package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import java.util.*;

import javax.persistence.*;
import javax.xml.ws.*;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.*;
import org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;
import org.openxava.mestrado.validators.*;

@Entity
@Views({
	@View(members="nome, mnemonico; "
			+"tipoMedida; "
			+"escala; "
			+"unidadeDeMedida; " 
			//+"listaObjetivos; "
			+"tipoDeEntidadeMedida; "
			//+"entidadeMedida; "
			+"elementoMensuravel; "
			+"medidasCorrelatas; "
			//+"definicaoOperacionalDeMedida; "
			+"Se a Medida é Derivada [ " 
			+"derivaDe; "
			+"calculadaPor; ]; "
			/*+"Detalhes para Medidas Derivadas { calculadaPor; "
			+"derivaDe; }; "
			+"Medidas Correlatas {"
			+"medidasCorrelatas; }"*/ //TODO: possivel bug do openxava, auto relacionamentos dentro de { } acarretam em um loop infinito 
	),
	@View(name="ForMedicao", members="nome, mnemonico; "
			//+"Entidade Mensurável [ entidadeMedida.nome; ];"
			+"Tipo de Entidade Mensurável [ tipoDeEntidadeMedida.nome; ];"
			+"Elemento Mensurável [ elementoMensuravel.nome; ]"
	),
	@View(name="Simple", members="nome"),
	@View(name="SimpleNoFrame", members="nome")
})
@Tabs({
	//@Tab(properties="nome, mnemonico, indicador", defaultOrder="${nome} asc")
	@Tab(properties="nome, mnemonico", defaultOrder="${nome} asc")
})
@EntityValidator(
		value=MedidaValidator.class, 
		properties={
			@PropertyValue(name="tipoMedida"),
			@PropertyValue(name="elementoMensuravel")
		}
)
public class Medida extends TreeItemPlanoMedicaoBase {
 
    @Column(length=500, unique=true) 
    @Required 
    private String nome;
	
	@Stereotype("TEXT_AREA") 
	@Column(columnDefinition="TEXT")
	private String descricao;

	@Column(length=6) 
	private String mnemonico;
	
	@ManyToOne 
	@Required 
	@NoCreate
	@NoModify
	//@Editor("ValidValuesRadioButton")
	@DescriptionsList(descriptionProperties="nome", order="${nome} asc")
	private TipoMedida tipoMedida;

/*
	@ManyToOne 
	@Required
	//@NoCreate
	//@NoModify
	//@NoFrame
	@ReferenceView("Simple")
	//@SearchAction("Medida.searchEntidadeMensuravelForMedida")
    private EntidadeMensuravel entidadeMedida;
	
	public EntidadeMensuravel getEntidadeMedida() {
		return entidadeMedida;
	}

	public void setEntidadeMedida(EntidadeMensuravel entidadeMedida) {
		this.entidadeMedida = entidadeMedida;
	}*/
	
	@ManyToOne 
	@DescriptionsList(descriptionProperties="nome", order="${nome} asc")
	@Required
	@NoFrame
	@ReferenceView("SimpleNoFrame")
    private TipoDeEntidadeMensuravel tipoDeEntidadeMedida;
	
	public TipoDeEntidadeMensuravel getTipoDeEntidadeMedida() {
		return tipoDeEntidadeMedida;
	}

	public void setTipoDeEntidadeMedida(
			TipoDeEntidadeMensuravel tipoDeEntidadeMedida) {
		this.tipoDeEntidadeMedida = tipoDeEntidadeMedida;
	}


	@ListProperties("nome")
	@OneToMany(mappedBy="medida")
	private Collection<DefinicaoOperacionalDeMedida> definicaoOperacionalDeMedida;

	public Collection<DefinicaoOperacionalDeMedida> getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(
			Collection<DefinicaoOperacionalDeMedida> definicaoOperacionalDeMedida) {
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}

	@ManyToOne
	@Required
	//@NoCreate
	//@NoModify
	@ReferenceView("Simple")
	@SearchAction("Medida.searchElementoMensuravelForMedida")
    private ElementoMensuravel elementoMensuravel;
	
	@ManyToOne 
	@DescriptionsList(descriptionProperties="nome") 
	@Required 
	private Escala escala;
	 
	@ManyToOne 
	@DescriptionsList(descriptionProperties="nome") 
	//@Required
	private UnidadeDeMedida unidadeDeMedida;
	
    @ManyToMany 
    @JoinTable(
	      name="medida_correlatas"
	      , joinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medida_id2")
	       }
	      )
    @ListProperties("nome, mnemonico")
	private Collection<Medida> medidasCorrelatas;	
    
    @ManyToMany 
    @JoinTable(
	      name="medida_derivaDe"
	      , joinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medida_id2")
	       }
	      )
    @ListProperties("nome, mnemonico")
	private Collection<Medida> derivaDe;
	
    public Collection<Medida> getDerivaDe() {
		return derivaDe;
	}

	public void setDerivaDe(Collection<Medida> derivaDe) {
		this.derivaDe = derivaDe;
	}

	public Collection<Medida> getMedidasCorrelatas() {
		return medidasCorrelatas;
	}

	public void setMedidasCorrelatas(Collection<Medida> medidasCorrelatas) {
		this.medidasCorrelatas = medidasCorrelatas;
	}

	@ManyToMany 
    @JoinTable(
	      name="medida_objetivo"
	      , joinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="objetivo_id")
	       }
	      )
	private Collection<Objetivo> listaObjetivos;	
	
	public Collection<Objetivo> getListaObjetivos() {
		return listaObjetivos;
	}

	public void setListaObjetivos(Collection<Objetivo> listaObjetivos) {
		this.listaObjetivos = listaObjetivos;
	}

    @ManyToMany
    @JoinTable(
	      name="medida_necessidadeDeInformacao"
	      , joinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="necessidadeDeInformacao_id")
	       }
	      )
    private Collection<NecessidadeDeInformacao> listaNecessidadeDeInformacao;


	public Collection<NecessidadeDeInformacao> getListaNecessidadeDeInformacao() {
		return listaNecessidadeDeInformacao;
	}

	public void setListaNecessidadeDeInformacao(Collection<NecessidadeDeInformacao> listaNecessidadeDeInformacao) {
		this.listaNecessidadeDeInformacao = listaNecessidadeDeInformacao;
	}	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMnemonico() {
		return mnemonico;
	}

	public void setMnemonico(String mnemonico) {
		this.mnemonico = mnemonico;
	}

	public TipoMedida getTipoMedida() {
		return tipoMedida;
	}

	public void setTipoMedida(TipoMedida tipo) {
		this.tipoMedida = tipo;
	}

	public ElementoMensuravel getElementoMensuravel() {
		return elementoMensuravel;
	}

	public void setElementoMensuravel(ElementoMensuravel elementoMensuravel) {
		this.elementoMensuravel = elementoMensuravel;
	}


    /*@OneToMany(mappedBy="medida") 
	private Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao;*/
	
/*	public Collection<MedidaPlanoDeMedicao> getMedidaPlanoDeMedicao() {
		return medidaPlanoDeMedicao;
	}

	public void setMedidaPlanoDeMedicao(Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao) {
		this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
	}*/
    
	//@OneToOne(cascade=CascadeType.REFRESH)
	//@OneToOne(cascade=CascadeType.REMOVE)
	@OneToMany(mappedBy="calcula", cascade = CascadeType.REMOVE)
	//@OneToMany(mappedBy="calcula")
	//@ListProperties("nome")
	//@OneToOne
    //@PrimaryKeyJoinColumn
   // @ReferenceView("Simple")
	private Collection<FormulaDeCalculoDeMedida> calculadaPor;
		
	public Collection<FormulaDeCalculoDeMedida> getCalculadaPor() {
		return calculadaPor;
	}

	public void setCalculadaPor(Collection<FormulaDeCalculoDeMedida> calculadaPor) {
		this.calculadaPor = calculadaPor;
	}

	public Escala getEscala() {
		return escala;
	}

	public void setEscala(Escala escala) {
		this.escala = escala;
	}

	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}
	
	public boolean isIndicador(){
		return (listaObjetivos != null && listaObjetivos.size() > 0) || (listaNecessidadeDeInformacao != null && listaNecessidadeDeInformacao.size() > 0);
	}
	
/*	@OneToMany(mappedBy="medida")
	private Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso;

	public Collection<BaselineDeDesempenhoDeProcesso> getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}*/
    
/*	@OneToMany(mappedBy="medida")
	private Collection<CapacidadeDeProcesso> capacidadeDeProcesso;*/
	
/*	@OneToMany(mappedBy="medida")
	private Collection<ModeloPreditivo> modeloPreditivo;*/

/*	public Collection<CapacidadeDeProcesso> getCapacidadeDeProcesso() {
		return capacidadeDeProcesso;
	}

	public void setCapacidadeDeProcesso(
			Collection<CapacidadeDeProcesso> capacidadeDeProcesso) {
		this.capacidadeDeProcesso = capacidadeDeProcesso;
	}*/

/*	public Collection<ModeloPreditivo> getModeloPreditivo() {
		return modeloPreditivo;
	}

	public void setModeloPreditivo(Collection<ModeloPreditivo> modeloPreditivo) {
		this.modeloPreditivo = modeloPreditivo;
	}*/
	
	
	
}
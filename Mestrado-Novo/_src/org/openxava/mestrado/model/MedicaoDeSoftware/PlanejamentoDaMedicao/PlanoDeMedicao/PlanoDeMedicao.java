package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao;

import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View (members="nome, versao, data; descricao; recursoHumano; objetivos { objetivoEstrategico; objetivoDeSoftware; objetivoDeMedicao; necessidadeDeInformacao; } MedidasDoPlano { medidaPlanoDeMedicao }"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc")	
})
public class PlanoDeMedicao {

	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
    @Column(length=500, unique=true) @Required
	private String nome;

	private Date data;
	 
	@Stereotype("TEXT_AREA")
	private String descricao;
	 
	private String versao;
	 
    @ManyToMany 
    @JoinTable(
  	      name="recursoHumano_planoDeMedicao"
  	      , joinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="recursoHumano_id")
  	       }
  	      )
	private Collection<RecursoHumano> recursoHumano;
    
    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_objetivoDeMedicao"
  	      , joinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="objetivoDeMedicao_id")
  	       }
  	      )
    private Collection<ObjetivoDeMedicao> objetivoDeMedicao;
	 
    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_objetivoEstrategico"
  	      , joinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="objetivoEstrategico_id")
  	       }
  	      )
	private Collection<ObjetivoEstrategico> objetivoEstrategico;
	 
    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_objetivoDeSoftware"
  	      , joinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="objetivoDeSoftware_id")
  	       }
  	      )
	private Collection<ObjetivoDeSoftware> objetivoDeSoftware;
	 
    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_necessidadeDeInformacao"
  	      , joinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="necessidadeDeInformacao_id")
  	       }
  	      )
	private Collection<NecessidadeDeInformacao> necessidadeDeInformacao;
	 
    @OneToMany(mappedBy="planoDeMedicao", cascade=CascadeType.REMOVE)
    @ListProperties("medida.nome, definicaoOperacionalDeMedida.nome")
    @CollectionView("PlanoMedicao")
	private Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public Collection<RecursoHumano> getRecursoHumano() {
		return recursoHumano;
	}

	public void setRecursoHumano(Collection<RecursoHumano> recursoHumano) {
		this.recursoHumano = recursoHumano;
	}

	public Collection<ObjetivoDeMedicao> getObjetivoDeMedicao() {
		return objetivoDeMedicao;
	}

	public void setObjetivoDeMedicao(Collection<ObjetivoDeMedicao> objetivoDeMedicao) {
		this.objetivoDeMedicao = objetivoDeMedicao;
	}

	public Collection<ObjetivoEstrategico> getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	public void setObjetivoEstrategico(
			Collection<ObjetivoEstrategico> objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}

	public Collection<ObjetivoDeSoftware> getObjetivoDeSoftware() {
		return objetivoDeSoftware;
	}

	public void setObjetivoDeSoftware(
			Collection<ObjetivoDeSoftware> objetivoDeSoftware) {
		this.objetivoDeSoftware = objetivoDeSoftware;
	}

	public Collection<NecessidadeDeInformacao> getNecessidadeDeInformacao() {
		return necessidadeDeInformacao;
	}

	public void setNecessidadeDeInformacao(
			Collection<NecessidadeDeInformacao> necessidadeDeInformacao) {
		this.necessidadeDeInformacao = necessidadeDeInformacao;
	}

	public Collection<MedidaPlanoDeMedicao> getMedidaPlanoDeMedicao() {
		return medidaPlanoDeMedicao;
	}

	public void setMedidaPlanoDeMedicao(
			Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao) {
		this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
	}
	
    
    
	/*private Collection<Medicao> medicao;
	
	private Collection<AnaliseDeMedicao> analiseDeMedicao;*/
	 
}
 

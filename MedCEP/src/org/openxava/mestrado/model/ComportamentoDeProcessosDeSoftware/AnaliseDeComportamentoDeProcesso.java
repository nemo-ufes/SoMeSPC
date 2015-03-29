package org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware;

import java.util.*;

import javax.persistence.*;
import javax.xml.ws.*;

import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.AnaliseDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;
import org.openxava.mestrado.validators.*;

@Entity
@Views({
	@View (members="processoPadrao; medida; CEPchart; li; lc; ls;"
			//+ "analiseDeMedicao; "
			+ "1AnaliseDeMedicao { analiseDeMedicao }, "
			+ "2BaselineDeDesempenhoDoProcesso { baselineDeDesempenhoDeProcesso },"
			+ "3CapacidadeDoProcesso { capacidadeDeProcesso }"
			)
})
@Tab( properties = "processoPadrao.nome, medida.nome, capacidadeDeProcesso.data" )
public class AnaliseDeComportamentoDeProcesso {

	//TODO: para o graficar sempre atualziar, mandar salvar após cada search ou add (isso n resolve)
	// mas apos o add de capacidade resolve o problema de recalcular a capacidade
	//colocar botao de javascrip pra dar reload
	
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Transient
	@ManyToMany
	@NoCreate
	@NoModify
	private Collection<CepChart> CEPchart;
		
	public Collection<CepChart> getCEPchart() {
		return CEPchart;
	}

	public void setCEPchart(Collection<CepChart> CEPchart) {
		this.CEPchart = CEPchart;
	}

	@Required
	@NoCreate
	@ManyToOne
	//@NoFrame
	@ReferenceView("Simple")
	//@DescriptionsList(descriptionProperties="nome")
	private ProcessoPadrao processoPadrao;
		
	public ProcessoPadrao getProcessoPadrao() {
		return processoPadrao;
	}

	public void setProcessoPadrao(ProcessoPadrao processoPadrao) {
		this.processoPadrao = processoPadrao;
	}
	
	@Required
	@NoCreate
	@ManyToOne
/*	@DescriptionsList(
			descriptionProperties="nome, mnemonico"
			,depends="processoPadrao"
			,condition="${entidadeMedida.id} = ?"
			,order="${nome} asc"
			)*/
	//@NoFrame
	@ReferenceView("Simple")
	@SearchAction("AnaliseDeComportamentoDeProcesso.searchMedida")
	private Medida medida;
	
	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	@OneToOne
	@NoFrame
	//@NoCreate
	@ReferenceView("CEP")
	@SearchAction("AnaliseDeComportamentoDeProcesso.searchAnaliseDeMedicao")
	private AnaliseDeMedicao analiseDeMedicao;
	
	public AnaliseDeMedicao getAnaliseDeMedicao() {
		return analiseDeMedicao;
	}

	public void setAnaliseDeMedicao(AnaliseDeMedicao analiseDeMedicao) {
		this.analiseDeMedicao = analiseDeMedicao;
	}
	
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@NoFrame
	@NoSearch
	private BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso;

	public BaselineDeDesempenhoDeProcesso getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}
	
	/*
	@ManyToOne
	@NoFrame
	@SearchAction("AnaliseDeComportamentoDeProcesso.SearchWhereProcessoPadraoEMedidaForCEP")
	@NoCreate
	private DesempenhoDeProcessoEspecificado desempenhoDeProcessoEspecificado;
	
	public DesempenhoDeProcessoEspecificado getDesempenhoDeProcessoEspecificado() {
		return desempenhoDeProcessoEspecificado;
	}

	public void setDesempenhoDeProcessoEspecificado(
			DesempenhoDeProcessoEspecificado desempenhoDeProcessoEspecificado) {
		this.desempenhoDeProcessoEspecificado = desempenhoDeProcessoEspecificado;
	}*/
	

	@OneToOne(cascade=CascadeType.REMOVE)
	@NoFrame
	@NoSearch
	private CapacidadeDeProcesso capacidadeDeProcesso;

	public CapacidadeDeProcesso getCapacidadeDeProcesso() {
		return capacidadeDeProcesso;
	}

	public void setCapacidadeDeProcesso(CapacidadeDeProcesso capacidadeDeProcesso) {
		this.capacidadeDeProcesso = capacidadeDeProcesso;
	}
	

	@PreCreate
	@PreUpdate
	public void ajustes()
	{
		if(baselineDeDesempenhoDeProcesso != null)
		{
			baselineDeDesempenhoDeProcesso.setProcessoPadrao(processoPadrao);
			baselineDeDesempenhoDeProcesso.setMedida(medida);
			baselineDeDesempenhoDeProcesso.setLimiteDeControle((LimiteDeControle)retornaLimites());
			
			if(capacidadeDeProcesso != null)
			{
				capacidadeDeProcesso.setProcessoPadrao(processoPadrao);
				capacidadeDeProcesso.setMedida(medida);
				capacidadeDeProcesso.setBaselineDeDesempenhoDeProcesso(baselineDeDesempenhoDeProcesso);
			}
		}
	}

	
	public Object retornaLimites()
	{
		if(analiseDeMedicao != null && analiseDeMedicao.getMedicao() != null && analiseDeMedicao.getMedicao().size() > 0)
		{
			//float somaX = 0;
			//float somaX2 = 0; 
			//float media = 0;
			//float media2 = 0;
			List<Float> dados = new ArrayList<Float>();
			
			for (Medicao medicao : analiseDeMedicao.getMedicao()) 
			{
				if(medicao.getValorMedido() instanceof ValorNumerico)
				{
					//somaX += ((ValorNumerico)medicao.getValorMedido()).getValorNumerico();
					//somaX2 += Math.pow(((ValorNumerico)medicao.getValorMedido()).getValorNumerico(), 2);
					Float x = new Float(((ValorNumerico)medicao.getValorMedido()).getValorNumerico());
					
					//Long x1 = Long.getLong(medicao.getValorMedido().getValorMedido().replace("", "."));
					//Long x2 = Long.getLong(medicao.getValorMedido().getValorMedido());
					//Long x3 = new Long(Long.parseLong(medicao.getValorMedido().getValorMedido()));
					//Long x4 = new Long(Long.parseLong(medicao.getValorMedido().getValorMedido().replace(",", ".")));
					dados.add(x);
				}
			}
			///media = somaX/analiseDeMedicao.getMedicao().size();
			//media2 = (float) Math.pow(media, 2);
			
			double LC = mediaAritimetica(dados);
			double sigma = desvioPadrao(dados);
			double LS = LC + 3*sigma;
			double LI = LC - 3*sigma;
			
			LimiteDeControle l = new LimiteDeControle();
			l.setLimiteCentral((float) LC);
			l.setLimiteSuperior((float) LS);
			l.setLimiteInferior((float) LI);
			
			return l;
		}
		return null;
	}
	
    private double desvioPadrao(List<Float> objetos) 
    {  
        if (objetos.size() == 1) {  
            return 0.0;  
        } else {  
            double mediaAritimetica = mediaAritimetica(objetos);  
            double somatorio = 0;  
            for (int i = 0; i < objetos.size(); i++) {  
                double result = objetos.get(i) - mediaAritimetica;  
                somatorio = somatorio + result * result;  
            }  
            return Math.sqrt(((double) 1 /( objetos.size()-1))  
                    * somatorio);  
        }  
    }    
  
    private double mediaAritimetica(List<Float> objetos) 
    {  
        double somatorio = 0;  
        for (Float d : objetos) {  
            somatorio += d.doubleValue();  
        }  
        return somatorio / objetos.size();  
    } 
	
    public String getLi()
    {
    	LimiteDeControle l = (LimiteDeControle)retornaLimites();
    	
    	return String.format("%.2f", l.getLimiteInferior());
    }
    
    public String getLc()
    {
    	LimiteDeControle l = (LimiteDeControle)retornaLimites();
    	
    	return String.format("%.2f", l.getLimiteCentral());
    }
    
    public String getLs()
    {
    	LimiteDeControle l = (LimiteDeControle)retornaLimites();
    	
    	return String.format("%.2f", l.getLimiteSuperior());
    }
}

package org.somespc.webservices.rest.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemPlanoDeMedicaoDTO {

	    
		public ItemPlanoDeMedicaoDTO(String oE, String oM, String NI, String medida) {
			this.medida = medida;
			this.nomeObjetivoEstrategico = oE;
			this.nomeObjetivoDeMedicao = oM;
			this.nomeNecessidadeDeInformacao = NI;			
		}
		
		public ItemPlanoDeMedicaoDTO() {
			// TODO Auto-generated constructor stub
		}
		
		@XmlElement(name = "nome_Medida")
	    private String medida;
	    
	    @XmlElement(name="nome_ObjetivoEstrategico")
	    private String nomeObjetivoEstrategico;
	    
	    @XmlElement(name="nome_ObjetivoDeMedicao")
	    private String nomeObjetivoDeMedicao;
	    
	    @XmlElement(name="nome_NecessidadeDeInformacao")
	    private String nomeNecessidadeDeInformacao;

		public String getMedida() {
			return medida;
		}

		public void setMedida(String medida) {
			this.medida = medida;
		}

		public String getNomeObjetivoEstrategico() {
			return nomeObjetivoEstrategico;
		}

		public void setNomeObjetivoEstrategico(String nomeObjetivoEstrategico) {
			this.nomeObjetivoEstrategico = nomeObjetivoEstrategico;
		}

		public String getNomeObjetivoDeMedicao() {
			return nomeObjetivoDeMedicao;
		}

		public void setNomeObjetivoDeMedicao(String nomeObjetivoDeMedicao) {
			this.nomeObjetivoDeMedicao = nomeObjetivoDeMedicao;
		}

		public String getNomeNecessidadeDeInformacao() {
			return nomeNecessidadeDeInformacao;
		}

		public void setNomeNecessidadeDeInformacao(String nomeNecessidadeDeInformacao) {
			this.nomeNecessidadeDeInformacao = nomeNecessidadeDeInformacao;
		}
	    
	    

}

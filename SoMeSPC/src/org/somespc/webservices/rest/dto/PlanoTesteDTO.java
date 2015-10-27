package org.somespc.webservices.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class PlanoTesteDTO {
	
	 	@XmlElement(name = "taiga_Login")
	    private TaigaLoginDTO taigaLogin;

	    @XmlElement(name = "apelido_Projetos")
	    private List<String> apelidosProjetos;

	    @XmlElement(name = "nome_Periodicidade")
	    private String nomePeriodicidade;

		public TaigaLoginDTO getTaigaLogin() {
			return taigaLogin;
		}

		public void setTaigaLogin(TaigaLoginDTO taigaLogin) {
			this.taigaLogin = taigaLogin;
		}

		public List<String> getApelidosProjetos() {
			return apelidosProjetos;
		}

		public void setApelidosProjetos(List<String> apelidosProjetos) {
			this.apelidosProjetos = apelidosProjetos;
		}

		public String getNomePeriodicidade() {
			return nomePeriodicidade;
		}

		public void setNomePeriodicidade(String nomePeriodicidade) {
			this.nomePeriodicidade = nomePeriodicidade;
		}
	    
	    

}

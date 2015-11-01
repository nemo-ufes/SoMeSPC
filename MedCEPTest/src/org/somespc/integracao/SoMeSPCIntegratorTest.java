package org.somespc.integracao;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.somespc.inicializacao.SoMeSPCStarter;
import org.somespc.integracao.sonarqube.SonarQubeIntegrator;
import org.somespc.integracao.sonarqube.model.Recurso;
import org.somespc.integracao.taiga.TaigaIntegrator;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.plano_de_medicao.PlanoDeMedicao;
import org.somespc.webservices.rest.dto.ItemPlanoDeMedicaoDTO;
import org.somespc.webservices.rest.dto.SonarLoginDTO;
import org.somespc.webservices.rest.dto.TaigaLoginDTO;

public class SoMeSPCIntegratorTest {
	
	private TaigaIntegrator taigaIntegrator;
	private SonarQubeIntegrator sonarIntegrator;

	@Before
	public void init() throws Exception {
		SoMeSPCStarter.inicializarSoMeSPC();
		taigaIntegrator = new TaigaIntegrator("https://api.taiga.io/", "vinnysoft", "teste123");
		sonarIntegrator = new SonarQubeIntegrator("http://localhost:9000/");
	}
	
	@Test
	public void testCriarPlanoMedicaoConjunto() throws Exception {
		
		SonarLoginDTO sonarLogin = new SonarLoginDTO();
		sonarLogin.setUrl("http://localhost:9000/");
		
		TaigaLoginDTO taigaLogin = new TaigaLoginDTO();
		taigaLogin.setUrl("https://api.taiga.io/");
		taigaLogin.setUsuario("vinnysoft");
		taigaLogin.setSenha("teste123");
		
		List<Periodicidade> periodicidades = SoMeSPCIntegrator.obterPeriodicidades();		
		Periodicidade periodicidadeSelecionada = null;

		for (Periodicidade periodicidade : periodicidades) {
			if (periodicidade.getNome().equalsIgnoreCase("Por Hora"))
				periodicidadeSelecionada = periodicidade;
		}
		String OE = "Melhorar o gerenciamento dos projetos de software da organização";
		
		String OM_1 = "Melhorar a aderência ao planejamento de pontos de estória nos projetos";
		String OM_7 = "Monitorar a qualidade do código fonte produzido";
    	
	    List<ItemPlanoDeMedicaoDTO> itensPlanoDeMedicao = new ArrayList<ItemPlanoDeMedicaoDTO>();
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_1, "Quantos pontos de estória foram planejados para o projeto?", "Pontos de Estória Planejados para o Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_1, "Quantos pontos de estória foram concluídos no projeto?", "Pontos de Estória Concluídos no Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_1, "Qual a taxa de conclusão de pontos de estória no projeto?", "Taxa de Conclusão de Pontos de Estória no Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_7, "Qual a complexidade ciclomática média por método?", "Média da Complexidade Ciclomática por Método", "SonarQube"));
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_7, "Qual a taxa de duplicação de código?", "Taxa de Duplicação de Código", "SonarQube"));
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_7, "Qual o percentual da dívida técnica?", "Percentual da Dívida Técnica", "SonarQube"));
	
		Recurso projetoSonar = sonarIntegrator.obterRecurso("SoMeSPC");
		Projeto projeto = taigaIntegrator.obterProjetoTaiga("almereyda-jon30");
				
		PlanoDeMedicao plano = SoMeSPCIntegrator.criarPlanoMedicaoProjetoTaigaSonarQubeSoMeSPC(itensPlanoDeMedicao, 
				periodicidadeSelecionada, taigaLogin, projeto, sonarLogin, Arrays.asList(projetoSonar));
		
		assertNotNull(plano);
				
		//dump(plano);		
	}

		
}

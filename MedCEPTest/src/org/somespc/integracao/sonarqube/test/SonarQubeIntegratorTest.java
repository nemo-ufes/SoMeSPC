/*
 * SoMeSPC - powerful tool for measurement
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.somespc.integracao.sonarqube.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.somespc.inicializacao.SoMeSPCStarter;
import org.somespc.integracao.SoMeSPCIntegrator;
import org.somespc.integracao.sonarqube.SonarQubeIntegrator;
import org.somespc.integracao.sonarqube.model.Medida;
import org.somespc.integracao.sonarqube.model.Metrica;
import org.somespc.integracao.sonarqube.model.Recurso;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.plano_de_medicao.PlanoDeMedicao;
import org.somespc.webservices.rest.dto.ItemPlanoDeMedicaoDTO;
import org.somespc.webservices.rest.dto.SonarLoginDTO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public class SonarQubeIntegratorTest {

	private SonarQubeIntegrator integrator;

	@Before
	public void init() throws Exception {
		SoMeSPCStarter.inicializarSoMeSPC();
		integrator = new SonarQubeIntegrator("http://localhost:9000/");
	}

	@Test
	public void testObterProjetos() {
		List<Recurso> projetos = integrator.obterProjetos();

		assertNotNull(projetos);
		assertNotEquals(projetos.size(), 0);

		dump(projetos);
	}
	
	@Test
	public void obterMetricasJobMedicao(){
		
		Recurso recurso = integrator.obterRecurso("SoMeSPC");
		Metrica functionComplexity = new Metrica();
		functionComplexity.setChave("function_complexity");
				
		Metrica duplicatedLinesDensity = new Metrica();
		duplicatedLinesDensity.setChave("duplicated_lines_density");
		
		Metrica sqaleDebtRatio = new Metrica();
		sqaleDebtRatio.setChave("sqale_debt_ratio");
		
		ArrayList<Metrica> metricas = new ArrayList<Metrica>();
		metricas.add(functionComplexity);
		metricas.add(duplicatedLinesDensity);
		metricas.add(sqaleDebtRatio);
		
		List<Medida> medidas = integrator.obterMedidasDoRecurso(metricas, recurso);
		
		assertNotNull(medidas);
		
		System.out.println("Medidas: ");
		dump(medidas);
	}
	
	@Ignore
	@Test
	public void testObterRecursosDoProjeto() {
		List<Recurso> recursos = integrator.obterRecursosDoProjeto("SoMeSPC");

		assertNotNull(recursos);
		assertNotEquals(recursos.size(), 0);

		dump(recursos);
	}

	@Ignore
	@Test
	public void testObterMetricas() {
		List<Metrica> metricas = integrator.obterMetricas();

		assertNotNull(metricas);
		assertNotEquals(metricas.size(), 0);

		dump(metricas);
	}

	@Ignore
	@Test
	public void testObterMedidasDoRecurso() {
		List<Metrica> metricas = integrator.obterMetricas();
		Recurso recurso = integrator.obterRecurso("SoMeSPC");

		List<org.somespc.integracao.sonarqube.model.Medida> medidas = integrator.obterMedidasDoRecurso(metricas,
				recurso);

		assertNotNull(medidas);
		assertNotEquals(medidas.size(), 0);

		dump(medidas);
	}
	
	@Test
	public void testCriarPlanoMedicao() throws Exception {
		
		SonarLoginDTO login = new SonarLoginDTO();
		login.setUrl("http://localhost:9000/");
		
		List<Periodicidade> periodicidades = SoMeSPCIntegrator.obterPeriodicidades();		
		Periodicidade periodicidadeSelecionada = null;

		for (Periodicidade periodicidade : periodicidades) {
			if (periodicidade.getNome().equalsIgnoreCase("Por Hora"))
				periodicidadeSelecionada = periodicidade;
		}
		String OE = "Melhorar o gerenciamento dos projetos de software da organização";
		
		String OM_7 = "Monitorar a qualidade do código fonte produzido";
    	
	    List<ItemPlanoDeMedicaoDTO> itensPlanoDeMedicao = new ArrayList<ItemPlanoDeMedicaoDTO>();
		
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_7, "Qual a complexidade ciclomática média por método?", "Média da Complexidade Ciclomática por Método", "SonarQube"));
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_7, "Qual a taxa de duplicação de código?", "Taxa de Duplicação de Código", "SonarQube"));
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_7, "Qual o percentual da dívida técnica?", "Percentual da Dívida Técnica", "SonarQube"));
	
		Recurso projetoSonar = integrator.obterRecurso("SoMeSPC");
		
		PlanoDeMedicao plano = SoMeSPCIntegrator.criarPlanoMedicaoProjetoSonarQubeSoMeSPC(itensPlanoDeMedicao, periodicidadeSelecionada, login, projetoSonar);
		
		assertNotNull(plano);
				
		//dump(plano);		
	}
	
	private void dump(Object object) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		System.out.println(xstream.toXML(object));
	}

}

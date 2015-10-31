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
package org.somespc.integracao.taiga.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.*;
import org.somespc.inicializacao.SoMeSPCStarter;
import org.somespc.integracao.SoMeSPCIntegrator;
import org.somespc.integracao.taiga.MedidasTaiga;
import org.somespc.integracao.taiga.TaigaIntegrator;
import org.somespc.integracao.taiga.model.EstadoProjeto;
import org.somespc.integracao.taiga.model.EstadoSprint;
import org.somespc.integracao.taiga.model.Estoria;
import org.somespc.integracao.taiga.model.Membro;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.integracao.taiga.model.Sprint;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.entidades_e_medidas.Medida;
import org.somespc.model.organizacao_de_software.Equipe;
import org.somespc.model.organizacao_de_software.PapelRecursoHumano;
import org.somespc.model.organizacao_de_software.RecursoHumano;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDaOrganizacao;
import org.somespc.model.processo_de_software.Artefato;
import org.somespc.model.processo_de_software.AtividadeProjeto;
import org.somespc.model.processo_de_software.ProcessoPadrao;
import org.somespc.model.processo_de_software.ProcessoProjeto;
import org.somespc.model.processo_de_software.TipoDeArtefato;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.json.*;

public class TaigaIntegratorTest {

	private TaigaIntegrator integrator;

	@Before
	public void init() throws Exception {
		SoMeSPCStarter.inicializarSoMeSPC();
		integrator = new TaigaIntegrator("https://api.taiga.io/", "vinnysoft", "teste123");
	}

	@Test
	public void testObterAuthToken() {
		String token = integrator.obterAuthToken();
		System.out.println("token: " + token);

		assertNotNull(token);
		assertNotEquals(token, "");
	}

	@Test
	public void testObterProjeto() {
		Projeto projeto = integrator.obterProjetoTaiga("paflopes-sincap");

		assertNotNull(projeto);
		
		dump(projeto);
	}

	@Test
	public void testObterSprintsDoProjetoTaiga() {
		List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga("paflopes-sincap");

		assertNotNull(sprints);

		dump(sprints);
	}

	@Test
	public void testObterEstadoSprintTaiga() {
		EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga("paflopes-sincap", "sprint-19");

		assertNotNull(estadoSprint);

		dump(estadoSprint);
	}

	@Test
	public void testObterEstadoProjetoTaiga() {
		EstadoProjeto estado = integrator.obterEstadoProjetoTaiga("paflopes-sincap");

		assertNotNull(estado);

		dump(estado);
	}

	@Test
	public void testObterProjetos() {
		List<Projeto> projetos = integrator.obterProjetosTaiga();

		assertNotNull(projetos);
		assertNotEquals(projetos.size(), 0);

		dump(projetos);
	}

	@Test
	public void testObterMembro() {
		Membro membro = integrator.obterMembroTaiga(8);

		assertNotNull(membro);
		assertNotEquals(membro.getId(), 0);

		dump(membro);
	}

		@Test
	public void testCriarRecursoHumanoSoMeSPC() throws Exception {
		Membro membro = integrator.obterMembroTaiga(4);

		assertNotNull(membro);
		assertNotEquals(membro.getId(), 0);

		dump(membro);

		RecursoHumano recurso = integrator.criarRecursoHumanoSoMeSPC(membro);

		assertNotNull(recurso);

		dump(recurso);
	}

	@Test
	public void testCriarPapelRecursoHumanoSoMeSPC() throws Exception {
		Membro membro = integrator.obterMembroTaiga(4);

		assertNotNull(membro);
		assertNotEquals(membro.getId(), 0);

		dump(membro);

		PapelRecursoHumano papel = integrator.criarPapelRecursoHumanoSoMeSPC(membro);

		assertNotNull(papel);

		dump(papel);
	}

	@Test
	public void testCriarEquipeSoMeSPC() throws Exception {
		Projeto sincap = integrator.obterProjetoTaiga("paflopes-sincap");

		assertNotNull(sincap);
		assertNotNull(sincap.getEquipe());

		dump(sincap);

		Equipe equipe = integrator.criarEquipeSoMeSPC("Equipe " + sincap.getNome(), sincap.getEquipe());

		assertNotNull(equipe);

		dump(equipe);
	}

	@Test
	public void testCriarProjetoSoMeSPC() throws Exception {
		Projeto sincap = integrator.obterProjetoTaiga("paflopes-sincap");

		assertNotNull(sincap);

		dump(sincap);

		org.somespc.model.organizacao_de_software.Projeto projeto = integrator.criarProjetoSoMeSPC(sincap);

		assertNotNull(projeto);
		assertNotEquals(projeto.getId(), "");

		dump(projeto);
	}

	@Test
	public void testCriarMedidasSoMeSPC() throws Exception {

//		
//		MedidasTaiga[] medidasTaiga = MedidasTaiga.PONTOS_ALOCADOS_PROJETO.getDeclaringClass().getEnumConstants();
//
//		List<Medida> medidas = integrator.criarMedidasSoMeSPC(new ArrayList<MedidasTaiga>(Arrays.asList(medidasTaiga)));
//
//		assertNotNull(medidas);
//		assertNotEquals(medidas.size(), 0);
//		assertTrue(medidas.size() > 0);

		// dump(medidas);
	}

	@Test
	public void testCriarPlanoMedicaoProjetoSoMeSPC() throws Exception {
		// Projeto projeto =
		// integrator.obterProjetoTaiga("paulossjunior-lifebox");
		Projeto projeto = integrator.obterProjetoTaiga("paflopes-sincap");

		List<Periodicidade> periodicidades = SoMeSPCIntegrator.obterPeriodicidades();
		Periodicidade porHora = null;
		for (Periodicidade periodicidade : periodicidades) {
			if (periodicidade.getNome().equalsIgnoreCase("Por Hora")) {
				porHora = periodicidade;
				break;
			}
		}

//		MedidasTaiga[] medidasTaiga = MedidasTaiga.PONTOS_ALOCADOS_PROJETO.getDeclaringClass().getEnumConstants();
		//integrator.criarPlanoMedicaoProjetoSoMeSPC(new ArrayList<MedidasTaiga>(Arrays.asList(medidasTaiga)), porHora, projeto);
	}

	private void dump(Object object) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		System.out.println(xstream.toXML(object));
	}

}

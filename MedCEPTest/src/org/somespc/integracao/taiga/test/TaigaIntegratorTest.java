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

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.somespc.inicializacao.SoMeSPCStarter;
import org.somespc.integracao.taiga.TaigaIntegrator;
import org.somespc.integracao.taiga.model.EstadoProjeto;
import org.somespc.integracao.taiga.model.EstadoSprint;
import org.somespc.integracao.taiga.model.Estoria;
import org.somespc.integracao.taiga.model.Membro;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.integracao.taiga.model.Sprint;
import org.somespc.integracao.taiga.model.Tarefa;
import org.somespc.model.organizacao_de_software.Equipe;
import org.somespc.model.organizacao_de_software.PapelRecursoHumano;
import org.somespc.model.organizacao_de_software.RecursoHumano;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

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
		Projeto projeto = integrator.obterProjetoTaiga("ser515asu-agiletweetviz-geekoh");

		assertNotNull(projeto);

		dump(projeto);
	}

	@Test
	public void testObterSprintsDoProjetoTaiga() {
		List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga("ser515asu-agiletweetviz-geekoh");

		assertNotNull(sprints);

		dump(sprints);
	}

	@Test
	public void testObterEstadoSprintTaiga() {
		// EstadoSprint estadoSprint =
		// integrator.obterEstadoSprintTaiga("almereyda-jon30",
		// "instantiation-delayed");
		EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga("ser515asu-agiletweetviz-geekoh", "spring-3-5");
		// EstadoSprint estadoSprint =
		// integrator.obterEstadoSprintTaiga("almereyda-jon30",
		// "einladungen-fortsetzung-besorgungen-und-feinschliff");

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
	public void testObterEstorias() {
		List<Estoria> estorias = integrator.obterEstoriasDoProjectBacklogTaiga("ser515asu-agiletweetviz-geekoh");
		List<Estoria> estorias2 = integrator.obterEstoriasDaSprintBacklogTaiga("ser515asu-agiletweetviz-geekoh",  "spring-3-5");

		assertNotNull(estorias);

		dump(estorias2);
	}
	
	@Test
	public void testObterTarefas() {
		List<Tarefa> tarefas = integrator.obterTarefasDoProjeto("ser515asu-agiletweetviz-geekoh");
		
		assertNotNull(tarefas);

		dump(tarefas);
	}

	@Test
	public void testObterMembro() {
		Membro membro = integrator.obterMembroTaiga(8);

		assertNotNull(membro);
		assertNotEquals(membro.getId(), 0);

		dump(membro);
	}
	
	@Test
	public void testObterMembros() {
		List<Membro> membros = integrator.obterMembrosDoProjetoTaiga("ser515asu-agiletweetviz-geekoh");

		assertNotNull(membros);
		
		dump(membros);
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
		Projeto sincap = integrator.obterProjetoTaiga("ser515asu-agiletweetviz-geekoh");

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
	public void testMedidaNTAM() throws Exception{
		
		List<Membro> membros = integrator.obterMembrosDoProjetoTaiga("almereyda-jon30");
		
		assertNotNull(membros);
				
		for(Membro membro : membros) {					
			dump(membro);
			integrator.criarRecursoHumanoSoMeSPC(membro);
		}
		
	}

	private void dump(Object object) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		System.out.println(xstream.toXML(object));
	}

}

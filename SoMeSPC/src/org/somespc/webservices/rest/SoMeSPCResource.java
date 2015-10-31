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
package org.somespc.webservices.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.NullArgumentException;
import org.openxava.jpa.XPersistence;
import org.somespc.integracao.SoMeSPCIntegrator;
import org.somespc.integracao.sonarqube.SonarQubeIntegrator;
import org.somespc.integracao.sonarqube.model.MedidasSonarQube;
import org.somespc.integracao.sonarqube.model.Recurso;
import org.somespc.integracao.taiga.TaigaIntegrator;
import org.somespc.integracao.taiga.model.MedidasTaiga;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.Medida;
import org.somespc.model.medicao.Medicao;
import org.somespc.model.plano_de_medicao.PlanoDeMedicao;
import org.somespc.util.json.JSONObject;
import org.somespc.webservices.rest.dto.EntidadeMensuravelDTO;
import org.somespc.webservices.rest.dto.ItemPlanoDeMedicaoDTO;
import org.somespc.webservices.rest.dto.MedicaoDTO;
import org.somespc.webservices.rest.dto.MedidaDTO;
import org.somespc.webservices.rest.dto.PeriodicidadeDTO;
import org.somespc.webservices.rest.dto.PlanoDTO;
import org.somespc.webservices.rest.dto.SonarLoginDTO;

/**
 * API REST para os recursos da SoMeSPC
 * 
 * @author Vinicius
 *
 */
@Path("")
public class SoMeSPCResource {
	@Context
	private UriInfo uriInfo;

	@Path("/ItensPlanoDeMedicao")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterObjetivos() throws Exception {

		String OE = "OE – Melhorar o gerenciamento dos projetos de software da organização";
		
		String OM_1 = "OM – Melhorar a aderência ao planejamento de pontos de estória nos projetos";
		String OM_2 = "OM – Melhorar a aderência ao planejamento do número de sprints dos projetos";
		String OM_3 = "OM – Melhorar aderência ao planejamento das sprints dos projetos";
		String OM_4 = "OM – Monitorar a produtividade das sprints dos projetos";
		String OM_5 = "OM – Monitorar quantidade de doses de locaine nas sprints";
		String OM_6 = "OM – Monitorar velocidade dos projetos";
		String OM_7 = "OM – Monitorar a qualidade do código fonte produzido";
	    	
	    List<ItemPlanoDeMedicaoDTO> itensPlanoDeMedicao = new ArrayList<ItemPlanoDeMedicaoDTO>();
		
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_1, "NI – Quantos pontos de estória foram planejados para o projeto?", "ME – Pontos de Estória Planejados para o Projeto)", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_1, "NI – Quantos pontos de estória foram concluídos no projeto?", "ME – Pontos de Estória Concluídos no Projeto)", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_1, "NI – Qual a taxa de conclusão de pontos de estória no projeto?", "ME – Taxa de Conclusão de Pontos de Estória no Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_2, "NI – Quantas sprints foram planejadas para o projeto?", "ME – Número de Sprints Planejadas para o Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_2, "NI – Quantas sprints foram realizadas no projeto?", "ME – Número de Sprints Realizadas no Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_2, "NI – Qual a taxa de conclusão de sprintsnoprojeto?", " ME – Taxa de Conclusão dedeSprintsno Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantas estórias foram planejadas para a sprint?", "ME – Número de Estórias Planejadas para a Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantas estórias foram concluídas na sprint?", "ME – Número de Estórias Concluídas na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Qual a taxa de conclusão de estórias na sprint?", "ME – Taxa de Conclusão de Estórias na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantos pontos de estória foram concluídos na sprint?", "ME – Pontos de Estória Concluídos na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Qual a taxa de conclusão de pontos de estórias na sprint?", "ME – Taxa de Conclusão de Pontos de Estórias na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantas tarefas foram planejados para a sprint?", "ME – Número de Tarefas Planejadas para a Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantas tarefas foram concluídas na sprint?", "ME – Número de Tarefas Concluídas na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Qual a taxa de conclusão de tarefas na sprint?", "ME – Taxa de Conclusão de Tarefas na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantos pontos de estória foram planejados para a sprint?", "ME – Pontos de Estória Planejados para a Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Quantas sprints foram realizadas no projeto?", "ME – Número de Sprints Realizadas no Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Quantas estórias foram concluídas para o projeto?", "ME – Número de Estórias Concluídas para o Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Qual o número médio de estórias concluídas por sprint no projeto?", "ME – Média de Estórias Concluídas por Sprint do Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Quantas estórias foram concluídas para o projeto?", "ME – Número de Estórias Concluídas para o Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Quantos pontos de estória foram concluídos no projeto?", "ME – Pontos de Estória Concluídos no Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Qual o número médio de pontos de estórias concluídos por sprint no projeto? ", "ME – Média de Pontos de Estórias Concluídos por Sprint do Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_5, "NI – Quantas doses de Iocaine ocorreram na sprint", "ME – Número de Doses de Iocaine na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_5, "NI – Qual a taxa de doses de Iocainena sprint?", "ME – Taxa de Doses de Iocaine na Sprint", "Taiga"));
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_6, "NI – Qual a velocidade da equipe no projeto?", "ME – Velocidade da Equipe no Projeto", "Taiga"));
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_7, "NI – Qual a complexidade ciclomática média por método?", "ME – Média da Complexidade Ciclomática por Método", "SonarQube"));
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_7, "NI – Qual a taxa de duplicação de código?", "ME – Taxa de Duplicação de Código", "SonarQube"));
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_7, "NI – Qual o percentual da dívida técnica?", "ME – Percentual da Dívida Técnica", "SonarQube"));

		return Response.ok().entity(itensPlanoDeMedicao).build();
	}
	
	/**
	 * Obtem as periodicidades.
	 * 
	 * @return
	 * @throws Exception
	 */
	@Path("Periodicidade")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterPeriodicidades() throws Exception {
		Response response;
		EntityManager manager = XPersistence.createManager();

		TypedQuery<Periodicidade> query = manager.createQuery("FROM Periodicidade", Periodicidade.class);
		List<Periodicidade> result = query.getResultList();

		if (result == null)
			response = Response.status(Status.NOT_FOUND).build();
		else {
			List<PeriodicidadeDTO> listaDto = new ArrayList<PeriodicidadeDTO>();

			for (Periodicidade periodicidade : result) {
				PeriodicidadeDTO p = new PeriodicidadeDTO();
				p.setNome(periodicidade.getNome());
				listaDto.add(p);
			}

			response = Response.status(Status.OK).entity(listaDto).build();
		}

		manager.close();
		return response;
	}

	/**
	 * Cria plano projeto.
	 * 
	 * @param planoDto
	 *            - Recebe um objeto que contém todas as entidades pertencentes
	 *            ao plano de medição para a persistencia do mesmo.
	 * @throws Exception
	 */
	@Path("/Plano")
	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public synchronized Response criarPlanoMedicao(PlanoDTO planoDto) throws Exception {

		if (planoDto.getNomePeriodicidade() == null || planoDto.getNomePeriodicidade().isEmpty())
			throw new NullArgumentException("Periodicidade");
		
		if (planoDto.getItensPlanoDeMedicao() == null || planoDto.getItensPlanoDeMedicao().isEmpty())
			throw new NullArgumentException("ItemPlanoDeMedicaoDTO");
				
		List<Periodicidade> periodicidades = SoMeSPCIntegrator.obterPeriodicidades();		
		Periodicidade periodicidadeSelecionada = null;

		for (Periodicidade periodicidade : periodicidades) {
			if (periodicidade.getNome().equalsIgnoreCase(planoDto.getNomePeriodicidade()))
				periodicidadeSelecionada = periodicidade;
		}
		
		boolean contemItemsTaiga = false;
		boolean contemItemsSonar = false;

		//Verifica se existem medidas do Taiga.
		for (ItemPlanoDeMedicaoDTO item : planoDto.getItensPlanoDeMedicao())
		{
			MedidasTaiga medidaTaiga = MedidasTaiga.get(item.getMedida());
			
			if (medidaTaiga != null)
			{
				contemItemsTaiga = true;
				break;
			}			
		}
		
		//Verifica se existem medidas do SonarQube.
		for (ItemPlanoDeMedicaoDTO item : planoDto.getItensPlanoDeMedicao())
		{
			MedidasSonarQube medidaSonar = MedidasSonarQube.get(item.getMedida());
			
			if (medidaSonar != null)
			{
				contemItemsSonar = true;
				break;
			}			
		}
	
		JSONObject json = new JSONObject();
		int i = 0;
		
		//Caso 1 - Apenas medidas do Taiga	
		if (contemItemsTaiga && !contemItemsSonar) {
			TaigaIntegrator taigaIntegrator = new TaigaIntegrator(planoDto.getTaigaLogin().getUrl(),
					planoDto.getTaigaLogin().getUsuario(), planoDto.getTaigaLogin().getSenha());

			
			for (String apelido : planoDto.getProjetosTaiga()) {				
				Projeto projeto = taigaIntegrator.obterProjetoTaiga(apelido);
				
				PlanoDeMedicao plano = SoMeSPCIntegrator.criarPlanoMedicaoProjetoTaigaSoMeSPC(planoDto.getItensPlanoDeMedicao(),
						periodicidadeSelecionada, planoDto.getTaigaLogin(), projeto);

				json.append("Plano " + (i + 1), plano.getNome());
				i++;
			}
		//Caso 2 - Apenas medidas do Sonar
		} else if (!contemItemsTaiga && contemItemsSonar) {
			/*
			SonarQubeIntegrator sonarIntegrator = new SonarQubeIntegrator(planoDto.getSonarLogin().getUrl());
			List<Recurso> projetosSonar = new ArrayList<Recurso>();
			
			for (String chave : planoDto.getProjetosSonar()) {
				Recurso projetoSonar = sonarIntegrator.obterRecurso(chave);
				projetosSonar.add(projetoSonar);
			}
			
			PlanoDeMedicao plano = SoMeSPCIntegrator.criarPlanoMedicaoProjetoSoMeSPC(planoDto.getItensPlanoDeMedicao(),
					periodicidadeSelecionada, null, planoDto.getSonarLogin(), null, projetosSonar);

			json.append("Plano " + (i + 1), plano.getNome());
			i++;
				*/
		//Caso 3 - medidas do Sonar e Taiga
		} else if (contemItemsTaiga && contemItemsSonar){
			/*
			TaigaIntegrator taigaIntegrator = new TaigaIntegrator(planoDto.getTaigaLogin().getUrl(),
					planoDto.getTaigaLogin().getUsuario(), planoDto.getTaigaLogin().getSenha());
			SonarQubeIntegrator sonarIntegrator = new SonarQubeIntegrator(planoDto.getSonarLogin().getUrl());
			
			//Para cada projeto do Taiga, adiciona as medidas do Taiga e Sonar.
			for (String apelido : planoDto.getProjetosTaiga()) {	
				
				List<Recurso> projetosSonar = new ArrayList<Recurso>();
				
				for (String chave : planoDto.getProjetosSonar()) {
					Recurso projetoSonar = sonarIntegrator.obterRecurso(chave);
					projetosSonar.add(projetoSonar);
				}
				
				Projeto projeto = taigaIntegrator.obterProjetoTaiga(apelido);
				
				PlanoDeMedicao plano = SoMeSPCIntegrator.criarPlanoMedicaoProjetoSoMeSPC(planoDto.getItensPlanoDeMedicao(),
						periodicidadeSelecionada, planoDto.getTaigaLogin(), planoDto.getSonarLogin(), projeto, projetosSonar);

				json.append("Plano " + (i + 1), plano.getNome());
				i++;
				
			}	*/
		//Caso 4 - Não encontrou itens de nenhuma das ferramentas
		} else {
			throw new Exception("Não foram informadas medidas para nenhuma das ferramentas coletoras disponíveis.");
		}
		
		return Response.ok().entity(json).build();
	}
	
	@Path("Medicao/Pagina")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterPaginasMedicoes(@QueryParam("tamanhoPagina") int tamanhoPagina,
			@QueryParam("medida") int idMedidaPlano, @QueryParam("entidade") int idEntidade) {
		Response response;
		EntityManager manager = XPersistence.createManager();

		Query queryTotal = manager.createQuery(
				String.format("SELECT COUNT(*) FROM Medicao m " + "WHERE m.medidaPlanoDeMedicao.medida.id = %d "
						+ "AND m.entidadeMensuravel.id = %d", idMedidaPlano, idEntidade));

		Long total = (Long) queryTotal.getSingleResult();

		manager.close();
		int ultimaPagina = (int) ((total / tamanhoPagina) + 1);

		List<Integer> paginas = new ArrayList<Integer>();
		for (int i = 1; i < ultimaPagina; i++) {
			paginas.add(new Integer(i));
		}

		response = Response.status(Status.OK).entity(paginas).build();
		return response;
	}

	@Path("Medicao/Total")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterTotalMedicoes(@QueryParam("medida") int idMedidaPlano,
			@QueryParam("entidade") int idEntidade) {
		Response response;
		EntityManager manager = XPersistence.createManager();

		Query queryTotal = manager.createQuery(
				String.format("SELECT COUNT(*) FROM Medicao m " + "WHERE m.medidaPlanoDeMedicao.medida.id = %d "
						+ "AND m.entidadeMensuravel.id = %d", idMedidaPlano, idEntidade));

		Long total = (Long) queryTotal.getSingleResult();

		manager.close();

		response = Response.status(Status.OK).entity(total).build();
		return response;
	}

	/**
	 * Obtem as medicoes.
	 * 
	 * @return
	 * @throws Exception
	 */
	@Path("Medicao")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterMedicoes(@QueryParam("medida") int idMedidaPlano, @QueryParam("entidade") int idEntidade,
			@QueryParam("indiceAtual") int indiceAtual, @QueryParam("tamanhoPagina") int tamanhoPagina)
					throws Exception {
		Response response;

		if (idMedidaPlano == 0 || idEntidade == 0) {
			response = Response.status(Status.BAD_REQUEST).build();
		} else {
			EntityManager manager = XPersistence.createManager();

			TypedQuery<Medicao> query = manager
					.createQuery(String.format(
							"Select m FROM Medicao m " + "WHERE m.medidaPlanoDeMedicao.medida.id = %d "
									+ "AND m.entidadeMensuravel.id = %d ORDER BY m.data ASC",
							idMedidaPlano, idEntidade), Medicao.class)
					.setFirstResult((indiceAtual * tamanhoPagina) - tamanhoPagina).setMaxResults(tamanhoPagina);

			List<Medicao> result = query.getResultList();

			if (result == null)
				response = Response.status(Status.NOT_FOUND).build();
			else {
				List<MedicaoDTO> listaDto = new ArrayList<MedicaoDTO>();

				for (Medicao medicao : result) {
					MedicaoDTO dto = new MedicaoDTO();

					dto.setId(medicao.getId());
					dto.setData(medicao.getData());
					dto.setValorMedido(medicao.getValorMedido().getValorMedido());

					listaDto.add(dto);
				}

				response = Response.status(Status.OK).entity(listaDto).build();
			}

			manager.close();
		}

		return response;
	}

	/**
	 * Obtem as entidades com medições.
	 * 
	 * @return Entidades com medições.
	 * @throws Exception
	 */
	@Path("Medicao/Entidade")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterEntidadesComMedicoes() throws Exception {
		Response response;

		EntityManager manager = XPersistence.createManager();

		TypedQuery<EntidadeMensuravel> query = manager
				.createQuery("Select distinct(m.entidadeMensuravel) FROM Medicao m ", EntidadeMensuravel.class);
		List<EntidadeMensuravel> result = query.getResultList();

		if (result == null)
			response = Response.status(Status.NOT_FOUND).build();
		else {
			List<EntidadeMensuravelDTO> listaDto = new ArrayList<EntidadeMensuravelDTO>();

			for (EntidadeMensuravel em : result) {
				EntidadeMensuravelDTO dto = new EntidadeMensuravelDTO();

				dto.setId(em.getId());
				dto.setNome(em.getNome());

				listaDto.add(dto);
			}

			response = Response.status(Status.OK).entity(listaDto).build();
		}

		manager.close();

		return response;
	}

	/**
	 * Obtém as medidas das entidades.
	 * 
	 * @param id-
	 *            Id da entidade para buscar as medidas.
	 * @return Medidas das da entidade informada.
	 * @throws Exception
	 */
	@Path("Medicao/Entidade/{id}/Medida")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterMedidasdasEntidadesComMedicoes(@PathParam("id") int id) throws Exception {
		Response response;

		if (id == 0) {
			response = Response.status(Status.BAD_REQUEST).build();
		}

		else {

			EntityManager manager = XPersistence.createManager();

			TypedQuery<Medida> query = manager.createQuery("Select distinct(m.medidaPlanoDeMedicao.medida) "
					+ "FROM Medicao m " + "WHERE m.entidadeMensuravel.id = " + id, Medida.class);
			List<Medida> result = query.getResultList();

			if (result == null)
				response = Response.status(Status.NOT_FOUND).build();
			else {
				List<MedidaDTO> listaDto = new ArrayList<MedidaDTO>();

				for (Medida med : result) {
					MedidaDTO dto = new MedidaDTO();

					dto.setId(med.getId());
					dto.setNome(med.getNome());

					listaDto.add(dto);
				}

				response = Response.status(Status.OK).entity(listaDto).build();
			}

			manager.close();
		}
		return response;
	}

	/**
	 * Obtém os valores das medidas por entidade.
	 * 
	 * @param idEntidade
	 *            - Id da entidade para buscar os valores
	 * @param idMedida
	 *            - Id da medida para buscar os valores
	 * @return Valores da medida.
	 * @throws Exception
	 */
	@Path("Medicao/Entidade/{id}/Medida/{id2}/Valor")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterValorMedicoesPorMedidaEntidade(@QueryParam("id") int idEntidade,
			@QueryParam("id2") int idMedida) throws Exception {
		Response response;

		if (idEntidade == 0) {
			response = Response.status(Status.BAD_REQUEST).build();
		}

		else {

			EntityManager manager = XPersistence.createManager();

			TypedQuery<String> query = manager.createQuery("Select m.valorMedido.valorMedido " + "FROM Medicao m "
					+ "WHERE m.entidadeMensuravel.id = " + idEntidade + " " + "AND m.medidaPlanoDeMedicao.medida.id = "
					+ idMedida + " ORDER BY m.id", String.class);
			List<String> result = query.getResultList();

			if (result == null)
				response = Response.status(Status.NOT_FOUND).build();
			else {
				List<Integer> listaDto = new ArrayList<Integer>();

				for (String valor : result) {
					listaDto.add(Integer.valueOf(valor));
				}

				response = Response.status(Status.OK).entity(listaDto).build();
			}

			manager.close();
		}
		return response;
	}

	/**
	 * Obtêmc as Datas/Horas das medições pelo projeto e pela medida.
	 * 
	 * @param idProjeto
	 * @param idMedida
	 * @return
	 * @throws Exception
	 */
	@Path("Medicao/DataHora")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterDataHoraMedicoesPorMedidaEProjeto(@QueryParam("entidade") int idEntidade,
			@QueryParam("medida") int idMedida) throws Exception {
		Response response;

		if (idEntidade == 0) {
			response = Response.status(Status.BAD_REQUEST).build();
		}

		else {

			EntityManager manager = XPersistence.createManager();

			TypedQuery<Medicao> query = manager
					.createQuery(
							"Select m " + "FROM Medicao m " + "WHERE m.entidadeMensuravel.id = " + idEntidade + " "
									+ "AND m.medidaPlanoDeMedicao.medida.id = " + idMedida + " ORDER BY m.id",
							Medicao.class);
			List<Medicao> result = query.getResultList();

			if (result == null)
				response = Response.status(Status.NOT_FOUND).build();
			else {
				List<Date> listaDto = new ArrayList<Date>();

				for (Medicao med : result) {
					listaDto.add(med.getData());
				}

				response = Response.status(Status.OK).entity(listaDto).build();
			}

			manager.close();
		}
		return response;
	}

}

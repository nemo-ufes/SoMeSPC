/*
 * SoMeSPC - powerful tool for measurement
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique N�spoli Castro, Vin�cius Soares Fonseca
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import org.somespc.model.medicao.Medicao;
import org.somespc.model.plano_de_medicao.PlanoDeMedicao;
import org.somespc.util.json.JSONObject;
import org.somespc.webservices.rest.dto.EntidadeMensuravelDTO;
import org.somespc.webservices.rest.dto.ItemPlanoDeMedicaoDTO;
import org.somespc.webservices.rest.dto.MedicaoDTO;
import org.somespc.webservices.rest.dto.PeriodicidadeDTO;
import org.somespc.webservices.rest.dto.PlanoDTO;

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

	//So funciona com esse padrao de data.
	private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	
	@Path("/ItensPlanoDeMedicao")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterItensPlanoDeMedicao() throws Exception {

		String OE_1 = "Melhorar o gerenciamento dos projetos de software da organiza��o";
		String OE_2 = "Melhorar a qualidade do c�digo fonte produzido";
		
		String OM_1 = "Monitorar a conclus�o de pontos de est�ria nos projetos";
		String OM_2 = "Monitorar a realiza��o de sprints nos projetos";
		String OM_3 = "Monitorar desempenho na sprint";
		String OM_4 = "Monitorar desempenho no projeto";
		String OM_5 = "Monitorar quantidade de doses de Iocaine nas sprints";
		String OM_6 = "Monitorar o desempenho dos membros da equipe no projeto";
		String OM_7 = "Monitorar a qualidade do c�digo fonte produzido nos projetos";
	    	
	    List<ItemPlanoDeMedicaoDTO> itensPlanoDeMedicao = new ArrayList<ItemPlanoDeMedicaoDTO>();
		
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_1, "Quantos pontos de est�ria foram planejados para o projeto?", "Pontos de Est�ria Planejados para o Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_1, "Quantos pontos de est�ria foram conclu�dos no projeto?", "Pontos de Est�ria Conclu�dos no Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_1, "Qual a taxa de conclus�o de pontos de est�ria no projeto?", "Taxa de Conclus�o de Pontos de Est�ria no Projeto", "Taiga"));
	    
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_2, "Quantas sprints foram planejadas para o projeto?", "N�mero de Sprints Planejadas para o Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_2, "Quantas sprints foram realizadas no projeto?", "N�mero de Sprints Realizadas no Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_2, "Qual a taxa de conclus�o de sprints no projeto?", "Taxa de Conclus�o de Sprints no Projeto", "Taiga"));
	    
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_3, "Quantas est�rias foram planejadas para a sprint?", "N�mero de Est�rias Planejadas para a Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_3, "Quantas est�rias foram conclu�das na sprint?", "N�mero de Est�rias Conclu�das na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_3, "Qual a taxa de conclus�o de est�rias na sprint?", "Taxa de Conclus�o de Est�rias na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_3, "Quantos pontos de est�ria foram conclu�dos na sprint?", "Pontos de Est�ria Conclu�dos na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_3, "Qual a taxa de conclus�o de pontos de est�rias na sprint?", "Taxa de Conclus�o de Pontos de Est�rias na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_3, "Quantas tarefas foram planejados para a sprint?", "N�mero de Tarefas Planejadas para a Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_3, "Quantas tarefas foram conclu�das na sprint?", "N�mero de Tarefas Conclu�das na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_3, "Qual a taxa de conclus�o de tarefas na sprint?", "Taxa de Conclus�o de Tarefas na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_3, "Quantos pontos de est�ria foram planejados para a sprint?", "Pontos de Est�ria Planejados para a Sprint", "Taiga"));
	    
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_4, "Quantas sprints foram realizadas no projeto?", "N�mero de Sprints Realizadas no Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_4, "Quantas est�rias foram conclu�das para o projeto?", "N�mero de Est�rias Conclu�das para o Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_4, "Quantos pontos de est�ria foram conclu�dos no projeto?", "Pontos de Est�ria Conclu�dos no Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_4, "Qual o n�mero m�dio de est�rias conclu�das por sprint no projeto?", "M�dia de Est�rias Conclu�das por Sprint do Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_4, "Qual o n�mero m�dio de pontos de est�rias conclu�dos por sprint no projeto? ", "Velocidade da Equipe no Projeto", "Taiga"));
	    
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_5, "Quantas tarefas foram conclu�das na sprint?", "N�mero de Tarefas Conclu�das na Sprint", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_5, "Quantas doses de Iocaine ocorreram na sprint", "N�mero de Doses de Iocaine na Sprint", "Taiga"));	    
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_5, "Qual a taxa de doses de Iocaine na sprint?", "Taxa de Doses de Iocaine na Sprint", "Taiga"));	    
	    
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_6, "Quantas tarefas foram atribu�das a um membro da equipe do projeto?", "N�mero de Tarefas Atribu�das a Membro do Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_6, "Quantas tarefas foram conclu�das por um membro da equipe do projeto?", "N�mero de Tarefas Conclu�das pelo Membro do Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_6, "Qual a taxa de conclus�o de tarefas de um membro da equipe do projeto?", "Taxa de Conclus�o de Tarefas de Membro do Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_6, "Quantos pontos de est�ria foram atribu�dos a um membro da equipe do projeto?", "N�mero de Pontos de Est�ria Atribu�dos a Membro do Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_6, "Quantas pontos de est�ria foram conclu�dos por um membro da equipe do projeto?", "N�mero de Pontos de Est�ria Conclu�dos pelo Membro do Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_6, "Qual a taxa de conclus�o de pontos de est�ria de um membro da equipe do projeto?", "Taxa de Conclus�o de Pontos de Est�ria de Membro do Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_6, "Quantas doses de Iocaine foram atribu�das a um membro da equipe do projeto?", "N�mero de Doses de Iocaine Atribu�das a Membro do Projeto", "Taiga"));
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_1, OM_6, "Qual a taxa de doses de Iocaine de um membro da equipe do projeto?", "Taxa de Doses de Iocaine de Membro do Projeto", "Taiga"));
	    
	    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_2, OM_7, "Qual a complexidade ciclom�tica m�dia por m�todo?", "M�dia da Complexidade Ciclom�tica por M�todo", "SonarQube"));
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_2, OM_7, "Qual a taxa de duplica��o de c�digo?", "Taxa de Duplica��o de C�digo", "SonarQube"));
		itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE_2, OM_7, "Qual o percentual da d�vida t�cnica?", "Percentual da D�vida T�cnica", "SonarQube"));

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
	 *            - Recebe um objeto que cont�m todas as entidades pertencentes
	 *            ao plano de medi��o para a persistencia do mesmo.
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
			
			SonarQubeIntegrator sonarIntegrator = new SonarQubeIntegrator(planoDto.getSonarLogin().getUrl());
		
			for (String chave : planoDto.getProjetosSonar()) {
				Recurso projetoSonar = sonarIntegrator.obterRecurso(chave);
							
				PlanoDeMedicao plano = SoMeSPCIntegrator.criarPlanoMedicaoProjetoSonarQubeSoMeSPC(planoDto.getItensPlanoDeMedicao(),
						periodicidadeSelecionada, planoDto.getSonarLogin(), projetoSonar);
	
				json.append("Plano " + (i + 1), plano.getNome());
				i++;
			}
		//Caso 3 - medidas do Sonar e Taiga
		} else if (contemItemsTaiga && contemItemsSonar){
			
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
				
				PlanoDeMedicao plano = SoMeSPCIntegrator.criarPlanoMedicaoProjetoTaigaSonarQubeSoMeSPC(planoDto.getItensPlanoDeMedicao(), periodicidadeSelecionada,
						planoDto.getTaigaLogin(), projeto, planoDto.getSonarLogin(), projetosSonar);

				json.append("Plano " + (i + 1), plano.getNome());
				i++;
				
			}	
		//Caso 4 - N�o encontrou itens de nenhuma das ferramentas
		} else {
			throw new Exception("N�o foram informadas medidas para nenhuma das ferramentas coletoras dispon�veis.");
		}
		
		return Response.ok().entity(json).build();
	}
	
	@Path("Medicao/Total")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterTotalMedicoes(@QueryParam("medida") String nomeMedida,
			@QueryParam("entidade") int idEntidade, @QueryParam("dataInicio") String dtInicio, 
			@QueryParam("dataFim") String dtFim) throws ParseException {
		Response response;

		Date dataInicio = fmt.parse(dtInicio);
		Date dataFim = fmt.parse(dtFim);
				
		EntityManager manager = XPersistence.createManager();

		Query queryTotal = manager.createQuery("SELECT COUNT(*) FROM Medicao m " + "WHERE m.medidaPlanoDeMedicao.medida.nome = :nomeMedida "
						+ "AND m.entidadeMensuravel.id = :idEntidade AND cast(m.data as date) BETWEEN :dataInicio AND :dataFim")
				.setParameter("nomeMedida", nomeMedida)
				.setParameter("idEntidade", idEntidade)
				.setParameter("dataInicio", dataInicio, TemporalType.DATE)
	            .setParameter("dataFim", dataFim, TemporalType.DATE);
			
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
	public Response obterMedicoes(@QueryParam("medida") String nomeMedida, @QueryParam("entidade") int idEntidade,
			@QueryParam("indiceAtual") int indiceAtual, @QueryParam("tamanhoPagina") int tamanhoPagina,
			 @QueryParam("dataInicio") String dtInicio, @QueryParam("dataFim") String dtFim)
					throws Exception {
		Response response;

		if (nomeMedida == null || nomeMedida.isEmpty() || 
			dtInicio == null || dtInicio.isEmpty() || 
			dtFim == null || dtFim.isEmpty() || 
			idEntidade == 0) {
			response = Response.status(Status.BAD_REQUEST).build();
		} else {
			EntityManager manager = XPersistence.createManager();
			Date dataInicio = fmt.parse(dtInicio);
			Date dataFim = fmt.parse(dtFim);
			
			TypedQuery<Medicao> query = manager
					.createQuery("Select m FROM Medicao m " + "WHERE m.medidaPlanoDeMedicao.medida.nome = :nomeMedida "
									+ "AND m.entidadeMensuravel.id = :idEntidade AND cast(m.data as date) BETWEEN :dataInicio AND :dataFim ORDER BY m.data ASC", Medicao.class)
					.setParameter("nomeMedida", nomeMedida)
					.setParameter("idEntidade", idEntidade)
					.setParameter("dataInicio", dataInicio, TemporalType.DATE)
		            .setParameter("dataFim", dataFim, TemporalType.DATE)
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
	 * Obtem as entidades com medi��es.
	 * 
	 * @return Entidades com medi��es.
	 * @throws Exception
	 */
	@Path("Medicao/Entidade")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterEntidadesComMedicoes(@QueryParam("medida") String nomeMedida) throws Exception {
		Response response;

		EntityManager manager = XPersistence.createManager();

		TypedQuery<EntidadeMensuravel> query = manager
				.createQuery("Select distinct(m.entidadeMensuravel) FROM Medicao m "
						+ "WHERE m.medidaPlanoDeMedicao.medida.nome=:nomeMedida ORDER BY m.entidadeMensuravel.nome ", EntidadeMensuravel.class)
				.setParameter("nomeMedida", nomeMedida);
		
		List<EntidadeMensuravel> result = query.getResultList();

		if (result == null)
			response = Response.status(Status.NOT_FOUND).build();
		else {
			List<EntidadeMensuravelDTO> listaDto = new ArrayList<EntidadeMensuravelDTO>();

			for (EntidadeMensuravel em : result) {
				EntidadeMensuravelDTO dto = new EntidadeMensuravelDTO();

				dto.setId(em.getId());
				dto.setNome(em.getNome());
				dto.setNomeTipo(em.getTipoDeEntidadeMensuravel().getNome());

				listaDto.add(dto);
			}

			response = Response.status(Status.OK).entity(listaDto).build();
		}

		manager.close();

		return response;
	}
		
}

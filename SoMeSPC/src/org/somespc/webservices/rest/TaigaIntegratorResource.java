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

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.somespc.integracao.SoMeSPCIntegrator;
import org.somespc.integracao.taiga.*;
import org.somespc.integracao.taiga.model.*;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.plano_de_medicao.*;
import org.somespc.util.json.*;
import org.somespc.webservices.rest.dto.*;

@Path("TaigaIntegrator")
public class TaigaIntegratorResource
{
    @Path("/Projetos")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterProjetos(TaigaLoginDTO login) throws Exception
    {

	Response response;

	if (login == null || login.getUrl().isEmpty()
		|| login.getUsuario().isEmpty() || login.getSenha().isEmpty())
	{
	    response = Response.status(Status.BAD_REQUEST).build();
	}
	else
	{
	    TaigaIntegrator integrator = new TaigaIntegrator(login.getUrl(), login.getUsuario(), login.getSenha());
	    try
	    {
		List<Projeto> projetos = integrator.obterProjetosTaiga();
		response = Response.ok().entity(projetos).build();
	    }
	    catch (Exception ex)
	    {
		ex.printStackTrace();
		response = Response.status(Status.BAD_REQUEST).build();
	    }
	}

	return response;
    }

    @Path("/Medidas")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterMedidas() throws Exception
    {
	MedidasTaiga[] medidasTaiga = MedidasTaiga.PONTOS_ALOCADOS_PROJETO.getDeclaringClass().getEnumConstants();

	List<String> nomesMedidas = new ArrayList<String>();
	for (MedidasTaiga medida : medidasTaiga)
	{
	    nomesMedidas.add(medida.toString());
	}

	return Response.ok().entity(nomesMedidas).build();
    }
    
    
    @Path("/ItensPlanoDeMedicao")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterObjetivos() throws Exception
    {

	String OE = "OE – Melhorar o gerenciamento dos projetos de software da organização";
	
	String OM_1 = "OM – Melhorar a aderência ao planejamento de pontos de estória nos projetos";
	String OM_2 = "OM – Melhorar a aderência ao planejamento do número de sprints dos projetos";
	String OM_3 = "OM – Melhorar aderência ao planejamento das sprints dos projetos";
	String OM_4 = "OM – Monitorar a produtividade das sprints dos projetos";
	String OM_5 = "OM – Monitorar quantidade de doses de locaine nas sprints";
	String OM_6 = "OM – Monitorar velocidade dos projetos";
    	
    List<ItemPlanoDeMedicaoDTO> itensPlanoDeMedicao = new ArrayList<ItemPlanoDeMedicaoDTO>();
	
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_1, "NI – Quantos pontos de estória foram planejados para o projeto?", "ME – Pontos de Estória Planejados para o Projeto (PEPP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_1, "NI – Quantos pontos de estória foram alocados no projeto?", "ME – Pontos de Estória Alocados no Projeto"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_1, "NI – Quantos pontos de estória foram concluídos no projeto?", "ME – Pontos de Estória Concluídos no Projeto (PECP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_1, "NI – Qual a taxa de conclusão de pontos de estória no projeto?", "ME – Taxa de Conclusão de Pontos de Estória no Projeto (TCPEP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_2, "NI – Quantas sprints foram planejadas para o projeto?", "ME – Número de Sprints Planejadas para o Projeto (NSPP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_2, "NI – Quantas sprints foram realizadas no projeto?", "ME – Número de Sprints Realizadas no Projeto (NSRP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_2, "NI – Qual ataxa de conclusão de sprintsnoprojeto?", " ME – Taxa de Conclusão dedeSprintsno Projeto (TCSP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantas estórias foram planejadas para a sprint?", "ME – Número de Estórias Planejadas para a Sprint (NEPS)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantas estórias foram concluídas na sprint?", "ME – Número de Estórias Concluídas na Sprint (NECS)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Qual a taxa de conclusão de estórias na sprint?", "ME – Taxa de Conclusão de Estórias na Sprint (TCES)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantos pontos de estória foram concluídos na sprint?", "ME – Pontos de Estória Concluídos na Sprint (PECS)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Qual a taxa de conclusão de pontos de estórias na sprint?", "ME – Taxa de Conclusão de Pontos de Estórias na Sprint (TCPES)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantas tarefas foram planejados para a sprint?", "ME – Número de Tarefas Planejadas para a Sprint (NTPS)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantas tarefas foram concluídas na sprint?", "ME – Número de Tarefas Concluídas na Sprint (NTCS)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Qual a taxa de conclusão de tarefas na sprint?", "ME – Taxa de Conclusão de Tarefas na Sprint (TCTS)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_3, "NI – Quantos pontos de estória foram planejados para a sprint?", "ME – Pontos de Estória Planejados para a Sprint (PEPS)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Quantas sprints foram realizadas no projeto?", "ME – Número de Sprints Realizadas no Projeto (NSRP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Quantas estórias foram concluídas para o projeto?", "ME – Número de Estórias Concluídas para o Projeto (NECP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Qual o número médio de estórias concluídas por sprint no projeto?", "ME – Média de Estórias Concluídas por Sprint do Projeto (MECSP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Quantas estórias foram concluídas para o projeto?", "ME – Número de Estórias Concluídas para o Projeto (NECP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Quantos pontos de estória foram concluídos no projeto?", "ME – Pontos de Estória Concluídos no Projeto (PECP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_4, "NI – Qual o número médio de pontos de estórias concluídos por sprint no projeto? ", "ME – Média de Pontos de Estórias Concluídos por Sprint do Projeto (MPECSP)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_5, "NI – Quantasdoses de Iocaine ocorreram na sprint", "ME – Número de Doses de Iocaine na Sprint (NDIS)"));
    itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_5, "NI – Quantas tarefas foram realizadas na sprint?", "ME – Número de Tarefas Realizadas na Sprint (NTRS)"));
	itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_5, "NI – Qual a taxa de doses de Iocainena sprint?", "ME – Taxa de Doses de Iocaine na Sprint (TDIS)"));
	itensPlanoDeMedicao.add(new ItemPlanoDeMedicaoDTO(OE, OM_6, "NI – Qual a velocidade do projeto?", "ME – Velocidade do Projeto (VP)"));

	return Response.ok().entity(itensPlanoDeMedicao).build();
    }

    /**
     * Cria plano projeto.
     * 
     * @param planoDto
     *            - Recebe um objeto que contém todas as entidades pertencentes ao plano de medição para a persistencia do mesmo.
     * @throws Exception
     */
    @Path("/Plano")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public synchronized Response criarPlanoMedicao(PlanoDTO planoDto) throws Exception
    {

	TaigaIntegrator integrator = new TaigaIntegrator(planoDto.getTaigaLogin().getUrl(),
		planoDto.getTaigaLogin().getUsuario(), planoDto.getTaigaLogin().getSenha());

	List<Periodicidade> periodicidades = SoMeSPCIntegrator.obterPeriodicidades();
	
	List<ItemPlanoDeMedicaoDTO> itens = planoDto.getItensPlanoDeMedicao();
	
	for (ItemPlanoDeMedicaoDTO item: itens){
		
		System.out.println(item.getMedida()+item.getNomeNecessidadeDeInformacao()+item.getNomeObjetivoDeMedicao()+item.getNomeObjetivoEstrategico());
	}

	Periodicidade periodicidadeSelecionada = null;

	for (Periodicidade periodicidade : periodicidades)
	{
	    if (periodicidade.getNome().equalsIgnoreCase(planoDto.getNomePeriodicidade()))
		periodicidadeSelecionada = periodicidade;
	}

	JSONObject json = new JSONObject();

	for (int i = 0; i < planoDto.getApelidosProjetos().size(); i++)
	{
	    String apelido = planoDto.getApelidosProjetos().get(i);
	    Projeto projeto = integrator.obterProjetoTaiga(apelido);
	    PlanoDeMedicao plano = integrator.criarPlanoMedicaoProjetoSoMeSPC(planoDto.getItensPlanoDeMedicao(), periodicidadeSelecionada, projeto);

	    json.append("Plano " + (i+1), plano.getNome());
	}

	return Response.ok().entity(json).build();
    }

    
    /**
     * Cria plano teste.
     * 
     * @param planoDto
     *            - Recebe um objeto que contém todas as entidades pertencentes ao plano de medição para a persistencia do mesmo.
     * @throws Exception
     */
//    @Path("/Plano_Teste")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
//    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//    public synchronized Response criarPlanoTeste(PlanoTesteDTO planoDto) throws Exception
//    {
//
//	TaigaIntegrator integrator = new TaigaIntegrator(planoDto.getTaigaLogin().getUrl(),
//		planoDto.getTaigaLogin().getUsuario(), planoDto.getTaigaLogin().getSenha());
//
//	List<Periodicidade> periodicidades = integrator.obterPeriodicidades();
//
//	Periodicidade periodicidadeSelecionada = null;
//
//	for (Periodicidade periodicidade : periodicidades)
//	{
//	    if (periodicidade.getNome().equalsIgnoreCase(planoDto.getNomePeriodicidade()))
//		periodicidadeSelecionada = periodicidade;
//	}
//
//	JSONObject json = new JSONObject();
//
//	for (int i = 0; i < planoDto.getApelidosProjetos().size(); i++)
//	{
//	    String apelido = planoDto.getApelidosProjetos().get(i);
//	    Projeto projeto = integrator.obterProjetoTaiga(apelido);
//	    PlanoDeMedicao plano = integrator.criarPlanoMedicaoProjetoSoMeSPCTeste(periodicidadeSelecionada, projeto);
//
//	    json.append("Plano " + (i+1), plano.getNome());
//	}
//
//	return Response.ok().entity(json).build();
//    }
}

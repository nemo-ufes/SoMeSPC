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
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.somespc.integracao.SoMeSPCIntegrator;
import org.somespc.integracao.taiga.TaigaIntegrator;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.plano_de_medicao.PlanoDeMedicao;
import org.somespc.util.json.JSONObject;
import org.somespc.webservices.rest.dto.ItemPlanoDeMedicaoDTO;
import org.somespc.webservices.rest.dto.PlanoDTO;
import org.somespc.webservices.rest.dto.TaigaLoginDTO;

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
	    PlanoDeMedicao plano = integrator.criarPlanoMedicaoProjetoSoMeSPC(planoDto.getItensPlanoDeMedicao(), periodicidadeSelecionada, projeto, null);

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

/*
 * MedCEP - A powerful tool for measure
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
package org.medcep.webservices.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.medcep.integracao.sonarqube.*;
import org.medcep.integracao.sonarqube.model.*;

@Path("SonarQubeIntegrator")
public class SonarQubeIntegratorResource
{
    @Path("/Projetos")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterProjetos(String urlSonar) throws Exception
    {
	Response response;

	if (urlSonar == null || urlSonar.isEmpty())
	{
	    response = Response.status(Status.BAD_REQUEST).build();
	}
	else
	{
	    SonarQubeIntegrator integrator = new SonarQubeIntegrator(urlSonar);
	    try
	    {
		List<Recurso> projetos = integrator.obterProjetos();
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

    @Path("/Metricas")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterMedidas(String urlSonar) throws Exception
    {
	Response response;

	if (urlSonar == null || urlSonar.isEmpty())
	{
	    response = Response.status(Status.BAD_REQUEST).build();
	}
	else
	{
	    SonarQubeIntegrator integrator = new SonarQubeIntegrator(urlSonar);
	    try
	    {
		List<Metrica> metricas = integrator.obterMetricas();
		Collections.sort(metricas);
		response = Response.ok().entity(metricas).build();
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
    /*
    @Path("/Plano")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public synchronized Response criarPlanoMedicao(PlanoDTO planoDto) throws Exception
    {

	
	List<MedidasTaiga> medidas = new ArrayList<MedidasTaiga>();

	for (String medida : planoDto.getNomesMedidas())
	{
	    MedidasTaiga medidaTaiga = MedidasTaiga.get(medida);
	    medidas.add(medidaTaiga);
	}

	TaigaIntegrator integrator = new TaigaIntegrator(planoDto.getTaigaLogin().getUrl(),
		planoDto.getTaigaLogin().getUsuario(), planoDto.getTaigaLogin().getSenha());

	List<Periodicidade> periodicidades = integrator.obterPeriodicidades();

	Periodicidade periodicidadeSelecionada = null;

	for (Periodicidade periodicidade : periodicidades)
	{
	    if (periodicidade.getNome().equalsIgnoreCase(planoDto.getNomePeriodicidade()))
		periodicidadeSelecionada = periodicidade;
	}

	Projeto projeto = integrator.obterProjetoTaiga(planoDto.getApelidoProjeto());

	PlanoDeMedicao plano = integrator.criarPlanoMedicaoProjetoMedCEP(medidas, periodicidadeSelecionada, projeto);
	
	JSONObject json = new JSONObject();
	json.append("nome", plano.getNome());
	
	return Response.ok().entity(json).build();
	
    }*/

}

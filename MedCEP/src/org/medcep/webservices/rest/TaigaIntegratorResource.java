package org.medcep.webservices.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.medcep.integracao.taiga.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.model.medicao.*;
import org.medcep.model.medicao.planejamento.*;
import org.medcep.util.json.*;
import org.medcep.webservices.rest.dto.*;

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
    }

}

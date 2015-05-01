package org.medcep.webservices.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.medcep.integracao.taiga.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.webservices.rest.dto.*;

import com.owlike.genson.*;

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
	    List<Projeto> projetos = integrator.obterProjetosTaiga();
	    response = Response.ok().entity(projetos).build();
	}

	return response;
    }

    @Path("/Medidas")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterMedidas() throws Exception
    {
	Genson genson = new Genson();
	MedidasTaiga[] medidasTaiga = MedidasTaiga.PONTOS_ALOCADOS_PROJETO.getDeclaringClass().getEnumConstants();

	String json = genson.serialize(medidasTaiga);

	return Response.ok().entity(json).build();
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
    public synchronized void criarPlanoMedicao(PlanoDTO planoDto) throws Exception
    {
	System.out.println(planoDto.getNomePeriodicidade());
	System.out.println(planoDto.getNomeProjeto());
	for (int i = 0; i < planoDto.getNomesMedidas().size(); i++)
	{
	    System.out.println(planoDto.getNomesMedidas().get(i));
	}
    }

}

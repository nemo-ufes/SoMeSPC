package org.medcep.webservices.rest;

import java.net.URI;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;
import org.medcep.integracao.taiga.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.model.medicao.TipoDeEntidadeMensuravel;
import org.medcep.model.processo.OcorrenciaProcesso;
import org.medcep.model.processo.ProcessoProjeto;
import org.medcep.webservices.rest.dto.OcorrenciaDTO;
import org.medcep.webservices.rest.dto.PlanoDTO;
import org.medcep.webservices.rest.exceptions.EntidadeNaoEncontradaException;
import org.openxava.jpa.XPersistence;

import com.owlike.genson.*;

@Path("TaigaIntegrator")
public class TaigaIntegratorResource
{
    @Path("/Projetos")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterProjetos() throws Exception
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	List<Projeto> projetos = integrator.obterProjetosTaiga();

	return Response.ok().entity(projetos).build();
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
    	for (int i =0; i<planoDto.getNomesMedidas().size(); i++){
    		System.out.println(planoDto.getNomesMedidas().get(i));
    	}
    }

}

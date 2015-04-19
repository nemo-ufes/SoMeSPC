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

import java.net.*;
import java.sql.*;
import java.text.*;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.*;
import org.medcep.model.medicao.*;
import org.medcep.model.processo.*;
import org.medcep.webservices.rest.dto.*;
import org.openxava.jpa.*;

/**
 * API REST para os recursos da MedCEP
 * 
 * @author Vinicius
 *
 */
@Path("")
public class MedCEPResource
{
    @Context
    private UriInfo uriInfo;

    /**
     * Cria uma ocorrência de processo.
     * 
     * @param ocorrenciaDto
     *            - Ocorrência com o nome e nome do processo ocorrido preenchido.
     * @return OcorrenciaProcesso criada.
     * @throws Exception
     */
    @Path("OcorrenciaProcesso")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response criarOcorrenciaProcesso(OcorrenciaDTO ocorrenciaDto) throws Exception
    {
	Response response;
	EntityManager manager = XPersistence.createManager();

	if (ocorrenciaDto == null || ocorrenciaDto.getNomeOcorrencia().isEmpty() || ocorrenciaDto.getNomeProcessoOcorrido().isEmpty())
	{
	    response = Response.status(Status.BAD_REQUEST).build();
	}

	String query1 = "SELECT e FROM ProcessoProjeto e WHERE e.nome='" + ocorrenciaDto.getNomeProcessoOcorrido() + "'";
	TypedQuery<ProcessoProjeto> typedQuery1 = manager.createQuery(query1, ProcessoProjeto.class);
	ProcessoProjeto processoOcorrido = typedQuery1.getSingleResult();

	String query2 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Ocorrência de Processo de Software'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery2 = manager.createQuery(query2, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoOcorrenciaProcesso = typedQuery2.getSingleResult();

	OcorrenciaProcesso ocorrencia = new OcorrenciaProcesso();
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());

	ocorrencia.setNome(ocorrenciaDto.getNomeOcorrencia() + " - " + dataHora);
	ocorrencia.setProcessoProjetoOcorrido(processoOcorrido);
	ocorrencia.setTipoDeEntidadeMensuravel(tipoOcorrenciaProcesso);

	//Persiste.	
	try
	{
	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    manager.getTransaction().begin();
	    manager.persist(ocorrencia);
	    manager.getTransaction().commit();
	    
	    OcorrenciaDTO dto = new OcorrenciaDTO();
	    dto.setId(ocorrencia.getId());
	    dto.setNomeOcorrencia(ocorrencia.getNome());
	    dto.setNomeProcessoOcorrido(ocorrencia.getProcessoProjetoOcorrido().getNome());	   
	    
	    URI location = new URI(String.format("%s/%s", uriInfo.getAbsolutePath().toString(), ocorrencia.getId()));
	    response = Response.created(location).entity(dto).build();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		response = Response.status(Status.CONFLICT).build();
	    }
	    else
	    {
		throw ex;
	    }
	}
	finally
	{
	    manager.close();
	}

	return response;
    }

    /**
     * Obtem a Ocorrência de Processo.
     * @param id
     * @return OcorrenciaProcesso
     * @throws Exception
     */
    @Path("OcorrenciaProcesso/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterOcorrenciaProcesso(@PathParam("id") Integer id) throws Exception
    {
	Response response;
	EntityManager manager = XPersistence.createManager();

	if (id == null || id == 0)
	    response = Response.status(Status.BAD_REQUEST).build();

	OcorrenciaProcesso ocorrencia = manager.find(OcorrenciaProcesso.class, id);

	if (ocorrencia == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	{
	    OcorrenciaDTO dto = new OcorrenciaDTO();
	    dto.setId(id);
	    dto.setNomeOcorrencia(ocorrencia.getNome());
	    dto.setNomeProcessoOcorrido(ocorrencia.getProcessoProjetoOcorrido().getNome());
	    response = Response.status(Status.OK).entity(dto).build();
	}

	return response;
    }
    
    /**
     * Cria uma ocorrência de atividade.
     * 
     * @param ocorrenciaDto
     *            - Ocorrência com o nome e nome da atividade ocorrida preenchido.
     * @return OcorrenciaAtividade criada.
     * @throws Exception
     */
    @Path("OcorrenciaAtividade")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response criarOcorrenciaAtividade(OcorrenciaDTO ocorrenciaDto) throws Exception
    {

	Response response;
	EntityManager manager = XPersistence.createManager();

	if (ocorrenciaDto == null || ocorrenciaDto.getNomeOcorrencia().isEmpty() || ocorrenciaDto.getNomeAtividadeOcorrida().isEmpty())
	{
	    response = Response.status(Status.BAD_REQUEST).build();
	}

	String query1 = "SELECT e FROM AtividadeProjeto e WHERE e.nome='" + ocorrenciaDto.getNomeAtividadeOcorrida() + "'";
	TypedQuery<AtividadeProjeto> typedQuery1 = manager.createQuery(query1, AtividadeProjeto.class);
	AtividadeProjeto atividadeOcorrida = typedQuery1.getSingleResult();

	String query2 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Ocorrência de Atividade'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery2 = manager.createQuery(query2, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoOcorrenciaAtividade = typedQuery2.getSingleResult();

	OcorrenciaAtividade ocorrencia = new OcorrenciaAtividade();
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());

	ocorrencia.setNome(ocorrenciaDto.getNomeOcorrencia() + " - " + dataHora);
	ocorrencia.setAtividadeProjetoOcorrida(atividadeOcorrida);
	ocorrencia.setTipoDeEntidadeMensuravel(tipoOcorrenciaAtividade);

	//Persiste.	
	try
	{
	    manager.getTransaction().begin();
	    manager.persist(ocorrencia);
	    manager.getTransaction().commit();
	    
	    OcorrenciaDTO dto = new OcorrenciaDTO();
	    dto.setId(ocorrencia.getId());
	    dto.setNomeOcorrencia(ocorrencia.getNome());
	    dto.setNomeAtividadeOcorrida(ocorrencia.getAtividadeProjetoOcorrida().getNome());

	    URI location = new URI(String.format("%s/%s", uriInfo.getAbsolutePath().toString(), ocorrencia.getId()));
	    response = Response.created(location).entity(dto).build();
	}
	catch (Exception ex)
	{

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		response = Response.status(Status.CONFLICT).build();
	    }
	    else
	    {
		throw ex;
	    }
	}
	finally
	{
	    manager.close();
	}

	return response;
    }

    /**
     * Obtem a Ocorrência de Atividade
     * @param id - Id da Ocorrência de Atividade.
     * @return OcorrenciaAtividade
     * @throws Exception
     */
    @Path("OcorrenciaAtividade/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterOcorrenciaAtividade(@PathParam("id") Integer id) throws Exception
    {
	Response response;
	EntityManager manager = XPersistence.createManager();

	if (id == null || id == 0)
	    response = Response.status(Status.BAD_REQUEST).build();

	OcorrenciaAtividade ocorrencia = manager.find(OcorrenciaAtividade.class, id);

	if (ocorrencia == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	{
	    OcorrenciaDTO dto = new OcorrenciaDTO();
	    dto.setId(id);
	    dto.setNomeOcorrencia(ocorrencia.getNome());
	    dto.setNomeAtividadeOcorrida(ocorrencia.getAtividadeProjetoOcorrida().getNome());
	    response = Response.status(Status.OK).entity(dto).build();
	}

	return response;
    }

}

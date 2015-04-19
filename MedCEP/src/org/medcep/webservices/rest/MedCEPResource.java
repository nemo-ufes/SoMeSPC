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
import java.util.*;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.*;
import org.medcep.model.medicao.*;
import org.medcep.model.medicao.planejamento.*;
import org.medcep.model.organizacao.*;
import org.medcep.model.processo.*;
import org.medcep.webservices.rest.dto.*;
import org.medcep.webservices.rest.exceptions.*;
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
     * Obtem as periodicidades.
     * @return
     * @throws Exception
     */
    @Path("Periodicidade")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterPeriodicidades() throws Exception
    {
	Response response;

	TypedQuery<Periodicidade> query = XPersistence.getManager().createQuery("FROM Periodicidade", Periodicidade.class);
	List<Periodicidade> result = query.getResultList();

	if (result == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	{
	    List<PeriodicidadeDTO> listaDto = new ArrayList<PeriodicidadeDTO>();
	    
	    for (Periodicidade periodicidade : result)
	    {
		PeriodicidadeDTO p = new PeriodicidadeDTO();
		p.setNome(periodicidade.getNome());		
		listaDto.add(p);
	    }
	    
	    response = Response.status(Status.OK).entity(listaDto).build();	    
	}

	return response;
    }
    
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

	ProcessoProjeto processoOcorrido;
	try
	{
	    String query1 = "SELECT e FROM ProcessoProjeto e WHERE e.nome='" + ocorrenciaDto.getNomeProcessoOcorrido() + "'";
	    TypedQuery<ProcessoProjeto> typedQuery1 = manager.createQuery(query1, ProcessoProjeto.class);
	    processoOcorrido = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException("Processo Ocorrido não encontrada.");
	}
	
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
     * 
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

	OcorrenciaProcesso ocorrencia;

	try
	{
	    ocorrencia = manager.find(OcorrenciaProcesso.class, id);
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException("Ocorrência de Processo com ID " + id + " não encontrada.");
	}

	OcorrenciaDTO dto = new OcorrenciaDTO();
	dto.setId(id);
	dto.setNomeOcorrencia(ocorrencia.getNome());
	dto.setNomeProcessoOcorrido(ocorrencia.getProcessoProjetoOcorrido().getNome());
	response = Response.status(Status.OK).entity(dto).build();

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

	AtividadeProjeto atividadeOcorrida;

	try
	{
	    String query1 = "SELECT e FROM AtividadeProjeto e WHERE e.nome='" + ocorrenciaDto.getNomeAtividadeOcorrida() + "'";
	    TypedQuery<AtividadeProjeto> typedQuery1 = manager.createQuery(query1, AtividadeProjeto.class);
	    atividadeOcorrida = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException("Atividade Ocorrida não encontrada.");
	}

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
     * 
     * @param id
     *            - Id da Ocorrência de Atividade.
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

	OcorrenciaAtividade ocorrencia;

	try
	{
	    ocorrencia = manager.find(OcorrenciaAtividade.class, id);
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException("Ocorrência de Atividade com ID " + id + " não encontrada.");
	}

	OcorrenciaDTO dto = new OcorrenciaDTO();
	dto.setId(id);
	dto.setNomeOcorrencia(ocorrencia.getNome());
	dto.setNomeAtividadeOcorrida(ocorrencia.getAtividadeProjetoOcorrida().getNome());
	response = Response.status(Status.OK).entity(dto).build();

	return response;
    }

    /**
     * Cria uma definição operacional de medida.
     * @param dto
     * @return
     * @throws Exception
     */
    @Path("DefinicaoOperacionalMedida")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response criarDefinicaoOperacionalMedida(DefinicaoOperacionalMedidaDTO dto) throws Exception
    {
	Response response = null;
	EntityManager manager = XPersistence.createManager();

	if (dto == null || dto.getNome().isEmpty())
	{
	    response = Response.status(Status.BAD_REQUEST).build();
	}

	Medida medida;

	try
	{
	    String query1 = "SELECT e FROM Medida e WHERE e.nome='" + dto.getNomeMedida() + "'";
	    TypedQuery<Medida> typedQuery1 = manager.createQuery(query1, Medida.class);
	    medida = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException(String.format("Medida %s não encontrada.", dto.getNomeMedida()));
	}
	
	//Medição.
	AtividadePadrao momentoMedicao;

	try
	{
	    String query1 = "SELECT e FROM AtividadePadrao e WHERE e.nome='" + dto.getMomentoMedicao() + "'";
	    TypedQuery<AtividadePadrao> typedQuery1 = manager.createQuery(query1, AtividadePadrao.class);
	    momentoMedicao = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException(String.format("Momento de Medição %s não encontrado.", dto.getMomentoMedicao()));
	}
	
	Periodicidade periodicidadeMedicao;

	try
	{
	    String query1 = "SELECT e FROM Periodicidade e WHERE e.nome='" + dto.getPeriodicidadeMedicao() + "'";
	    TypedQuery<Periodicidade> typedQuery1 = manager.createQuery(query1, Periodicidade.class);
	    periodicidadeMedicao = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException(String.format("Periodicidade de Medição %s não encontrada.", dto.getPeriodicidadeMedicao()));
	}
	
	PapelRecursoHumano papelResponsavelMedicao;

	try
	{
	    String query1 = "SELECT e FROM PapelRecursoHumano e WHERE e.nome='" + dto.getPapelResponsavelMedicao() + "'";
	    TypedQuery<PapelRecursoHumano> typedQuery1 = manager.createQuery(query1, PapelRecursoHumano.class);
	    papelResponsavelMedicao = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException(String.format("Papel Responsável pela Medição %s não encontrado.", dto.getPapelResponsavelMedicao()));
	}

	ProcedimentoDeMedicao procedimentoMedicao;

	try
	{
	    String query1 = "SELECT e FROM ProcedimentoDeMedicao e WHERE e.nome='" + dto.getNomeProcedimentoMedicao() + "'";
	    TypedQuery<ProcedimentoDeMedicao> typedQuery1 = manager.createQuery(query1, ProcedimentoDeMedicao.class);
	    procedimentoMedicao = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException(String.format("Procedimento de Medição %s não encontrado.", dto.getNomeProcedimentoMedicao()));
	}

	//Análise de Medição.
	AtividadePadrao momentoAnalise;

	try
	{
	    String query1 = "SELECT e FROM AtividadePadrao e WHERE e.nome='" + dto.getMomentoAnalise() + "'";
	    TypedQuery<AtividadePadrao> typedQuery1 = manager.createQuery(query1, AtividadePadrao.class);
	    momentoAnalise = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException(String.format("Momento da Análise da Medição %s não encontrado.", dto.getMomentoAnalise()));
	}
	
	Periodicidade periodicidadeAnalise;

	try
	{
	    String query1 = "SELECT e FROM Periodicidade e WHERE e.nome='" + dto.getPeriodicidadeAnalise() + "'";
	    TypedQuery<Periodicidade> typedQuery1 = manager.createQuery(query1, Periodicidade.class);
	    periodicidadeAnalise = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException(String.format("Periodicidade da Análise da Medição %s não encontrada.", dto.getPeriodicidadeAnalise()));
	}
	
	PapelRecursoHumano papelResponsavelAnalise;

	try
	{
	    String query1 = "SELECT e FROM PapelRecursoHumano e WHERE e.nome='" + dto.getPapelResponsavelAnalise() + "'";
	    TypedQuery<PapelRecursoHumano> typedQuery1 = manager.createQuery(query1, PapelRecursoHumano.class);
	    papelResponsavelAnalise = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException(String.format("Papel Responsável pela Análise da Medição %s não encontrado.", dto.getPapelResponsavelAnalise()));
	}

	ProcedimentoDeAnaliseDeMedicao procedimentoAnalise;

	try
	{
	    String query1 = "SELECT e FROM ProcedimentoDeAnaliseDeMedicao e WHERE e.nome='" + dto.getNomeProcedimentoAnalise() + "'";
	    TypedQuery<ProcedimentoDeAnaliseDeMedicao> typedQuery1 = manager.createQuery(query1, ProcedimentoDeAnaliseDeMedicao.class);
	    procedimentoAnalise = typedQuery1.getSingleResult();
	}
	catch (Exception ex)
	{
	    throw new EntidadeNaoEncontradaException(String.format("Procedimento de Análise de Medição %s não encontrado.", dto.getNomeProcedimentoAnalise()));
	}
	
	DefinicaoOperacionalDeMedida definicao = new DefinicaoOperacionalDeMedida();
	
	definicao.setNome(dto.getNome());
	definicao.setData(dto.getData());
	definicao.setDescricao(dto.getDescricao());
	definicao.setMedida(medida);
	definicao.setMomentoDeMedicao(momentoMedicao);
	definicao.setPeriodicidadeDeMedicao(periodicidadeMedicao);
	definicao.setResponsavelPelaMedicao(papelResponsavelMedicao);
	definicao.setProcedimentoDeMedicao(procedimentoMedicao);
	definicao.setMomentoDeAnaliseDeMedicao(momentoAnalise);
	definicao.setPeriodicidadeDeAnaliseDeMedicao(periodicidadeAnalise);
	definicao.setResponsavelPelaAnaliseDeMedicao(papelResponsavelAnalise);
	definicao.setProcedimentoDeAnaliseDeMedicao(procedimentoAnalise);
		
	
	//Persiste.	
	try
	{
	    manager.getTransaction().begin();
	    manager.persist(definicao);
	    manager.getTransaction().commit();

	    DefinicaoOperacionalMedidaDTO definicaoDto = new DefinicaoOperacionalMedidaDTO();
	    
	    definicaoDto.setId(definicao.getId());
	    definicaoDto.setData(definicao.getData());
	    definicaoDto.setDescricao(definicao.getDescricao());
	    definicaoDto.setNomeMedida(definicao.getMedida().getNome());
	    definicaoDto.setMomentoMedicao(definicao.getMomentoDeMedicao().getNome());
	    definicaoDto.setPeriodicidadeMedicao(definicao.getPeriodicidadeDeMedicao().getNome());
	    definicaoDto.setPapelResponsavelMedicao(definicao.getResponsavelPelaMedicao().getNome());
	    definicaoDto.setNomeProcedimentoMedicao(definicao.getProcedimentoDeMedicao().getNome());
	    definicaoDto.setMomentoAnalise(definicao.getMomentoDeAnaliseDeMedicao().getNome());
	    definicaoDto.setPeriodicidadeAnalise(definicao.getPeriodicidadeDeAnaliseDeMedicao().getNome());
	    definicaoDto.setPapelResponsavelAnalise(definicao.getResponsavelPelaAnaliseDeMedicao().getNome());
	    definicaoDto.setNomeProcedimentoAnalise(definicao.getProcedimentoDeAnaliseDeMedicao().getNome());
	    
	    URI location = new URI(String.format("%s/%s", uriInfo.getAbsolutePath().toString(), definicao.getId()));
	    response = Response.created(location).entity(definicaoDto).build();
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

    
    
}

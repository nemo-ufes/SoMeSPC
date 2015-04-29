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
import java.util.Date;

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
     * 
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
	EntityManager manager = XPersistence.createManager();

	TypedQuery<Periodicidade> query = manager.createQuery("FROM Periodicidade", Periodicidade.class);
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

	manager.close();
	return response;
    }

    /**
     * Obtem os objetivos estrategicos.
     * 
     * @return
     * @throws Exception
     */
    @Path("ObjetivoEstrategico")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterObjetivosEstrategicos() throws Exception
    {
	Response response;
	EntityManager manager = XPersistence.createManager();

	TypedQuery<ObjetivoEstrategico> query = manager.createQuery("FROM ObjetivoEstrategico", ObjetivoEstrategico.class);
	List<ObjetivoEstrategico> result = query.getResultList();

	if (result == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	{
	    List<ObjetivoEstrategicoDTO> listaDto = new ArrayList<ObjetivoEstrategicoDTO>();

	    for (ObjetivoEstrategico obj : result)
	    {
		ObjetivoEstrategicoDTO o = new ObjetivoEstrategicoDTO();
		o.setNome(obj.getNome());
		listaDto.add(o);
	    }

	    response = Response.status(Status.OK).entity(listaDto).build();
	}

	manager.close();
	return response;
    }

    /**
     * Obtem os objetivos de software.
     * 
     * @return
     * @throws Exception
     */
    @Path("ObjetivoSoftware")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterObjetivosSoftware() throws Exception
    {
	Response response;
	EntityManager manager = XPersistence.createManager();

	TypedQuery<ObjetivoDeSoftware> query = manager.createQuery("FROM ObjetivoDeSoftware", ObjetivoDeSoftware.class);
	List<ObjetivoDeSoftware> result = query.getResultList();

	if (result == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	{
	    List<ObjetivoSoftwareDTO> listaDto = new ArrayList<ObjetivoSoftwareDTO>();

	    for (ObjetivoDeSoftware obj : result)
	    {
		ObjetivoSoftwareDTO dto = new ObjetivoSoftwareDTO();
		dto.setObjetivosEstrategicos(new ArrayList<String>());
		dto.setNome(obj.getNome());

		for (ObjetivoEstrategico objEstrategico : obj.getObjetivoEstrategico())
		{
		    dto.getObjetivosEstrategicos().add(objEstrategico.getNome());
		}

		listaDto.add(dto);
	    }

	    response = Response.status(Status.OK).entity(listaDto).build();
	}

	manager.close();
	return response;
    }

    /**
     * Obtem os objetivos de medição.
     * 
     * @return
     * @throws Exception
     */
    @Path("ObjetivoMedicao")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterObjetivosMedicao() throws Exception
    {
	Response response;
	EntityManager manager = XPersistence.createManager();

	TypedQuery<ObjetivoDeMedicao> query = manager.createQuery("FROM ObjetivoDeMedicao", ObjetivoDeMedicao.class);
	List<ObjetivoDeMedicao> result = query.getResultList();

	if (result == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	{
	    List<ObjetivoMedicaoDTO> listaDto = new ArrayList<ObjetivoMedicaoDTO>();

	    for (ObjetivoDeMedicao obj : result)
	    {
		ObjetivoMedicaoDTO dto = new ObjetivoMedicaoDTO();
		dto.setObjetivosEstrategicos(new ArrayList<String>());
		dto.setObjetivosSoftware(new ArrayList<String>());
		dto.setNome(obj.getNome());

		for (ObjetivoEstrategico objEstrategico : obj.getObjetivoEstrategico())
		{
		    dto.getObjetivosEstrategicos().add(objEstrategico.getNome());
		}

		for (ObjetivoDeSoftware objSoftware : obj.getObjetivoDeSoftware())
		{
		    dto.getObjetivosSoftware().add(objSoftware.getNome());
		}

		listaDto.add(dto);
	    }

	    response = Response.status(Status.OK).entity(listaDto).build();
	}

	manager.close();

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
    public synchronized Response criarOcorrenciaProcesso(OcorrenciaDTO ocorrenciaDto) throws Exception
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
    public synchronized Response criarOcorrenciaAtividade(OcorrenciaDTO ocorrenciaDto) throws Exception
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

    @Path("Medicao/Pagina")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterPaginasMedicoes(
	    @QueryParam("tamanhoPagina") int tamanhoPagina,
	    @QueryParam("medida") int idMedidaPlano,
	    @QueryParam("entidade") int idEntidade)
    {
	Response response;
	EntityManager manager = XPersistence.createManager();

	Query queryTotal = manager.createQuery(String.format("SELECT COUNT(*) FROM Medicao m "
		+ "WHERE m.medidaPlanoDeMedicao.medida.id = %d "
		+ "AND m.entidadeMensuravel.id = %d", idMedidaPlano, idEntidade));

	Long total = (Long) queryTotal.getSingleResult();

	manager.close();
	int ultimaPagina = (int) ((total / tamanhoPagina) + 1);

	List<Integer> paginas = new ArrayList<Integer>();
	for (int i = 1; i < ultimaPagina; i++)
	{
	    paginas.add(new Integer(i));
	}

	response = Response.status(Status.OK).entity(paginas).build();
	return response;
    }

    @Path("Medicao/Total")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterTotalMedicoes(
	    @QueryParam("medida") int idMedidaPlano,
	    @QueryParam("entidade") int idEntidade)
    {
	Response response;
	EntityManager manager = XPersistence.createManager();

	Query queryTotal = manager.createQuery(String.format("SELECT COUNT(*) FROM Medicao m "
		+ "WHERE m.medidaPlanoDeMedicao.medida.id = %d "
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
    public Response obterMedicoes(@QueryParam("medida") int idMedidaPlano,
	    @QueryParam("entidade") int idEntidade,
	    @QueryParam("indiceAtual") int indiceAtual,
	    @QueryParam("tamanhoPagina") int tamanhoPagina) throws Exception
    {
	Response response;

	if (idMedidaPlano == 0 || idEntidade == 0)
	{
	    response = Response.status(Status.BAD_REQUEST).build();
	}
	else
	{
	    EntityManager manager = XPersistence.createManager();

	    TypedQuery<Medicao> query = manager.createQuery(String.format("Select m FROM Medicao m "
		    + "WHERE m.medidaPlanoDeMedicao.medida.id = %d "
		    + "AND m.entidadeMensuravel.id = %d ORDER BY m.data ASC", idMedidaPlano, idEntidade), Medicao.class)
		    .setFirstResult((indiceAtual * tamanhoPagina) - tamanhoPagina)
		    .setMaxResults(tamanhoPagina);

	    List<Medicao> result = query.getResultList();

	    if (result == null)
		response = Response.status(Status.NOT_FOUND).build();
	    else
	    {
		List<MedicaoDTO> listaDto = new ArrayList<MedicaoDTO>();

		for (Medicao medicao : result)
		{
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
    public Response obterEntidadesComMedicoes() throws Exception
    {
	Response response;

	EntityManager manager = XPersistence.createManager();

	TypedQuery<EntidadeMensuravel> query = manager.createQuery(
		"Select distinct(m.entidadeMensuravel) FROM Medicao m "
		, EntidadeMensuravel.class);
	List<EntidadeMensuravel> result = query.getResultList();

	if (result == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	{
	    List<EntidadeMensuravelDTO> listaDto = new ArrayList<EntidadeMensuravelDTO>();

	    for (EntidadeMensuravel em : result)
	    {
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
     * @param id- Id da entidade para buscar as medidas.
     * @return Medidas das da entidade informada.
     * @throws Exception
     */
    @Path("Medicao/Entidade/{id}/Medida")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterMedidasdasEntidadesComMedicoes(@PathParam("id") int id) throws Exception
    {
	Response response;

	if (id == 0)
	{
	    response = Response.status(Status.BAD_REQUEST).build();
	}

	else
	{

	    EntityManager manager = XPersistence.createManager();

	    TypedQuery<Medida> query = manager.createQuery(
		    "Select distinct(m.medidaPlanoDeMedicao.medida) "
			    + "FROM Medicao m "
			    + "WHERE m.entidadeMensuravel.id = " + id
		    , Medida.class);
	    List<Medida> result = query.getResultList();

	    if (result == null)
		response = Response.status(Status.NOT_FOUND).build();
	    else
	    {
		List<MedidaDTO> listaDto = new ArrayList<MedidaDTO>();

		for (Medida med : result)
		{
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
     * @param idEntidade - Id da entidade para buscar os valores
     * @param idMedida - Id da medida para buscar os valores
     * @return Valores da medida.
     * @throws Exception
     */
    @Path("Medicao/Entidade/{id}/Medida/{id2}/Valor")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterValorMedicoesPorMedidaEntidade(@QueryParam("id") int idEntidade, @QueryParam("id2") int idMedida) throws Exception
    {
	Response response;

	if (idEntidade == 0)
	{
	    response = Response.status(Status.BAD_REQUEST).build();
	}

	else
	{

	    EntityManager manager = XPersistence.createManager();

	    TypedQuery<String> query = manager.createQuery(
		    "Select m.valorMedido.valorMedido "
			    + "FROM Medicao m "
			    + "WHERE m.entidadeMensuravel.id = " + idEntidade + " "
			    + "AND m.medidaPlanoDeMedicao.medida.id = " + idMedida
			    + " ORDER BY m.id"
		    , String.class);
	    List<String> result = query.getResultList();

	    if (result == null)
		response = Response.status(Status.NOT_FOUND).build();
	    else
	    {
		List<Integer> listaDto = new ArrayList<Integer>();

		for (String valor : result)
		{
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
    public Response obterDataHoraMedicoesPorMedidaEProjeto(@QueryParam("entidade") int idEntidade, @QueryParam("medida") int idMedida) throws Exception
    {
	Response response;

	if (idEntidade == 0)
	{
	    response = Response.status(Status.BAD_REQUEST).build();
	}

	else
	{

	    EntityManager manager = XPersistence.createManager();

	    TypedQuery<Medicao> query = manager.createQuery(
		    "Select m "
			    + "FROM Medicao m "
			    + "WHERE m.entidadeMensuravel.id = " + idEntidade + " "
			    + "AND m.medidaPlanoDeMedicao.medida.id = " + idMedida
			    + " ORDER BY m.id"
		    , Medicao.class);
	    List<Medicao> result = query.getResultList();

	    if (result == null)
		response = Response.status(Status.NOT_FOUND).build();
	    else
	    {
		List<Date> listaDto = new ArrayList<Date>();

		for (Medicao med : result)
		{
		    listaDto.add(med.getData());
		}

		response = Response.status(Status.OK).entity(listaDto).build();
	    }

	    manager.close();
	}
	return response;
    }

    /**
     * Cria uma definição operacional de medida.
     * 
     * @param dto
     * @return
     * @throws Exception
     */
    @Path("DefinicaoOperacionalMedida")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public synchronized Response criarDefinicaoOperacionalMedida(DefinicaoOperacionalMedidaDTO dto) throws Exception
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

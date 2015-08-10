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
package org.medcep.integracao.taiga;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Calendar;

import javax.persistence.*;
import javax.ws.rs.client.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.*;
import org.hibernate.validator.*;
import org.medcep.integracao.agendador.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.integracao.taiga.model.Projeto;
import org.medcep.model.definicao_operacional_de_medida.DefinicaoOperacionalDeMedida;
import org.medcep.model.definicao_operacional_de_medida.Periodicidade;
import org.medcep.model.definicao_operacional_de_medida.ProcedimentoDeAnaliseDeMedicao;
import org.medcep.model.definicao_operacional_de_medida.ProcedimentoDeMedicao;
import org.medcep.model.entidades_e_medidas.*;
import org.medcep.model.medicao.ContextoDeMedicao;
import org.medcep.model.medicao.Medicao;
import org.medcep.model.medicao.ValorNumerico;
import org.medcep.model.objetivos.NecessidadeDeInformacao;
import org.medcep.model.objetivos.ObjetivoDeMedicao;
import org.medcep.model.objetivos.ObjetivoEstrategico;
import org.medcep.model.organizacao_de_software.*;
import org.medcep.model.plano_de_medicao.*;
import org.medcep.model.processo_de_software.*;
import org.medcep.util.json.*;
import org.openxava.jpa.*;
import org.quartz.*;

/**
 * Classe para integração do Taiga com a MedCEP.
 * 
 * @author Vinicius
 *
 */
public class TaigaIntegrator
{
    private final String urlTaiga;
    private final AuthInfo authInfo;
    private static Client client;

    /**
     * Construtor
     * 
     * @param urlTaiga
     *            - URL base do Taiga.
     * @param usuario
     *            - login do Taiga.
     * @param senha
     *            - senha do Taiga.
     */
    public TaigaIntegrator(String urlTaiga, String usuario, String senha)
    {
	if (urlTaiga.endsWith("/"))
	{
	    urlTaiga = urlTaiga.substring(0, urlTaiga.length() - 1);
	}

	this.urlTaiga = urlTaiga + "/api/v1/";
	this.authInfo = new AuthInfo(usuario, senha);
	client = ClientBuilder.newClient();
    }

    /**
     * Busca o token de autenticação do Taiga.
     */
    public String obterAuthToken()
    {
	WebTarget target = client.target(this.urlTaiga).path("auth");

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.post(Entity.entity(authInfo, MediaType.APPLICATION_JSON));

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException("Erro ao obter o token de autenticação do Taiga. HTTP Code: " + response.getStatus());
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	return json.get("auth_token").toString();
    }

    /**
     * Obtem os dados do projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto a ser buscado.
     * @return Projeto
     */
    public Projeto obterProjetoTaiga(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("projects/" + idProjeto);

	Projeto projeto = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(Projeto.class);

	return projeto;
    }

    /**
     * Obtem os dados do projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto a ser buscado.
     * @return Json do projeto Taiga
     */
    public String obterProjetoTaigaJson(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("projects/" + idProjeto);

	Response projetoResponse = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	JSONObject projetoJson = new JSONObject(projetoResponse.readEntity(String.class));

	return projetoJson.toString();
    }

    /**
     * Obtem os dados de Sprints do Taiga em JSON.
     * 
     * @return Json das Sprints Taiga
     */
    public String obterSprintsTaigaJson()
    {
	//Busca informações de milestones.
	WebTarget target = client.target(this.urlTaiga).path("milestones");

	Response milestoneResponse = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	JSONArray milestoneJson = new JSONArray(milestoneResponse.readEntity(String.class));

	return milestoneJson.toString();
    }

    /**
     * Obtem as Sprints de um determinado projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido do projeto.
     * @return List<Sprint> - Sprints do projeto
     */
    public List<Sprint> obterSprintsDoProjetoTaiga(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações das sprints.
	target = client.target(this.urlTaiga).path("milestones").queryParam("project", idProjeto);

	List<Sprint> sprints = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(new GenericType<List<Sprint>>() {
		});

	return sprints;
    }

    /**
     * Obtem o Estado de uma Sprint de um projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido do projeto.
     * @param apelidoSprint
     *            - Apelido da Sprint.
     * @return Estado atual da Sprint.
     */
    public EstadoSprint obterEstadoSprintTaiga(String apelidoProjeto, String apelidoSprint)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase()).queryParam("milestone", apelidoSprint.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idSprint = json.getInt("milestone");

	//Busca informações da sprint.
	target = client.target(this.urlTaiga).path(String.format("milestones/%d/stats", idSprint));

	EstadoSprint estadoSprint = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(EstadoSprint.class);

	return estadoSprint;
    }

    /**
     * Obtem demais dados de andamento do projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto a ser buscado.
     * @return Json de dados do andamento do projeto Taiga
     */
    public String obterEstadoProjetoTaigaJson(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path(String.format("projects/%d/stats", idProjeto));

	Response projetoResponse = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	JSONObject projetoJson = new JSONObject(projetoResponse.readEntity(String.class));

	return projetoJson.toString();
    }

    /**
     * Obtem o estado de um projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto a ser obtido.
     * @return EstadoProjeto - Estado do Projeto com medidas obtidas do projeto Taiga.
     */
    public EstadoProjeto obterEstadoProjetoTaiga(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path(String.format("projects/%d/stats", idProjeto));

	EstadoProjeto estado = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(EstadoProjeto.class);

	return estado;
    }

    /**
     * Obtem as estorias do Project Backlog de um Projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto.
     * @return JSON com as estórias do Project Backlog.
     */
    public String obterEstoriasDoProjectBacklogTaigaJson(String apelidoProjeto)
    {

	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("userstories").queryParam("project", idProjeto).queryParam("milestone__isnull", true);

	Response estoriasResponse = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	JSONArray estorias = new JSONArray(estoriasResponse.readEntity(String.class));

	return estorias.toString();
    }

    /**
     * Obtem as estorias do Project Backlog de um Projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto.
     * @return List<Estoria> com as estórias do Project Backlog.
     */
    public List<Estoria> obterEstoriasDoProjectBacklogTaiga(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("userstories").queryParam("project", idProjeto).queryParam("milestone__isnull", true);

	List<Estoria> estorias = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(new GenericType<List<Estoria>>() {
		});

	return estorias;
    }

    /**
     * Obtem as estorias do Project Backlog de uma Sprint Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto.
     * @param apelidoSprint
     *            - Apelido (slug) da Sprint
     * @return JSON com as estórias da Sprint Backlog.
     */
    public String obterEstoriasDaSprintBacklogTaigaJson(String apelidoProjeto, String apelidoSprint)
    {

	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase()).queryParam("milestone", apelidoSprint.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");
	int idSprint = json.getInt("milestone");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("userstories").queryParam("project", idProjeto).queryParam("milestone", idSprint);

	Response estoriasResponse = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	JSONArray estorias = new JSONArray(estoriasResponse.readEntity(String.class));

	return estorias.toString();
    }

    /**
     * Obtem as estorias do Project Backlog de um Projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto.
     * @return List<Estoria> com as estórias do Project Backlog.
     */
    public List<Estoria> obterEstoriasDaSprintBacklogTaiga(String apelidoProjeto, String apelidoSprint)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase()).queryParam("milestone", apelidoSprint.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");
	int idSprint = json.getInt("milestone");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("userstories").queryParam("project", idProjeto).queryParam("milestone", idSprint);

	List<Estoria> estorias = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(new GenericType<List<Estoria>>() {
		});

	return estorias;
    }

    /**
     * Obtem todos os projetos.
     * 
     * @return List<Projeto>
     */
    public List<Projeto> obterProjetosTaiga()
    {
	//Busca informações dos projetos.
	WebTarget target = client.target(this.urlTaiga).path("projects");

	List<Projeto> projetos = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(new GenericType<List<Projeto>>() {
		});

	return projetos;
    }

    /**
     * Obtem os dados do Membro pelo ID.
     * 
     * @param idMembro
     *            - ID do membro a ser buscado.
     * @return Membro
     */
    public Membro obterMembroTaiga(int idMembro)
    {
	//Busca informações do membro.
	WebTarget target = client.target(this.urlTaiga).path("memberships/" + idMembro);
	Membro membro;

	try
	{
	    membro = target
		    .request(MediaType.APPLICATION_JSON_TYPE)
		    .header("Authorization", String.format("Bearer %s", obterAuthToken()))
		    .get(Membro.class);
	}
	catch (Exception ex)
	{
	    membro = null;
	}

	return membro;
    }

    /**
     * Obtem os dados do Membro pelo ID.
     * 
     * @param idMembro
     *            - ID do membro a ser buscado.
     * @return Membro
     */
    public List<Membro> obterMembrosDoProjetoTaiga(int idProjeto)
    {
	//Busca informações do membro.
	WebTarget target = client.target(this.urlTaiga).path("memberships").queryParam("project", idProjeto);

	List<Membro> membros = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(new GenericType<List<Membro>>() {
		});

	return membros;
    }
    
    /**
     * Obtem os dados de Sprints do Taiga em JSON.
     * 
     * @return Json das Sprints Taiga
     */
    public String obterMembrosDoProjetoTaigaJson(int idProjeto)
    {
	//Busca informações do membro.
	WebTarget target = client.target(this.urlTaiga).path("memberships").queryParam("project", idProjeto);

	Response membrosResponse = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	JSONArray membrosJson = new JSONArray(membrosResponse.readEntity(String.class));

	return membrosJson.toString();
    }

    /**
     * Obtem as periodicidades cadastradas.
     * 
     * @return
     */
    public List<Periodicidade> obterPeriodicidades()
    {
	EntityManager manager = XPersistence.createManager();

	TypedQuery<Periodicidade> query = manager.createQuery("FROM Periodicidade", Periodicidade.class);
	List<Periodicidade> result = query.getResultList();

	manager.close();
	return result;
    }

    /**
     * Cadastra um RecursoHumano na MedCEP a partir de um Membro do Taiga.
     * Se já existir, retorna o RecursoHumano existente.
     * 
     * @param membro
     *            - Membro a ser cadastrado.
     * @return RecursoHumano criado/existente.
     * @throws Exception
     */
    public RecursoHumano criarRecursoHumanoMedCEP(Membro membro) throws Exception
    {

	EntityManager manager = XPersistence.createManager();
	RecursoHumano recursoHumano = new RecursoHumano();
	recursoHumano.setNome(membro.getNome());

	try
	{
	    String query = String.format("SELECT r FROM RecursoHumano r WHERE r.nome='%s'", membro.getNome());
	    TypedQuery<RecursoHumano> typedQuery = manager.createQuery(query, RecursoHumano.class);
	    recursoHumano = typedQuery.getSingleResult();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    manager.getTransaction().begin();
	    manager.persist(recursoHumano);
	    manager.getTransaction().commit();

	}
	finally
	{
	    manager.close();
	}

	return recursoHumano;
    }

    /**
     * Cadastra um Papel de Recurso Humano na MedCEP a partir de um Membro do Taiga.
     * Se já existir, retorna o Papel existente.
     * 
     * @param membro
     *            - Membro a ser cadastrado.
     * @return Papel criado/existente.
     * @throws Exception
     */
    public PapelRecursoHumano criarPapelRecursoHumanoMedCEP(Membro membro) throws Exception
    {
	EntityManager manager = XPersistence.createManager();
	PapelRecursoHumano papel = new PapelRecursoHumano();
	papel.setNome(membro.getPapel());

	try
	{
	    String query = String.format("SELECT p FROM PapelRecursoHumano p WHERE p.nome='%s'", membro.getPapel());
	    TypedQuery<PapelRecursoHumano> typedQuery = manager.createQuery(query, PapelRecursoHumano.class);
	    papel = typedQuery.getSingleResult();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    manager.getTransaction().begin();
	    manager.persist(papel);
	    manager.getTransaction().commit();

	}
	finally
	{
	    manager.close();
	}

	return papel;
    }

    /**
     * Cria uma Equipe na MedCEP baseado nos Membros do projeto Taiga.
     * Se já existir, retorna a Equipe existente.
     * 
     * @param nomeEquipe
     *            - Nome da equipe a ser criada.
     * @param membrosDaEquipe
     *            - Membros da equipe.
     * @return Equipe criada/existente.
     * @throws Exception
     */
    public Equipe criarEquipeMedCEP(String nomeEquipe, List<Membro> membrosDaEquipe) throws Exception
    {
	EntityManager manager = XPersistence.createManager();

	Equipe equipe = new Equipe();
	equipe.setNome(nomeEquipe);

	String tipoEntidadeQuery = String.format("SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Alocação de Recurso Humano'");
	TypedQuery<TipoDeEntidadeMensuravel> tipoEntidadeTypedQuery = manager.createQuery(tipoEntidadeQuery, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoAlocacaco = tipoEntidadeTypedQuery.getSingleResult();

	//Cria os recursos humanos, papeis e faz a alocação.
	List<AlocacaoEquipe> alocacoes = new ArrayList<AlocacaoEquipe>();
	List<RecursoHumano> recursosHumanos = new ArrayList<RecursoHumano>();

	for (Membro membro : membrosDaEquipe)
	{
	    AlocacaoEquipe alocacao = new AlocacaoEquipe();
	    alocacao.setEquipe(equipe);

	    //Insere o Recurso Humano na Equipe e na Alocacao. 
	    //Acredito que relacionamento direto entre Equipe <-> RecursoHumano seja para facilitar a visualização dos recursos da equipe.
	    RecursoHumano rec = this.criarRecursoHumanoMedCEP(membro);
	    recursosHumanos.add(rec);

	    alocacao.setRecursoHumano(rec);
	    alocacao.setPapelRecursoHumano(this.criarPapelRecursoHumanoMedCEP(membro));
	    alocacao.setTipoDeEntidadeMensuravel(tipoAlocacaco);
	    alocacoes.add(alocacao);
	}

	equipe.setRecursoHumano(recursosHumanos);
	equipe.setAlocacaoEquipe(alocacoes);

	try
	{
	    manager.getTransaction().begin();
	    manager.persist(equipe);

	    for (AlocacaoEquipe alocacaoEquipe : alocacoes)
	    {
		manager.persist(alocacaoEquipe);
	    }

	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println(String.format("Equipe %s já existe.", equipe.getNome()));

		String query = String.format("SELECT e FROM Equipe e WHERE e.nome='%s'", equipe.getNome());
		TypedQuery<Equipe> typedQuery = manager.createQuery(query, Equipe.class);

		equipe = typedQuery.getSingleResult();
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

	return equipe;
    }

    /**
     * Adiciona um membro na equipe.
     * 
     * @param equipe
     * @param membro
     * @return
     * @throws Exception
     */
    public Equipe adicionarMembroEmEquipeMedCEP(Equipe equipe, Membro membro) throws Exception
    {
	EntityManager manager = XPersistence.createManager();

	//Verifica se o membro já está na equipe.
	for (AlocacaoEquipe aloc : equipe.getAlocacaoEquipe())
	{
	    if (aloc.getRecursoHumano().getNome().equalsIgnoreCase(membro.getNome())
		    && aloc.getPapelRecursoHumano().getNome().equalsIgnoreCase(membro.getPapel()))
	    {
		return equipe;
	    }
	}

	String equipeQuery = String.format("SELECT e FROM Equipe e WHERE e.nome='%s'", equipe.getNome());
	TypedQuery<Equipe> equipeTypedQuery = manager.createQuery(equipeQuery, Equipe.class);
	Equipe equipeExistente = equipeTypedQuery.getSingleResult();

	String tipoEntidadeQuery = String.format("SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Alocação de Recurso Humano'");
	TypedQuery<TipoDeEntidadeMensuravel> tipoEntidadeTypedQuery = manager.createQuery(tipoEntidadeQuery, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoAlocacaco = tipoEntidadeTypedQuery.getSingleResult();

	AlocacaoEquipe alocacao = new AlocacaoEquipe();
	alocacao.setEquipe(equipeExistente);

	//Insere o Recurso Humano na Equipe e na Alocacao. 
	//Acredito que relacionamento direto entre Equipe <-> RecursoHumano seja para facilitar a visualização dos recursos da equipe.
	RecursoHumano rec = this.criarRecursoHumanoMedCEP(membro);

	alocacao.setRecursoHumano(rec);
	alocacao.setPapelRecursoHumano(this.criarPapelRecursoHumanoMedCEP(membro));
	alocacao.setTipoDeEntidadeMensuravel(tipoAlocacaco);

	try
	{
	    manager.getTransaction().begin();
	    manager.persist(alocacao);
	    manager.getTransaction().commit();
	}
	finally
	{
	    manager.close();
	}

	return equipe;
    }

    /**
     * Cria um Projeto na MedCEP baseado em um Projeto Taiga.
     * Se já existir, retorna o Projeto MedCEP existente.
     * 
     * @param projeto
     *            - projeto Taiga a ser criado.
     * @return Projeto MedCEP criado/existente.
     * @throws Exception
     */
    public org.medcep.model.organizacao_de_software.Projeto criarProjetoMedCEP(Projeto projeto) throws Exception
    {
	EntityManager manager = XPersistence.createManager();
	Equipe equipe = this.criarEquipeMedCEP("Equipe " + projeto.getNome(), projeto.getMembros());
	List<Equipe> equipes = new ArrayList<Equipe>();
	equipes.add(equipe);

	String tipoEntidadeQuery = String.format("SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Projeto'");
	TypedQuery<TipoDeEntidadeMensuravel> tipoEntidadeTypedQuery = manager.createQuery(tipoEntidadeQuery, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoProjeto = tipoEntidadeTypedQuery.getSingleResult();

	org.medcep.model.organizacao_de_software.Projeto projetoMedCEP = new org.medcep.model.organizacao_de_software.Projeto();
	projetoMedCEP.setNome(projeto.getNome());
	projetoMedCEP.setEquipe(equipes);
	projetoMedCEP.setDataInicio(projeto.getDataCriacao());
	projetoMedCEP.setTipoDeEntidadeMensuravel(tipoProjeto);

	try
	{
	    manager.getTransaction().begin();
	    manager.persist(projetoMedCEP);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println(String.format("Projeto %s já existe.", projeto.getNome()));

		String query = String.format("SELECT p FROM Projeto p WHERE p.nome='%s'", projeto.getNome());
		TypedQuery<org.medcep.model.organizacao_de_software.Projeto> typedQuery = manager.createQuery(query, org.medcep.model.organizacao_de_software.Projeto.class);

		projetoMedCEP = typedQuery.getSingleResult();
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

	return projetoMedCEP;
    }

    /**
     * Cria as Medidas do Taiga no banco de dados da MedCEP.
     * Não atribui valores, somente cria as definições das medidas.
     * 
     * @param medidasTaiga
     *            - Lista com as medidas Taiga a serem criadas na MedCEP.
     * @return List<Medida> - Medidas criadas na MedCEP.
     * @throws Exception
     */
    public List<Medida> criarMedidasMedCEP(List<MedidasTaiga> medidasTaiga) throws Exception
    {

	this.criarAtividadesPadraoScrumMedCEP();

	EntityManager manager = XPersistence.createManager();
	List<Medida> medidasCadastradas = new ArrayList<Medida>();

	//Obtem o tipo de medida base.
	String query = "SELECT mb FROM AtividadePadrao mb WHERE mb.nome='Sprint'";
	TypedQuery<AtividadePadrao> typedQuery = manager.createQuery(query, AtividadePadrao.class);
	AtividadePadrao sprint = typedQuery.getSingleResult();

	//Obtem o tipo de medida base.
	String query1 = "SELECT mb FROM TipoMedida mb WHERE mb.nome='Medida Base'";
	TypedQuery<TipoMedida> typedQuery1 = manager.createQuery(query1, TipoMedida.class);
	TipoMedida medidaBase = typedQuery1.getSingleResult();

	//Obtem a escala racional.
	String query2 = "SELECT e FROM Escala e WHERE e.nome='Números Racionais'";
	TypedQuery<Escala> typedQuery2 = manager.createQuery(query2, Escala.class);
	Escala escala = typedQuery2.getSingleResult();

	//Obtem a unidade de medida Pontos de Estória.
	String query3 = "SELECT u FROM UnidadeDeMedida u WHERE u.nome='Pontos de Estória'";
	TypedQuery<UnidadeDeMedida> typedQuery3 = manager.createQuery(query3, UnidadeDeMedida.class);
	UnidadeDeMedida unidadeMedida = typedQuery3.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Projeto.
	String query5 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Projeto'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery5 = manager.createQuery(query5, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoProjeto = typedQuery5.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Recurso Humano.
	String query6 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Alocação de Recurso Humano'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery6 = manager.createQuery(query6, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoAlocacaoEquipe = typedQuery6.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Atividade Padrão.
	String query7 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Atividade Padrão'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery7 = manager.createQuery(query7, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoAtividadePadrao = typedQuery7.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Tipo de Artefatoo.
	String query8 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Tipo de Artefato'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery8 = manager.createQuery(query8, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoArtefato = typedQuery8.getSingleResult();

	//Obtem o ElementoMensuravel Desempenho.
	String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
	TypedQuery<ElementoMensuravel> typedQueryDesempenho = manager.createQuery(queryDesempenho, ElementoMensuravel.class);
	ElementoMensuravel desempenho = typedQueryDesempenho.getSingleResult();

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	@SuppressWarnings("unused")
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	manager.close();

	boolean primeiroLoop = true;

	//Define a medida de acordo com a lista informada.
	for (MedidasTaiga medidaTaiga : medidasTaiga)
	{
	    Medida medida = new Medida();
	    medida.setNome(medidaTaiga.toString());

	    switch (medidaTaiga)
	    {
		case PONTOS_ALOCADOS_PROJETO:
		    medida.setMnemonico("TAIGA-PA");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case PONTOS_ALOCADOS_POR_PAPEL_PROJETO:
		    medida.setMnemonico("TAIGA-PAP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacaoEquipe)));
		    break;
		case PONTOS_DEFINIDOS_PROJETO:
		    medida.setMnemonico("TAIGA-PD");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case PONTOS_DEFINIDOS_POR_PAPEL_PROJETO:
		    medida.setMnemonico("TAIGA-PDP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacaoEquipe)));
		    break;
		case PONTOS_FECHADOS_PROJETO:
		    medida.setMnemonico("TAIGA-PF");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case PONTOS_FECHADOS_POR_PAPEL_PROJETO:
		    medida.setMnemonico("TAIGA-PFP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacaoEquipe)));
		    break;
		case TOTAL_SPRINTS_PROJETO:
		    medida.setMnemonico("TAIGA-TM");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case TOTAL_PONTOS_PROJETO:
		    medida.setMnemonico("TAIGA-TP");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case VELOCIDADE_PROJETO:
		    medida.setMnemonico("TAIGA-VEL");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case DOSES_IOCAINE_SPRINT:
		    medida.setMnemonico("TAIGA-IOC");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case ESTORIAS_COMPLETADAS_SPRINT:
		    medida.setMnemonico("TAIGA-ECS");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case PONTOS_COMPLETADOS_SPRINT:
		    medida.setMnemonico("TAIGA-PCS");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case TAREFAS_COMPLETADAS_SPRINT:
		    medida.setMnemonico("TAIGA-TCS");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case TOTAL_ESTORIAS_SPRINT:
		    medida.setMnemonico("TAIGA-TES");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case TOTAL_PONTOS_SPRINT:
		    medida.setNome("Total de Pontos da Sprint");
		    medida.setMnemonico("TAIGA-TPS");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case TOTAL_TAREFAS_SPRINT:
		    medida.setMnemonico("TAIGA-TTS");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case PONTOS_ESTORIA:
		    medida.setMnemonico("TAIGA-PES");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoArtefato)));
		    break;
		default:
		    System.out.println(String.format("Medida %s inexistente no Taiga.", medidaTaiga.toString()));
	    }

	    medida.setDescricao("Medida obtida pela API Taiga conforme a documentação: http://taigaio.github.io/taiga-doc/dist/api.html");
	    medida.setTipoMedida(medidaBase);
	    medida.setEscala(escala);
	    medida.setUnidadeDeMedida(unidadeMedida);

	    DefinicaoOperacionalDeMedida definicao = new DefinicaoOperacionalDeMedida();

	    definicao.setNome("Definição operacional padrão Taiga-MedCEP para " + medida.getNome());
	    Calendar cal = Calendar.getInstance();
	    definicao.setData(cal.getTime());
	    definicao.setDescricao("Definição operacional criada automaticamente.");
	    definicao.setMedida(medida);
	    definicao.setMomentoDeMedicao(sprint);
	    definicao.setMomentoDeAnaliseDeMedicao(sprint);

	    PapelRecursoHumano papel = new PapelRecursoHumano();

	    if (primeiroLoop)
	    {
		papel.setNome("Taiga Integrator");

		try
		{
		    if (!manager.isOpen())
			manager = XPersistence.createManager();

		    manager.getTransaction().begin();
		    manager.persist(papel);
		    manager.getTransaction().commit();
		}
		catch (Exception ex)
		{
		    if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();

		    manager = XPersistence.createManager();

		    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		    {

			String queryPapel = String.format("SELECT p FROM PapelRecursoHumano p WHERE p.nome='Taiga Integrator'");
			TypedQuery<PapelRecursoHumano> typedQueryPapel = manager.createQuery(queryPapel, PapelRecursoHumano.class);

			papel = typedQueryPapel.getSingleResult();
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
	    }
	    else
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		String queryPapel = String.format("SELECT p FROM PapelRecursoHumano p WHERE p.nome='Taiga Integrator'");
		TypedQuery<PapelRecursoHumano> typedQueryPapel = manager.createQuery(queryPapel, PapelRecursoHumano.class);

		papel = typedQueryPapel.getSingleResult();

		manager.close();
	    }

	    definicao.setResponsavelPelaAnaliseDeMedicao(papel);
	    definicao.setResponsavelPelaMedicao(papel);

	    ProcedimentoDeMedicao procedimentoMedicao = new ProcedimentoDeMedicao();
	    ProcedimentoDeAnaliseDeMedicao procedimentoAnalise = new ProcedimentoDeAnaliseDeMedicao();

	    if (primeiroLoop)
	    {
		procedimentoMedicao.setNome("Medição automática feita via Taiga Integrator");
		procedimentoMedicao.setDescricao("Medição automática feita via Taiga Integrator.");

		procedimentoAnalise.setNome("Análise de Medição automática feita via Taiga Integrator");
		procedimentoAnalise.setDescricao("Análise de Medição automática feita via Taiga Integrator.");

		try
		{
		    if (!manager.isOpen())
			manager = XPersistence.createManager();

		    manager.getTransaction().begin();
		    manager.persist(procedimentoMedicao);
		    manager.persist(procedimentoAnalise);
		    manager.getTransaction().commit();
		}
		catch (Exception ex)
		{
		    if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();

		    manager = XPersistence.createManager();

		    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		    {

			String queryProcedimentoMedicao = String.format("SELECT p FROM ProcedimentoDeMedicao p WHERE p.nome='Medição automática feita via Taiga Integrator'");
			TypedQuery<ProcedimentoDeMedicao> typedQueryProcedimentoMedicao = manager.createQuery(queryProcedimentoMedicao, ProcedimentoDeMedicao.class);

			String queryProcedimentoAnalise = String.format("SELECT p FROM ProcedimentoDeAnaliseDeMedicao p WHERE p.nome='Análise de Medição automática feita via Taiga Integrator'");
			TypedQuery<ProcedimentoDeAnaliseDeMedicao> typedQueryProcedimentoAnalise = manager.createQuery(queryProcedimentoAnalise, ProcedimentoDeAnaliseDeMedicao.class);

			procedimentoMedicao = typedQueryProcedimentoMedicao.getSingleResult();
			procedimentoAnalise = typedQueryProcedimentoAnalise.getSingleResult();
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
	    }
	    else
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		String queryProcedimentoMedicao = String.format("SELECT p FROM ProcedimentoDeMedicao p WHERE p.nome='Medição automática feita via Taiga Integrator'");
		TypedQuery<ProcedimentoDeMedicao> typedQueryProcedimentoMedicao = manager.createQuery(queryProcedimentoMedicao, ProcedimentoDeMedicao.class);

		String queryProcedimentoAnalise = String.format("SELECT p FROM ProcedimentoDeAnaliseDeMedicao p WHERE p.nome='Análise de Medição automática feita via Taiga Integrator'");
		TypedQuery<ProcedimentoDeAnaliseDeMedicao> typedQueryProcedimentoAnalise = manager.createQuery(queryProcedimentoAnalise, ProcedimentoDeAnaliseDeMedicao.class);

		procedimentoMedicao = typedQueryProcedimentoMedicao.getSingleResult();
		procedimentoAnalise = typedQueryProcedimentoAnalise.getSingleResult();

		manager.close();
	    }

	    definicao.setProcedimentoDeAnaliseDeMedicao(procedimentoAnalise);
	    definicao.setProcedimentoDeMedicao(procedimentoMedicao);

	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(medida);
		manager.persist(definicao);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (manager.getTransaction().isActive())
		    manager.getTransaction().rollback();

		manager = XPersistence.createManager();

		if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		{
		    System.out.println(String.format("A Medida %s já existe.", medida.getNome()));

		    String queryMedida = String.format("SELECT m FROM Medida m WHERE m.nome='%s'", medida.getNome());
		    TypedQuery<Medida> typedQueryMedida = manager.createQuery(queryMedida, Medida.class);

		    medida = typedQueryMedida.getSingleResult();
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

	    medidasCadastradas.add(medida);
	    primeiroLoop = false;
	}

	return medidasCadastradas;
    }

    /**
     * Cria os tipos de artefatos do Scrum.
     * 
     * @throws Exception
     */
    public List<TipoDeArtefato> criarTiposArtefatosScrumMedCEP() throws Exception
    {

	EntityManager manager = XPersistence.createManager();

	//Instancia os tipos de artefatos.
	TipoDeArtefato estoriaPB = new TipoDeArtefato();
	TipoDeArtefato estoriaSB = new TipoDeArtefato();
	TipoDeArtefato codigoFonte = new TipoDeArtefato();
	TipoDeArtefato documentacao = new TipoDeArtefato();
	TipoDeArtefato releaseSoftware = new TipoDeArtefato();

	//Obtem o tipo de Entidade Mensurável Tipo de Artefato.
	String queryTipoArtefato = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Tipo de Artefato'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQueryTipoArtefato = manager.createQuery(queryTipoArtefato, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoDeArtefato = typedQueryTipoArtefato.getSingleResult();

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	estoriaPB.setNome("Estória de Product Backlog");
	estoriaPB.setDescricao("Estória de Product Backlog do Scrum.");
	estoriaPB.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho, duracao)));

	estoriaSB.setNome("Estória de Sprint Backlog");
	estoriaSB.setDescricao("Estória de Sprint Backlog do Scrum.");
	estoriaSB.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho, duracao)));

	codigoFonte.setNome("Código Fonte");
	codigoFonte.setDescricao("Código fonte de um software.");
	codigoFonte.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho)));

	documentacao.setNome("Documentação");
	documentacao.setDescricao("Documentação de software. Inclui modelos, diagramas, relatórios, etc.");
	documentacao.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho)));

	releaseSoftware.setNome("Release de Software");
	releaseSoftware.setDescricao("Incremento de software pronto para implantação.");
	releaseSoftware.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho)));

	List<TipoDeArtefato> tiposDeArtefatoParaPersistir = new ArrayList<TipoDeArtefato>();

	tiposDeArtefatoParaPersistir.add(estoriaPB);
	tiposDeArtefatoParaPersistir.add(estoriaSB);
	tiposDeArtefatoParaPersistir.add(codigoFonte);
	tiposDeArtefatoParaPersistir.add(documentacao);
	tiposDeArtefatoParaPersistir.add(releaseSoftware);

	List<TipoDeArtefato> tiposDeArtefato = new ArrayList<TipoDeArtefato>();

	//Persiste.
	for (TipoDeArtefato tipo : tiposDeArtefatoParaPersistir)
	{

	    tipo.setTipoDeEntidadeMensuravel(tipoDeArtefato);

	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(tipo);
		manager.getTransaction().commit();
		tiposDeArtefato.add(tipo);
	    }

	    catch (Exception ex)
	    {
		if (manager.getTransaction().isActive())
		    manager.getTransaction().rollback();

		manager.close();
		manager = XPersistence.createManager();

		if (ex.getCause() instanceof InvalidStateException)
		{
		    for (InvalidValue invalidValue : ((InvalidStateException) ex.getCause()).getInvalidValues())
		    {
			System.out.println("Instance of bean class: " + invalidValue.getBeanClass().getSimpleName() +
				" has an invalid property: " + invalidValue.getPropertyName() +
				" with message: " + invalidValue.getMessage());
		    }
		}

		if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		{

		    System.out.println(String.format("O Tipo de Artefato %s já existe.", tipo.getNome()));

		    String query = String.format("SELECT t FROM TipoDeArtefato t WHERE t.nome='%s'", tipo.getNome());
		    TypedQuery<TipoDeArtefato> typedQuery = manager.createQuery(query, TipoDeArtefato.class);

		    TipoDeArtefato tipoExistente = typedQuery.getSingleResult();
		    tiposDeArtefato.add(tipoExistente);
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
	}

	return tiposDeArtefato;
    }

    /**
     * Cria as atividades Reunião de Planejamento da Sprint, Sprint e Reunião de Revisão da Sprint.
     * 
     * @return - List<AtividadeParao> com as atividades criadas.
     * @throws Exception
     */
    public void criarAtividadesPadraoScrumMedCEP() throws Exception
    {
	EntityManager manager = XPersistence.createManager();

	//Instancia os tipos de artefatos.
	AtividadePadrao reuniaoPS = new AtividadePadrao();
	AtividadePadrao sprint = new AtividadePadrao();
	AtividadePadrao reuniaoRS = new AtividadePadrao();

	//Cria os artefatos Scrum.
	List<TipoDeArtefato> tiposCriados = this.criarTiposArtefatosScrumMedCEP();

	List<String> nomes = new ArrayList<String>();

	for (TipoDeArtefato tipo : tiposCriados)
	{
	    nomes.add(tipo.getNome());
	}

	String queryTiposArtefatoScrum = "SELECT e FROM TipoDeArtefato e WHERE e.nome IN :nomes ";
	TypedQuery<TipoDeArtefato> typedQueryTAS = manager.createQuery(queryTiposArtefatoScrum, TipoDeArtefato.class).setParameter("nomes", nomes);
	List<TipoDeArtefato> tiposArtefatos = typedQueryTAS.getResultList();

	//Obtem o tipo de Entidade Mensurável AtividadePadrao.
	String queryAtividadePadrao = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Atividade Padrão'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQueryAP = manager.createQuery(queryAtividadePadrao, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel atividadePadrao = typedQueryAP.getSingleResult();

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Desempenho.
	String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
	TypedQuery<ElementoMensuravel> typedQueryDesempenho = manager.createQuery(queryDesempenho, ElementoMensuravel.class);
	ElementoMensuravel desempenho = typedQueryDesempenho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	//Preenche a Reunião de Planejamento da Sprint.
	reuniaoPS.setNome("Reunião de Planejamento da Sprint");
	reuniaoPS.setDescricao("Reunião de Planejamento da Sprint do Scrum.");

	List<TipoDeArtefato> artefatosRequeridosReuniaoPS = new ArrayList<TipoDeArtefato>();
	loop_requeridos: for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Estória de Product Backlog"))
	    {
		artefatosRequeridosReuniaoPS.add(tipo);
		break loop_requeridos;
	    }
	}

	List<TipoDeArtefato> artefatosProduzidosReuniaoPS = new ArrayList<TipoDeArtefato>();
	loop_produzidos: for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Estória de Sprint Backlog"))
	    {
		artefatosProduzidosReuniaoPS.add(tipo);
		break loop_produzidos;
	    }
	}

	reuniaoPS.setRequerTipoDeArtefato(artefatosRequeridosReuniaoPS);
	reuniaoPS.setProduzTipoDeArtefato(artefatosProduzidosReuniaoPS);
	reuniaoPS.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(duracao)));

	//Preenche a Sprint
	sprint.setNome("Sprint");
	sprint.setDescricao("Sprint do Scrum.");

	List<TipoDeArtefato> artefatosRequeridosSprint = new ArrayList<TipoDeArtefato>();
	loop_requeridos: for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Estória de Sprint Backlog"))
	    {
		artefatosRequeridosSprint.add(tipo);
		break loop_requeridos;
	    }
	}

	List<TipoDeArtefato> artefatosProduzidosSprint = new ArrayList<TipoDeArtefato>();
	for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Código Fonte")
		    || tipo.getNome().equalsIgnoreCase("Documentação"))
	    {
		artefatosProduzidosSprint.add(tipo);
	    }
	}

	sprint.setRequerTipoDeArtefato(artefatosRequeridosSprint);
	sprint.setProduzTipoDeArtefato(artefatosProduzidosSprint);
	sprint.setDependeDe(new ArrayList<AtividadePadrao>(Arrays.asList(reuniaoPS)));
	sprint.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho, desempenho, duracao)));

	//Preenche a Reunião de Revisão da Sprint
	reuniaoRS.setNome("Reunião de Revisão da Sprint");
	reuniaoRS.setDescricao("Reunião de Revisão da Sprint do Scrum.");

	List<TipoDeArtefato> artefatosRequeridosReuniaoRS = new ArrayList<TipoDeArtefato>();
	for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Estória de Sprint Backlog")
		    || tipo.getNome().equalsIgnoreCase("Código Fonte")
		    || tipo.getNome().equalsIgnoreCase("Documentação"))
	    {
		artefatosRequeridosReuniaoRS.add(tipo);
	    }
	}

	List<TipoDeArtefato> artefatosProduzidosReuniaoRS = new ArrayList<TipoDeArtefato>();
	loop_produzidos: for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Release de Software"))
	    {
		artefatosProduzidosReuniaoRS.add(tipo);
		break loop_produzidos;
	    }
	}

	reuniaoRS.setRequerTipoDeArtefato(artefatosRequeridosReuniaoRS);
	reuniaoRS.setProduzTipoDeArtefato(artefatosProduzidosReuniaoRS);
	reuniaoRS.setDependeDe(new ArrayList<AtividadePadrao>(Arrays.asList(sprint)));
	reuniaoRS.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(duracao)));

	List<AtividadePadrao> atividadesParaPersistir = new ArrayList<AtividadePadrao>();

	atividadesParaPersistir.add(reuniaoPS);
	atividadesParaPersistir.add(sprint);
	atividadesParaPersistir.add(reuniaoRS);

	//Persiste.	
	try
	{
	    manager.getTransaction().begin();

	    for (AtividadePadrao atividade : atividadesParaPersistir)
	    {
		atividade.setTipoDeEntidadeMensuravel(atividadePadrao);
		manager.persist(atividade);
	    }

	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println("A Atividades Padrão do Scrum já existem.");
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

    }

    /**
     * Cria o processo Scrum na MedCEP.
     * 
     * @return ProcessoPadrao Scrum criado.
     * @throws Exception
     */
    public ProcessoPadrao criarProcessoPadraoScrumMedCEP() throws Exception
    {
	EntityManager manager = XPersistence.createManager();

	this.criarAtividadesPadraoScrumMedCEP();

	ProcessoPadrao scrum = new ProcessoPadrao();

	scrum.setNome("Scrum");
	scrum.setVersao("1.0");
	scrum.setDescricao("A framework to support teams in complex product development. "
		+ "Scrum consists of Scrum Teams and their associated roles, events, artifacts,"
		+ " and rules, as defined in the Scrum GuideTM (https://www.scrum.org/Resources/Scrum-Glossary).");

	//Obtem o tipo de Entidade Mensurável AtividadePadrao.
	String queryProcessoPadrao = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Processo de Software Padrão'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQueryProcessoPadrao = manager.createQuery(queryProcessoPadrao, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel processoPadrao = typedQueryProcessoPadrao.getSingleResult();

	//Obtem as atividades Scrum.
	String queryAtividade1 = "SELECT e FROM AtividadePadrao e WHERE e.nome='Reunião de Planejamento da Sprint'";
	TypedQuery<AtividadePadrao> typedQueryAtividade1 = manager.createQuery(queryAtividade1, AtividadePadrao.class);
	AtividadePadrao atividade1 = typedQueryAtividade1.getSingleResult();

	String queryAtividade2 = "SELECT e FROM AtividadePadrao e WHERE e.nome='Sprint'";
	TypedQuery<AtividadePadrao> typedQueryAtividade2 = manager.createQuery(queryAtividade2, AtividadePadrao.class);
	AtividadePadrao atividade2 = typedQueryAtividade2.getSingleResult();

	String queryAtividade3 = "SELECT e FROM AtividadePadrao e WHERE e.nome='Reunião de Revisão da Sprint'";
	TypedQuery<AtividadePadrao> typedQueryAtividade3 = manager.createQuery(queryAtividade3, AtividadePadrao.class);
	AtividadePadrao atividade3 = typedQueryAtividade3.getSingleResult();

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Desempenho.
	String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
	TypedQuery<ElementoMensuravel> typedQueryDesempenho = manager.createQuery(queryDesempenho, ElementoMensuravel.class);
	ElementoMensuravel desempenho = typedQueryDesempenho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	scrum.setTipoDeEntidadeMensuravel(processoPadrao);
	scrum.setAtividadePadrao(new ArrayList<AtividadePadrao>(Arrays.asList(atividade1, atividade2, atividade3)));
	scrum.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho, desempenho, duracao)));

	//Persiste.	
	try
	{
	    manager.getTransaction().begin();
	    manager.persist(scrum);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if (ex.getCause() instanceof InvalidStateException)
	    {
		for (InvalidValue invalidValue : ((InvalidStateException) ex.getCause()).getInvalidValues())
		{
		    System.out.println("Instance of bean class: " + invalidValue.getBeanClass().getSimpleName() +
			    " has an invalid property: " + invalidValue.getPropertyName() +
			    " with message: " + invalidValue.getMessage());
		}
	    }

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println("O Processo Padrão Scrum já existe.");

		String query = String.format("SELECT p FROM ProcessoPadrao p WHERE p.nome='%s'", scrum.getNome());
		TypedQuery<ProcessoPadrao> typedQuery = manager.createQuery(query, ProcessoPadrao.class);

		scrum = typedQuery.getSingleResult();
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

	return scrum;
    }

    /**
     * Cria as atividades de projeto Reunião de Planejamento da Sprint, Sprints e Reunião de Revisão da Sprint para o projeto infomado.
     * 
     * @param projeto
     *            - Projeto Taiga para criação das atividades de projeto.
     * @throws Exception
     */
    public List<AtividadeProjeto> criarAtividadesProjetoScrumMedCEP(Projeto projeto) throws Exception
    {

	EntityManager manager = XPersistence.createManager();

	//Cria as atividades padrão.
	this.criarAtividadesPadraoScrumMedCEP();

	//Cria as reuniões de planejamento.
	String queryAtividadePadrao = "SELECT e FROM AtividadePadrao e WHERE e.nome='Reunião de Planejamento da Sprint'";
	TypedQuery<AtividadePadrao> typedQueryAP = manager.createQuery(queryAtividadePadrao, AtividadePadrao.class);
	AtividadePadrao reuniaoPS = typedQueryAP.getSingleResult();

	String queryAPSprint = "SELECT e FROM AtividadePadrao e WHERE e.nome='Sprint'";
	TypedQuery<AtividadePadrao> typedQueryAPSprint = manager.createQuery(queryAPSprint, AtividadePadrao.class);
	AtividadePadrao atividadeSprint = typedQueryAPSprint.getSingleResult();

	String queryReuniaoRS = "SELECT e FROM AtividadePadrao e WHERE e.nome='Reunião de Revisão da Sprint'";
	TypedQuery<AtividadePadrao> typedQueryReuniaoRS = manager.createQuery(queryReuniaoRS, AtividadePadrao.class);
	AtividadePadrao reuniaoRS = typedQueryReuniaoRS.getSingleResult();

	String tipoEntidadeAPQuery = String.format("SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Atividade de Projeto'");
	TypedQuery<TipoDeEntidadeMensuravel> tipoEntidadeAPTypedQuery = manager.createQuery(tipoEntidadeAPQuery, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoEntidadeAP = tipoEntidadeAPTypedQuery.getSingleResult();

	String query1 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Artefato'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery1 = manager.createQuery(query1, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoArtefato = typedQuery1.getSingleResult();

	String query2 = "SELECT e FROM TipoDeArtefato e WHERE e.nome='Código Fonte'";
	TypedQuery<TipoDeArtefato> typedQuery2 = manager.createQuery(query2, TipoDeArtefato.class);
	TipoDeArtefato tipoCodigoFonte = typedQuery2.getSingleResult();

	String query3 = "SELECT e FROM TipoDeArtefato e WHERE e.nome='Documentação'";
	TypedQuery<TipoDeArtefato> typedQuery3 = manager.createQuery(query3, TipoDeArtefato.class);
	TipoDeArtefato tipoDocumentacao = typedQuery3.getSingleResult();

	String query4 = "SELECT e FROM TipoDeArtefato e WHERE e.nome='Release de Software'";
	TypedQuery<TipoDeArtefato> typedQuery4 = manager.createQuery(query4, TipoDeArtefato.class);
	TipoDeArtefato tipoRelease = typedQuery4.getSingleResult();

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Desempenho.
	String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
	TypedQuery<ElementoMensuravel> typedQueryDesempenho = manager.createQuery(queryDesempenho, ElementoMensuravel.class);
	ElementoMensuravel desempenho = typedQueryDesempenho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	List<Sprint> sprints = this.obterSprintsDoProjetoTaiga(projeto.getApelido());
	List<AtividadeProjeto> atividadesProjeto = new ArrayList<AtividadeProjeto>();

	for (Sprint sprint : sprints)
	{
	    //Preenche a Reunião de Planejamento da Sprint do projeto.
	    AtividadeProjeto reuniaoPSProjeto = new AtividadeProjeto();
	    reuniaoPSProjeto.setNome(String.format("Reunião de Planejamento da Sprint - %s do Projeto %s", sprint.getNome(), projeto.getNome()));
	    reuniaoPSProjeto.setDescricao(String.format("Reunião de Planejamento da Sprint - %s do Projeto %s.", sprint.getNome(), projeto.getNome()));
	    reuniaoPSProjeto.setBaseadoEm(reuniaoPS);
	    reuniaoPSProjeto.setTipoDeEntidadeMensuravel(tipoEntidadeAP);
	    reuniaoPSProjeto.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(duracao)));

	    //Obtem as estórias do PB.
	    List<Estoria> estoriasPB = this.obterEstoriasDoProjectBacklogTaiga(projeto.getApelido());
	    boolean saoEstoriasDeProductBacklog = true;
	    List<Artefato> artEstoriasPB = this.criarEstoriasComoArtefatosMedCEP(estoriasPB, saoEstoriasDeProductBacklog);

	    reuniaoPSProjeto.setRequer(artEstoriasPB);

	    //Obtem as estórias da SB.
	    List<Estoria> estoriasSB = this.obterEstoriasDaSprintBacklogTaiga(projeto.getApelido(), sprint.getApelido());
	    saoEstoriasDeProductBacklog = false;
	    List<Artefato> artEstoriasSB = this.criarEstoriasComoArtefatosMedCEP(estoriasSB, saoEstoriasDeProductBacklog);

	    reuniaoPSProjeto.setProduz(artEstoriasSB);

	    //Preenche a Sprint
	    AtividadeProjeto sprintProjeto = new AtividadeProjeto();

	    sprintProjeto.setNome(sprint.getNome() + " do Projeto " + projeto.getNome());
	    sprintProjeto.setDescricao(sprint.getNome() + " do Projeto " + projeto.getNome());
	    sprintProjeto.setBaseadoEm(atividadeSprint);
	    sprintProjeto.setTipoDeEntidadeMensuravel(tipoEntidadeAP);
	    sprintProjeto.setDependeDe(new ArrayList<AtividadeProjeto>(Arrays.asList(reuniaoPSProjeto)));
	    sprintProjeto.setRequer(artEstoriasSB);
	    sprintProjeto.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho, desempenho, duracao)));

	    Artefato codigoFonteProjeto = new Artefato();
	    codigoFonteProjeto.setNome("Código fonte do Projeto " + projeto.getNome());
	    codigoFonteProjeto.setDescricao("Código fonte do Projeto " + projeto.getNome() + " criado durante a Sprint - " + sprint.getNome());
	    codigoFonteProjeto.setTipoDeEntidadeMensuravel(tipoArtefato);
	    codigoFonteProjeto.setTipoDeArtefato(tipoCodigoFonte);
	    codigoFonteProjeto.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho)));

	    //Persiste.	
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(codigoFonteProjeto);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (manager.getTransaction().isActive())
		    manager.getTransaction().rollback();

		if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		{
		    String query = String.format("SELECT a FROM Artefato a WHERE a.nome='%s'", codigoFonteProjeto.getNome());
		    TypedQuery<Artefato> typedQuery = manager.createQuery(query, Artefato.class);

		    codigoFonteProjeto = typedQuery.getSingleResult();
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

	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    Artefato documentacaoProjeto = new Artefato();
	    documentacaoProjeto.setNome("Documentação do Projeto " + projeto.getNome());
	    documentacaoProjeto.setDescricao("Documentação do Projeto " + projeto.getNome() + " criada durante a Sprint - " + sprint.getNome());
	    documentacaoProjeto.setTipoDeEntidadeMensuravel(manager.find(TipoDeEntidadeMensuravel.class, tipoArtefato.getId()));
	    documentacaoProjeto.setTipoDeArtefato(manager.find(TipoDeArtefato.class, tipoDocumentacao.getId()));
	    documentacaoProjeto.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho)));

	    //Persiste.	
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(documentacaoProjeto);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (manager.getTransaction().isActive())
		    manager.getTransaction().rollback();

		if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		{
		    String query = String.format("SELECT a FROM Artefato a WHERE a.nome='%s'", documentacaoProjeto.getNome());
		    TypedQuery<Artefato> typedQuery = manager.createQuery(query, Artefato.class);

		    documentacaoProjeto = typedQuery.getSingleResult();
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

	    sprintProjeto.setProduz(new ArrayList<Artefato>(Arrays.asList(codigoFonteProjeto, documentacaoProjeto)));

	    //Preenche a Reunião de Revisão da Sprint do Projeto
	    AtividadeProjeto reuniaoRSProjeto = new AtividadeProjeto();

	    reuniaoRSProjeto.setNome("Reunião de Revisão da Sprint - " + sprint.getNome() + " do Projeto " + projeto.getNome());
	    reuniaoRSProjeto.setDescricao("Reunião de Revisão da Sprint - " + sprint.getNome() + " do Projeto " + projeto.getNome());
	    reuniaoRSProjeto.setBaseadoEm(reuniaoRS);
	    reuniaoRSProjeto.setTipoDeEntidadeMensuravel(tipoEntidadeAP);
	    reuniaoRSProjeto.setDependeDe(new ArrayList<AtividadeProjeto>(Arrays.asList(sprintProjeto)));

	    List<Artefato> artefatosRequeridosReuniaoRS = new ArrayList<Artefato>();
	    artefatosRequeridosReuniaoRS.addAll(artEstoriasSB);
	    artefatosRequeridosReuniaoRS.addAll(new ArrayList<Artefato>(Arrays.asList(codigoFonteProjeto, documentacaoProjeto)));

	    reuniaoRSProjeto.setRequer(artefatosRequeridosReuniaoRS);
	    reuniaoRSProjeto.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(duracao)));

	    Artefato releaseSoftware = new Artefato();
	    releaseSoftware.setNome("Release do Projeto " + projeto.getNome());
	    releaseSoftware.setDescricao("Release do Projeto " + projeto.getNome() + " criado após a Sprint - " + sprint.getNome());
	    releaseSoftware.setTipoDeEntidadeMensuravel(tipoArtefato);
	    releaseSoftware.setTipoDeArtefato(tipoRelease);
	    releaseSoftware.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho)));

	    //Persiste.	
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(releaseSoftware);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (manager.getTransaction().isActive())
		    manager.getTransaction().rollback();

		if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		{
		    String query = String.format("SELECT a FROM Artefato a WHERE a.nome='%s'", releaseSoftware.getNome());
		    TypedQuery<Artefato> typedQuery = manager.createQuery(query, Artefato.class);

		    releaseSoftware = typedQuery.getSingleResult();
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

	    reuniaoRSProjeto.setProduz(new ArrayList<Artefato>(Arrays.asList(releaseSoftware)));

	    List<AtividadeProjeto> atividadesParaPersistir = new ArrayList<AtividadeProjeto>();
	    atividadesParaPersistir.add(reuniaoPSProjeto);
	    atividadesParaPersistir.add(sprintProjeto);
	    atividadesParaPersistir.add(reuniaoRSProjeto);

	    for (AtividadeProjeto atividadeProjeto : atividadesParaPersistir)
	    {
		//Persiste.	
		try
		{
		    if (!manager.isOpen())
			manager = XPersistence.createManager();

		    manager.getTransaction().begin();
		    manager.persist(atividadeProjeto);
		    manager.getTransaction().commit();

		    atividadesProjeto.add(atividadeProjeto);
		}
		catch (Exception ex)
		{
		    if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();

		    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		    {
			System.out.println(String.format("A Atividade de Projeto %s já existe.", atividadeProjeto.getNome()));

			String query = String.format("SELECT a FROM AtividadeProjeto a WHERE a.nome='%s'", atividadeProjeto.getNome());
			TypedQuery<AtividadeProjeto> typedQuery = manager.createQuery(query, AtividadeProjeto.class);

			atividadesProjeto.add(typedQuery.getSingleResult());
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
	    }

	}

	return atividadesProjeto;

    }

    /**
     * Cria um Processo de Projeto baseado em Scrum para o projeto informado.
     * 
     * @param projeto
     *            - Projeto para criar o processo de projeto baseado em Scrum.
     * @throws Exception
     */
    public ProcessoProjeto criarProcessoProjetoScrumMedCEP(Projeto projeto) throws Exception
    {
	EntityManager manager = XPersistence.createManager();
	ProcessoProjeto scrum = new ProcessoProjeto();

	ProcessoPadrao processoScrum = this.criarProcessoPadraoScrumMedCEP();

	scrum.setNome("Processo de Software Scrum do Projeto " + projeto.getNome());
	scrum.setDescricao("Processo de Software Scrum do Projeto" + projeto.getNome());

	//Obtem as atividades de projeto 
	List<AtividadeProjeto> atividades = this.criarAtividadesProjetoScrumMedCEP(projeto);

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Desempenho.
	String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
	TypedQuery<ElementoMensuravel> typedQueryDesempenho = manager.createQuery(queryDesempenho, ElementoMensuravel.class);
	ElementoMensuravel desempenho = typedQueryDesempenho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	String query1 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Processo de Software em Projeto'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery1 = manager.createQuery(query1, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoPP = typedQuery1.getSingleResult();

	String query2 = "SELECT e FROM Projeto e WHERE e.nome='" + projeto.getNome() + "'";
	TypedQuery<org.medcep.model.organizacao_de_software.Projeto> typedQuery2 = manager.createQuery(query2, org.medcep.model.organizacao_de_software.Projeto.class);
	org.medcep.model.organizacao_de_software.Projeto proj = typedQuery2.getSingleResult();

	scrum.setBaseadoEm(processoScrum);
	scrum.setTipoDeEntidadeMensuravel(tipoPP);
	scrum.setAtividadeProjeto(atividades);
	scrum.setProjeto(proj);
	scrum.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho, desempenho, duracao)));

	//Persiste.	
	try
	{
	    manager.getTransaction().begin();
	    manager.persist(scrum);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println(String.format("O Processo de Projeto %s já existe.", scrum.getNome()));

		String query = String.format("SELECT p FROM ProcessoProjeto p WHERE p.nome='%s'", scrum.getNome());
		TypedQuery<ProcessoProjeto> typedQuery = manager.createQuery(query, ProcessoProjeto.class);

		scrum = typedQuery.getSingleResult();
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

	return scrum;
    }

    /**
     * Cria as estórias do Product Backlog como artefatos.
     * 
     * @param estorias
     *            - Estórias do Product Backlog.
     * @return List<Artefato> artefatos criados.
     * @throws Exception
     */
    public List<Artefato> criarEstoriasComoArtefatosMedCEP(List<Estoria> estorias, boolean saoEstoriasDeProductBacklog) throws Exception
    {

	this.criarTiposArtefatosScrumMedCEP();

	EntityManager manager = XPersistence.createManager();

	String query1 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Artefato'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery1 = manager.createQuery(query1, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoArtefato = typedQuery1.getSingleResult();

	TipoDeArtefato tipoEstoria;

	if (saoEstoriasDeProductBacklog)
	{
	    String query2 = "SELECT e FROM TipoDeArtefato e WHERE e.nome='Estória de Product Backlog'";
	    TypedQuery<TipoDeArtefato> typedQuery2 = manager.createQuery(query2, TipoDeArtefato.class);
	    tipoEstoria = typedQuery2.getSingleResult();
	}
	else
	{
	    String query2 = "SELECT e FROM TipoDeArtefato e WHERE e.nome='Estória de Sprint Backlog'";
	    TypedQuery<TipoDeArtefato> typedQuery2 = manager.createQuery(query2, TipoDeArtefato.class);
	    tipoEstoria = typedQuery2.getSingleResult();
	}

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	List<Artefato> artefatosCriados = new ArrayList<Artefato>();

	for (Estoria estoria : estorias)
	{
	    Artefato artefato = new Artefato();
	    artefato.setNome(estoria.getTitulo());
	    artefato.setTipoDeEntidadeMensuravel(tipoArtefato);
	    artefato.setTipoDeArtefato(tipoEstoria);
	    artefato.setElementoMensuravel(new ArrayList<ElementoMensuravel>(Arrays.asList(tamanho, duracao)));

	    if (estoria.getDescricao().isEmpty() || estoria.getDescricao().equals(""))
		artefato.setDescricao(estoria.getTitulo());
	    else
		artefato.setDescricao(estoria.getDescricao());

	    //Persiste.	
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(artefato);
		manager.getTransaction().commit();
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
		    System.out.println(String.format("O Artefato %s já existe.", artefato.getNome()));

		    String query = String.format("SELECT a FROM Artefato a WHERE a.nome='%s'", artefato.getNome());
		    TypedQuery<Artefato> typedQuery = manager.createQuery(query, Artefato.class);

		    artefato = typedQuery.getSingleResult();
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

	    artefatosCriados.add(artefato);
	}

	return artefatosCriados;
    }

    /**
     * Cria o plano de medição da organização com as medidas informadas.
     * 
     * @param medidasTaiga
     *            - Medidas a serem incluídas no Plano de Medição da Organização.
     * @return Plano criado.
     * @throws Exception
     */
    public PlanoDeMedicaoDaOrganizacao criarPlanoMedicaoOrganizacaoMedCEP(List<MedidasTaiga> medidasTaiga) throws Exception
    {
	PlanoDeMedicaoDaOrganizacao plano = new PlanoDeMedicaoDaOrganizacao();
	EntityManager manager = XPersistence.createManager();

	//Verifica se o plano está criado.
	try
	{
	    String queryPO = "SELECT p FROM PlanoDeMedicaoDaOrganizacao p WHERE p.nome='Plano de Medição da Organização (Wizard)'";
	    TypedQuery<PlanoDeMedicaoDaOrganizacao> typedQueryPO = manager.createQuery(queryPO, PlanoDeMedicaoDaOrganizacao.class);
	    plano = typedQueryPO.getSingleResult();
	    return plano;
	}
	catch (Exception ex)
	{
	    this.criarMedidasMedCEP(medidasTaiga);
	}

	Calendar cal = Calendar.getInstance();
	plano.setData(cal.getTime());
	plano.setNome("Plano de Medição da Organização (Wizard)");
	plano.setVersao("1");
	plano.setDescricao("Plano de Medição da Organização criado via Wizard");

	RecursoHumano wizard = new RecursoHumano();
	wizard.setNome("Wizard MedCEP");

	//Persiste o Wizard como RH.	
	try
	{
	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    manager.getTransaction().begin();
	    manager.persist(wizard);
	    manager.getTransaction().commit();
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

		String query = String.format("SELECT p FROM RecursoHumano p WHERE p.nome='%s'", wizard.getNome());
		TypedQuery<RecursoHumano> typedQuery = manager.createQuery(query, RecursoHumano.class);

		wizard = typedQuery.getSingleResult();
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

	plano.setRecursoHumano(new ArrayList<RecursoHumano>(Arrays.asList(wizard)));

	ObjetivoEstrategico objEstrategico = new ObjetivoEstrategico();
	ObjetivoDeMedicao objMedicao = new ObjetivoDeMedicao();

	objEstrategico.setNome("Aumentar a qualidade dos projetos de software da organização");

	objMedicao.setNome("Monitorar os projetos de software ágeis");
	objMedicao.setObjetivoEstrategico(new ArrayList<ObjetivoEstrategico>(Arrays.asList(objEstrategico)));

	try
	{
	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    manager.getTransaction().begin();
	    manager.persist(objEstrategico);
	    manager.persist(objMedicao);
	    manager.getTransaction().commit();

	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    String query = "SELECT p FROM ObjetivoEstrategico p WHERE p.nome='Aumentar a qualidade dos projetos de software da organização'";
	    TypedQuery<ObjetivoEstrategico> typedQuery = manager.createQuery(query, ObjetivoEstrategico.class);
	    objEstrategico = typedQuery.getSingleResult();

	    String query2 = "SELECT p FROM ObjetivoDeMedicao p WHERE p.nome='Monitorar os projetos de software ágeis'";
	    TypedQuery<ObjetivoDeMedicao> typedQuery2 = manager.createQuery(query2, ObjetivoDeMedicao.class);
	    objMedicao = typedQuery2.getSingleResult();

	}

	TreeItemPlanoMedicao objEstrategicoTree = new TreeItemPlanoMedicao();
	objEstrategicoTree.setNome("OE - " + objEstrategico.getNome());
	objEstrategicoTree.setItem(objEstrategico);

	TreeItemPlanoMedicao objMedicaoTree = new TreeItemPlanoMedicao();
	objMedicaoTree.setNome("OM - " + objMedicao.getNome());
	objMedicaoTree.setItem(objMedicao);

	//Persiste o Objetivo Estratégico e Objetivo de Medição (tree e tree base também).
	manager.getTransaction().begin();
	manager.persist(objEstrategicoTree);
	Integer idObjEstrategicoTree = objEstrategicoTree.getId();
	objMedicaoTree.setPath("/" + idObjEstrategicoTree);
	manager.persist(objMedicaoTree);
	Integer idObjMedicaoTree = objMedicaoTree.getId();
	manager.getTransaction().commit();

	//Cria uma Necessidade de Informação (tree e tree base) e Medida (tree e tree base também) para cada medida.
	List<TreeItemPlanoMedicao> necessidadesTree = new ArrayList<TreeItemPlanoMedicao>();
	List<TreeItemPlanoMedicao> medidasTree = new ArrayList<TreeItemPlanoMedicao>();
	List<MedidaPlanoDeMedicao> medidasPlano = new ArrayList<MedidaPlanoDeMedicao>();

	for (MedidasTaiga medida : medidasTaiga)
	{
	    NecessidadeDeInformacao necessidade = new NecessidadeDeInformacao();
	    necessidade.setNome("Qual o valor de " + medida.toString() + "?");

	    List<Objetivo> objetivos = new ArrayList<Objetivo>();
	    objetivos.add(objMedicao);

	    necessidade.setIndicadoPelosObjetivos(objetivos);

	    //Persiste.	
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(necessidade);
		manager.getTransaction().commit();

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
		    String query = String.format("SELECT p FROM NecessidadeDeInformacao p WHERE p.nome='%s'", necessidade.getNome());
		    TypedQuery<NecessidadeDeInformacao> typedQuery = manager.createQuery(query, NecessidadeDeInformacao.class);
		    necessidade = typedQuery.getSingleResult();
		}
		else
		{
		    throw ex;
		}
	    }

	    TreeItemPlanoMedicao necessidadeTree = new TreeItemPlanoMedicao();
	    necessidadeTree.setNome("NI - " + necessidade.getNome());
	    necessidadeTree.setPath("/" + idObjEstrategicoTree + "/" + idObjMedicaoTree);
	    necessidadeTree.setItem(necessidade);

	    //Persiste.	
	    manager.getTransaction().begin();
	    manager.persist(necessidadeTree);
	    Integer idNecessidadeTree = necessidadeTree.getId();
	    manager.getTransaction().commit();

	    necessidadesTree.add(necessidadeTree);

	    String queryMedida = String.format("SELECT p FROM Medida p WHERE p.nome='%s'", medida.toString());
	    TypedQuery<Medida> typedQueryMedida = manager.createQuery(queryMedida, Medida.class);
	    Medida med = typedQueryMedida.getSingleResult();

	    String queryDefMedida = "SELECT p FROM DefinicaoOperacionalDeMedida p WHERE p.nome='Definição operacional padrão Taiga-MedCEP para " + medida.toString() + "'";
	    TypedQuery<DefinicaoOperacionalDeMedida> typedQueryDefMedida = manager.createQuery(queryDefMedida, DefinicaoOperacionalDeMedida.class);
	    DefinicaoOperacionalDeMedida defMed = typedQueryDefMedida.getSingleResult();

	    //Obtem as Medidas como MedidaPlanoDeMedicao
	    MedidaPlanoDeMedicao medidaPlano = new MedidaPlanoDeMedicao();
	    medidaPlano.setMedida(med);
	    medidaPlano.setDefinicaoOperacionalDeMedida(defMed);

	    //Persiste a Medida Plano.	
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(medidaPlano);
		manager.getTransaction().commit();

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
		    String query = String.format("SELECT mp FROM MedidaPlanoDeMedicao mp WHERE mp.medida.nome='%s' AND mp.definicaoOperacionalDeMedida.nome='%s'",
			    medidaPlano.getMedida().getNome(), medidaPlano.getDefinicaoOperacionalDeMedida().getNome());
		    TypedQuery<MedidaPlanoDeMedicao> typedQuery = manager.createQuery(query, MedidaPlanoDeMedicao.class);
		    medidaPlano = typedQuery.getSingleResult();
		}
		else
		{
		    throw ex;
		}
	    }

	    TreeItemPlanoMedicao medidaTree = new TreeItemPlanoMedicao();
	    medidaTree.setNome("ME - " + med.getNome());
	    medidaTree.setPath("/" + objEstrategicoTree.getId() + "/" + objMedicaoTree.getId() + "/" + idNecessidadeTree);
	    medidaTree.setItem(med);

	    manager.getTransaction().begin();
	    manager.persist(medidaTree);
	    manager.getTransaction().commit();

	    medidasTree.add(medidaTree);
	    medidasPlano.add(medidaPlano);
	}

	//Adiciona os Tree Itens no plano.
	List<TreeItemPlanoMedicao> itensPlanoMedicao = new ArrayList<TreeItemPlanoMedicao>();
	itensPlanoMedicao.add(objEstrategicoTree);
	itensPlanoMedicao.add(objMedicaoTree);
	itensPlanoMedicao.addAll(necessidadesTree);
	itensPlanoMedicao.addAll(medidasTree);

	for (TreeItemPlanoMedicao treeItemPlanoMedicao : itensPlanoMedicao)
	{
	    treeItemPlanoMedicao.setPlanoDeMedicaoContainer(plano);
	}

	for (MedidaPlanoDeMedicao medida : medidasPlano)
	{
	    medida.setPlanoDeMedicao(plano);
	}

	plano.setPlanoTree(itensPlanoMedicao);
	plano.setMedidaPlanoDeMedicao(medidasPlano);

	//Finalmente... Persiste o plano.
	try
	{
	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    manager.getTransaction().begin();

	    manager.persist(plano);

	    for (TreeItemPlanoMedicao treeItemPlanoMedicao : itensPlanoMedicao)
	    {
		String query = String.format("SELECT p FROM TreeItemPlanoMedicao p WHERE p.id='%d'", treeItemPlanoMedicao.getId());
		TypedQuery<TreeItemPlanoMedicao> typedQuery = manager.createQuery(query, TreeItemPlanoMedicao.class);

		treeItemPlanoMedicao = typedQuery.getSingleResult();
		treeItemPlanoMedicao.setPlanoDeMedicaoContainer(plano);

		manager.persist(treeItemPlanoMedicao);
	    }

	    for (MedidaPlanoDeMedicao medidaPlano : medidasPlano)
	    {
		String query = String.format("SELECT mp FROM MedidaPlanoDeMedicao mp WHERE mp.id='%d'", medidaPlano.getId());
		TypedQuery<MedidaPlanoDeMedicao> typedQuery = manager.createQuery(query, MedidaPlanoDeMedicao.class);

		medidaPlano = typedQuery.getSingleResult();
		medidaPlano.setPlanoDeMedicao(plano);

		manager.persist(medidaPlano);
	    }

	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    throw ex;

	}
	finally
	{
	    manager.close();
	}

	return plano;
    }

    /**
     * Cria o plano de medição do projeto com as medidas informadas.
     * 
     * @param medidasTaiga
     *            - Medidas a serem incluídas no Plano de Medição do Projeto.
     * @param projeto
     *            - Projeto a ser incluído no Plano de Medição do Projeto.
     * @throws Exception
     */
    public synchronized PlanoDeMedicaoDoProjeto criarPlanoMedicaoProjetoMedCEP(List<MedidasTaiga> medidasTaiga, Periodicidade periodicidadeMedicao, Projeto projeto) throws Exception
    {
	PlanoDeMedicaoDoProjeto plano = new PlanoDeMedicaoDoProjeto();
	EntityManager manager = XPersistence.createManager();

	PlanoDeMedicaoDaOrganizacao planoOrganizacao = new PlanoDeMedicaoDaOrganizacao();
	//Verifica se o plano da organização está criado.
	try
	{
	    String queryPO = "SELECT p FROM PlanoDeMedicaoDaOrganizacao p WHERE p.nome='Plano de Medição da Organização (Wizard)'";
	    TypedQuery<PlanoDeMedicaoDaOrganizacao> typedQueryPO = manager.createQuery(queryPO, PlanoDeMedicaoDaOrganizacao.class);
	    planoOrganizacao = typedQueryPO.getSingleResult();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    //Se não existir, cria o plano da organização com todas as medidas.
	    MedidasTaiga[] todasMedidas = MedidasTaiga.PONTOS_ALOCADOS_PROJETO.getDeclaringClass().getEnumConstants();
	    planoOrganizacao = this.criarPlanoMedicaoOrganizacaoMedCEP(new ArrayList<MedidasTaiga>(new ArrayList<MedidasTaiga>(Arrays.asList(todasMedidas))));
	}

	org.medcep.model.organizacao_de_software.Projeto proj = new org.medcep.model.organizacao_de_software.Projeto();
	//Verifica se o projeto está criado.
	try
	{
	    String queryProjeto = String.format("SELECT p FROM Projeto p WHERE p.nome='%s'", projeto.getNome());
	    TypedQuery<org.medcep.model.organizacao_de_software.Projeto> typedQueryProjeto = manager.createQuery(queryProjeto, org.medcep.model.organizacao_de_software.Projeto.class);
	    proj = typedQueryProjeto.getSingleResult();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    proj = this.criarProjetoMedCEP(projeto);
	}

	this.criarProcessoProjetoScrumMedCEP(projeto);

	Calendar cal = Calendar.getInstance();
	plano.setData(cal.getTime());

	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());

	plano.setNome("Plano de Medição do Projeto " + projeto.getNome() + " (Wizard) - " + dataHora);
	plano.setVersao("1");
	plano.setDescricao("Plano de Medição da Organização criado via Wizard em " + dataHora);
	plano.setPlanoDeMedicaoDaOrganizacao(planoOrganizacao);
	plano.setProjeto(proj);

	RecursoHumano wizard = new RecursoHumano();
	wizard.setNome("Wizard MedCEP");

	//Persiste o Wizard como RH.	
	try
	{
	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    manager.getTransaction().begin();
	    manager.persist(wizard);
	    manager.getTransaction().commit();
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
		String query = String.format("SELECT p FROM RecursoHumano p WHERE p.nome='%s'", wizard.getNome());
		TypedQuery<RecursoHumano> typedQuery = manager.createQuery(query, RecursoHumano.class);

		wizard = typedQuery.getSingleResult();
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

	plano.setRecursoHumano(new ArrayList<RecursoHumano>(Arrays.asList(wizard)));

	ObjetivoEstrategico objEstrategico = new ObjetivoEstrategico();
	ObjetivoDeMedicao objMedicao = new ObjetivoDeMedicao();

	objEstrategico.setNome("Aumentar a qualidade dos projetos de software da organização");

	objMedicao.setNome("Monitorar os projetos de software ágeis");
	objMedicao.setObjetivoEstrategico(new ArrayList<ObjetivoEstrategico>(Arrays.asList(objEstrategico)));

	try
	{
	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    manager.getTransaction().begin();
	    manager.persist(objEstrategico);
	    manager.persist(objMedicao);
	    manager.getTransaction().commit();

	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    String query = "SELECT p FROM ObjetivoEstrategico p WHERE p.nome='Aumentar a qualidade dos projetos de software da organização'";
	    TypedQuery<ObjetivoEstrategico> typedQuery = manager.createQuery(query, ObjetivoEstrategico.class);
	    objEstrategico = typedQuery.getSingleResult();

	    String query2 = "SELECT p FROM ObjetivoDeMedicao p WHERE p.nome='Monitorar os projetos de software ágeis'";
	    TypedQuery<ObjetivoDeMedicao> typedQuery2 = manager.createQuery(query2, ObjetivoDeMedicao.class);
	    objMedicao = typedQuery2.getSingleResult();

	}

	TreeItemPlanoMedicao objEstrategicoTree = new TreeItemPlanoMedicao();
	objEstrategicoTree.setNome("OE - " + objEstrategico.getNome());
	objEstrategicoTree.setItem(objEstrategico);

	TreeItemPlanoMedicao objMedicaoTree = new TreeItemPlanoMedicao();
	objMedicaoTree.setNome("OM - " + objMedicao.getNome());
	objMedicaoTree.setItem(objMedicao);

	//Persiste o Objetivo Estratégico e Objetivo de Medição (tree e tree base também).
	manager.getTransaction().begin();
	manager.persist(objEstrategicoTree);
	Integer idObjEstrategicoTree = objEstrategicoTree.getId();
	objMedicaoTree.setPath("/" + idObjEstrategicoTree);
	manager.persist(objMedicaoTree);
	Integer idObjMedicaoTree = objMedicaoTree.getId();
	manager.getTransaction().commit();

	//Cria uma Necessidade de Informação (tree e tree base) e Medida (tree e tree base também) para cada medida.
	List<TreeItemPlanoMedicao> necessidadesTree = new ArrayList<TreeItemPlanoMedicao>();
	List<TreeItemPlanoMedicao> medidasTree = new ArrayList<TreeItemPlanoMedicao>();
	List<MedidaPlanoDeMedicao> medidasPlano = new ArrayList<MedidaPlanoDeMedicao>();

	for (MedidasTaiga medida : medidasTaiga)
	{
	    NecessidadeDeInformacao necessidade = new NecessidadeDeInformacao();
	    necessidade.setNome("Qual o valor de " + medida.toString() + "?");

	    List<Objetivo> objetivos = new ArrayList<Objetivo>();
	    objetivos.add(objMedicao);

	    necessidade.setIndicadoPelosObjetivos(objetivos);

	    //Persiste.	
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(necessidade);
		manager.getTransaction().commit();

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
		    String query = String.format("SELECT p FROM NecessidadeDeInformacao p WHERE p.nome='%s'", necessidade.getNome());
		    TypedQuery<NecessidadeDeInformacao> typedQuery = manager.createQuery(query, NecessidadeDeInformacao.class);
		    necessidade = typedQuery.getSingleResult();
		}
		else
		{
		    throw ex;
		}
	    }

	    TreeItemPlanoMedicao necessidadeTree = new TreeItemPlanoMedicao();
	    necessidadeTree.setNome("NI - " + necessidade.getNome());
	    necessidadeTree.setPath("/" + idObjEstrategicoTree + "/" + idObjMedicaoTree);
	    necessidadeTree.setItem(necessidade);

	    //Persiste.	
	    manager.getTransaction().begin();
	    manager.persist(necessidadeTree);
	    Integer idNecessidadeTree = necessidadeTree.getId();
	    manager.getTransaction().commit();

	    necessidadesTree.add(necessidadeTree);

	    String queryMedida = String.format("SELECT p FROM Medida p WHERE p.nome='%s'", medida.toString());
	    TypedQuery<Medida> typedQueryMedida = manager.createQuery(queryMedida, Medida.class);
	    Medida med = typedQueryMedida.getSingleResult();

	    String queryDefMedida = "SELECT p FROM DefinicaoOperacionalDeMedida p WHERE p.nome='Definição operacional padrão Taiga-MedCEP para " + medida.toString() + "'";
	    TypedQuery<DefinicaoOperacionalDeMedida> typedQueryDefMedida = manager.createQuery(queryDefMedida, DefinicaoOperacionalDeMedida.class);
	    DefinicaoOperacionalDeMedida defMed = typedQueryDefMedida.getSingleResult();
	    defMed.setPeriodicidadeDeMedicao(periodicidadeMedicao);

	    //Obtem as Medidas como MedidaPlanoDeMedicao
	    MedidaPlanoDeMedicao medidaPlano = new MedidaPlanoDeMedicao();
	    medidaPlano.setMedida(med);
	    medidaPlano.setDefinicaoOperacionalDeMedida(defMed);

	    //Persiste a Medida Plano.	
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(defMed);
		manager.persist(medidaPlano);
		manager.getTransaction().commit();

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
		    String query = String.format("SELECT mp FROM MedidaPlanoDeMedicao mp WHERE mp.medida.nome='%s' AND mp.definicaoOperacionalDeMedida.nome='%s'",
			    medidaPlano.getMedida().getNome(), medidaPlano.getDefinicaoOperacionalDeMedida().getNome());
		    TypedQuery<MedidaPlanoDeMedicao> typedQuery = manager.createQuery(query, MedidaPlanoDeMedicao.class);
		    medidaPlano = typedQuery.getSingleResult();
		}
		else
		{
		    throw ex;
		}
	    }

	    TreeItemPlanoMedicao medidaTree = new TreeItemPlanoMedicao();
	    medidaTree.setNome("ME - " + med.getNome());
	    medidaTree.setPath("/" + objEstrategicoTree.getId() + "/" + objMedicaoTree.getId() + "/" + idNecessidadeTree);
	    medidaTree.setItem(med);

	    manager.getTransaction().begin();
	    manager.persist(medidaTree);
	    manager.getTransaction().commit();

	    medidasTree.add(medidaTree);
	    medidasPlano.add(medidaPlano);
	}

	//Adiciona os Tree Itens no plano.
	List<TreeItemPlanoMedicao> itensPlanoMedicao = new ArrayList<TreeItemPlanoMedicao>();
	itensPlanoMedicao.add(objEstrategicoTree);
	itensPlanoMedicao.add(objMedicaoTree);
	itensPlanoMedicao.addAll(necessidadesTree);
	itensPlanoMedicao.addAll(medidasTree);

	for (TreeItemPlanoMedicao treeItemPlanoMedicao : itensPlanoMedicao)
	{
	    treeItemPlanoMedicao.setPlanoDeMedicaoContainer(plano);
	}

	for (MedidaPlanoDeMedicao medida : medidasPlano)
	{
	    medida.setPlanoDeMedicao(plano);
	}

	plano.setPlanoTree(itensPlanoMedicao);
	plano.setMedidaPlanoDeMedicao(medidasPlano);

	//Finalmente... Persiste o plano.
	try
	{
	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    manager.getTransaction().begin();

	    manager.persist(plano);

	    for (TreeItemPlanoMedicao treeItemPlanoMedicao : itensPlanoMedicao)
	    {
		String query = String.format("SELECT p FROM TreeItemPlanoMedicao p WHERE p.id='%d'", treeItemPlanoMedicao.getId());
		TypedQuery<TreeItemPlanoMedicao> typedQuery = manager.createQuery(query, TreeItemPlanoMedicao.class);

		treeItemPlanoMedicao = typedQuery.getSingleResult();
		treeItemPlanoMedicao.setPlanoDeMedicaoContainer(plano);

		manager.persist(treeItemPlanoMedicao);
	    }

	    for (MedidaPlanoDeMedicao medidaPlano : medidasPlano)
	    {
		String query = String.format("SELECT mp FROM MedidaPlanoDeMedicao mp WHERE mp.id='%d'", medidaPlano.getId());
		TypedQuery<MedidaPlanoDeMedicao> typedQuery = manager.createQuery(query, MedidaPlanoDeMedicao.class);

		medidaPlano = typedQuery.getSingleResult();
		medidaPlano.setPlanoDeMedicao(plano);

		manager.persist(medidaPlano);
	    }

	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    throw ex;

	}
	finally
	{
	    manager.close();
	}

	//Após criar o plano, agenda as medições.
	this.agendarMedicoesPlanoMedicaoProjeto(plano, projeto);

	return plano;
    }

    /**
     * Cria uma medição.
     * 
     * @throws Exception
     */
    public synchronized void criarMedicaoMedCEP(PlanoDeMedicaoDoProjeto plano, Timestamp data, String nomeMedida, String entidadeMedida,
	    String valorMedido, String momento) throws Exception
    {
	EntityManager manager = XPersistence.createManager();
	Medicao medicao = new Medicao();

	medicao.setData(data);
	medicao.setPlanoDeMedicao(plano);

	for (MedidaPlanoDeMedicao med : plano.getMedidaPlanoDeMedicao())
	{
	    if (med.getMedida().getNome().equalsIgnoreCase(nomeMedida))
	    {
		medicao.setMedidaPlanoDeMedicao(med);
	    }
	}

	String queryEntidade = String.format("SELECT p FROM EntidadeMensuravel p WHERE p.nome='%s'", entidadeMedida);
	TypedQuery<EntidadeMensuravel> typedQueryEntidade = manager.createQuery(queryEntidade, EntidadeMensuravel.class);
	medicao.setEntidadeMensuravel(typedQueryEntidade.getSingleResult());

//	String queryMomento = String.format("SELECT p FROM EntidadeMensuravel p WHERE p.nome='%s'", momento);
//	TypedQuery<EntidadeMensuravel> typedQueryMomento = manager.createQuery(queryMomento, EntidadeMensuravel.class);
//	medicao.setMomentoRealDaMedicao(typedQueryMomento.getSingleResult());

	ValorNumerico valor = new ValorNumerico();
	valor.setValorNumerico(Float.parseFloat(valorMedido));
	valor.setValorMedido(valorMedido);

	medicao.setValorMedido(valor);

	RecursoHumano medicaoJob = new RecursoHumano();
	medicaoJob.setNome("Medição Job");

	//Persiste o job como RH.	
	try
	{
	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    String query = String.format("SELECT p FROM RecursoHumano p WHERE p.nome='%s'", medicaoJob.getNome());
	    TypedQuery<RecursoHumano> typedQuery = manager.createQuery(query, RecursoHumano.class);

	    medicaoJob = typedQuery.getSingleResult();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    manager.getTransaction().begin();
	    manager.persist(medicaoJob);
	    manager.getTransaction().commit();
	}
	finally
	{
	    manager.close();
	}

	medicao.setExecutorDaMedicao(medicaoJob);

	ContextoDeMedicao contexto = new ContextoDeMedicao();
	contexto.setDescricao("Medição automática feita pelo Job de Medição.");

	//Persiste o contexto.	
	try
	{
	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    String query = String.format("SELECT p FROM ContextoDeMedicao p WHERE p.descricao='%s'", contexto.getDescricao());
	    TypedQuery<ContextoDeMedicao> typedQuery = manager.createQuery(query, ContextoDeMedicao.class);

	    contexto = typedQuery.getSingleResult();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    manager.getTransaction().begin();
	    manager.persist(contexto);
	    manager.getTransaction().commit();
	}
	finally
	{
	    manager.close();
	}

	medicao.setContextoDeMedicao(contexto);

	if (!manager.isOpen())
	    manager = XPersistence.createManager();

	manager.getTransaction().begin();
	manager.persist(medicao);
	manager.getTransaction().commit();

    }

    /**
     * Agenda as medições de acordo com as medidas e definições operacionais de medida do plano.
     * 
     * @param plano
     * @throws Exception
     */
    public synchronized void agendarMedicoesPlanoMedicaoProjeto(PlanoDeMedicaoDoProjeto plano, Projeto projeto) throws Exception
    {
	/**
	 * Inicia os agendamentos.
	 */
	SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
	Scheduler sched = schedFact.getScheduler();

	if (!sched.isStarted())
	    sched.start();

	//Cria um agendamento de medição para cada medida e entidade medida. 
	for (MedidaPlanoDeMedicao medida : plano.getMedidaPlanoDeMedicao())
	{
	    JobDetail job = null;
	    Trigger trigger = null;
	    String nomeJob = "Job do plano " + plano.getNome();

	    //Converte a periodicidade em horas.
	    String period = medida.getDefinicaoOperacionalDeMedida().getPeriodicidadeDeMedicao().getNome();
	    int horas = 0;

	    if (period.equalsIgnoreCase("Por Hora"))
	    {
		horas = 1;
	    }
	    else if (period.equalsIgnoreCase("Diária"))
	    {
		horas = 24;
	    }
	    else if (period.equalsIgnoreCase("Semanal"))
	    {
		horas = 24 * 7;
	    }
	    else if (period.equalsIgnoreCase("Quinzenal"))
	    {
		horas = 24 * 15;
	    }
	    else if (period.equalsIgnoreCase("Mensal"))
	    {
		horas = 24 * 30; //TODO: mes de 30 dias apenas...
	    }
	    else if (period.equalsIgnoreCase("Trimestral"))
	    {
		horas = 24 * 30 * 3; //TODO: mes de 30 dias apenas...
	    }
	    else if (period.equalsIgnoreCase("Semestral"))
	    {
		horas = 24 * 30 * 6; //TODO: mes de 30 dias apenas...
	    }
	    else if (period.equalsIgnoreCase("Anual"))
	    {
		horas = 24 * 30 * 12; //TODO: mes de 30 dias apenas...
	    }
	    else
	    {
		String mensagem = String.format("Periodicidade %s inexistente no Taiga.", period);
		System.out.println(mensagem);
		throw new Exception(mensagem);
	    }

	    JobDataMap map = new JobDataMap();

	    map.put("urlTaiga", this.urlTaiga.replace("/api/v1/", ""));
	    map.put("usuarioTaiga", authInfo.getUsername());
	    map.put("senhaTaiga", authInfo.getPassword());
	    map.put("apelidoProjeto", projeto.getApelido());
	    map.put("nomePlano", plano.getNome());
	    map.put("nomeMedida", medida.getMedida().getNome());

	    //Espera um segundo para cadastrar cada job, para evitar erros.
	    Thread.sleep(1000);

	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());

	    if (medida.getMedida().getNome().contains("Projeto"))
	    {

		String nomeGrupo = projeto.getNome();
		String nomeTrigger = String.format("Medição %s da medida %s (%s) - criado em %s",
			medida.getDefinicaoOperacionalDeMedida().getPeriodicidadeDeMedicao().getNome(),
			medida.getMedida().getNome(),
			medida.getMedida().getMnemonico(),
			dataHora);

		map.put("entidadeMedida", plano.getProjeto().getNome());
		map.put("momento", plano.getProjeto().getNome());

		boolean existeJob = sched.checkExists(new JobKey(nomeJob, nomeGrupo));

		//Cria um job para cada medida de um projeto.
		if (!existeJob)
		{
		    job = JobBuilder.newJob(MedicaoJob.class)
			    .withIdentity(nomeJob, nomeGrupo)
			    .build();

		    trigger = TriggerBuilder.newTrigger().forJob(job)
			    .withIdentity(nomeTrigger, nomeGrupo)
			    .usingJobData(map)
			    .startNow()
			    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
				    .withIntervalInHours(horas)
				    .repeatForever())
			    .build();

		    sched.scheduleJob(job, trigger);
		}
		else
		{
		    job = sched.getJobDetail(new JobKey(nomeJob, nomeGrupo));

		    trigger = TriggerBuilder.newTrigger().forJob(job)
			    .withIdentity(nomeTrigger, nomeGrupo)
			    .usingJobData(map)
			    .startNow()
			    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
				    .withIntervalInHours(horas)
				    .repeatForever())
			    .build();

		    sched.scheduleJob(trigger);
		}

	    }
	    else if (medida.getMedida().getNome().contains("Sprint"))
	    {

		//Cria um agendamento para cada medida de cada sprint.
		List<Sprint> sprints = this.obterSprintsDoProjetoTaiga(projeto.getApelido());

		for (Sprint sprint : sprints)
		{
		    //Espera um segundo para cadastrar cada job, para evitar erros.
		    Thread.sleep(1000);

		    String nomeGrupo = sprint.getNome();
		    String nomeTrigger = String.format("Medição %s da medida %s (%s) - criado em %s",
			    medida.getDefinicaoOperacionalDeMedida().getPeriodicidadeDeMedicao().getNome(),
			    medida.getMedida().getNome(),
			    medida.getMedida().getMnemonico(),
			    dataHora);

		    map.put("apelidoSprint", sprint.getApelido());
		    map.put("entidadeMedida", sprint.getNome() + " do Projeto " + projeto.getNome());
		    map.put("momento", sprint.getNome() + " do Projeto " + projeto.getNome());

		    boolean existeJob = sched.checkExists(new JobKey(nomeJob, nomeGrupo));

		    if (!existeJob)
		    {
			job = JobBuilder.newJob(MedicaoJob.class)
				.withIdentity(nomeJob, nomeGrupo)
				.build();

			trigger = TriggerBuilder.newTrigger().forJob(job)
				.withIdentity(nomeTrigger, nomeGrupo)
				.usingJobData(map)
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInHours(horas)
					.repeatForever())
				.build();

			sched.scheduleJob(job, trigger);
		    }
		    else
		    {
			job = sched.getJobDetail(new JobKey(nomeJob, nomeGrupo));

			trigger = TriggerBuilder.newTrigger().forJob(job)
				.withIdentity(nomeTrigger, nomeGrupo)
				.usingJobData(map)
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInHours(horas)
					.repeatForever())
				.build();

			sched.scheduleJob(trigger);
		    }
		}

	    }
	    else if (medida.getMedida().getNome().contains("Estória"))
	    {
		//Cria um agendamento para cada medida de cada estoria de cada sprint.
		List<Sprint> sprints = this.obterSprintsDoProjetoTaiga(projeto.getApelido());

		for (Sprint sprint : sprints)
		{
		    map.put("apelidoSprint", sprint.getApelido());

		    List<Estoria> estorias = this.obterEstoriasDaSprintBacklogTaiga(projeto.getApelido(), sprint.getApelido());

		    for (Estoria estoria : estorias)
		    {
			//Espera um segundo para cadastrar cada job, para evitar erros.
			Thread.sleep(1000);

			String nomeGrupo = String.format("Estória (%s)", estoria.getTitulo());
			String nomeTrigger = String.format("Medição %s da medida %s (%s) - criado em %s",
				medida.getDefinicaoOperacionalDeMedida().getPeriodicidadeDeMedicao().getNome(),
				medida.getMedida().getNome(),
				medida.getMedida().getMnemonico(),
				dataHora);

			map.put("entidadeMedida", estoria.getTitulo());
			map.put("momento", estoria.getTitulo());

			boolean existeJob = sched.checkExists(new JobKey(nomeJob, nomeGrupo));

			if (!existeJob)
			{
			    job = JobBuilder.newJob(MedicaoJob.class)
				    .withIdentity(nomeJob, nomeGrupo)
				    .build();

			    trigger = TriggerBuilder.newTrigger().forJob(job)
				    .withIdentity(nomeTrigger, nomeGrupo)
				    .usingJobData(map)
				    .startNow()
				    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
					    .withIntervalInHours(horas)
					    .repeatForever())
				    .build();

			    sched.scheduleJob(job, trigger);
			}
			else
			{
			    job = sched.getJobDetail(new JobKey(nomeJob, nomeGrupo));

			    trigger = TriggerBuilder.newTrigger().forJob(job)
				    .withIdentity(nomeTrigger, nomeGrupo)
				    .usingJobData(map)
				    .startNow()
				    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
					    .withIntervalInHours(horas)
					    .repeatForever())
				    .build();

			    sched.scheduleJob(trigger);
			}
		    }
		}
	    }
	}
    }

}
